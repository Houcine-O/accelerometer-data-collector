package com.example.myapplication;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class DaoAccess_Impl implements DaoAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRecolt;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRecolt;

  public DaoAccess_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecolt = new EntityInsertionAdapter<Recolt>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `recolts`(`a`,`laltitude`,`longitude`,`speed`,`speedAcc`,`direction`,`directAcc`,`vilocity`,`x`,`y`,`z`,`mark`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recolt value) {
        stmt.bindLong(1, value.a);
        stmt.bindDouble(2, value.getLaltitude());
        stmt.bindDouble(3, value.getLongitude());
        stmt.bindDouble(4, value.getSpeed());
        stmt.bindDouble(5, value.getSpeedAcc());
        stmt.bindDouble(6, value.getDirection());
        stmt.bindDouble(7, value.getDirectAcc());
        stmt.bindDouble(8, value.getVilocity());
        stmt.bindDouble(9, value.getX());
        stmt.bindDouble(10, value.getY());
        stmt.bindDouble(11, value.getZ());
        final int _tmp;
        _tmp = value.isMarkable() ? 1 : 0;
        stmt.bindLong(12, _tmp);
      }
    };
    this.__deletionAdapterOfRecolt = new EntityDeletionOrUpdateAdapter<Recolt>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `recolts` WHERE `a` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recolt value) {
        stmt.bindLong(1, value.a);
      }
    };
  }

  @Override
  public void insertRacolt(Recolt... donnees) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecolt.insert(donnees);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteRacolt(Recolt donnees) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfRecolt.handle(donnees);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Recolt> loadalldatas() {
    final String _sql = "SELECT * FROM recolts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfA = _cursor.getColumnIndexOrThrow("a");
      final int _cursorIndexOfLaltitude = _cursor.getColumnIndexOrThrow("laltitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final int _cursorIndexOfSpeed = _cursor.getColumnIndexOrThrow("speed");
      final int _cursorIndexOfSpeedAcc = _cursor.getColumnIndexOrThrow("speedAcc");
      final int _cursorIndexOfDirection = _cursor.getColumnIndexOrThrow("direction");
      final int _cursorIndexOfDirectAcc = _cursor.getColumnIndexOrThrow("directAcc");
      final int _cursorIndexOfVilocity = _cursor.getColumnIndexOrThrow("vilocity");
      final int _cursorIndexOfX = _cursor.getColumnIndexOrThrow("x");
      final int _cursorIndexOfY = _cursor.getColumnIndexOrThrow("y");
      final int _cursorIndexOfZ = _cursor.getColumnIndexOrThrow("z");
      final int _cursorIndexOfMarkable = _cursor.getColumnIndexOrThrow("mark");
      final List<Recolt> _result = new ArrayList<Recolt>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Recolt _item;
        final double _tmpLaltitude;
        _tmpLaltitude = _cursor.getDouble(_cursorIndexOfLaltitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final double _tmpSpeed;
        _tmpSpeed = _cursor.getDouble(_cursorIndexOfSpeed);
        final double _tmpSpeedAcc;
        _tmpSpeedAcc = _cursor.getDouble(_cursorIndexOfSpeedAcc);
        final double _tmpDirection;
        _tmpDirection = _cursor.getDouble(_cursorIndexOfDirection);
        final double _tmpDirectAcc;
        _tmpDirectAcc = _cursor.getDouble(_cursorIndexOfDirectAcc);
        final double _tmpVilocity;
        _tmpVilocity = _cursor.getDouble(_cursorIndexOfVilocity);
        final double _tmpX;
        _tmpX = _cursor.getDouble(_cursorIndexOfX);
        final double _tmpY;
        _tmpY = _cursor.getDouble(_cursorIndexOfY);
        final double _tmpZ;
        _tmpZ = _cursor.getDouble(_cursorIndexOfZ);
        final boolean _tmpMarkable;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfMarkable);
        _tmpMarkable = _tmp != 0;
        _item = new Recolt(_tmpLaltitude,_tmpLongitude,_tmpSpeed,_tmpSpeedAcc,_tmpDirection,_tmpDirectAcc,_tmpVilocity,_tmpX,_tmpY,_tmpZ,_tmpMarkable);
        _item.a = _cursor.getInt(_cursorIndexOfA);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
