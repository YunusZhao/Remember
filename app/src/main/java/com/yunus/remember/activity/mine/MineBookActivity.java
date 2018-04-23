package com.yunus.remember.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.adapter.BookAdapter;
import com.yunus.remember.entity.Book;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MineBookActivity extends BaseActivity {

    private List<Book> myBook;
    private List<Book> finishBook;
    private Book studyBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_book);

        TextView bookName = findViewById(R.id.book_name);
        TextView wordNum = findViewById(R.id.book_word_num);
        ProgressBar bookProgress = findViewById(R.id.book_progress);
        TextView progressText = findViewById(R.id.book_progress_text);
        TextView finishTime = findViewById(R.id.book_finish_time);

        initBook();

        bookName.setText(studyBook.getName());
        wordNum.setText(studyBook.getWordNum());
        int progress = studyBook.getStudyWordNum() * 100 / studyBook.getWordNum();
        bookProgress.setProgress(progress);
        String string = progress + "%";
        progressText.setText(string);
        finishTime.setText("//完成");

        ListView myList = findViewById(R.id.book_mine_list);
        ListView finishList = findViewById(R.id.book_finish_list);
        if (myBook.isEmpty()) {
            myList.setVisibility(View.GONE);
        } else {
            BookAdapter myAdapter = new BookAdapter(MineBookActivity.this, R.layout.item_book, myBook);
            myList.setAdapter(myAdapter);
        }
        if (finishBook.isEmpty()) {
            finishList.setVisibility(View.GONE);
        } else {
            BookAdapter finishAdapter = new BookAdapter(MineBookActivity.this, R.layout.item_book, finishBook);
            finishList.setAdapter(finishAdapter);
        }
    }

    private void initBook() {
        myBook = DataSupport.where("state = ?", "0").find(Book.class);
        finishBook = DataSupport.where("state = ?", "1").find(Book.class);
        studyBook = DataSupport.where("state = ?", "-1").findFirst(Book.class);
    }
}
