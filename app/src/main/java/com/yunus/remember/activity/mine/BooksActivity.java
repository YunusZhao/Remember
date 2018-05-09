package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.adapter.BookAdapter;
import com.yunus.remember.entity.Book;
import com.yunus.remember.utils.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BooksActivity extends BaseActivity {

    Toolbar toolbar;
    ListView bookListView;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        toolbar = findViewById(R.id.books_toolbar);
        toolbar.setTitle(R.string.all_books);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookListView = findViewById(R.id.books_list);

        updateAllBooks();
    }

    private void updateAllBooks() {
        HttpUtil.getBook(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtil.d("book", result);
                Gson gson = new Gson();
                books = gson.fromJson(result, new TypeToken<List<Book>>() {}.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookAdapter bookAdapter = new BookAdapter(BooksActivity.this,
                                R.layout.item_book, books);
                        bookListView.setAdapter(bookAdapter);
                    }
                });

            }
        });
    }
}
