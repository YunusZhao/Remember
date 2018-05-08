package com.yunus.remember.adapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.activity.home.DiariesActivity;
import com.yunus.remember.activity.home.TestActivity;
import com.yunus.remember.activity.mine.BooksActivity;
import com.yunus.remember.activity.mine.ProgressActivity;
import com.yunus.remember.entity.Book;
import com.yunus.remember.entity.SevenDaysReview;
import com.yunus.remember.entity.TodayWord;
import com.yunus.remember.entity.Word;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yun on 2018/3/29.
 */

public class HomeFragment extends Fragment {

    TextView doneDay;
    TextView newNum;
    TextView todayNum;
    TextView remainNum;
    TextView mineNum;
    TextView btMineNum;
    Button startStudy;
    LinearLayout numLayout;
    LinearLayout welcomeLayout;
    LinearLayout commonLayout;
    TextView addBook;
    ProgressBar progress;
    ImageView allDiary;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        doneDay = getActivity().findViewById(R.id.done_day);
        newNum = getActivity().findViewById(R.id.home_num_new);
        todayNum = getActivity().findViewById(R.id.home_num_today);
        remainNum = getActivity().findViewById(R.id.home_num_remain);
        mineNum = getActivity().findViewById(R.id.home_num_mine);
        btMineNum = getActivity().findViewById(R.id.home_bt_mine);
        startStudy = getActivity().findViewById(R.id.start_study);
        numLayout = getActivity().findViewById(R.id.home_num_layout);
        welcomeLayout = getActivity().findViewById(R.id.home_welcome_layout);
        commonLayout = getActivity().findViewById(R.id.home_common_layout);
        addBook = getActivity().findViewById(R.id.home_add_book);
        progress = getActivity().findViewById(R.id.home_progress);
        allDiary = getActivity().findViewById(R.id.home_calendar);

        startStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });

        btMineNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProgressActivity.class);
                startActivity(intent);
            }
        });

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BooksActivity.class);
                getActivity().startActivity(intent);
            }
        });

        allDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiariesActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!StorageUtil.getString(getContext(), StorageUtil.TODAY_DATE, " ").equals(StorageUtil
                .getToday())) {

            commonLayout.setVisibility(View.GONE);
            numLayout.setVisibility(View.INVISIBLE);
            welcomeLayout.setVisibility(View.VISIBLE);
            startStudy.setText("准备中");
            startStudy.setEnabled(false);

            if (!(DataSupport.count(Book.class) == 0)) {
                new DownloadTask().execute();
            }
        }
    }

    private void initText() {
        doneDay.setText(String.valueOf(StorageUtil.getInt(getContext(), StorageUtil.REGISTER_DAY,
                0)));
        newNum.setText(String.valueOf(StorageUtil.getInt(getContext(), StorageUtil
                .TODAY_REAL_NEW_NUM, 0)));
        todayNum.setText(String.valueOf(StorageUtil.getInt(getContext(), StorageUtil.TODAY_NUM,
                0)));
        remainNum.setText(String.valueOf(StorageUtil.getInt(getContext(), StorageUtil
                .TODAY_REMAIN_NUM, 0)));
        mineNum.setText(String.valueOf(StorageUtil.getInt(getContext(), StorageUtil.WORDS_NUM, 0)));
    }

    private Word getWord(int level) {
        if (DataSupport.where("level > 0").count(Word.class) <= 0) {
            return null;
        } else {
            if (DataSupport.where("level = ?", level + "").count(Word.class) == 0) {
                return getWord(level % 5 + 1);
            }
            int importance;
            switch ((int) (Math.random() * 10)) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    importance = 3;
                    break;

                case 5:
                case 6:
                case 7:
                    importance = 2;
                    break;

                case 8:
                case 9:
                    importance = 1;
                    break;
                default:
                    importance = 3;

            }
            if (DataSupport.where("level = ? and importance = ?", level + "", importance + "")
                    .count(Word.class) == 0) {
                return getWord(level);
            }
            Word word = DataSupport.where("level = ? and importance = ?", level + "", importance
                    + "").findFirst(Word
                    .class);
            DataSupport.delete(Word.class, word.getId());
            return word;
        }
    }

    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            //前期更新
            List<Word> allWord = DataSupport.where("level > 0").find(Word.class);
            for (Word word : allWord) {
                word.setImportance(word.getImportance() + 1);
            }
            DataSupport.saveAll(allWord);

            //计算要下载单词量
            int needStudyNum = StorageUtil.getInt(getContext(), StorageUtil.WORDS_NUM, 0) -
                    StorageUtil.getInt(getContext(), StorageUtil.WORDS_STUDIED_NUM, 0);
            if (needStudyNum < StorageUtil.getInt(getContext(), StorageUtil.TODAY_NUM, 0)) {
                StorageUtil.updateInt(getContext(), StorageUtil.TODAY_REAL_NEW_NUM, StorageUtil
                        .getInt(getContext(),
                                StorageUtil.TODAY_NUM, 0) - needStudyNum);
            } else {
                int wordNum = DataSupport.where("importance > 3").count(Word.class);
                if (wordNum >= StorageUtil.getInt(getContext(), StorageUtil.TODAY_NEW_NUM, 0)) {
                    StorageUtil.updateInt(getContext(), StorageUtil.TODAY_REAL_NEW_NUM, 0);
                } else {
                    StorageUtil.updateInt(getContext(), StorageUtil.TODAY_REAL_NEW_NUM,
                            StorageUtil.getInt(getContext
                                    (), StorageUtil.TODAY_NEW_NUM, 0) - wordNum);

                }
            }

            //更新上次登陆记录
            DataSupport.deleteAll(SevenDaysReview.class, "theDate before ?", new Date(System
                    .currentTimeMillis() -
                    (long) (6 * 24 * 60 * 60 * 1000)).toString());
            SevenDaysReview lastReview = DataSupport.findLast(SevenDaysReview.class);
            lastReview.setStudiedTime(StorageUtil.getInt(getContext(), StorageUtil.STUDY_TIME, 0));
            lastReview.save();

            //更新今天记录
            int studiedNum = DataSupport.where("level < 1").count(Word.class);
            SevenDaysReview newReview = new SevenDaysReview(new Date(System.currentTimeMillis()),
                    studiedNum);
            newReview.save();

            //本地词库填充
            DataSupport.deleteAll(TodayWord.class);
            int needNum = StorageUtil.getInt(getContext(), StorageUtil.TODAY_NUM, 0) -
                    StorageUtil.getInt(getContext
                            (), StorageUtil.TODAY_REAL_NEW_NUM, 0);
            List<Word> words = DataSupport.where("importance > 3").limit(needNum).order
                    ("importance desc").find(Word
                    .class);
            List<TodayWord> todayWords = new ArrayList<>();
            for (Word word : words) {
                todayWords.add(new TodayWord(word.getSpell(), word.getMean(), word.getPhonogram()
                        , word.getSentence()
                        , 1));
            }
            needNum = needNum - words.size();
            List<Word> saveWords = new ArrayList<>();
            while (needNum > 0) {
                int levelNeed = (needNum + 3) / 4;
                int level;
                for (level = 1; level < 5; level++) {
                    for (int i = 0; i < levelNeed; i++) {
                        saveWords.add(getWord(level));
                        needNum--;
                        if (needNum == 0) {
                            break;
                        }
                    }
                }
            }
            DataSupport.saveAll(saveWords);
            for (Word word : saveWords) {
                todayWords.add(new TodayWord(word.getSpell(), word.getMean(), word.getPhonogram()
                        , word.getSentence()
                        , 1));
            }
            DataSupport.saveAll(todayWords);

            //联网，下载生成今日单词
            RequestBody body = new FormBody.Builder()
                    .add("userId", "" + StorageUtil.getInt(getContext(), StorageUtil.USER_ID, 0))
                    .add("bookId", "" + DataSupport.where("state = -1").findFirst(Book.class)
                            .getId())
                    .add("needNum", "" + StorageUtil.getInt(getContext(), StorageUtil
                            .TODAY_REAL_NEW_NUM, 0))
                    .build();
            HttpUtil.todayWord(body, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    List<Word> netWords = gson.fromJson(result, new TypeToken<List<Word>>() {
                    }.getType());
                    for (Word word : netWords) {
                        word.setLevel(5);
                        word.setImportance(0);
                        new TodayWord(word.getSpell(), word.getMean(), word.getPhonogram(),
                                word.getSentence(), 1).save();
                    }
                    DataSupport.saveAll(netWords);
                }
            });

            StorageUtil.updateString(getContext(), StorageUtil.TODAY_DATE, StorageUtil.getToday());
            StorageUtil.updateInt(getContext(), StorageUtil.STUDY_TIME, 0);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progress.setVisibility(View.GONE);
            commonLayout.setVisibility(View.VISIBLE);
            startStudy.setText("开始学习");
            startStudy.setEnabled(true);
            numLayout.setVisibility(View.VISIBLE);
            welcomeLayout.setVisibility(View.GONE);
            initText();
        }
    }
}