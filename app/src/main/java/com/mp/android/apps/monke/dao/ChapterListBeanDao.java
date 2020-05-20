package com.mp.android.apps.monke.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mp.android.apps.monke.monkeybook.bean.ChapterListBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAPTER_LIST_BEAN".
*/
public class ChapterListBeanDao extends AbstractDao<ChapterListBean, String> {

    public static final String TABLENAME = "CHAPTER_LIST_BEAN";

    /**
     * Properties of entity ChapterListBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property NoteUrl = new Property(0, String.class, "noteUrl", false, "NOTE_URL");
        public final static Property DurChapterIndex = new Property(1, int.class, "durChapterIndex", false, "DUR_CHAPTER_INDEX");
        public final static Property DurChapterUrl = new Property(2, String.class, "durChapterUrl", true, "DUR_CHAPTER_URL");
        public final static Property DurChapterName = new Property(3, String.class, "durChapterName", false, "DUR_CHAPTER_NAME");
        public final static Property Tag = new Property(4, String.class, "tag", false, "TAG");
        public final static Property HasCache = new Property(5, Boolean.class, "hasCache", false, "HAS_CACHE");
    };


    public ChapterListBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChapterListBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAPTER_LIST_BEAN\" (" + //
                "\"NOTE_URL\" TEXT," + // 0: noteUrl
                "\"DUR_CHAPTER_INDEX\" INTEGER NOT NULL ," + // 1: durChapterIndex
                "\"DUR_CHAPTER_URL\" TEXT PRIMARY KEY NOT NULL ," + // 2: durChapterUrl
                "\"DUR_CHAPTER_NAME\" TEXT," + // 3: durChapterName
                "\"TAG\" TEXT," + // 4: tag
                "\"HAS_CACHE\" INTEGER);"); // 5: hasCache
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAPTER_LIST_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChapterListBean entity) {
        stmt.clearBindings();
 
        String noteUrl = entity.getNoteUrl();
        if (noteUrl != null) {
            stmt.bindString(1, noteUrl);
        }
        stmt.bindLong(2, entity.getDurChapterIndex());
 
        String durChapterUrl = entity.getDurChapterUrl();
        if (durChapterUrl != null) {
            stmt.bindString(3, durChapterUrl);
        }
 
        String durChapterName = entity.getDurChapterName();
        if (durChapterName != null) {
            stmt.bindString(4, durChapterName);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(5, tag);
        }
 
        Boolean hasCache = entity.getHasCache();
        if (hasCache != null) {
            stmt.bindLong(6, hasCache ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChapterListBean entity) {
        stmt.clearBindings();
 
        String noteUrl = entity.getNoteUrl();
        if (noteUrl != null) {
            stmt.bindString(1, noteUrl);
        }
        stmt.bindLong(2, entity.getDurChapterIndex());
 
        String durChapterUrl = entity.getDurChapterUrl();
        if (durChapterUrl != null) {
            stmt.bindString(3, durChapterUrl);
        }
 
        String durChapterName = entity.getDurChapterName();
        if (durChapterName != null) {
            stmt.bindString(4, durChapterName);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(5, tag);
        }
 
        Boolean hasCache = entity.getHasCache();
        if (hasCache != null) {
            stmt.bindLong(6, hasCache ? 1L: 0L);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2);
    }    

    @Override
    public ChapterListBean readEntity(Cursor cursor, int offset) {
        ChapterListBean entity = new ChapterListBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // noteUrl
            cursor.getInt(offset + 1), // durChapterIndex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // durChapterUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // durChapterName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // tag
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0 // hasCache
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChapterListBean entity, int offset) {
        entity.setNoteUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDurChapterIndex(cursor.getInt(offset + 1));
        entity.setDurChapterUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDurChapterName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTag(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setHasCache(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(ChapterListBean entity, long rowId) {
        return entity.getDurChapterUrl();
    }
    
    @Override
    public String getKey(ChapterListBean entity) {
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
