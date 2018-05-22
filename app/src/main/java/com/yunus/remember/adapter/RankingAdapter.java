package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends ArrayAdapter<Friend> {
    private int resourceId;

    public RankingAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Friend>
            objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Friend friend = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ranking = view.findViewById(R.id.ranking_id);
            viewHolder.image = view.findViewById(R.id.ranking_image);
            viewHolder.name = view.findViewById(R.id.ranking_name);
            viewHolder.summary = view.findViewById(R.id.ranking_summary);
            viewHolder.allNum = view.findViewById(R.id.ranking_num);
            viewHolder.allTime = view.findViewById(R.id.ranking_time);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ranking.setText((position + 1) + "");
        Glide.with(view.getContext()).load(Base64.decode(friend.getPortrait(), Base64.DEFAULT))
                .into(viewHolder.image);
        viewHolder.name.setText(friend.getName());
        viewHolder.summary.setText(friend.getSummary());
        viewHolder.allNum.setText("单词数 " + friend.getAllNum());
        viewHolder.allTime.setText("时间 " + friend.getAllTime());
        return view;
    }

    class ViewHolder {
        TextView ranking;
        CircleImageView image;
        TextView name;
        TextView summary;
        TextView allNum;
        TextView allTime;
    }
}
