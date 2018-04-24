package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeFriendAdapter extends ArrayAdapter<Friend> {

    private int resourceId;

    public NoticeFriendAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Friend> objects) {
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
            viewHolder.image = view.findViewById(R.id.item_friend_image);
            viewHolder.name = view.findViewById(R.id.item_friend_name);
            viewHolder.summary = view.findViewById(R.id.item_friend_summary);
            viewHolder.addFriend = view.findViewById(R.id.item_friend_add);
            viewHolder.num = view.findViewById(R.id.item_friend_message_num);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        viewHolder.image.;
        viewHolder.name.setText(friend.getName());
        viewHolder.summary.setText(friend.getSummary());
//        viewHolder.num.setText();
        return view;
    }

    class ViewHolder {
        CircleImageView image;
        TextView name;
        TextView summary;
        Button addFriend;
        TextView num;
    }
}
