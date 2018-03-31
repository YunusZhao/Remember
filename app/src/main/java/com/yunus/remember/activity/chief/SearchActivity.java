package com.yunus.remember.activity.chief;

import com.example.yunus.utils.LogUtil;
import com.yunus.remember.R;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.adapter.SearchWordAdapter;
import com.yunus.remember.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private List<Word> wordList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.searchListView);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView.onActionViewExpanded();
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                wordList.clear();
                wordList.addAll(getWords());
                LogUtil.d("before", wordList.toString());
                setAdapter(wordList);
                return false;
            }
        });
    }

    private void setAdapter(List<Word> list) {
        if (listView.getAdapter() == null) {
            SearchWordAdapter adapter = new SearchWordAdapter(SearchActivity.this,
                    R.layout.search_word_item, list);
            listView.setAdapter(adapter);
        } else {
            ((SearchWordAdapter)listView.getAdapter()).notifyDataSetChanged();
            LogUtil.d("SearchActivity", list.toString());
        }
    }

    private List<Word> getWords() {
        List<Word> list = new ArrayList<>();
        list.add(new Word("qwe", "as"));
        return list;
    }

}
