package com.mp.android.apps.monke.monkeybook.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mp.android.apps.monke.monkeybook.bean.BookContentBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK_CONTENT_BEAN".
*/
public class BookContentBeanDao extends AbstractDao<BookContentBean, String> {

    public static final String TABLENAME = "BOOK_CONTENT_BEAN";

    /**
     * Properties of entity BookContentBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property DurChapterUrl = new Property(0, String.class, "durChapterUrl", true, "DUR_CHAPTER_URL");
        public final static Property DurChapterIndex = new Property(1, int.class, "durChapterIndex", false, "DUR_CHAPTER_INDEX");
        public final static Property DurCapterContent = new Property(2, String.class, "durCapterContent", false, "DUR_CAPTER_CONTENT");
        public final static Property Tag = new Property(3, String.class, "tag", false, "TAG");
    };


    public BookContentBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BookContentBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK_CONTENT_BEAN\" (" + //
                "\"DUR_CHAPTER_URL\" TEXT PRIMARY KEY NOT NULL ," + // 0: durChapterUrl
                "\"DUR_CHAPTER_INDEX\" INTEGER NOT NULL ," + // 1: durChapterIndex
                "\"DUR_CAPTER_CONTENT\" TEXT," + // 2: durCapterContent
                "\"TAG\" TEXT);"); // 3: tag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_CONTENT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookContentBean entity) {
        stmt.clearBindings();
 
        String durChapterUrl = entity.getDurChapterUrl();
        if (durChapterUrl != null) {
            stmt.bindString(1, durChapterUrl);
        }
        stmt.bindLong(2, entity.getDurChapterIndex());
 
        String durCapterContent = entity.getDurCapterContent();
        if (durCapterContent != null) {
            stmt.bindString(3, durCapterContent);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(4, tag);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookContentBean entity) {
        stmt.clearBindings();
 
        String durChapterUrl = entity.getDurChapterUrl();
        if (durChapterUrl != null) {
            stmt.bindString(1, durChapterUrl);
        }
        stmt.bindLong(2, entity.getDurChapterIndex());
 
        String durCapterContent = entity.getDurCapterContent();
        if (durCapterContent != null) {
            stmt.bindString(3, durCapterContent);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(4, tag);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BookContentBean readEntity(Cursor cursor, int offset) {
        BookContentBean entity = new BookContentBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // durChapterUrl
            cursor.getInt(offset + 1), // durChapterIndex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // durCapterContent
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // tag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookContentBean entity, int offset) {
        entity.setDurChapterUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDurChapterIndex(cursor.getInt(offset + 1));
        entity.setDurCapterContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTag(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BookContentBean entity, long rowId) {
        return entity.getDurChapterUrl();
    }
    
    @Override
    public String getKey(BookContentBean entity) {
        if(entity != null) {
            return entity.getDurChapterUrl();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
