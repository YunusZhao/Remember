package com.yunus.remember.activity.chief;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;

public class ChatActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Friend friend = (Friend)getIntent().getSerializableExtra("friend");

        toolbar = (Toolbar) findViewById(R.id.books_toolbar);
        toolbar.setTitle(friend.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
