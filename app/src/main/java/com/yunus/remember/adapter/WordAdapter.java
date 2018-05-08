package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.entity.Word;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private int resourceId;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.spell = view.findViewById(R.id.item_word_spell);
            viewHolder.mean = view.findViewById(R.id.item_word_mean);
            viewHolder.phonogram = view.findViewById(R.id.item_word_phonogram);
            viewHolder.voice = view.findViewById(R.id.item_word_voice);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.spell.setText(word.getSpell());
        viewHolder.mean.setText(word.getMean());
        viewHolder.phonogram.setText(word.getPhonogram());
        viewHolder.voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        return view;
    }

    class ViewHolder {
        TextView spell;
        TextView mean;
        TextView phonogram;
        ImageButton voice;
    }
}
