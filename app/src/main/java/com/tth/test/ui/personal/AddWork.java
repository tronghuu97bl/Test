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

public class AddWork {
    public void showPopUpWindow (final View view){
        LayoutInflater layoutInflater =(LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
         View popupView = layoutInflater.inflate(R.layout.activity_add_work,null);
         int width = LinearLayout.LayoutParams.MATCH_PARENT;
         int height = LinearLayout.LayoutParams.MATCH_PARENT;

         boolean focusable  = true;

         final PopupWindow popupWindow = new PopupWindow(popupView, width,height,focusable);
         popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        TextView test = popupView.findViewById(R.id.titleText);
        test.setText(R.string.tv_test);
        Button button = popupView.findViewById(R.id.messageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "abcdefg", Toast.LENGTH_SHORT).show();
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
