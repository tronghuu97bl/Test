package com.tth.test.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.tth.test.R;

public class DetailNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        TextView textView =findViewById(R.id.textView2);
        Intent intent =getIntent();
        String value1 =intent.getStringExtra("ABC");
        textView.setText(value1);
        CheckedTextView checkedTextView = findViewById(R.id.checkedTextView2);
        checkedTextView.setText(value1);
        checkedTextView.setChecked(true);

    }
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}