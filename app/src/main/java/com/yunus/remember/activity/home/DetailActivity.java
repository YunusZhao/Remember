package com.yunus.remember.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.SearchActivity;
import com.yunus.remember.entity.TodayWord;
import com.yunus.remember.utils.StorageUtil;

public class DetailActivity extends BaseActivity {

    TextView detailSpell;
    TextView detailPhonogram;
    ImageButton detailVoice;
    TextView detailMean;
    TextView sentenceEnglish1;
    TextView sentenceEnglish2;
    TextView sentenceEnglish3;
    TextView sentenceChinese1;
    TextView sentenceChinese2;
    TextView sentenceChinese3;
    Button next;
    TodayWord todayWord;
    long beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        todayWord = getIntent().getParcelableExtra("today_word");

        Toolbar toolbar = findViewById(R.id.detail_toolBar);
        toolbar.setTitle("探索模式");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        detailSpell = findViewById(R.id.test_spell);
        detailPhonogram = findViewById(R.id.test_phonogram);
        detailVoice = findViewById(R.id.test_voice);
        detailMean = findViewById(R.id.detail_mean);
        sentenceEnglish1 = findViewById(R.id.detail_sentence_English_1);
        sentenceEnglish2 = findViewById(R.id.detail_sentence_English_2);
        sentenceEnglish3 = findViewById(R.id.detail_sentence_English_3);
        sentenceChinese1 = findViewById(R.id.detail_sentence_Chinese_1);
        sentenceChinese2 = findViewById(R.id.detail_sentence_Chinese_2);
        sentenceChinese3 = findViewById(R.id.detail_sentence_Chinese_3);
        next = findViewById(R.id.detail_next);

        initText();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        beginTime = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        StorageUtil.updateInt(DetailActivity.this, StorageUtil.STUDY_TIME, ((StorageUtil.getInt
                (DetailActivity.this, StorageUtil.STUDY_TIME, 0) + (int) (System
                .currentTimeMillis() - beginTime))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolnar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(DetailActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_delete:
                todayWord.setLevel(-1);
                setResult(RESULT_OK);
                finish();
                break;
            default:

        }
        return true;
    }

    private void initText() {
        detailSpell.setText(todayWord.getSpell());
        detailPhonogram.setText(todayWord.getPhonogram());
        detailMean.setText(todayWord.getMean());
        String[] sentences = todayWord.getSentence().split("\\\\n");
        switch (sentences.length) {
            case 6:
                sentenceEnglish3.setVisibility(View.VISIBLE);
                sentenceChinese3.setVisibility(View.VISIBLE);
                sentenceEnglish3.setText(sentences[4]);
                sentenceChinese3.setText(sentences[5]);
            case 4:
                sentenceEnglish2.setVisibility(View.VISIBLE);
                sentenceChinese2.setVisibility(View.VISIBLE);
                sentenceEnglish2.setText(sentences[2]);
                sentenceChinese2.setText(sentences[3]);
            case 2:
                sentenceEnglish1.setText(sentences[0]);
                sentenceChinese1.setText(sentences[1]);
                break;
            default:
                break;
        }
    }
}
