package com.yunus.remember.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.entity.RegisterCount;
import com.yunus.remember.utils.StorageUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryActivity extends BaseActivity {

    Toolbar toolbar;
    TextView diaryNum;
    CircleImageView image;
    TextView diaryName;
    TextView diaryDate;
    TextView textLong;
    Button allDiary;

    RegisterCount registerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //获取日期，其他地方传过来

        registerCount = (RegisterCount) getIntent().getSerializableExtra("date");

        toolbar =  findViewById(R.id.detail_toolBar);

        toolbar.setTitle(R.string.register_diary);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        diaryNum = findViewById(R.id.diary_num);
        image = findViewById(R.id.diary_image);
        diaryName = findViewById(R.id.diary_name);
        diaryDate = findViewById(R.id.diary_date);
        textLong = findViewById(R.id.diary_state_long);
        allDiary = findViewById(R.id.diary_allDiary);

        allDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, DiariesActivity.class);
                startActivity(intent);
            }
        });

        initView();
    }

    private void initView() {
        diaryNum.setText(registerCount.getDayCount());
        diaryDate.setText(StorageUtil.getDate(registerCount.getRegisterDate()));
        textLong.setText("今日学习了" + registerCount.getWordNum() + "个单词，学习时间"
                + registerCount.getStudyTime() + "分钟");
    }
}
