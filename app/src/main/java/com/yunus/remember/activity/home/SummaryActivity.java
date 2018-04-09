package com.yunus.remember.activity.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.yunus.remember.R;
import com.yunus.remember.adapter.SummaryWordAdapter;
import com.yunus.remember.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    Button next;
    private List<Word> wordList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.summary_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("小结模式");
        next = (Button) findViewById(R.id.summary_next);
        initList();
        listView = (ListView) findViewById(R.id.summary_list_view);
        SummaryWordAdapter adapter = new SummaryWordAdapter(SummaryActivity.this, R.layout.item_summary_word, wordList);
        listView.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initList() {
        
    }
}
