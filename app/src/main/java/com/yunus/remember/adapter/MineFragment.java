package com.yunus.remember.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.NoticeActivity;
import com.yunus.remember.activity.home.DiariesActivity;
import com.yunus.remember.activity.mine.MineBookActivity;
import com.yunus.remember.activity.mine.MineWordsActivity;
import com.yunus.remember.activity.mine.ProgressActivity;
import com.yunus.remember.activity.mine.SetupActivity;
import com.yunus.remember.utils.StorageUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yun on 2018/3/29.
 */

public class MineFragment extends Fragment {
    public MineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CircleImageView image = getActivity().findViewById(R.id.main_image);
        TextView name = getActivity().findViewById(R.id.main_name);
        TextView email = getActivity().findViewById(R.id.main_email);
        LinearLayout register = getActivity().findViewById(R.id.main_register);
        TextView registerText = getActivity().findViewById(R.id.main_register_text);
        Button book = getActivity().findViewById(R.id.main_book);
        Button word = getActivity().findViewById(R.id.main_word);
        Button progress = getActivity().findViewById(R.id.main_progress);
        Button message = getActivity().findViewById(R.id.main_message);
        Button setup = getActivity().findViewById(R.id.main_setup);

        //init Text
        Glide.with(getActivity()).load(StorageUtil.getString(getActivity(), StorageUtil.PORTRAIT,
                "").getBytes()).into(image);
        name.setText(StorageUtil.getString(getActivity(), StorageUtil.USER_NAME, ""));
        email.setText(StorageUtil.getString(getActivity(), StorageUtil.EMAIL, ""));
        registerText.setText("打卡" + StorageUtil.getInt(getActivity(), StorageUtil.REGISTER_DAY,
                0) + "天");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DiariesActivity.class);
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MineBookActivity.class);
                startActivity(intent);
            }
        });
        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MineWordsActivity.class);
                startActivity(intent);
            }
        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProgressActivity.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetupActivity.class);
                startActivity(intent);
            }
        });
    }

}
