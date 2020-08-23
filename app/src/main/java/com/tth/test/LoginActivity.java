package com.tth.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tth.test.db.DBHelper;
import com.tth.test.helper.InputValidation;
import com.tth.test.model.Account;
import com.tth.test.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import static com.tth.test.MainActivity.KEY;

public class LoginActivity extends AppCompatActivity {

    private final AppCompatActivity activity = LoginActivity.this;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DBHelper dbHelper;
    List<UserModel> userModels;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initObjects();
        initViews();
        initListeners();

    }

    private void initViews() {
        edtUsername = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btn_login);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
        userModels = new ArrayList<>();
        userModels.addAll(dbHelper.getAllUser());
    }

    private void initListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyFromSQLite();
            }
        });
        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private void initObjects() {
        dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void verifyFromSQLite() {
        if (!inputValidation.isEditTextFilled(edtUsername)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!inputValidation.isEditTextFilled(edtPassword)) {
            Toast.makeText(this, getString(R.string.error_message_notfilled), Toast.LENGTH_SHORT).show();
            return;
        }
        checkAccountLogin(userModels, edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
    }

    private void checkAccountLogin(List<UserModel> userModels, String user, String pass) {
        int got = 0;
        for (int i = 0; i < userModels.size(); i++) {
            System.out.println(userModels.get(i).getUsername() + "_" + userModels.get(i).getPass());
            if (user.equals(userModels.get(i).getUsername())) {
                if (pass.equals(userModels.get(i).getPass())) {
                    got = 1;
                    finish();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    e = getSharedPreferences(KEY, MODE_PRIVATE).edit();
                    e.putBoolean("loginStatus", true);
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    e.putString("username", user);
                    e.commit();
                    break;
                } else {
                    Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (got != 1) {
            Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
        }
    }
}