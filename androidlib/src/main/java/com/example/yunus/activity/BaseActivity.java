package com.example.yunus.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yunus.utils.ActivityCollector;
import com.example.yunus.utils.LogUtil;

/**
 * Created by yun on 2017/10/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //接收其他页面的intent参数
    protected abstract void initVariables();
    //加载布局，初始化控件，注册点击事件
    protected abstract void initViews(Bundle savedInstanceState);
    //调用MobileAPI获取数据
    protected abstract void loadData();
}
