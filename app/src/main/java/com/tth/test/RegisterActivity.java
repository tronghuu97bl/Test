package com.tth.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.db.DBHelper;
import com.tth.test.helper.InputValidation;
import com.tth.test.model.Account;
import com.tth.test.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import static com.tth.test.MainActivity.KEY;

public class RegisterActivity extends AppCompatActivity {
    private final AppCompatActivity activity = RegisterActivity.this;

    private EditText edtUsername, edtEmail, edtPassword, edtPassconfirm;
    private Button btnRegister;
    private InputValidation inputValidation;
    private DBHelper dbHelper;
    private UserModel userModel;
    List<UserModel> userModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initObjects();
        initViews();
        initListeners();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edt_user1);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_pass1);
        edtPassconfirm = findViewById(R.id.edt_confirm_pass);
        btnRegister = findViewById(R.id.register_btn);
        userModels = new ArrayList<>();
        userModels.addAll(dbHelper.getAllUser());
    }

    private void initListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        dbHelper = new DBHelper(activity);
        userModel = new UserModel();
    }

    private void postDataToSQLite() {
        if (!inputValidation.isEditTextFilled(edtUsername)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        };
        if (!inputValidation.isEditTextFilled(edtEmail)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        };
        if (!inputValidation.isEditTextFilled(edtPassword)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        };
        if (!inputValidation.isEditTextFilled(edtPassconfirm)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        };
        if (!inputValidation.isEditTextEmail(edtEmail)) {
            Toast.makeText(this, getString(R.string.error_wrong_email), Toast.LENGTH_SHORT).show();
            return;
        };
        if (!inputValidation.isEditTextMatches(edtPassword, edtPassconfirm)) {
            Toast.makeText(this, getString(R.string.error_pass_notmatched), Toast.LENGTH_SHORT).show();
            return;
        }
        userModel.setUser(edtUsername.getText().toString().trim());
        userModel.setEmail(edtEmail.getText().toString().trim());
        userModel.setPass(edtPassword.getText().toString().trim());
        checkUserExisted(userModels, edtUsername.getText().toString().trim(), userModel);
    }

    private void emptyInputEditText() {
        edtUsername.setText(null);
        edtEmail.setText(null);
        edtPassword.setText(null);
        edtPassconfirm.setText(null);
    }

    private void checkUserExisted(List<UserModel> userModels, String user, UserModel userModel) {
        int got = 0;
        for (int i = 0; i < userModels.size(); i++) {
            if (user.equals(userModels.get(i).getUsername())) {
                got = 1;
                Toast.makeText(this, getString(R.string.error_user_existed), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(got != 1) {
            dbHelper.addUser(userModel);
            emptyInputEditText();
            Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
            finish();
            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentLogin);
        }
    }
}