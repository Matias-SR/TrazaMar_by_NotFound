package cl.aldemar.trazamar.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios", indices = [Index(value = ["username"], unique = true)])
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val nombreCompleto: String,
    val rol: String,
    val passwordHash: String,
    val activo: Boolean = true,
    val createdAt: String,
)
