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
import com.yunus.remember.entity.RegisterCount;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<RegisterCount>{

    private int resourceId;

    public DiaryAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<RegisterCount> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RegisterCount diary = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date = view.findViewById(R.id.diaries_item_date);
            viewHolder.textLong = view.findViewById(R.id.diaries_item_text_long);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.date.setText(diary.getRegisterDate());
        viewHolder.textLong.setText("第"+diary.getDayCount()+"天打卡/学习了"+diary.getWordNum()+"个单词，学习时间"+diary.getStudyTime()+"分钟");
        return view;
    }

    class ViewHolder {
        TextView date;
        TextView textLong;
    }
}
