package com.android.zj.ai.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "PHONE_DATA".
 */
public class PhoneDataDao extends AbstractDao<PhoneData, Long> {

    public static final String TABLENAME = "PHONE_DATA";

    /**
     * Properties of entity TestData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {

        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property height = new Property(1, String.class, "height", false, "height");
        public final static Property width = new Property(2, String.class, "width", false, "width");
        public final static Property keyword = new Property(3, String.class, "keyword", false, "keyword");
        public final static Property count = new Property(4, String.class, "count", false, "count");
        public final static Property brand = new Property(5, String.class, "brand", false, "brand");
        public final static Property version = new Property(6, String.class, "version", false, "version");
        public final static Property tac = new Property(7, String.class, "tac", false, "tac");
        public final static Property name = new Property(8, String.class, "name", false, "name");
    }


    public PhoneDataDao(DaoConfig config) {
        super(config);
    }

    public PhoneDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"PHONE_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"height\" TEXT," + // 1: testString
                "\"width\" TEXT," + // 2: testLong
                "\"keyword\" TEXT," + // 3: testDate
                "\"count\" TEXT," + // 4: testInt
                "\"brand\" TEXT," + // 5: testInt
                "\"version\" TEXT," + // 6: testInt
                "\"tac\" TEXT," + // 7: testInt
                "\"name\" TEXT);"); // 8: testBoolean
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PHONE_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PhoneData entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        String height = entity.getHeight();
        if (height != null) {
            stmt.bindString(2, height);
        }
        String width = entity.getWidth();
        if (width != null) {
            stmt.bindString(3, width);
        }
        String keyword = entity.getKeyword();
        if (keyword != null) {
            stmt.bindString(4, keyword);
        }
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(5, count);
        }
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(6, brand);
        }
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(7, version);
        }
        String tac = entity.getTac();
        if (tac != null) {
            stmt.bindString(8, tac);
        }
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(9, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PhoneData entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        String height = entity.getHeight();
        if (height != null) {
            stmt.bindString(2, height);
        }
        String width = entity.getWidth();
        if (width != null) {
            stmt.bindString(3, width);
        }
        String keyword = entity.getKeyword();
        if (keyword != null) {
            stmt.bindString(4, keyword);
        }
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(5, count);
        }
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(6, brand);
        }
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(7, version);
        }
        String tac = entity.getTac();
        if (tac != null) {
            stmt.bindString(8, tac);
        }
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(9, name);
        }

//        Boolean testBoolean = entity.getTestBoolean();
//        if (testBoolean != null) {
//            stmt.bindLong(6, testBoolean ? 1L : 0L);
//        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public PhoneData readEntity(Cursor cursor, int offset) {
        PhoneData entity = new PhoneData(
                //cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0),
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4),
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5),
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6),
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7)
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, PhoneData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setHeight(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWidth(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setKeyword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCount(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBrand(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setVersion(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTac(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    @Override
    protected final Long updateKeyAfterInsert(PhoneData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(PhoneData entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PhoneData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
