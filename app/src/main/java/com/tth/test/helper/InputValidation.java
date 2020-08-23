package com.tth.test.helper;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.tth.test.LoginActivity;

import java.util.regex.Pattern;

public class InputValidation {

    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    public boolean isEditTextFilled(EditText editText){
        String value = editText.getText().toString().trim();
        if(value.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isEditTextEmail(EditText editText) {
        String value = editText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return false;
        }
        return true;
    }

    public boolean isEditTextMatches(EditText editText, EditText editText1) {
        String value1 = editText.getText().toString().trim();
        String value2 = editText1.getText().toString().trim();
        if(!value1.contentEquals(value2)) {
            return false;
        }
        return true;
    }

}
