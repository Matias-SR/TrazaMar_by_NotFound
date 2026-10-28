package cl.aldemar.trazamar.data.repository

import android.content.Context
import cl.aldemar.trazamar.data.local.AppDatabase
import cl.aldemar.trazamar.data.local.entities.UsuarioEntity
import cl.aldemar.trazamar.data.model.dto.ActualizarUsuarioRequest
import cl.aldemar.trazamar.data.model.dto.CrearUsuarioRequest
import cl.aldemar.trazamar.data.model.dto.UsuarioDto
import java.time.LocalDateTime

class UsuarioRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.usuarioDao()
    private val auth = AuthRepository(context)

    suspend fun listar(): ResultadoApi<List<UsuarioDto>> = try {
        auth.prepararDatosIniciales()
        ResultadoApi.Exito(dao.listar().map { it.aUsuarioDto() })
    } catch (e: Exception) { ResultadoApi.Error("No se pudo leer los usuarios locales: ${e.message}") }

    suspend fun crear(username: String, password: String, nombreCompleto: String, rol: String): ResultadoApi<UsuarioDto> { return try {
        auth.prepararDatosIniciales()
        val user = username.trim()
        if (user.isBlank() || password.length < 4 || nombreCompleto.isBlank()) return ResultadoApi.Error("Completa los campos y usa una contraseña de al menos 4 caracteres.")
        if (rol !in setOf("operador", "supervisor", "analista", "admin")) return ResultadoApi.Error("Rol no válido.")
        if (dao.porUsername(user) != null) return ResultadoApi.Error("Ese nombre de usuario ya existe.")
        val id = dao.insertar(UsuarioEntity(username = user, nombreCompleto = nombreCompleto.trim(), rol = rol, passwordHash = hashPassword(password), createdAt = LocalDateTime.now().toString())).toInt()
        ResultadoApi.Exito(dao.porId(id)!!.aUsuarioDto())
    } catch (e: Exception) { ResultadoApi.Error("No se pudo crear el usuario: ${e.message}") } }

    suspend fun actualizar(id: Int, request: ActualizarUsuarioRequest): ResultadoApi<UsuarioDto> { return try {
        val old = dao.porId(id) ?: return ResultadoApi.Error("Usuario no encontrado.")
        if (request.rol != null && request.rol !in setOf("operador", "supervisor", "analista", "admin")) return ResultadoApi.Error("Rol no válido.")
        if (request.password != null && request.password.length < 4) return ResultadoApi.Error("La contraseña debe tener al menos 4 caracteres.")
        val updated = old.copy(
            nombreCompleto = request.nombreCompleto?.trim()?.takeIf { it.isNotBlank() } ?: old.nombreCompleto,
            rol = request.rol ?: old.rol,
            passwordHash = request.password?.let(::hashPassword) ?: old.passwordHash,
            activo = request.activo ?: old.activo,
        )
        dao.actualizar(updated)
        ResultadoApi.Exito(updated.aUsuarioDto())
    } catch (e: Exception) { ResultadoApi.Error("No se pudo actualizar el usuario: ${e.message}") } }

    suspend fun desactivar(id: Int): ResultadoApi<Unit> = when (val r = actualizar(id, ActualizarUsuarioRequest(activo = false))) {
        is ResultadoApi.Exito -> ResultadoApi.Exito(Unit)
        is ResultadoApi.Error -> r
    }
}
