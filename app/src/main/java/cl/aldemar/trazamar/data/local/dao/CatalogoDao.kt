package cl.aldemar.trazamar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.aldemar.trazamar.data.local.entities.CentroEntity
import cl.aldemar.trazamar.data.local.entities.LineaEntity
import cl.aldemar.trazamar.data.local.entities.TrenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogoDao {
    @Query("SELECT * FROM centros_cache WHERE activo = 1 ORDER BY nombre") fun observarCentros(): Flow<List<CentroEntity>>
    @Query("SELECT * FROM centros_cache ORDER BY nombre") suspend fun obtenerCentros(): List<CentroEntity>
    @Query("SELECT * FROM trenes_cache WHERE centroId = :centroId AND activo = 1 ORDER BY nombre") fun observarTrenes(centroId: Int): Flow<List<TrenEntity>>
    @Query("SELECT * FROM lineas_cache WHERE trenId = :trenId AND activo = 1 ORDER BY nombre") fun observarLineas(trenId: Int): Flow<List<LineaEntity>>
    @Query("SELECT * FROM trenes_cache ORDER BY nombre") suspend fun obtenerTrenes(): List<TrenEntity>
    @Query("SELECT * FROM lineas_cache ORDER BY nombre") suspend fun obtenerLineas(): List<LineaEntity>
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM centros_cache") suspend fun siguienteCentroId(): Int
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM trenes_cache") suspend fun siguienteTrenId(): Int
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM lineas_cache") suspend fun siguienteLineaId(): Int
    @Query("SELECT COUNT(*) FROM centros_cache") suspend fun contarCentros(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarCentros(centros: List<CentroEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarCentro(centro: CentroEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarTrenes(trenes: List<TrenEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarTren(tren: TrenEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarLineas(lineas: List<LineaEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertarLinea(linea: LineaEntity)
}
