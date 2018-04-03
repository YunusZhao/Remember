package com.yunus.remember.activity.begin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.ActivityCollector;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.MainActivity;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();
        ViewUtil.setToollbar(LoginActivity.this, toolbar);

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                ActivityCollector.finishAll();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.sign_in_short);
    }

    private void getView() {
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.begin_toolbar_title);
        portrait = (CircleImageView) findViewById(R.id.login_portrait);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.btn_login);
        forgetPassword = (TextView) findViewById(R.id.login_forget_password);
        register = (TextView) findViewById(R.id.login_to_register);
    }

}
