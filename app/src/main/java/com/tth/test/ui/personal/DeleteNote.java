package com.tth.test.ui.personal;

import android.view.View;
import android.widget.ArrayAdapter;

import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DeleteNote {
    private List<Note> listNote = new ArrayList<Note>();
    private ArrayAdapter<Note> listViewAdapter;
    DBHelper dbHelper;

    public DeleteNote(Note noteItem) {
        dbHelper.deleteNote(noteItem);
        listNote.remove(noteItem);
        //refresh list
        listViewAdapter.notifyDataSetChanged();
    }
}
