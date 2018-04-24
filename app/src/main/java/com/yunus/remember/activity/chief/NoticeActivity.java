package com.yunus.remember.activity.chief;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.FriendAdapter;
import com.yunus.remember.entity.Friend;

import java.util.List;

public class NoticeActivity extends BaseActivity {

    Toolbar toolbar;
    ListView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        toolbar = (Toolbar) findViewById(R.id.notice_toolbar);
        toolbar.setTitle(R.string.message);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        friendList = findViewById(R.id.notice_friend_list);
        List<Friend> friends = getNoticeFriends();
        FriendAdapter friendAdapter = new FriendAdapter(NoticeActivity.this, R.layout.item_notice_friend, friends);
        friendList.setAdapter(friendAdapter);
    }

    private List<Friend> getNoticeFriends() {
        //联网获取消息记录
        return null;
    }
}
