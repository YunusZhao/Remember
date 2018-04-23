package com.yunus.remember.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.DiaryAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);

        toolbar = findViewById(R.id.diaries_toolbar);
        toolbar.setTitle(R.string.allDiary);
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

        //排序
        diaries = DataSupport.findAll(RegisterCount.class);
        DiaryAdapter diaryAdapter = new DiaryAdapter(DiariesActivity.this, R.layout.item_diary, diaries);
        lvDiaries.setAdapter(diaryAdapter);
    }
}
