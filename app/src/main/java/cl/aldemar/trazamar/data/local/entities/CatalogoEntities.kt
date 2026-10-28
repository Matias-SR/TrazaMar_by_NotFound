package cl.aldemar.trazamar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "centros_cache")
data class CentroEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val codigo: String,
    val activo: Boolean = true,
)

@Entity(tableName = "trenes_cache")
data class TrenEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val centroId: Int,
    val activo: Boolean = true,
)

@Entity(tableName = "lineas_cache")
data class LineaEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val trenId: Int,
    val activo: Boolean = true,
)
