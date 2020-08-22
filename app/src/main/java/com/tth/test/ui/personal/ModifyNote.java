package com.tth.test.ui.personal;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Note;
import com.tth.test.model.Work;
import com.tth.test.util.KeyboardUtils;

import java.util.Calendar;
import java.util.Date;

public class ModifyNote {
    Note note = new Note();
    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_note, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        final TextView title= popupView.findViewById(R.id.edt_titleNote);
        final TextView content = popupView.findViewById(R.id.edt_note);
        //test.setText(R.string.tv_test);
        Button button = popupView.findViewById(R.id.button_time);
        Button button2 = popupView.findViewById(R.id.button_done);
        button.setText("Đặt nhắc nhở");
        button2.setText("Hoàn tất");
        //set time
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "abcdefg", Toast.LENGTH_SHORT).show();
            }
        });
        //save
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(view.getContext());
                Date current_time = Calendar.getInstance().getTime();
                String noteTitle = title.getText().toString();
                String noteContent = content.getText().toString();
                String last_modify = current_time.toString();
                Note note = new Note(noteTitle,noteContent, last_modify,0,0);
                dbHelper.updateNote(note);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //popupWindow.dismiss();
                return true;
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                KeyboardUtils.showKeyboard2(view);
            }
        });
        KeyboardUtils.showKeyboard2(content);
    }
}
