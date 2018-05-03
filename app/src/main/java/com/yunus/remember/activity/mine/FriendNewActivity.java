package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.yunus.remember.R;
import com.yunus.remember.adapter.NewFriendAdapter;
import com.yunus.remember.adapter.SearchWordAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendNewActivity extends BaseActivity {

    private ListView listView;
    private List<Friend> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_new);

        Toolbar toolbar = findViewById(R.id.friend_new_toolbar);
        SearchView searchView = findViewById(R.id.friend_new_search_view);
        listView = findViewById(R.id.friend_new_list_view);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchView.onActionViewExpanded();
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                friendList.clear();
                getFriend(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getFriend(String data) {
        HttpUtil.searchFriend(data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (friendList.isEmpty()) {
                            listView.setVisibility(View.GONE);
                        } else {
                            setAdapter(friendList);
                        }

                    }
                });
            }
        });
    }

    private void setAdapter(List<Friend> list) {
        if (listView.getAdapter() == null) {
            NewFriendAdapter adapter = new NewFriendAdapter(FriendNewActivity.this,
                    R.layout.item_friend, list);
            listView.setAdapter(adapter);
        } else {
            ((SearchWordAdapter) listView.getAdapter()).notifyDataSetChanged();
            LogUtil.d("SearchActivity", list.toString());
        }
    }
}
