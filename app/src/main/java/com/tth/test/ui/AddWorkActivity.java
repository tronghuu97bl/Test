package com.tth.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.MainActivity;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Work;
import com.tth.test.ui.personal.WorkFragment;
import com.tth.test.util.KeyboardUtils;

import java.util.Calendar;
import java.util.Date;

public class AddWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_work);
        View view = findViewById(R.id.constraintLayout1);
        //showPopUpWindow(view);
    }

    public void showPopUpWindow(final View view) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_add_work, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        final TextView test = popupView.findViewById(R.id.editTextTextMultiLine);

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
                String last_modify = currentime.toString();
                Work work = new Work(content, last_modify, 0);
                dbHelper.addWork(work);
                popupWindow.dismiss();
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
                finish();
            }
        });
        KeyboardUtils.showKeyboard2(test);
    }

    @Override
    public void finish() {
        Log.d("TEST","DDD");
        Intent data = new Intent();
        data.putExtra("needRefresh", true);
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}