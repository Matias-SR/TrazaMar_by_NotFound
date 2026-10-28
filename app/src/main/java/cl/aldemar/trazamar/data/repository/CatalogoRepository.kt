package cl.aldemar.trazamar.data.repository

import android.content.Context
import androidx.room.withTransaction
import cl.aldemar.trazamar.data.local.AppDatabase
import cl.aldemar.trazamar.data.local.entities.CentroEntity
import cl.aldemar.trazamar.data.local.entities.TrenEntity
import cl.aldemar.trazamar.data.local.entities.LineaEntity
import cl.aldemar.trazamar.data.model.dto.*

class CatalogoRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.catalogoDao()

    suspend fun obtenerCentros(): ResultadoApi<List<CentroDto>> = try {
        asegurarCatalogoInicial()
        ResultadoApi.Exito(dao.obtenerCentros().filter { it.activo }.map { c ->
            val trenes = dao.obtenerTrenes().filter { it.centroId == c.id && it.activo }.map { t ->
                TrenDto(t.id, t.nombre, t.centroId, t.activo, dao.obtenerLineas().filter { it.trenId == t.id && it.activo }.map { l -> LineaDto(l.id, l.nombre, l.trenId, l.activo) })
            }
            CentroDto(c.id, c.nombre, c.codigo, c.activo, trenes)
        })
    } catch (e: Exception) { ResultadoApi.Error("No se pudieron cargar los catálogos locales: ${e.message}") }

    private suspend fun asegurarCatalogoInicial() {
        if (dao.contarCentros() != 0) return
        db.withTransaction {
            if (dao.contarCentros() == 0) {
                dao.insertarCentro(CentroEntity(1, "Centro Demo", "CD-01"))
                dao.insertarTren(TrenEntity(1, "Tren 1", 1))
                dao.insertarLinea(LineaEntity(1, "Línea 1", 1))
                dao.insertarLinea(LineaEntity(2, "Línea 2", 1))
            }
        }
    }

    suspend fun crearCentro(nombre: String, codigo: String): ResultadoApi<CentroDto> { return try {
        if (nombre.isBlank() || codigo.isBlank()) return ResultadoApi.Error("Nombre y código son obligatorios.")
        val id = dao.siguienteCentroId()
        dao.insertarCentro(CentroEntity(id, nombre.trim(), codigo.trim()))
        ResultadoApi.Exito(CentroDto(id, nombre.trim(), codigo.trim(), true, emptyList()))
    } catch (e: Exception) { ResultadoApi.Error("No se pudo crear el centro: ${e.message}") } }

    suspend fun crearTren(nombre: String, centroId: Int): ResultadoApi<TrenDto> { return try {
        if (nombre.isBlank()) return ResultadoApi.Error("El nombre del tren es obligatorio.")
        if (dao.obtenerCentros().none { it.id == centroId && it.activo }) return ResultadoApi.Error("El centro seleccionado no existe.")
        val id = dao.siguienteTrenId()
        dao.insertarTren(TrenEntity(id, nombre.trim(), centroId))
        ResultadoApi.Exito(TrenDto(id, nombre.trim(), centroId, true, emptyList()))
    } catch (e: Exception) { ResultadoApi.Error("No se pudo crear el tren: ${e.message}") } }

    suspend fun crearLinea(nombre: String, trenId: Int): ResultadoApi<LineaDto> { return try {
        if (nombre.isBlank()) return ResultadoApi.Error("El nombre de la línea es obligatorio.")
        if (dao.obtenerTrenes().none { it.id == trenId && it.activo }) return ResultadoApi.Error("El tren seleccionado no existe.")
        val id = dao.siguienteLineaId()
        dao.insertarLinea(LineaEntity(id, nombre.trim(), trenId))
        ResultadoApi.Exito(LineaDto(id, nombre.trim(), trenId, true))
    } catch (e: Exception) { ResultadoApi.Error("No se pudo crear la línea: ${e.message}") } }
}
