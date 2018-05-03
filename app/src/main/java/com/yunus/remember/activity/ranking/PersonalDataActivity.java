package com.yunus.remember.activity.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.home.DiariesActivity;
import com.yunus.remember.activity.mine.FriendActivity;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.StorageUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDataActivity extends BaseActivity {

    CircleImageView image;
    ImageButton back;
    ImageButton write;
    TextView name;
    Button friend;
    Button register;
    TextView sex;
    TextView birthday;
    TextView place;
    TextView school;
    TextView summary;
    Friend person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        person = (Friend) getIntent().getSerializableExtra("friend");

        image = findViewById(R.id.personal_image);
        back = findViewById(R.id.personal_back);
        write = findViewById(R.id.personal_write);
        name = findViewById(R.id.personal_name);
        friend = findViewById(R.id.personal_friend);
        register = findViewById(R.id.personal_register);
        sex = findViewById(R.id.personal_sex);
        birthday = findViewById(R.id.personal_birthday);
        place = findViewById(R.id.personal_place);
        school = findViewById(R.id.personal_school);
        summary = findViewById(R.id.personal_summary);

        initText();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, PersonalDataUpdateActivity
                        .class);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, FriendActivity.class);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, DiariesActivity.class);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });
    }

    private void initText() {
        if (StorageUtil.getInt(PersonalDataActivity.this, StorageUtil.USER_ID, 0) == (person
                .getId())) {
            write.setVisibility(View.GONE);
        }
        Glide.with(PersonalDataActivity.this).load(person.getPortrait()).into(image);
        name.setText(person.getName());
        friend.setText("好友" + person.getFriendNum() + "人");
        register.setText("打卡" + person.getRegisterNum() + "天");
        sex.setText(person.getSex());
        birthday.setText(person.getBirthday());
        place.setText(person.getPlace());
        school.setText(person.getSchool());
        summary.setText(person.getSummary());
    }
}
