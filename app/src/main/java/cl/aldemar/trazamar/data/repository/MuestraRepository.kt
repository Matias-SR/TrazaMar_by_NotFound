package cl.aldemar.trazamar.data.repository

import android.content.Context
import cl.aldemar.trazamar.data.local.AppDatabase
import cl.aldemar.trazamar.data.local.entities.MuestraEntity
import cl.aldemar.trazamar.data.model.dto.*
import kotlinx.coroutines.flow.Flow
import java.io.File

class MuestraRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.muestraDao()
    private val catalogos = db.catalogoDao()
    private val usuarios = db.usuarioDao()

    fun observarCache(): Flow<List<MuestraEntity>> = dao.observarTodas()

    suspend fun sincronizarHistorial(
        centroId: Int? = null, trenId: Int? = null, lineaId: Int? = null,
        estado: String? = null, operadorId: Int? = null,
        fechaDesde: String? = null, fechaHasta: String? = null,
    ): ResultadoApi<List<MuestraDto>> = try {
        val resultado = dao.obtenerTodasSync().filter { m ->
            (centroId == null || m.centroId == centroId) &&
            (trenId == null || m.trenId == trenId) &&
            (lineaId == null || m.lineaId == lineaId) &&
            (estado.isNullOrBlank() || m.estado.equals(estado, true)) &&
            (operadorId == null || m.operadorId == operadorId) &&
            (fechaDesde.isNullOrBlank() || m.fecha >= fechaDesde) &&
            (fechaHasta.isNullOrBlank() || m.fecha <= fechaHasta)
        }.map { it.aDto() }
        ResultadoApi.Exito(resultado)
    } catch (e: Exception) { ResultadoApi.Error("No se pudo consultar el historial local: ${e.message}") }

    suspend fun obtenerMuestra(id: Int): ResultadoApi<MuestraDto> =
        dao.obtenerPorId(id)?.let { ResultadoApi.Exito(it.aDto()) } ?: ResultadoApi.Error("Muestra no encontrada.")

    suspend fun crearMuestra(request: CrearMuestraRequest): ResultadoApi<MuestraDto> { return try {
        val centro = catalogos.obtenerCentros().firstOrNull { it.id == request.centroId }
        val tren = catalogos.obtenerTrenes().firstOrNull { it.id == request.trenId }
        val linea = catalogos.obtenerLineas().firstOrNull { it.id == request.lineaId }
        if (centro == null || tren == null || linea == null || tren.centroId != centro.id || linea.trenId != tren.id)
            return ResultadoApi.Error("Selecciona un centro, tren y línea válidos.")
        val usuario = usuarios.porId(request.operadorId)
        val id = dao.siguienteId()
        val entity = MuestraEntity(
            id = id, centroId = request.centroId, centroNombre = centro.nombre,
            trenId = request.trenId, trenNombre = tren.nombre, lineaId = request.lineaId,
            lineaNombre = linea.nombre, fecha = request.fecha, hora = request.hora,
            tramoMetros = request.tramoMetros, operadorId = request.operadorId,
            operadorNombre = usuario?.nombreCompleto, conteoIndividuos = request.conteoIndividuos,
            observaciones = request.observaciones, calibrePromedio = request.calibrePromedio,
            calibreMin = request.calibreMin, calibreMax = request.calibreMax,
            pesoMuestra = request.pesoMuestra, fotoUrl = null, estado = "borrador",
            comentarioSupervisor = null, supervisorNombre = null, sincronizada = true,
        )
        dao.insertar(entity)
        ResultadoApi.Exito(entity.aDto())
    } catch (e: Exception) { ResultadoApi.Error("No se pudo guardar la muestra: ${e.message}") } }

    suspend fun actualizarMuestra(id: Int, request: ActualizarMuestraRequest): ResultadoApi<MuestraDto> { return try {
        val old = dao.obtenerPorId(id) ?: return ResultadoApi.Error("No se encontró la muestra local.")
        val updated = old.copy(
            conteoIndividuos = request.conteoIndividuos ?: old.conteoIndividuos,
            observaciones = request.observaciones ?: old.observaciones,
            calibrePromedio = request.calibrePromedio ?: old.calibrePromedio,
            calibreMin = request.calibreMin ?: old.calibreMin,
            calibreMax = request.calibreMax ?: old.calibreMax,
            pesoMuestra = request.pesoMuestra ?: old.pesoMuestra,
            tramoMetros = request.tramoMetros ?: old.tramoMetros,
            sincronizada = true,
        )
        dao.insertar(updated)
        ResultadoApi.Exito(updated.aDto())
    } catch (e: Exception) { ResultadoApi.Error("No se pudo actualizar la muestra: ${e.message}") } }

    suspend fun enviarARevision(id: Int): ResultadoApi<MuestraDto> = cambiarEstadoLocal(id, "pendiente")

    private suspend fun cambiarEstadoLocal(id: Int, nuevoEstado: String): ResultadoApi<MuestraDto> {
        val old = dao.obtenerPorId(id) ?: return ResultadoApi.Error("Muestra no encontrada.")
        val updated = old.copy(estado = nuevoEstado, sincronizada = true)
        dao.insertar(updated)
        return ResultadoApi.Exito(updated.aDto())
    }

    suspend fun subirFoto(id: Int, archivo: File): ResultadoApi<String> {
        val old = dao.obtenerPorId(id) ?: return ResultadoApi.Error("Muestra no encontrada.")
        if (!archivo.exists()) return ResultadoApi.Error("No se encontró el archivo de fotografía.")
        dao.insertar(old.copy(fotoUrl = archivo.absolutePath, sincronizada = true))
        return ResultadoApi.Exito(archivo.absolutePath)
    }

    suspend fun revisar(id: Int, estado: String, comentario: String?): ResultadoApi<MuestraDto> { return try {
        if (estado !in setOf("observada", "corregida", "validada", "pendiente")) return ResultadoApi.Error("Estado de revisión no válido.")
        val old = dao.obtenerPorId(id) ?: return ResultadoApi.Error("Muestra no encontrada.")
        val supervisor = usuarios.listar().firstOrNull { it.rol == "supervisor" || it.rol == "admin" }
        val updated = old.copy(estado = estado, comentarioSupervisor = comentario, supervisorNombre = supervisor?.nombreCompleto, sincronizada = true)
        dao.insertar(updated)
        ResultadoApi.Exito(updated.aDto())
    } catch (e: Exception) { ResultadoApi.Error("No se pudo revisar la muestra: ${e.message}") } }
}

private fun MuestraEntity.aDto() = MuestraDto(
    id = id, centroId = centroId, trenId = trenId, lineaId = lineaId,
    fecha = fecha, hora = hora, tramoMetros = tramoMetros, operadorId = operadorId,
    conteoIndividuos = conteoIndividuos, observaciones = observaciones,
    calibrePromedio = calibrePromedio, calibreMin = calibreMin, calibreMax = calibreMax,
    pesoMuestra = pesoMuestra, fotoUrl = fotoUrl, estado = estado,
    comentarioSupervisor = comentarioSupervisor, supervisorId = null, fechaRevision = null,
    centro = centroNombre?.let { CentroDto(centroId, it, "", true, null) },
    tren = trenNombre?.let { TrenDto(trenId, it, centroId, true, null) },
    linea = lineaNombre?.let { LineaDto(lineaId, it, trenId, true) },
    operador = operadorNombre?.let { UsuarioDto(operadorId, "", it, "operador", true, null) },
    supervisor = supervisorNombre?.let { UsuarioDto(0, "", it, "supervisor", true, null) },
)
