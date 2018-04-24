package com.yunus.remember.activity.ranking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.adapter.RankingAdapter;
import com.yunus.remember.entity.Book;
import com.yunus.remember.entity.Friend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingActivity extends AppCompatActivity {

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

        String mode = getIntent().getStringExtra("mode");

        toolbar = (Toolbar) findViewById(R.id.ranking_toolbar);
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
        RankingAdapter adapter = new RankingAdapter(RankingActivity.this, R.layout.item_ranking_information, friendList);
        rankListView.setAdapter(adapter);

        initText();
    }

    private List<Friend> getRankingList(String mode) {
        return null;
    }

    private void initText() {
        //更新自己的数据
    }
}
