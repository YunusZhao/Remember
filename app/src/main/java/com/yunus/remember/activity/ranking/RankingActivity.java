package com.yunus.remember.activity.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.RankingAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        friendList = getRankingList(mode);
        initText(mode);
        RankingAdapter adapter = new RankingAdapter(RankingActivity.this, R.layout
                .item_ranking_information, friendList);
        rankListView.setAdapter(adapter);
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

    private List<Friend> getRankingList(int mode) {
        switch (mode) {
            case 0:
                //todo
                return null;
            case 1:
                //todo
                return null;
            case 2:
                return DataSupport.order("wordNum").find(Friend.class);
            case 3:
                return DataSupport.order("allTime").find(Friend.class);
            default:
                return null;
        }
    }

    private void initText(int mode) {
        Friend me;
        switch (mode) {
            case 0:
            case 1:
                me = friendList.get(friendList.size() - 1);
                ranking.setText(me.getId());
                Glide.with(RankingActivity.this).load(me.getPortrait()).into(image);
                name.setText(me.getName());
                summary.setText(me.getSummary());
                allNum.setText(me.getWordNum());
                allTime.setText(me.getAllTime());
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
                ranking.setText(i);
                Glide.with(RankingActivity.this).load(me.getPortrait()).into(image);
                name.setText(me.getName());
                summary.setText(me.getSummary());
                allNum.setText(me.getWordNum());
                allTime.setText(me.getAllTime());
                break;
            default:
        }
    }
}
