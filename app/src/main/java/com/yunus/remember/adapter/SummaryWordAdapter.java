package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.entity.Word;

import java.util.List;

/**
 * Created by yun on 2018/4/1.
 */

public class SummaryWordAdapter extends ArrayAdapter<Word> {
    private int resourceId;

    public SummaryWordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView spell = (TextView) view.findViewById(R.id.summary_word_spell);
        TextView mean = (TextView) view.findViewById(R.id.summary_word_chinese_mean);
        spell.setText(word.getSpell());
        mean.setText(word.getMean());
        return view;
    }
}
