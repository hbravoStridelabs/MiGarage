package com.migarage.data.local.db.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.migarage.data.local.db.entity.AlertEntity;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AlertDao_Impl implements AlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlertEntity> __insertionAdapterOfAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsResolved;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteResolvedAlerts;

  public AlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlertEntity = new EntityInsertionAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alerts` (`id`,`type`,`title`,`message`,`dueDate`,`dueMileage`,`relatedDocumentId`,`relatedMaintenanceId`,`isResolved`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getMessage());
        if (entity.getDueDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDueDate());
        }
        if (entity.getDueMileage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDueMileage());
        }
        if (entity.getRelatedDocumentId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRelatedDocumentId());
        }
        if (entity.getRelatedMaintenanceId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRelatedMaintenanceId());
        }
        final int _tmp = entity.isResolved() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfMarkAsResolved = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alerts SET isResolved = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alerts WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteResolvedAlerts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alerts WHERE isResolved = 1";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAlertEntity.insert(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsResolved(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsResolved.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsResolved.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteResolvedAlerts(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteResolvedAlerts.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteResolvedAlerts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AlertEntity>> getActiveAlerts() {
    final String _sql = "SELECT * FROM alerts WHERE isResolved = 0 ORDER BY dueDate ASC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMileage");
          final int _cursorIndexOfRelatedDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedDocumentId");
          final int _cursorIndexOfRelatedMaintenanceId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedMaintenanceId");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Integer _tmpDueMileage;
            if (_cursor.isNull(_cursorIndexOfDueMileage)) {
              _tmpDueMileage = null;
            } else {
              _tmpDueMileage = _cursor.getInt(_cursorIndexOfDueMileage);
            }
            final String _tmpRelatedDocumentId;
            if (_cursor.isNull(_cursorIndexOfRelatedDocumentId)) {
              _tmpRelatedDocumentId = null;
            } else {
              _tmpRelatedDocumentId = _cursor.getString(_cursorIndexOfRelatedDocumentId);
            }
            final String _tmpRelatedMaintenanceId;
            if (_cursor.isNull(_cursorIndexOfRelatedMaintenanceId)) {
              _tmpRelatedMaintenanceId = null;
            } else {
              _tmpRelatedMaintenanceId = _cursor.getString(_cursorIndexOfRelatedMaintenanceId);
            }
            final boolean _tmpIsResolved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTitle,_tmpMessage,_tmpDueDate,_tmpDueMileage,_tmpRelatedDocumentId,_tmpRelatedMaintenanceId,_tmpIsResolved,_tmpCreatedAt);
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
  public Flow<List<AlertEntity>> getResolvedAlerts() {
    final String _sql = "SELECT * FROM alerts WHERE isResolved = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMileage");
          final int _cursorIndexOfRelatedDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedDocumentId");
          final int _cursorIndexOfRelatedMaintenanceId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedMaintenanceId");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Integer _tmpDueMileage;
            if (_cursor.isNull(_cursorIndexOfDueMileage)) {
              _tmpDueMileage = null;
            } else {
              _tmpDueMileage = _cursor.getInt(_cursorIndexOfDueMileage);
            }
            final String _tmpRelatedDocumentId;
            if (_cursor.isNull(_cursorIndexOfRelatedDocumentId)) {
              _tmpRelatedDocumentId = null;
            } else {
              _tmpRelatedDocumentId = _cursor.getString(_cursorIndexOfRelatedDocumentId);
            }
            final String _tmpRelatedMaintenanceId;
            if (_cursor.isNull(_cursorIndexOfRelatedMaintenanceId)) {
              _tmpRelatedMaintenanceId = null;
            } else {
              _tmpRelatedMaintenanceId = _cursor.getString(_cursorIndexOfRelatedMaintenanceId);
            }
            final boolean _tmpIsResolved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTitle,_tmpMessage,_tmpDueDate,_tmpDueMileage,_tmpRelatedDocumentId,_tmpRelatedMaintenanceId,_tmpIsResolved,_tmpCreatedAt);
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
  public Flow<AlertEntity> getAlertById(final String id) {
    final String _sql = "SELECT * FROM alerts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<AlertEntity>() {
      @Override
      @Nullable
      public AlertEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMileage");
          final int _cursorIndexOfRelatedDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedDocumentId");
          final int _cursorIndexOfRelatedMaintenanceId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedMaintenanceId");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final AlertEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Integer _tmpDueMileage;
            if (_cursor.isNull(_cursorIndexOfDueMileage)) {
              _tmpDueMileage = null;
            } else {
              _tmpDueMileage = _cursor.getInt(_cursorIndexOfDueMileage);
            }
            final String _tmpRelatedDocumentId;
            if (_cursor.isNull(_cursorIndexOfRelatedDocumentId)) {
              _tmpRelatedDocumentId = null;
            } else {
              _tmpRelatedDocumentId = _cursor.getString(_cursorIndexOfRelatedDocumentId);
            }
            final String _tmpRelatedMaintenanceId;
            if (_cursor.isNull(_cursorIndexOfRelatedMaintenanceId)) {
              _tmpRelatedMaintenanceId = null;
            } else {
              _tmpRelatedMaintenanceId = _cursor.getString(_cursorIndexOfRelatedMaintenanceId);
            }
            final boolean _tmpIsResolved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new AlertEntity(_tmpId,_tmpType,_tmpTitle,_tmpMessage,_tmpDueDate,_tmpDueMileage,_tmpRelatedDocumentId,_tmpRelatedMaintenanceId,_tmpIsResolved,_tmpCreatedAt);
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
