package com.vision.financiera.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vision.financiera.model.Transaccion;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TransaccionDao_Impl implements TransaccionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Transaccion> __insertionAdapterOfTransaccion;

  public TransaccionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransaccion = new EntityInsertionAdapter<Transaccion>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Transaccion` (`id`,`descripcion`,`monto`,`categoria`,`tipo`,`fecha`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Transaccion entity) {
        statement.bindLong(1, entity.id);
        if (entity.descripcion == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.descripcion);
        }
        statement.bindDouble(3, entity.monto);
        if (entity.categoria == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.categoria);
        }
        if (entity.tipo == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.tipo);
        }
        final Long _tmp = Converters.dateToTimestamp(entity.fecha);
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
      }
    };
  }

  @Override
  public void insertar(final Transaccion transaccion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTransaccion.insert(transaccion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Transaccion> obtenerTodas() {
    final String _sql = "SELECT * FROM transaccion ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
      final int _cursorIndexOfMonto = CursorUtil.getColumnIndexOrThrow(_cursor, "monto");
      final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
      final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
      final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
      final List<Transaccion> _result = new ArrayList<Transaccion>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Transaccion _item;
        _item = new Transaccion();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDescripcion)) {
          _item.descripcion = null;
        } else {
          _item.descripcion = _cursor.getString(_cursorIndexOfDescripcion);
        }
        _item.monto = _cursor.getDouble(_cursorIndexOfMonto);
        if (_cursor.isNull(_cursorIndexOfCategoria)) {
          _item.categoria = null;
        } else {
          _item.categoria = _cursor.getString(_cursorIndexOfCategoria);
        }
        if (_cursor.isNull(_cursorIndexOfTipo)) {
          _item.tipo = null;
        } else {
          _item.tipo = _cursor.getString(_cursorIndexOfTipo);
        }
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfFecha)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfFecha);
        }
        _item.fecha = Converters.fromTimestamp(_tmp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double obtenerTotalIngresos() {
    final String _sql = "SELECT SUM(monto) FROM transaccion WHERE tipo = 'Ingreso'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double obtenerTotalGastos() {
    final String _sql = "SELECT SUM(monto) FROM transaccion WHERE tipo = 'Gasto'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
