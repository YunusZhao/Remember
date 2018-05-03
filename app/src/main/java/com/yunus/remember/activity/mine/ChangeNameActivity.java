package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class ChangeNameActivity extends BaseActivity {

    private EditText oldName;
    private EditText newName;
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

        oldName = findViewById(R.id.change_old);
        newName = findViewById(R.id.change_new);
        ok = findViewById(R.id.change_name_ok);

        oldName.setText(StorageUtil.getString(this, StorageUtil.USER_NAME, ""));
        oldName.setEnabled(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newName.getText().toString().isEmpty() ||
                        !RWUtil.isName(newName.getText().toString())) {
                    Toast.makeText(ChangeNameActivity.this, "新昵称不合法", Toast.LENGTH_SHORT).show();
                } else {
                    updateName();
                }
            }
        });
    }

    private void updateName() {
        HttpUtil.changeName(StorageUtil.getInt(this, StorageUtil.USER_ID, 0),
                newName.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeNameActivity.this, "网络异常 更新失败",
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
                                    Toast.makeText(ChangeNameActivity.this, "更新成功",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeNameActivity.this, "更新失败",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });
    }
}
