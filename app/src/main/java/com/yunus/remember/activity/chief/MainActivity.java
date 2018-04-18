package com.yunus.remember.activity.chief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.example.yunus.utils.RWUtil;
import com.yunus.remember.R;
import com.yunus.remember.adapter.MainFragmentPagerAdapter;
import com.yunus.remember.entity.Book;
import com.yunus.remember.entity.Word;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener{

    //UI Objects
    private RadioGroup mainBottom;
    private RadioButton btnHome;
    private RadioButton btnRanking;
    private RadioButton btnMine;
    private ViewPager viewPager;
    private TextView tvSearch;
    private ImageButton ibMessage;

    private MainFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        btnHome.setChecked(true);
        Book book = new Book();
        book.setName("123");
        book.save();
        initDatabase();
    }

    private void bindViews() {
        mainBottom = (RadioGroup) findViewById(R.id.main_bottom_bar);
        btnHome = (RadioButton) findViewById(R.id.main_bottom_home);
        btnRanking = (RadioButton) findViewById(R.id.main_bottom__ranking);
        btnMine = (RadioButton) findViewById(R.id.main_bottom_mine);
        tvSearch = (TextView) findViewById(R.id.main_top_search);
        ibMessage = (ImageButton) findViewById(R.id.main_message);
        mainBottom.setOnCheckedChangeListener(this);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.main_bottom_home:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.main_bottom__ranking:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.main_bottom_mine:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
           default:
               viewPager.setCurrentItem(PAGE_ONE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    btnHome.setChecked(true);
                    break;
                case PAGE_TWO:
                    btnRanking.setChecked(true);
                    break;
                case PAGE_THREE:
                    btnMine.setChecked(true);
                    break;
            }
        }
    }

    private void initDatabase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String words = RWUtil.inputStream2String(getResources().openRawResource(R.raw.word));
                    parseXMLWithPull(words);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            Word word = new Word();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG : {
                        if ("id".equals(nodeName)) {
                            word.setId(Integer.valueOf(xmlPullParser.nextText()));
                        } else if ("spell".equals(nodeName)) {
                           word.setSpell(xmlPullParser.nextText());
                        } else if ("meaning".equals(nodeName)) {
                           word.setMean(xmlPullParser.nextText());
                        } else if ("yinbiao".equals(nodeName)) {
                            word.setPhonogram(xmlPullParser.nextText());
                        } else if ("lx".equals(nodeName)) {
                           word.setSentence(xmlPullParser.nextText());
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("RECORD".equals(nodeName)) {
                            word.save();
                            word = new Word();
                        }
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
