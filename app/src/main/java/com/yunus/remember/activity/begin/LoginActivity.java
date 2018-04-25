package com.yunus.remember.activity.begin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.ActivityCollector;
import com.example.yunus.utils.RWUtil;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.MainActivity;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    CircleImageView portrait;
    EditText email;
    EditText password;
    Button login;
    TextView forgetPassword;
    TextView register;
    ProgressDialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();
        ViewUtil.setToolbar(LoginActivity.this, toolbar);

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
                if (email.getText().toString().isEmpty() || !RWUtil.isEmail(email.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "邮箱输入错误", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty() || !RWUtil.isPwd(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    myDialog = ProgressDialog.show(LoginActivity.this, "提示...", "登陆中...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loginToIntel();
                        }
                    }).start();
                }
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

    private void loginToIntel() {
        final RequestBody requestBody = new FormBody.Builder()
                .add("email", email.getText().toString())
                .add("password", password.getText().toString())
                .add("action", "login")
                .build();
        HttpUtil.postOkhttpRequest(requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (!"-1".equals(result)){
                    //TODO 解析数据保存至数据库
                    String session = response.header("set-cookie");
                    StorageUtil.updateString(LoginActivity.this, StorageUtil.SESSION, session);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        switch (result) {
                            case "-1":
                                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                ActivityCollector.finishAll();
                                startActivity(intent);
                                break;
                        }
                    }
                });
            }
        });
    }
}
