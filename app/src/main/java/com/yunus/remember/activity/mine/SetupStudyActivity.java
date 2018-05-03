package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.utils.StorageUtil;

public class SetupStudyActivity extends BaseActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_study);

        Toolbar toolbar = findViewById(R.id.setup_study_toolbar);
        toolbar.setTitle("学习量设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioGroup = findViewById(R.id.setup_study_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int num = 100;
                switch (checkedId) {
                    case R.id.setup_study_rb_1:
                        num = 50;
                        break;
                    case R.id.setup_study_rb_2:
                        num = 100;
                        break;
                    case R.id.setup_study_rb_3:
                        num = 150;
                        break;
                    case R.id.setup_study_rb_4:
                        num = 200;
                        break;
                    case R.id.setup_study_rb_5:
                        num = 250;
                        break;
                    case R.id.setup_study_rb_6:
                        num = 300;
                        break;
                    case R.id.setup_study_rb_7:
                        num = 400;
                        break;
                    case R.id.setup_study_rb_8:
                        num = 500;
                        break;
                    case R.id.setup_study_rb_9:
                        num = 600;
                        break;
                    case R.id.setup_study_rb_0:
                        num = 700;
                        break;
                    default:
                        break;
                }
                StorageUtil.updateInt(SetupStudyActivity.this, StorageUtil.TODAY_NUM, num);
                StorageUtil.updateInt(SetupStudyActivity.this, StorageUtil.TODAY_NEW_NUM, num / 5);
            }
        });
    }
}
