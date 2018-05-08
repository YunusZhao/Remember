package com.yunus.remember.utils;

import com.yunus.remember.MyApplication;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final String address = "http://123.34.43.1";
    private static final String login = address + "/user/login";
    private static final String searchWord = address + "/searchWord";
    private static final String searchFriend = address + "/searchFriend";
    private static final String changeName = address + "/name";
    private static final String changePassword = address + "/password";
    private static final String register = address + "/user/register";
    private static final String userBook = address + "/book/userBook";
    private static final String registerCount = address + "/user/registerCount";
    private static final String friend = address + "/user/friend";
    private static final String word = address + "/word/getWord";

    public static void get(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(RequestBody body, String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("cookie", StorageUtil
                .getString(MyApplication
                        .getContext(), StorageUtil.SESSION, " ")).post(body).build();
        client.newCall(request).enqueue(callback);
    }

    public static void register(RequestBody body, okhttp3.Callback callback) {
        post(body, register, callback);
    }

    public static void login(RequestBody body, okhttp3.Callback callback) {
        post(body, login, callback);
    }

    public static void word(String id, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("id", id).url(word).build();
        client.newCall(request).enqueue(callback);
    }

    public static void registerCount(String id, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("id", id).url(registerCount).build();
        client.newCall(request).enqueue(callback);
    }

    public static void friend(String id, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("id", id).url(friend).build();
        client.newCall(request).enqueue(callback);
    }

    public static void userBook(String id, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("id", id).url(userBook).build();
        client.newCall(request).enqueue(callback);
    }

    public static void searchWord(String data, okhttp3.Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("data", data)
                .build();
        post(requestBody, searchWord, callback);
    }

    public static void getBook(okhttp3.Callback callback) {
        get(searchWord, callback);
    }

    public static void changeName(int id, String name, okhttp3.Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("id", id + "")
                .add("name", name)
                .build();
        post(requestBody, changeName, callback);
    }

    public static void changePassword(int id, String name, okhttp3.Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("id", id + "")
                .add("password", name)
                .build();
        post(requestBody, changePassword, callback);
    }

    public static void searchFriend(String data, okhttp3.Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("data", data)
                .build();
        post(requestBody, searchFriend, callback);
    }
}
