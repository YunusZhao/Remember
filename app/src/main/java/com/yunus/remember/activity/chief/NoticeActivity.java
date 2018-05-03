package com.yunus.remember.activity.chief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.NoticeFriendAdapter;
import com.yunus.remember.entity.Friend;

import java.util.List;

public class NoticeActivity extends BaseActivity {

    Toolbar toolbar;
    ListView friendListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        toolbar = findViewById(R.id.notice_toolbar);
        toolbar.setTitle(R.string.message);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        friendListView = findViewById(R.id.notice_friend_list);
        final List<Friend> friends = getNoticeFriends();
        NoticeFriendAdapter noticeFriendAdapter = new NoticeFriendAdapter(NoticeActivity.this, R
                .layout.item_friend, friends);
        friendListView.setAdapter(noticeFriendAdapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend friend = friends.get(i);
                Intent intent = new Intent(NoticeActivity.this, ChatActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });
    }

    private List<Friend> getNoticeFriends() {
        //联网获取消息记录
        return null;
    }
}
