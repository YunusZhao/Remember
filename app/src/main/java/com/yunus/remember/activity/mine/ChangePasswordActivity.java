package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.RWUtil;
import com.yunus.remember.R;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePasswordActivity extends BaseActivity {

    private EditText first;
    private EditText second;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Toolbar toolbar = findViewById(R.id.change_name_toolbar);
        toolbar.setTitle(R.string.change_name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        first = findViewById(R.id.change_old);
        second = findViewById(R.id.change_new);
        ok = findViewById(R.id.change_name_ok);
        first.setHint("输入新密码");
        first.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        second.setHint("再次输入新密码");
        second.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first.getText().toString().isEmpty() ||
                        !RWUtil.isPwd(first.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "新密码不合法",
                            Toast.LENGTH_SHORT).show();
                } else if (!first.getText().toString().equals(second.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "密码不一致",
                            Toast.LENGTH_SHORT).show();
                } else {
                    updatePassword();
                }
            }
        });
    }

    private void updatePassword() {
        HttpUtil.changePassword(StorageUtil.getInt(this, StorageUtil.USER_ID, 0),
                first.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangePasswordActivity.this, "网络异常 更新失败",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if ("ok".equals(response.body().string())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangePasswordActivity.this, "更新成功",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangePasswordActivity.this, "更新失败",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });
    }
}
