package com.yunus.remember.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunus.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.adapter.DiaryAdapter;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.entity.RegisterCount;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiariesActivity extends BaseActivity {

    Toolbar toolbar;
    CircleImageView image;
    TextView name;
    ListView lvDiaries;
    List<RegisterCount> diaries;
    Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        friend = (Friend) getIntent().getSerializableExtra("person");

        toolbar = findViewById(R.id.diaries_toolbar);
        toolbar.setTitle(R.string.all_diary);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image = findViewById(R.id.dailies_image);
        name = findViewById(R.id.diaries_name);
        lvDiaries = findViewById(R.id.diaries_list);

        if (friend == null) {
            friend = DataSupport.where("id = ?", StorageUtil.getInt(DiariesActivity.this,
                    StorageUtil.USER_ID, 0) + "").findFirst(Friend.class);
            initText();
            HttpUtil.registerCount(friend.getId() + "", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    diaries = gson.fromJson(response.body().string(),
                            new TypeToken<List<RegisterCount>>() {
                            }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DiaryAdapter diaryAdapter = new DiaryAdapter(DiariesActivity.this,
                                    R.layout.item_diary, diaries);
                            lvDiaries.setAdapter(diaryAdapter);
                        }
                    });
                }
            });
        } else {
            name.setText(StorageUtil.getString(DiariesActivity.this, StorageUtil.USER_NAME, ""));
            Glide.with(DiariesActivity.this).load(Base64.decode(StorageUtil.getString
                            (DiariesActivity.this, StorageUtil.PORTRAIT, ""),
                    Base64.DEFAULT)
            ).into(image);
            diaries = DataSupport.order("dayCount desc").find(RegisterCount.class);
            DiaryAdapter diaryAdapter = new DiaryAdapter(DiariesActivity.this,
                    R.layout.item_diary, diaries);
            lvDiaries.setAdapter(diaryAdapter);
        }

        lvDiaries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegisterCount registerCount = diaries.get(position);
                Intent intent = new Intent(DiariesActivity.this, DiaryActivity.class);
                intent.putExtra("date", registerCount);
                intent.putExtra("user", friend);
                startActivity(intent);
            }
        });
    }

    private void initText() {
        name.setText(friend.getName());
        Glide.with(DiariesActivity.this).load(Base64.decode(friend.getPortrait(), Base64.DEFAULT)
        ).into(image);
    }
}
