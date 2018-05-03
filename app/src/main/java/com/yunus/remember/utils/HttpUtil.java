package com.yunus.remember.utils;

import com.yunus.remember.MyApplication;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final String address = "http://123.34.43.1";
    private static final String login = address + "/logon";
    private static final String searchWord = address + "/searchWord";
    private static final String searchFriend = address + "/searchFriend";
    private static final String changeName = address + "/name";
    private static final String changePassword = address + "/password";

    public static void get(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(RequestBody body, okhttp3.Callback callback) {
        post(body, address, callback);
    }

    public static void post(RequestBody body, String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("cookie", StorageUtil
                .getString(MyApplication
                        .getContext(), StorageUtil.SESSION, " ")).post(body).build();
        client.newCall(request).enqueue(callback);
    }


    public static void login(String email, String password, okhttp3.Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        post(requestBody, login, callback);
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
