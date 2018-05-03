package com.yunus.remember.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.ActivityCollector;
import com.yunus.remember.R;
import com.yunus.remember.activity.begin.LoginActivity;

public class SetupActivity extends BaseActivity implements View.OnClickListener {

    Button study;
    Button account;
    Button about;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Toolbar toolbar = findViewById(R.id.books_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        study = findViewById(R.id.setup_study);
        account = findViewById(R.id.setup_account);
        about = findViewById(R.id.setup_about);
        logout = findViewById(R.id.setup_logout);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.setup_study:
                intent = new Intent(SetupActivity.this, SetupStudyActivity.class);
                startActivity(intent);
                break;
            case R.id.setup_account:
                intent = new Intent(SetupActivity.this, SetupAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.setup_about:
                //todo
                break;
            case R.id.setup_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(SetupActivity.this);
                dialog.setTitle("注销登陆");
                dialog.setMessage("确认注销登陆");
                dialog.setCancelable(true);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SetupActivity.this, LoginActivity.class);
                        ActivityCollector.finishAll();
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }
}
