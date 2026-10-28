package cl.aldemar.trazamar.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.aldemar.trazamar.data.local.entities.CentroEntity;
import cl.aldemar.trazamar.data.local.entities.LineaEntity;
import cl.aldemar.trazamar.data.local.entities.TrenEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CatalogoDao_Impl implements CatalogoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CentroEntity> __insertionAdapterOfCentroEntity;

  private final EntityInsertionAdapter<TrenEntity> __insertionAdapterOfTrenEntity;

  private final EntityInsertionAdapter<LineaEntity> __insertionAdapterOfLineaEntity;

  public CatalogoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCentroEntity = new EntityInsertionAdapter<CentroEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `centros_cache` (`id`,`nombre`,`codigo`,`activo`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CentroEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getCodigo());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
    this.__insertionAdapterOfTrenEntity = new EntityInsertionAdapter<TrenEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trenes_cache` (`id`,`nombre`,`centroId`,`activo`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrenEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindLong(3, entity.getCentroId());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
    this.__insertionAdapterOfLineaEntity = new EntityInsertionAdapter<LineaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `lineas_cache` (`id`,`nombre`,`trenId`,`activo`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LineaEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindLong(3, entity.getTrenId());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
  }

  @Override
  public Object insertarCentros(final List<CentroEntity> centros,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCentroEntity.insert(centros);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarCentro(final CentroEntity centro,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCentroEntity.insert(centro);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarTrenes(final List<TrenEntity> trenes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrenEntity.insert(trenes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarTren(final TrenEntity tren, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrenEntity.insert(tren);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarLineas(final List<LineaEntity> lineas,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLineaEntity.insert(lineas);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarLinea(final LineaEntity linea,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLineaEntity.insert(linea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CentroEntity>> observarCentros() {
    final String _sql = "SELECT * FROM centros_cache WHERE activo = 1 ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"centros_cache"}, new Callable<List<CentroEntity>>() {
      @Override
      @NonNull
      public List<CentroEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCodigo = CursorUtil.getColumnIndexOrThrow(_cursor, "codigo");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<CentroEntity> _result = new ArrayList<CentroEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CentroEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpCodigo;
            _tmpCodigo = _cursor.getString(_cursorIndexOfCodigo);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new CentroEntity(_tmpId,_tmpNombre,_tmpCodigo,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerCentros(final Continuation<? super List<CentroEntity>> $completion) {
    final String _sql = "SELECT * FROM centros_cache ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CentroEntity>>() {
      @Override
      @NonNull
      public List<CentroEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCodigo = CursorUtil.getColumnIndexOrThrow(_cursor, "codigo");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<CentroEntity> _result = new ArrayList<CentroEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CentroEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpCodigo;
            _tmpCodigo = _cursor.getString(_cursorIndexOfCodigo);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new CentroEntity(_tmpId,_tmpNombre,_tmpCodigo,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TrenEntity>> observarTrenes(final int centroId) {
    final String _sql = "SELECT * FROM trenes_cache WHERE centroId = ? AND activo = 1 ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, centroId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trenes_cache"}, new Callable<List<TrenEntity>>() {
      @Override
      @NonNull
      public List<TrenEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCentroId = CursorUtil.getColumnIndexOrThrow(_cursor, "centroId");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<TrenEntity> _result = new ArrayList<TrenEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrenEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpCentroId;
            _tmpCentroId = _cursor.getInt(_cursorIndexOfCentroId);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new TrenEntity(_tmpId,_tmpNombre,_tmpCentroId,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LineaEntity>> observarLineas(final int trenId) {
    final String _sql = "SELECT * FROM lineas_cache WHERE trenId = ? AND activo = 1 ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, trenId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lineas_cache"}, new Callable<List<LineaEntity>>() {
      @Override
      @NonNull
      public List<LineaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfTrenId = CursorUtil.getColumnIndexOrThrow(_cursor, "trenId");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<LineaEntity> _result = new ArrayList<LineaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LineaEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpTrenId;
            _tmpTrenId = _cursor.getInt(_cursorIndexOfTrenId);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new LineaEntity(_tmpId,_tmpNombre,_tmpTrenId,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerTrenes(final Continuation<? super List<TrenEntity>> $completion) {
    final String _sql = "SELECT * FROM trenes_cache ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TrenEntity>>() {
      @Override
      @NonNull
      public List<TrenEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCentroId = CursorUtil.getColumnIndexOrThrow(_cursor, "centroId");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<TrenEntity> _result = new ArrayList<TrenEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrenEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpCentroId;
            _tmpCentroId = _cursor.getInt(_cursorIndexOfCentroId);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new TrenEntity(_tmpId,_tmpNombre,_tmpCentroId,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerLineas(final Continuation<? super List<LineaEntity>> $completion) {
    final String _sql = "SELECT * FROM lineas_cache ORDER BY nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<LineaEntity>>() {
      @Override
      @NonNull
      public List<LineaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfTrenId = CursorUtil.getColumnIndexOrThrow(_cursor, "trenId");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<LineaEntity> _result = new ArrayList<LineaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LineaEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpTrenId;
            _tmpTrenId = _cursor.getInt(_cursorIndexOfTrenId);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new LineaEntity(_tmpId,_tmpNombre,_tmpTrenId,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object siguienteCentroId(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM centros_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object siguienteTrenId(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM trenes_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object siguienteLineaId(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM lineas_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarCentros(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM centros_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
