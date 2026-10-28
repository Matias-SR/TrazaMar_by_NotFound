package cl.aldemar.trazamar.data.repository

import android.content.Context
import cl.aldemar.trazamar.data.local.AppDatabase
import cl.aldemar.trazamar.data.local.entities.UsuarioEntity
import cl.aldemar.trazamar.data.session.SessionManager
import cl.aldemar.trazamar.data.session.SesionUsuario
import cl.aldemar.trazamar.data.model.dto.UsuarioDto
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import java.time.LocalDateTime

sealed class ResultadoApi<out T> {
    data class Exito<T>(val datos: T) : ResultadoApi<T>()
    data class Error(val mensaje: String) : ResultadoApi<Nothing>()
}

internal fun hashPassword(password: String): String = MessageDigest.getInstance("SHA-256")
    .digest(password.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }

internal fun UsuarioEntity.aUsuarioDto() = UsuarioDto(id, username, nombreCompleto, rol, activo, createdAt)

class AuthRepository(private val context: Context) {
    private val dao = AppDatabase.getInstance(context).usuarioDao()
    private val session = SessionManager(context)

    suspend fun prepararDatosIniciales() {
        if (dao.contar() == 0) {
            val now = LocalDateTime.now().toString()
            listOf(
                Triple("OP-01", "Operador Uno", "operador"),
                Triple("OP-02", "Operador Dos", "operador"),
                Triple("SUP-01", "Supervisor Uno", "supervisor"),
                Triple("AN-01", "Analista Uno", "analista"),
                Triple("ADMIN-01", "Administrador Uno", "admin"),
            ).forEach { (username, nombre, rol) ->
                dao.insertar(UsuarioEntity(username = username, nombreCompleto = nombre, rol = rol, passwordHash = hashPassword("1234"), createdAt = now))
            }
        }
    }

    suspend fun login(username: String, password: String): ResultadoApi<String> {
        prepararDatosIniciales()
        if (username.isBlank() || password.isBlank()) return ResultadoApi.Error("Ingresa usuario y contraseña.")
        val usuario = dao.porUsername(username.trim())
        if (usuario == null || !usuario.activo || usuario.passwordHash != hashPassword(password)) {
            return ResultadoApi.Error("Usuario o contraseña incorrectos, o cuenta desactivada.")
        }
        session.guardarSesion("local_${usuario.id}", usuario.id, usuario.username, usuario.nombreCompleto, usuario.rol)
        return ResultadoApi.Exito(usuario.rol)
    }

    suspend fun cerrarSesion() = session.cerrarSesion()
    val sesion: Flow<SesionUsuario> get() = session.sesion
}
