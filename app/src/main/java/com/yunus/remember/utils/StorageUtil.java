package com.yunus.remember.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class StorageUtil {

    public static final String REGISTER_DAY = "registerDay";//打卡天数
    public static final String WORDS_NUM = "wordsNum";//单词总数
    public static final String WORDS_STUDIED_NUM = "wordsHadNum";//已掌握单词数
    public static final String TODAY_NUM = "todayNum";//今日单词
    public static final String TODAY_NEW_NUM = "todayNewNum";//今日新单词
    public static final String TODAY_REAL_NEW_NUM = "realNewNum";//实际新单词
    public static final String TODAY_REMAIN_NUM = "todayRemainNum";//今日剩余单词
    public static final String TODAY_DATE = "todayDate";//今天日期
    public static final String STUDY_TIME = "studyTime";//学习时间
    public static final String USER_ID = "userId";//用户ID
    public static final String SESSION = "session";//cookie
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static String getToday() {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return df.format(new Date(System.currentTimeMillis()));

    }

    public static String getDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return df.format(date);
    }

    public static void updateInt(Context context, String name, int data) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, data);
        editor.apply();
    }

    public static void updateString(Context context, String name, String data) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, data);
        editor.apply();
    }

    public static int getInt(Context context, String name, int data) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getInt(name, data);
    }

    public static String getString(Context context, String name, String data) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getString(name, data);
    }

    public static byte[] file2byte(String path) {
        byte[] data = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}
