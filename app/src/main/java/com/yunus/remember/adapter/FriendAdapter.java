package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {

    private int resourceId;


    public FriendAdapter(@NonNull Context context, int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        resourceId = resource;
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
            viewHolder.name = view.findViewById(R.id.item_friend_name);
            viewHolder.summary = view.findViewById(R.id.item_friend_summary);
            viewHolder.right = view.findViewById(R.id.item_friend_right);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(friend.getName());
        viewHolder.summary.setText(friend.getSummary());
        viewHolder.right.setVisibility(View.GONE);
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView summary;
        LinearLayout right;
    }
}
