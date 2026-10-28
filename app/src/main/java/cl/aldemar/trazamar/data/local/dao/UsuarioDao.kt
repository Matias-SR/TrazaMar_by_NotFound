package cl.aldemar.trazamar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.aldemar.trazamar.data.local.entities.UsuarioEntity

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios ORDER BY nombreCompleto") suspend fun listar(): List<UsuarioEntity>
    @Query("SELECT * FROM usuarios WHERE username = :username COLLATE NOCASE LIMIT 1") suspend fun porUsername(username: String): UsuarioEntity?
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1") suspend fun porId(id: Int): UsuarioEntity?
    @Query("SELECT COUNT(*) FROM usuarios") suspend fun contar(): Int
    @Insert(onConflict = OnConflictStrategy.ABORT) suspend fun insertar(usuario: UsuarioEntity): Long
    @Update suspend fun actualizar(usuario: UsuarioEntity)
}
