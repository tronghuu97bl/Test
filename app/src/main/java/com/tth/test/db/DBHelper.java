package com.tth.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tth.test.model.Note;
import com.tth.test.model.Work;
import com.tth.test.model.Works;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAM = "note.tth.db";
    private static final int DATABASE_VERSION = 3;
    //table note (ghi chu ca nhan)
    private static final String TABLE_NOTE_NAME = "note";
    private static final String NOTE_ID = "note_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_MDF = "note_last_mdf";
    private static final String NOTE_CHECK = "note_checked";
    //table work (cong viec ca nhan)
    private static final String TABLE_WORK_NAME = "work";
    private static final String WORK_ID = "work_id";
    private static final String WORK_CONTENT = "work_content";
    private static final String WORK_MDF = "work_last_mdf";
    private static final String WORK_CHECK = "work_checked";
    // table works - cong viec nhom
    private static final String TABLE_WORKS_NAME = "works";
    private static final String WORKS_ID = "works_id";
    private static final String WORKS_CONTENT ="works_content";
    private static final String WORKS_MDF = "works_last_mdf";
    private static final String WORKS_CHECK = "works_check";
    public DBHelper(Context context) {
        super(context, DATABASE_NAM, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOTE_NAME + "( " +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT, " +
                NOTE_CONTENT + " TEXT, " +
                NOTE_MDF + " TEXT, " +
                NOTE_CHECK + " INTEGER)";
        db.execSQL(query);
        // add table work
        query = "CREATE TABLE " + TABLE_WORK_NAME + "( " +
                WORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORK_CONTENT + " TEXT, " +
                WORK_MDF + " TEXT, " +
                WORK_CHECK + " INTEGER)";
        db.execSQL(query);
        //add table works
        query = "CREATE TABLE "+ TABLE_WORKS_NAME+ "(" +
                WORKS_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+
                WORKS_CONTENT + " TEXT, "+
                WORKS_MDF + " TEXT, "+
                WORKS_CHECK+"INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WORKS_NAME);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, note.getTitle());
        cv.put(NOTE_CONTENT, note.getContent());
        cv.put(NOTE_MDF, note.getLast_mdf());
        cv.put(NOTE_CHECK, note.getChecked());
        long result = db.insert(TABLE_NOTE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Add Note Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Note Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void addWork(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORK_CONTENT, work.getContent());
        cv.put(WORK_MDF, work.getLast_mdf());
        cv.put(WORK_CHECK, work.getChecked());
        long result = db.insert(TABLE_WORK_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Add Work Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Work Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void addWorks(Works works) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORK_CONTENT, works.getContent());
        cv.put(WORK_MDF, works.getLast_mdf());
        cv.put(WORK_CHECK, works.getChecked());
        long result = db.insert(TABLE_WORK_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Add Works Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Works Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NOTE_NAME, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_MDF, NOTE_CHECK},
                NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));
        db.close();
        return note;
    }

    public Work getWork(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_WORK_NAME, new String[]{WORK_ID, WORK_CONTENT, WORK_MDF, WORK_CHECK},
                WORK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Work work = new Work(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));
        db.close();
        return work;
    }


    // get works
    public Works getWorks(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_WORKS_NAME, new String[]{WORKS_ID, WORKS_CONTENT, WORKS_MDF, WORKS_CHECK},
                WORKS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Works works = new Works(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));
        db.close();
        return works;
    }

    public List<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteid(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setLast_mdf(cursor.getString(3));
                note.setChecked(Integer.parseInt(cursor.getString(4)));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return noteList;
    }

    public List<Work> getAllWork() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Work> workList = new ArrayList<Work>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORK_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int a= cursor.getCount();
        Log.d("TEST",Integer.toString(a));
        if (cursor.moveToFirst()) {
            do {
                Work work = new Work();
                work.setWorkid(Integer.parseInt(cursor.getString(0)));
                work.setContent(cursor.getString(1));
                work.setLast_mdf(cursor.getString(2));
                work.setChecked(Integer.parseInt(cursor.getString(3)));
                // Adding note to list
                workList.add(work);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return workList;
    }



    public List<Works> getAllWorks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Works> worksList = new ArrayList<Works>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORKS_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int a= cursor.getCount();
        Log.d("TEST",Integer.toString(a));
        if (cursor.moveToFirst()) {
            do {
                Works works = new Works();
                works.setWorksid(Integer.parseInt(cursor.getString(0)));
                works.setContent(cursor.getString(1));
                works.setLast_mdf(cursor.getString(2));
                works.setChecked(Integer.parseInt(cursor.getString(3)));
                // Adding note to list
                worksList.add(works);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return worksList;
    }
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getWorkCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORK_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getWorksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORKS_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, note.getTitle());
        cv.put(NOTE_CONTENT, note.getContent());
        cv.put(NOTE_MDF, note.getLast_mdf());
        cv.put(NOTE_CHECK, note.getChecked());
        // updating row
        return db.update(TABLE_NOTE_NAME, cv, NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteid())});
    }

    public int updateWork(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORK_CONTENT, work.getContent());
        cv.put(WORK_MDF, work.getLast_mdf());
        cv.put(WORK_CHECK, work.getChecked());
        // updating row
        return db.update(TABLE_WORK_NAME, cv, WORK_ID + " = ?",
                new String[]{String.valueOf(work.getWorkid())});
    }

    public int updateWorks(Works works) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORKS_CONTENT, works.getContent());
        cv.put(WORKS_MDF, works.getLast_mdf());
        cv.put(WORKS_CHECK, works.getChecked());
        // updating row
        return db.update(TABLE_WORKS_NAME, cv, WORKS_ID + " = ?",
                new String[]{String.valueOf(works.getWorksid())});
    }
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE_NAME, NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteid())});
        db.close();
    }

    public void deleteWork(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORK_NAME, WORK_ID + " = ?",
                new String[]{String.valueOf(work.getWorkid())});
        db.close();
    }
    public void deleteWorks(Works works) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKS_NAME, WORKS_ID + " = ?",
                new String[]{String.valueOf(works.getWorksid())});
        db.close();
    }
    /*public Cursor readAllData_Work() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WORK_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }*/

    public void createDefaultNotesIfNeed() {
        int count = this.getNotesCount();
        if (count == 0) {
            Note note1 = new Note("Note1_title", "content note 1", "1/1/2020", 0);
            Note note2 = new Note("Note2_title", "content note 2", "1/2/2020", 0);
            this.addNote(note1);
            this.addNote(note2);
        }
    }

    public void createDefaultWorkIfNeed() {
        int count = this.getWorkCount();
        if (count == 0) {
            Work work1 = new Work("thiên hạ vô song thiên hạ vô song thiên hạ vô song thiên hạ vô song ",
                    "1/1/2020", 0);
            Work work2 = new Work("abcdef ",
                    "1/1/2020", 0);
            this.addWork(work1);
            this.addWork(work2);
        }
    }
}
