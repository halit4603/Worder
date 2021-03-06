package com.education.worder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
;

public class DBWords extends SQLiteOpenHelper {

    private static final String TAG = DBWords.class.getSimpleName();
    private static final String NAME_DB = "worder.db";
    private static final String MAIN_TABLE = "main";
    private static final int VERSION = 3;


    public DBWords(Context context) {
        super(context, NAME_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    /*
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
    */

    public Cursor loaAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MAIN_TABLE,null,null,null,null,null,null);
        return cursor;
    }

    public long insert(String word, String translate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBEntry.WORD, word);
        cv.put(DBEntry.TRANSLATE, translate);
        long result = db.insertOrThrow(MAIN_TABLE,null, cv);
        return result;
    }

    public int delete(long id){
        String command = DBEntry.ID + " = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(MAIN_TABLE,command, args);
        return result;
    }

    public int update(long id, String word, String translate, boolean selected){
        String command = DBEntry.ID + " = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBEntry.WORD,word);
        cv.put(DBEntry.TRANSLATE, translate);
        cv.put(DBEntry.SELECTED, selected?1:0);
        int result = db.update(MAIN_TABLE,cv,command, args);
        return result;
    }

    private static final String SQL_CREATE =
            "CREATE TABLE " + MAIN_TABLE + " ("+
            DBEntry.ID  + " INTEGER PRIMARY KEY, "+
            DBEntry.WORD + " TEXT, "+
            DBEntry.TRANSLATE + " TEXT, "+
            DBEntry.SELECTED + " INTEGER"+");";

    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + MAIN_TABLE;

    public static class DBEntry{
        public static final String ID = "_id";
        public static final String WORD = "word";
        public static final String TRANSLATE = "translate";
        public static final String SELECTED = "selected";
    }
}
