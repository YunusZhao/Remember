package com.yunus.remember.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yunus.remember.MyApplication;
import com.yunus.remember.R;
import com.yunus.remember.entity.Word;

import java.util.List;
import java.util.Locale;

public class WordAdapter extends ArrayAdapter<Word> {

    private int resourceId;
    TextToSpeech tts;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Word word = getItem(position);
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
                tts = new TextToSpeech(MyApplication.getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        tts.setLanguage(Locale.ENGLISH);
                    }
                });
                tts.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                tts.speak(word.getSpell(), TextToSpeech.QUEUE_FLUSH, null);
                tts.shutdown();
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
