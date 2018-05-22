package com.yunus.remember.activity.begin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.MainActivity;
import com.yunus.remember.entity.SevenDaysReview;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Thread(this).start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            //todo 添加引导页

            String email = StorageUtil.getString(WelcomeActivity.this, StorageUtil.EMAIL, " ");
            if (" ".equals(email)) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (DataSupport.count(SevenDaysReview.class) == 0) {
            List<SevenDaysReview> reviews = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                SevenDaysReview review = new SevenDaysReview();
                review.setTheDate("2018.05." + (16 + i));
                review.setAllWordsCount(200 + i * 20);
                review.setAllHadCount(100 + i * 20);
                review.setStudiedTime((int) (20 + Math.random() * (60 - 20 + 1)));
                review.setTodayStudiedCount((int) (60 + Math.random() * (100 - 60 + 1)));
                reviews.add(review);
            }
            DataSupport.saveAll(reviews);
        }
    }
}
