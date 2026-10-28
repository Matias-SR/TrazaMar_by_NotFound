package cl.aldemar.trazamar.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.aldemar.trazamar.data.local.entities.UsuarioEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UsuarioDao_Impl implements UsuarioDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UsuarioEntity> __insertionAdapterOfUsuarioEntity;

  private final EntityDeletionOrUpdateAdapter<UsuarioEntity> __updateAdapterOfUsuarioEntity;

  public UsuarioDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuarioEntity = new EntityInsertionAdapter<UsuarioEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `usuarios` (`id`,`username`,`nombreCompleto`,`rol`,`passwordHash`,`activo`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UsuarioEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getNombreCompleto());
        statement.bindString(4, entity.getRol());
        statement.bindString(5, entity.getPasswordHash());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindString(7, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfUsuarioEntity = new EntityDeletionOrUpdateAdapter<UsuarioEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `usuarios` SET `id` = ?,`username` = ?,`nombreCompleto` = ?,`rol` = ?,`passwordHash` = ?,`activo` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UsuarioEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getNombreCompleto());
        statement.bindString(4, entity.getRol());
        statement.bindString(5, entity.getPasswordHash());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindString(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertar(final UsuarioEntity usuario,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUsuarioEntity.insertAndReturnId(usuario);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizar(final UsuarioEntity usuario,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUsuarioEntity.handle(usuario);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object listar(final Continuation<? super List<UsuarioEntity>> $completion) {
    final String _sql = "SELECT * FROM usuarios ORDER BY nombreCompleto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UsuarioEntity>>() {
      @Override
      @NonNull
      public List<UsuarioEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfNombreCompleto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreCompleto");
          final int _cursorIndexOfRol = CursorUtil.getColumnIndexOrThrow(_cursor, "rol");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<UsuarioEntity> _result = new ArrayList<UsuarioEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UsuarioEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpNombreCompleto;
            _tmpNombreCompleto = _cursor.getString(_cursorIndexOfNombreCompleto);
            final String _tmpRol;
            _tmpRol = _cursor.getString(_cursorIndexOfRol);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            _item = new UsuarioEntity(_tmpId,_tmpUsername,_tmpNombreCompleto,_tmpRol,_tmpPasswordHash,_tmpActivo,_tmpCreatedAt);
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
  public Object porUsername(final String username,
      final Continuation<? super UsuarioEntity> $completion) {
    final String _sql = "SELECT * FROM usuarios WHERE username = ? COLLATE NOCASE LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, username);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UsuarioEntity>() {
      @Override
      @Nullable
      public UsuarioEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfNombreCompleto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreCompleto");
          final int _cursorIndexOfRol = CursorUtil.getColumnIndexOrThrow(_cursor, "rol");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UsuarioEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpNombreCompleto;
            _tmpNombreCompleto = _cursor.getString(_cursorIndexOfNombreCompleto);
            final String _tmpRol;
            _tmpRol = _cursor.getString(_cursorIndexOfRol);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            _result = new UsuarioEntity(_tmpId,_tmpUsername,_tmpNombreCompleto,_tmpRol,_tmpPasswordHash,_tmpActivo,_tmpCreatedAt);
          } else {
            _result = null;
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
  public Object porId(final int id, final Continuation<? super UsuarioEntity> $completion) {
    final String _sql = "SELECT * FROM usuarios WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UsuarioEntity>() {
      @Override
      @Nullable
      public UsuarioEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfNombreCompleto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreCompleto");
          final int _cursorIndexOfRol = CursorUtil.getColumnIndexOrThrow(_cursor, "rol");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UsuarioEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpNombreCompleto;
            _tmpNombreCompleto = _cursor.getString(_cursorIndexOfNombreCompleto);
            final String _tmpRol;
            _tmpRol = _cursor.getString(_cursorIndexOfRol);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            _result = new UsuarioEntity(_tmpId,_tmpUsername,_tmpNombreCompleto,_tmpRol,_tmpPasswordHash,_tmpActivo,_tmpCreatedAt);
          } else {
            _result = null;
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
  public Object contar(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM usuarios";
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
