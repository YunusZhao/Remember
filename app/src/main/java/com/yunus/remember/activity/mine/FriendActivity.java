package com.yunus.remember.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.ranking.PersonalDataActivity;
import com.yunus.remember.adapter.FriendAdapter;
import com.yunus.remember.entity.Friend;

import org.litepal.crud.DataSupport;

import java.util.List;

public class FriendActivity extends BaseActivity {

    private Button addFriend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Toolbar toolbar = findViewById(R.id.friend_toolbar);
        toolbar.setTitle("好友列表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addFriend = findViewById(R.id.friend_new);
        listView = findViewById(R.id.friend_list_view);

        //todo 他人好友
        final List<Friend> friends = DataSupport.findAll(Friend.class);
        FriendAdapter adapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend,
                friends);
        listView.setAdapter(adapter);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendActivity.this, FriendNewActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friends.get(position);
                Intent intent = new Intent(FriendActivity.this, PersonalDataActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);

            }
        });
    }
}
