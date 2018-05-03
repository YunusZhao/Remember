package com.yunus.remember.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.DiaryAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.entity.RegisterCount;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiariesActivity extends BaseActivity {

    Toolbar toolbar;
    CircleImageView image;
    TextView name;
    ListView lvDiaries;
    List<RegisterCount> diaries;
    Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        friend = (Friend) getIntent().getSerializableExtra("person");

        toolbar = findViewById(R.id.diaries_toolbar);
        toolbar.setTitle(R.string.all_diary);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image = findViewById(R.id.dailies_image);
        name = findViewById(R.id.diaries_name);
        lvDiaries = findViewById(R.id.diaries_list);

        //todo 他人日记

        //排序
        diaries = DataSupport.order("dayCount desc").find(RegisterCount.class);
        DiaryAdapter diaryAdapter = new DiaryAdapter(DiariesActivity.this,
                R.layout.item_diary, diaries);
        lvDiaries.setAdapter(diaryAdapter);

        lvDiaries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegisterCount registerCount = diaries.get(position);
                Intent intent = new Intent(DiariesActivity.this, DiaryActivity.class);
                intent.putExtra("date", registerCount);
                startActivity(intent);
            }
        });
    }
}
