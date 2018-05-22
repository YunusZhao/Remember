package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.WordAdapter;
import com.yunus.remember.entity.TodayWord;
import com.yunus.remember.entity.Word;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MineWordsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup words;
    private RadioButton today;
    private RadioButton newWord;
    private RadioButton study;
    private RadioButton had;
    private RadioButton simple;
    private ListView wordListView;
    private List<Word> wordList = new ArrayList<>();
    private List<TodayWord> todayWordList;
    WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_words);

        Toolbar toolbar = findViewById(R.id.mine_words_toolbar);
        toolbar.setTitle("单词列表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        today = findViewById(R.id.today_word);
        newWord = findViewById(R.id.new_word);
        study = findViewById(R.id.study_word);
        had = findViewById(R.id.had_word);
        simple = findViewById(R.id.simple_word);

        bindViews();

        adapter = new WordAdapter(this, R.layout.item_word, wordList);
        wordListView.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.new_word:
                wordList = DataSupport.where("level = 5").find(Word.class);
                adapter = new WordAdapter(this, R.layout.item_word, wordList);
                wordListView.setAdapter(adapter);
                break;
            case R.id.study_word:
                wordList = DataSupport.where("level < 5 and level > 0").find(Word.class);
                adapter = new WordAdapter(this, R.layout.item_word, wordList);
                wordListView.setAdapter(adapter);
                break;
            case R.id.had_word:
                wordList = DataSupport.where("level = 0").find(Word.class);
                adapter = new WordAdapter(this, R.layout.item_word, wordList);
                wordListView.setAdapter(adapter);
                break;
            case R.id.simple_word:
                wordList = DataSupport.where("level = -1").find(Word.class);
                adapter = new WordAdapter(this, R.layout.item_word, wordList);
                wordListView.setAdapter(adapter);
                break;
            case R.id.today_word:
            default:
                todayWordList = DataSupport.findAll(TodayWord.class);
                for (TodayWord todayWord : todayWordList) {
                    wordList.add(new Word(todayWord.getSpell(), todayWord.getMean(), todayWord
                            .getPhonogram()));
                }
                adapter = new WordAdapter(this, R.layout.item_word, wordList);
                wordListView.setAdapter(adapter);
                break;
        }
    }

    private void bindViews() {
        words = findViewById(R.id.words_group);
        today = findViewById(R.id.today_word);
        newWord = findViewById(R.id.new_word);
        study = findViewById(R.id.study_word);
        had = findViewById(R.id.had_word);
        simple = findViewById(R.id.simple_word);
        wordListView = findViewById(R.id.mine_words_list_view);

        words.setOnCheckedChangeListener(this);
        today.setChecked(true);
    }
}
