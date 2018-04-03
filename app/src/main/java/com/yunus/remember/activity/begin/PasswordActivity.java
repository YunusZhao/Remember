package com.yunus.remember.activity.begin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;

public class PasswordActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    EditText email;
    Button getPassword;
    TextView register ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        getView();
        ViewUtil.setToollbar(PasswordActivity.this, toolbar);

        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.get_password);
    }

    private void getView() {
        email = (EditText) findViewById(R.id.forget_email);
        getPassword = (Button) findViewById(R.id.btn_get_password);
        register = (TextView) findViewById(R.id.password_to_register);
    }
}
