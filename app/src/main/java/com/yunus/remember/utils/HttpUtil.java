package com.yunus.remember.utils;

import com.yunus.remember.MyApplication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final String address = "http://123.34.43.1";

    public static void sendOkhttpRequest(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void postOkhttpRequest(RequestBody body, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("cookie", StorageUtil.getString(MyApplication
                .getContext(), StorageUtil.SESSION, " ")).post(body).build();
        client.newCall(request).enqueue(callback);
    }
}
