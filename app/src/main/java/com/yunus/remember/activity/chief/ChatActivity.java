package com.yunus.remember.activity.chief;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.ChatAdapter;
import com.yunus.remember.entity.Chat;
import com.yunus.remember.entity.Friend;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChatActivity extends BaseActivity {

    private Toolbar toolbar;
    private List<Chat> chatList;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        friend = (Friend) getIntent().getSerializableExtra("friend");

        toolbar = findViewById(R.id.books_toolbar);
        toolbar.setTitle(friend.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initChat();

        recyclerView = findViewById(R.id.chat_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(adapter);

        Chat chat = new Chat();
        chat.setRead(true);
        chat.updateAll("friend_id = ?", friend.getId() + "");
    }

    private void initChat() {
        chatList = DataSupport
                .where("friend_id = ?", friend.getId() + "")
                .order("time desc")
                .find(Chat.class);
    }
}
