package com.tth.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.tth.test.ui.about.AboutFragment;
import com.tth.test.ui.friend.FriendFragment;
import com.tth.test.ui.personal.HomeFragment;
import com.tth.test.ui.works.WorksFragment;
import com.tth.test.model.Account;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private View layoutnav;
    private TextView tvName;
    SharedPreferences.Editor e;
    public static String KEY = "******";
    SharedPreferences p;
    boolean loginStatus;
    String username;

    private NavigationView navLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              *//*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
                //Toast.makeText(MainActivity.this, "ddasdasda", Toast.LENGTH_SHORT).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //
        p = getSharedPreferences(KEY, MODE_PRIVATE);
        e = getSharedPreferences(KEY, MODE_PRIVATE).edit();
        layoutnav = navigationView.getHeaderView(0);
        tvName = layoutnav.findViewById(R.id.textView);

        loginStatus = p.getBoolean("loginStatus", false);
        username = p.getString("username", "");
        tvName.setText(username);
        layoutnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(loginStatus);
                if (loginStatus) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Đăng xuất")
                            .setMessage("Bạn có muốn đăng xuất không??")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    loginStatus = false;
                                    tvName.setText("");
                                    e.putBoolean("loginStatus",false);
                                    e.putString("username", "");
                                    e.commit();
                                    Toast.makeText(MainActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                                }
                            })

                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loginStatus = true;
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_personal, R.id.nav_works, R.id.nav_friend, R.id.nav_group, R.id.nav_setting, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        p = getSharedPreferences(KEY, MODE_PRIVATE);
        this.loginStatus = p.getBoolean("login",false);
        this.username = p.getString("username", "");
        if (loginStatus) {
            tvName.setText(username);
        }else {
            tvName.setText(username);
        }

        System.out.println("resume");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_personal:
                intent = new Intent(MainActivity.this, HomeFragment.class);
                startActivity(intent);
                break;
            case R.id.nav_works:
                intent = new Intent(MainActivity.this, WorksFragment.class);
                startActivity(intent);
                break;
            case R.id.nav_friend:
                intent = new Intent(MainActivity.this, FriendFragment.class);
                startActivity(intent);
                break;
            case R.id.nav_group:
                intent = new Intent(MainActivity.this, FriendFragment.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(MainActivity.this,AboutFragment.class );
                startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(MainActivity.this, HomeFragment.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "default",Toast.LENGTH_LONG);
                break;
        }
        return true;
    }
}