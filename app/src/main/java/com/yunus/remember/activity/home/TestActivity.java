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
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.SearchActivity;
import com.yunus.remember.entity.Word;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Locale;

public class TestActivity extends BaseActivity {

    Toolbar toolbar;
    TextView spell;
    TextView phonogram;
    ImageButton voice;
    TextView mean;
    Button next;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = (Toolbar) findViewById(R.id.test_toolbar);

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
        voice = findViewById(R.id.test_voice);
        mean = findViewById(R.id.test_word_mean);
        next = findViewById(R.id.test_next);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });

        initText();

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null) {
                    tts.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    tts.speak(spell.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
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

                break;
            default:

        }
        return true;
    }

    private void initText() {
        List<Word> words = DataSupport.findAll(Word.class);
        spell.setText(words.get(0).getSpell());
        phonogram.setText(words.get(0).getPhonogram());
        mean.setText(words.get(0).getMean());
    }
}
