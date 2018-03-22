package com.yunus.remember.activity.begin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    CircleImageView portrait;
    EditText email;
    EditText password;
    Button login;
    TextView forgetPassword;
    TextView register ;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        portrait = (CircleImageView) findViewById(R.id.begin_portrait);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.btn_login);
        forgetPassword = (TextView) findViewById(R.id.login_forget_password);
        register = (TextView) findViewById(R.id.login_register);

        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbarTitle.setText(R.string.begin_sign_in_short);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    @Override
    protected void loadData() {

    }
}
