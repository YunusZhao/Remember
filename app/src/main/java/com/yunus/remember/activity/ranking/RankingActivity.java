package com.yunus.remember.activity.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.adapter.RankingAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RankingActivity extends BaseActivity {

    Toolbar toolbar;
    ListView rankListView;
    List<Friend> friendList;
    TextView ranking;
    CircleImageView image;
    TextView name;
    TextView summary;
    TextView allNum;
    TextView allTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        int mode = getIntent().getIntExtra("mode", 0);

        toolbar = findViewById(R.id.ranking_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ranking = findViewById(R.id.ranking_id);
        image = findViewById(R.id.ranking_image);
        name = findViewById(R.id.ranking_name);
        summary = findViewById(R.id.ranking_summary);
        allNum = findViewById(R.id.ranking_num);
        allTime = findViewById(R.id.ranking_time);
        rankListView = findViewById(R.id.ranking_list_view);

        getRankingList(mode);

        rankListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friendList.get(position);
                Intent intent = new Intent(RankingActivity.this, PersonalDataActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });
    }

    private void getRankingList(final int mode) {
        switch (mode) {
            case 0:
            case 1:
                HttpUtil.getRankingList(StorageUtil.getInt(RankingActivity.this, StorageUtil
                                .USER_ID, 0) + "", mode + "", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws
                                    IOException {
                                String result = response.body().string();
                                LogUtil.d("RankingActivity", result);
                                Gson gson = new Gson();
                                friendList = gson.fromJson(result,
                                        new TypeToken<List<Friend>>() {
                                        }.getType());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initText(mode);
                                    }
                                });
                            }
                        }
                );
                break;
            case 2:
                friendList = DataSupport.order("allNum desc").limit(10).find(Friend.class);
                initText(mode);
                break;
            case 3:
                friendList = DataSupport.order("allTime desc").limit(10).find(Friend.class);
                initText(mode);
                break;
        }
    }

    private void initText(int mode) {
        Friend me;
        switch (mode) {
            case 0:
            case 1:
                me = friendList.get(friendList.size() - 1);
                friendList.remove(me);
                ranking.setText(me.getId() + "");
                Glide.with(RankingActivity.this).load(Base64.decode(me.getPortrait(),
                        Base64.DEFAULT)).into(image);
                name.setText(me.getName());
                summary.setText(me.getSummary());
                allNum.setText(me.getAllNum() + "");
                allTime.setText(me.getAllTime() + "");
                break;
            case 2:
            case 3:
                int i = 0;
                for (; ; i++) {
                    if (friendList.get(i).getId() == StorageUtil.getInt(RankingActivity.this,
                            StorageUtil.USER_ID, 0)) {
                        me = friendList.get(i);
                        break;
                    }
                }
                ranking.setText((1 + i) + "");
                Glide.with(RankingActivity.this).load(Base64.decode(me.getPortrait(),
                        Base64.DEFAULT)).into(image);
                name.setText(me.getName());
                summary.setText(me.getSummary());
                allNum.setText("单词数 " + me.getAllNum());
                allTime.setText("时间 " + me.getAllTime());
                break;
            default:
        }

        RankingAdapter adapter = new RankingAdapter(RankingActivity.this, R.layout
                .item_ranking_information, friendList);
        rankListView.setAdapter(adapter);
    }
}