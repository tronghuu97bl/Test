package com.tth.test.ui.personal;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.R;
import com.tth.test.db.DBHelper;

import java.util.Calendar;
import java.util.Date;

public class AddWork {
    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_add_work, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view,Gravity.BOTTOM,0,0);
        final TextView test = popupView.findViewById(R.id.editText);
        //test.setText(R.string.tv_test);
        Button button = popupView.findViewById(R.id.button_settime);
        Button button2 = popupView.findViewById(R.id.button_hoantat);
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
                Date currentime = Calendar.getInstance().getTime();

                String content = test.getText().toString();
                String title = content.substring(0,5);
                String last_modify = currentime.toString();

                //dbHelper.addWork(title,content,last_modify,0);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
