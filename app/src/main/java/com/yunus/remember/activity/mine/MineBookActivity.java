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
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MineBookActivity extends BaseActivity {

    TextView bookName;
    TextView wordNum;
    ProgressBar bookProgress;
    TextView progressText;
    TextView finishTime;
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

        bookName = findViewById(R.id.mine_book_study_name);
        wordNum = findViewById(R.id.mine_book_study_word_num);
        bookProgress = findViewById(R.id.mine_book_progress);
        progressText = findViewById(R.id.mine_book_progress_text);
        finishTime = findViewById(R.id.book_finish_time);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBook();
        LogUtil.d("MineBookActivity", DataSupport.count(Book.class) + "");
        LogUtil.d("MineBookActivity", studyBook.toString());

        bookName.setText(studyBook.getName());
        wordNum.setText(studyBook.getWordNum() + "");
        int progress = studyBook.getStudyWordNum() * 100 / studyBook.getWordNum();
        bookProgress.setProgress(progress);
        String string = progress + "%";
        progressText.setText(string);
        int days = (studyBook.getWordNum() - studyBook.getStudyWordNum())
                / StorageUtil.getInt(MineBookActivity.this, StorageUtil.TODAY_NEW_NUM, 20);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        finishTime.setText("预计"+sdf.format(c.getTime())+"完成");

        ListView myList = findViewById(R.id.book_mine_list);
        ListView finishList = findViewById(R.id.book_finish_list);
        if (myBook.isEmpty()) {

        } else {
            BookAdapter myAdapter = new BookAdapter(MineBookActivity.this, R.layout.item_book,
                    myBook);
            myList.setAdapter(myAdapter);
        }
        if (finishBook.isEmpty()) {

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
