package com.yunus.remember.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.activity.ranking.PersonalDataActivity;
import com.yunus.remember.adapter.FriendAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendActivity extends BaseActivity {

    Friend friend;
    List<Friend> friends;
    private Button addFriend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        friend = (Friend) getIntent().getSerializableExtra("person");

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

        if (StorageUtil.getInt(FriendActivity.this, StorageUtil.USER_ID, 0) == friend.getId()) {
            friends = DataSupport.findAll(Friend.class);
            FriendAdapter adapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend,
                    friends);
            listView.setAdapter(adapter);
        } else {
            HttpUtil.friend(friend.getId() + "", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    friends = gson.fromJson(response.body().string(),
                            new TypeToken<List<Friend>>() {
                            }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addFriend.setVisibility(View.GONE);
                            FriendAdapter adapter = new FriendAdapter(FriendActivity.this, R.layout.item_friend,
                                    friends);
                            listView.setAdapter(adapter);
                        }
                    });
                }
            });
        }



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
