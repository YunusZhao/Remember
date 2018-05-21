package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.utils.StorageUtil;

public class SetupStudyActivity extends BaseActivity {

    RadioGroup radioGroup;
    RadioButton button1;
    RadioButton button2;
    RadioButton button3;
    RadioButton button4;
    RadioButton button5;
    RadioButton button6;
    RadioButton button7;
    RadioButton button8;
    RadioButton button9;
    RadioButton button0;

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

        button1 = findViewById(R.id.setup_study_rb_1);
        button2 = findViewById(R.id.setup_study_rb_2);
        button3 = findViewById(R.id.setup_study_rb_3);
        button4 = findViewById(R.id.setup_study_rb_4);
        button5 = findViewById(R.id.setup_study_rb_5);
        button6 = findViewById(R.id.setup_study_rb_6);
        button7 = findViewById(R.id.setup_study_rb_7);
        button8 = findViewById(R.id.setup_study_rb_8);
        button9 = findViewById(R.id.setup_study_rb_9);
        button0 = findViewById(R.id.setup_study_rb_0);

        radioGroup = findViewById(R.id.setup_study_rg);

        int defaultNum = StorageUtil.getInt(SetupStudyActivity.this, StorageUtil.TODAY_NUM, 0);
        switch (defaultNum) {
            case 50:
                button1.setChecked(true);
                break;
            case 100:
                button2.setChecked(true);
                break;
            case 150:
                button3.setChecked(true);
                break;
            case 200:
                button4.setChecked(true);
                break;
            case 250:
                button5.setChecked(true);
                break;
            case 300:
                button6.setChecked(true);
                break;
            case 400:
                button7.setChecked(true);
                break;
            case 500:
                button8.setChecked(true);
                break;
            case 600:
                button9.setChecked(true);
                break;
            case 700:
                button0.setChecked(true);
                break;
            default:
                button2.setChecked(true);
                break;
        }

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
                updateRadio(checkedId);
                StorageUtil.updateInt(SetupStudyActivity.this, StorageUtil.TODAY_NUM, num);
                StorageUtil.updateInt(SetupStudyActivity.this, StorageUtil.TODAY_NEW_NUM, num / 5);
                Toast.makeText(SetupStudyActivity.this, "明日开始，每日学习单词变更为" + num + "个", Toast
                        .LENGTH_SHORT).show();
            }
        });
    }

    private void updateRadio(int id) {
        button1.setChecked(false);
        button2.setChecked(false);
        button3.setChecked(false);
        button4.setChecked(false);
        button5.setChecked(false);
        button6.setChecked(false);
        button7.setChecked(false);
        button8.setChecked(false);
        button9.setChecked(false);
        button0.setChecked(false);
        ((RadioButton) findViewById(id)).setChecked(true);
    }
}
