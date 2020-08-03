package com.tth.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAM = "note.tth.db";
    private static final int DATABASE_VERSION = 1;
    //table work (cong viec personal)
    private static final String TABLE_WORK_NAME = "work";
    private static final String WORK_ID_WORK = "id_work";
    private static final String WORK_LAST_MODIFICATION = "last_modification";
    private static final String WORK_TITLE = "title";
    private static final String WORK_CONTENT = "content";
    private static final String WORK_CHECK = "checked";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAM, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "CREATE TABLE " + TABLE_WORK_NAME + "( " +
                WORK_ID_WORK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORK_LAST_MODIFICATION + " INTEGER, " +
                WORK_TITLE + " TEXT, " +
                WORK_CONTENT + " TEXT, " +
                WORK_CHECK + " INTEGER)";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //onCreate(db);
    }

    void addWork(String title, String content, int last_modify, int check) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORK_TITLE, title);
        cv.put(WORK_CONTENT, content);
        cv.put(WORK_LAST_MODIFICATION, last_modify);
        cv.put(WORK_CHECK, check);
        long result = db.insert(TABLE_WORK_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData_Work() {
        String query = "SELECT * FROM " + TABLE_WORK_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor =db.rawQuery(query,null);
        }
        return cursor;
    }
}
