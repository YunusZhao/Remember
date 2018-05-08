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
import android.widget.Toast;

import com.yunus.remember.R;
import com.yunus.remember.activity.mine.FriendNewActivity;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewFriendAdapter extends ArrayAdapter<Friend> {

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
                HttpUtil.addFriend(StorageUtil.getInt(getContext(), StorageUtil.USER_ID, 0) + "",
                        friend.getId() + "", new Callback() {


                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws
                                    IOException {
                                friend.save();
                                ((FriendNewActivity)getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

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
