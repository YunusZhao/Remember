package com.yunus.remember.activity.begin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    CircleImageView portrait;
    ImageButton addPortrait;
    EditText name;
    EditText email;
    EditText password;
    Button register;
    TextView login ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getView();
        ViewUtil.setToolbar(RegisterActivity.this, toolbar);

        addPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle("");
        toolbarTitle.setText(getString(R.string.register_short));
    }

    private void getView() {
        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.begin_toolbar_title);
        portrait = (CircleImageView) findViewById(R.id.register_portrait);
        addPortrait = (ImageButton) findViewById(R.id.register_add_portrait);
        name = findViewById(R.id.register_name);
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.btn_register);
        login = (TextView) findViewById(R.id.register_to_login);
    }
}
