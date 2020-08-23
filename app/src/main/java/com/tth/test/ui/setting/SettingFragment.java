package com.tth.test.ui.setting;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tth.test.R;
import com.tth.test.db.DBHelper;
import com.tth.test.model.Password;
import com.tth.test.ui.personal.NoteAdapter;

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView text1 = root.findViewById(R.id.text_changeSecret);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view);
            }
        });
        return root;
    }
    public void onButtonShowPopupWindowClick(View view){

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.change_secret_password, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;//lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width,height,focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        //LAM MO NEN
        View container = popupWindow.getContentView().getRootView();
        if (container != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.3f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }
        //show popup window
        //which view you pass in doesn't matter, it is only used for the windeow tolken
        //tao bong
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            popupWindow.setElevation(20);
        }

        final TextView old_pass =  popupView.findViewById(R.id.old_password);
        final TextView newPass = popupView.findViewById(R.id.new_password);
        final Button change = popupView.findViewById(R.id.button_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password pass = new Password();
                Log.d("error","1");
                DBHelper dbHelper = new DBHelper(getContext());
                Log.d("error","2");
                pass = dbHelper.getPassword(1);
                Log.d("error","3");
                if(old_pass.getText().toString().equals(pass.getPassword())){
                  pass.setPassword(newPass.getText().toString());
                    Log.d("error","4");
                  dbHelper.updatePass(pass);
                    Log.d("error","5");
                }
                }
        });
        //dissmiss the popup window when touch
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
