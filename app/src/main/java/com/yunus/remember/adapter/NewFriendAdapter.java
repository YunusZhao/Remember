package com.yunus.remember.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunus.remember.R;
import com.yunus.remember.entity.Friend;

import java.util.List;

public class NewFriendAdapter extends ArrayAdapter<Friend>{

    private int resourceId;


    public NewFriendAdapter(@NonNull Context context, int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Friend friend = getItem(position);
        View view;
        NewFriendAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new NewFriendAdapter.ViewHolder();
            viewHolder.name = view.findViewById(R.id.item_friend_name);
            viewHolder.summary = view.findViewById(R.id.item_friend_summary);
            viewHolder.addFriend = view.findViewById(R.id.item_friend_add);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (NewFriendAdapter.ViewHolder) view.getTag();
        }
        viewHolder.name.setText(friend.getName());
        viewHolder.summary.setText(friend.getSummary());
        viewHolder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo
                friend.save();
                Toast.makeText(v.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView summary;
        Button addFriend;
    }
}
