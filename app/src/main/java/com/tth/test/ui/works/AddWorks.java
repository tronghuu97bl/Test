package com.tth.test.ui.works;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Works;
import com.tth.test.util.KeyboardUtils;

import java.util.Calendar;
import java.util.Date;

public class AddWorks {
    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_works, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        final TextView test = popupView.findViewById(R.id.edt_works);
        //test.setText(R.string.tv_test);
        Button button = popupView.findViewById(R.id.btn_works_time);
        Button button2 = popupView.findViewById(R.id.btn_works_done);
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
                String content = test.getText().toString();
                String last_modify = current_time.toString();
                Works works = new Works(content,last_modify,0);
                dbHelper.addWorks(works);
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
        KeyboardUtils.showKeyboard2(test);
    }
}