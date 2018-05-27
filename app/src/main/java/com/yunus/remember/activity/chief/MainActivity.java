package com.yunus.remember.activity.chief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.MainFragmentPagerAdapter;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    //UI Objects
    private RadioGroup mainBottom;
    private RadioButton btnHome;
    private RadioButton btnRanking;
    private RadioButton btnMine;
    private ViewPager viewPager;
    private TextView tvSearch;
    private MainFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        btnHome.setChecked(true);
    }

    private void bindViews() {
        mainBottom = findViewById(R.id.main_bottom_bar);
        btnHome = findViewById(R.id.main_bottom_home);
        btnRanking = findViewById(R.id.main_bottom__ranking);
        btnMine = findViewById(R.id.main_bottom_mine);
        tvSearch = findViewById(R.id.main_top_search);
        mainBottom.setOnCheckedChangeListener(this);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.main_view_pager);
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
}
