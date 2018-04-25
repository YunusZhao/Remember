package com.yunus.remember.activity.begin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.RWUtil;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;
import com.yunus.remember.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    EditText email;
    Button getPassword;
    TextView register;
    private Dialog myDialog;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        getView();
        ViewUtil.setToolbar(PasswordActivity.this, toolbar);

        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() || !RWUtil.isEmail(email.getText().toString())) {
                    Toast.makeText(PasswordActivity.this, "邮箱输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    myDialog = ProgressDialog.show(PasswordActivity.this, "提示...", "找回中...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getPasswordToIntel();
                        }
                    }).start();

                }

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
        toolbarTitle.setText(getString(R.string.get_password));
    }

    private void getView() {
        toolbar = findViewById(R.id.password_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.begin_toolbar_title);
        email = (EditText) findViewById(R.id.forget_email);
        getPassword = (Button) findViewById(R.id.btn_get_password);
        register = (TextView) findViewById(R.id.password_to_register);
    }

    private void getPasswordToIntel() {
        final RequestBody requestBody = new FormBody.Builder()
                .add("email", email.getText().toString())
                .add("action", "password")
                .build();
        HttpUtil.postOkhttpRequest(requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        Toast.makeText(PasswordActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        switch (result) {
                            case "0":
                                Toast.makeText(PasswordActivity.this, "邮箱未注册", Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(PasswordActivity.this, "新密码已发至邮箱", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                finish();
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(PasswordActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
