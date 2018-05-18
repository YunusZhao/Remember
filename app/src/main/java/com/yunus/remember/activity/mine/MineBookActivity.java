package com.yunus.remember.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.yunus.remember.R;
import com.yunus.remember.adapter.BookAdapter;
import com.yunus.remember.entity.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MineBookActivity extends BaseActivity {

    private List<Book> myBook;
    private List<Book> finishBook;
    private Book studyBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_book);

        Toolbar toolbar = findViewById(R.id.mine_book_toolbar);

        toolbar.setTitle(R.string.my_book);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView bookName = findViewById(R.id.mine_book_study_name);
        TextView wordNum = findViewById(R.id.mine_book_study_word_num);
        ProgressBar bookProgress = findViewById(R.id.mine_book_progress);
        TextView progressText = findViewById(R.id.mine_book_progress_text);
        TextView finishTime = findViewById(R.id.book_finish_time);

        initBook();
        LogUtil.d("MineBookActivity", DataSupport.count(Book.class) + "");
        LogUtil.d("MineBookActivity", studyBook.toString());

        bookName.setText(studyBook.getName());
        wordNum.setText(studyBook.getWordNum() + "");
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
            BookAdapter myAdapter = new BookAdapter(MineBookActivity.this, R.layout.item_book,
                    myBook);
            myList.setAdapter(myAdapter);
        }
        if (finishBook.isEmpty()) {
            finishList.setVisibility(View.GONE);
        } else {
            BookAdapter finishAdapter = new BookAdapter(MineBookActivity.this, R.layout
                    .item_book, finishBook);
            finishList.setAdapter(finishAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addbook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_book:
                Intent intent = new Intent(MineBookActivity.this, BooksActivity.class);
                startActivity(intent);
                break;
            default:

        }
        return true;
    }

    private void initBook() {
        myBook = DataSupport.where("state = ?", "0").find(Book.class);
        finishBook = DataSupport.where("state = ?", "1").find(Book.class);
        studyBook = DataSupport.where("state = ?", "-1").findFirst(Book.class);
    }
}
