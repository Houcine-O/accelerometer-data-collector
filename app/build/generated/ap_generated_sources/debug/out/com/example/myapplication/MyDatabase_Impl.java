package com.example.myapplication;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MyDatabase_Impl extends MyDatabase {
  private volatile DaoAccess _daoAccess;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(8) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `recolts` (`a` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `laltitude` REAL NOT NULL, `longitude` REAL NOT NULL, `speed` REAL NOT NULL, `speedAcc` REAL NOT NULL, `direction` REAL NOT NULL, `directAcc` REAL NOT NULL, `vilocity` REAL NOT NULL, `x` REAL NOT NULL, `y` REAL NOT NULL, `z` REAL NOT NULL, `mark` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"c0541f48372d53c477a46e68d029b54e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `recolts`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRecolts = new HashMap<String, TableInfo.Column>(12);
        _columnsRecolts.put("a", new TableInfo.Column("a", "INTEGER", true, 1));
        _columnsRecolts.put("laltitude", new TableInfo.Column("laltitude", "REAL", true, 0));
        _columnsRecolts.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        _columnsRecolts.put("speed", new TableInfo.Column("speed", "REAL", true, 0));
        _columnsRecolts.put("speedAcc", new TableInfo.Column("speedAcc", "REAL", true, 0));
        _columnsRecolts.put("direction", new TableInfo.Column("direction", "REAL", true, 0));
        _columnsRecolts.put("directAcc", new TableInfo.Column("directAcc", "REAL", true, 0));
        _columnsRecolts.put("vilocity", new TableInfo.Column("vilocity", "REAL", true, 0));
        _columnsRecolts.put("x", new TableInfo.Column("x", "REAL", true, 0));
        _columnsRecolts.put("y", new TableInfo.Column("y", "REAL", true, 0));
        _columnsRecolts.put("z", new TableInfo.Column("z", "REAL", true, 0));
        _columnsRecolts.put("mark", new TableInfo.Column("mark", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecolts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecolts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecolts = new TableInfo("recolts", _columnsRecolts, _foreignKeysRecolts, _indicesRecolts);
        final TableInfo _existingRecolts = TableInfo.read(_db, "recolts");
        if (! _infoRecolts.equals(_existingRecolts)) {
          throw new IllegalStateException("Migration didn't properly handle recolts(com.example.myapplication.Recolt).\n"
                  + " Expected:\n" + _infoRecolts + "\n"
                  + " Found:\n" + _existingRecolts);
        }
      }
    }, "c0541f48372d53c477a46e68d029b54e", "1e70a7b7d85572a47ba9ebe1c115ed79");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "recolts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `recolts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  DaoAccess daoAccess() {
    if (_daoAccess != null) {
      return _daoAccess;
    } else {
      synchronized(this) {
        if(_daoAccess == null) {
          _daoAccess = new DaoAccess_Impl(this);
        }
        return _daoAccess;
      }
    }
  }
}
