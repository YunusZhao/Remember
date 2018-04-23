package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.entity.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private int resourceId;

    public BookAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Book> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Book book = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.bookName = view.findViewById(R.id.book_name);
            viewHolder.wordNum = view.findViewById(R.id.book_word_num);
            viewHolder.bookProgress = view.findViewById(R.id.book_progress);
            viewHolder.progressText = view.findViewById(R.id.book_progress_text);
            viewHolder.finishTime = view.findViewById(R.id.book_finish_time);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bookName.setText(book.getName());
        viewHolder.wordNum.setText(book.getWordNum());
        int progress = book.getStudyWordNum() * 100 / book.getWordNum();
        viewHolder.bookProgress.setProgress(progress);
        String string = progress + "%";
        viewHolder.progressText.setText(string);
        viewHolder.finishTime.setText("//完成");
        return view;
    }

    class ViewHolder {
        TextView bookName;
        TextView wordNum;
        ProgressBar bookProgress;
        TextView progressText;
        TextView finishTime;
    }
}
