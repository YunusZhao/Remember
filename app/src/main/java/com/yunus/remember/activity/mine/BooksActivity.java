package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.BookAdapter;
import com.yunus.remember.entity.Book;

import java.util.List;

public class BooksActivity extends BaseActivity {

    Toolbar toolbar;
    ListView bookListView;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        toolbar = (Toolbar) findViewById(R.id.books_toolbar);
        toolbar.setTitle(R.string.allBooks);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookListView = findViewById(R.id.books_list);

        books = getAllBooks();
        BookAdapter bookAdapter = new BookAdapter(BooksActivity.this, R.layout.item_book, books);
        bookListView.setAdapter(bookAdapter);
    }

    private List<Book> getAllBooks() {
        //联网获取全部图书
        return null;
    }
}
