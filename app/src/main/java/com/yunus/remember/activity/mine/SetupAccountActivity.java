package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.utils.StorageUtil;

public class SetupAccountActivity extends BaseActivity {

    TextView email;
    TextView name;
    ImageButton nameGo;
    ImageButton passwordGo;
    ImageButton logoutGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);

        Toolbar toolbar = findViewById(R.id.setup_account_toolbar);
        toolbar.setTitle("账号设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        email = findViewById(R.id.setup_account_email);
        name = findViewById(R.id.setup_account_name);
        nameGo = findViewById(R.id.name_go);
        passwordGo = findViewById(R.id.password_go);
        logoutGo = findViewById(R.id.logout_go);

        initText();

        nameGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        passwordGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        logoutGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
    }

    private void initText() {
        email.setText(StorageUtil.getString(SetupAccountActivity.this, StorageUtil.EMAIL, ""));
        name.setText(StorageUtil.getString(SetupAccountActivity.this, StorageUtil.USER_NAME, ""));
    }
}
