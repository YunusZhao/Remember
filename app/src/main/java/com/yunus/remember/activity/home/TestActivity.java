package com.yunus.remember.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.SearchActivity;
import com.yunus.remember.entity.SevenDaysReview;
import com.yunus.remember.entity.TodayWord;
import com.yunus.remember.entity.Word;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestActivity extends BaseActivity {

    Toolbar toolbar;
    TextView spell;
    TextView phonogram;
    TextView sentenceWord;
    ImageButton voice;
    ImageButton sentenceVoice;
    TextView mean;
    Button know;
    Button unknow;
    Button detail;
    TextToSpeech tts;
    LinearLayout sentence;
    List<TodayWord> words;
    int position;
    long beginTime;
    Runnable command = new Runnable() {
        @Override
        public void run() {
            updateWord(words.get(position));
        }
    };
    ExecutorService single = Executors.newSingleThreadExecutor();
    private byte state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = findViewById(R.id.test_toolbar);

        toolbar.setTitle(R.string.self_test);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spell = findViewById(R.id.test_spell);
        phonogram = findViewById(R.id.test_phonogram);
        sentenceWord = findViewById(R.id.test_word_sentence);
        voice = findViewById(R.id.test_voice);
        sentenceVoice = findViewById(R.id.test_voice_sentence);
        mean = findViewById(R.id.test_word_mean);
        know = findViewById(R.id.test_know);
        unknow = findViewById(R.id.test_unknow);
        detail = findViewById(R.id.test_detail);
        sentence = findViewById(R.id.test_sentence);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });

        words = DataSupport.where("level > ?", "0").find(TodayWord.class);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null) {
                    tts.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    tts.speak(spell.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        sentenceVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null) {
                    tts.setPitch(1.2f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    tts.speak(sentenceWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                words.get(position).setLevel(words.get(position).getLevel() - 1);

                //已经学会，更改单词数据库数据
                if (words.get(position).getLevel() == 0) {
                    single.execute(command);
                }
                Intent intent = new Intent(TestActivity.this, DetailActivity.class);
                intent.putExtra("today_word", words.get(position));
                startActivity(intent);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                words.get(position).setLevel(3);
                Intent intent = new Intent(TestActivity.this, DetailActivity.class);
                intent.putExtra("today_word", words.get(position));
                startActivity(intent);
            }
        });

        unknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state) {
                    case 0:
                        sentence.setVisibility(View.VISIBLE);
                        if (tts != null) {
                            tts.setPitch(1.2f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                            tts.speak(sentenceWord.getText().toString(), TextToSpeech
                                    .QUEUE_FLUSH, null);
                        }
                        state += 1;
                        words.get(position).setLevel(2);
                        break;
                    case 1:
                        mean.setVisibility(View.VISIBLE);
                        know.setVisibility(View.GONE);
                        unknow.setVisibility(View.GONE);
                        detail.setVisibility(View.VISIBLE);
                        words.get(position).setLevel(3);
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        beginTime = System.currentTimeMillis();
        initText();
    }

    @Override
    protected void onStop() {
        super.onStop();
        StorageUtil.updateInt(TestActivity.this, StorageUtil.STUDY_TIME, ((StorageUtil.getInt
                (TestActivity.this, StorageUtil.STUDY_TIME, 0) + (int) (System.currentTimeMillis
                () - beginTime))));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save学习时间
        Date today = new Date(System.currentTimeMillis());
        SevenDaysReview review = DataSupport.where("date = ?", today.toString()).findLast
                (SevenDaysReview.class);
        LogUtil.d("TestActivity", today.toString());
        review.setStudiedTime(StorageUtil.getInt(this, StorageUtil.STUDY_TIME, 0));
        review.save();

        DataSupport.saveAll(words);
        if (tts != null) {
            tts.shutdown();
        }
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
                Intent intent = new Intent(TestActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_delete:
                words.get(position).setLevel(-1);
                single.execute(command);
                initText();
                break;
            default:

        }
        return true;
    }

    private void initText() {
        state = 0;
        updateView();
        do {
            position = (int) (Math.random() * words.size());
        } while (words.get(position).getLevel() <= 0);
        spell.setText(words.get(position).getSpell());
        phonogram.setText(words.get(position).getPhonogram());
        sentenceWord.setText(words.get(position).getSentence());
        mean.setText(words.get(position).getMean());
    }

    private void updateView() {
        sentence.setVisibility(View.GONE);
        mean.setVisibility(View.GONE);
        know.setVisibility(View.VISIBLE);
        unknow.setVisibility(View.VISIBLE);
        detail.setVisibility(View.GONE);
    }

    private void updateWord(TodayWord todayWord) {
        Word word = DataSupport.where("spell = ?", todayWord.getSpell()).findFirst(Word.class);
        word.setImportance(0);
        if (todayWord.getLevel() == -1) {
            word.setLevel(-1);
        } else {
            word.setLevel(word.getLevel() - 1);
        }
        word.save();
    }
}
