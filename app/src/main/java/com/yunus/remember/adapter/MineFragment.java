package com.yunus.remember.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunus.remember.R;

/**
 * Created by yun on 2018/3/29.
 */

public class MineFragment extends Fragment {
    public MineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_mine, container, false);
        TextView tv = (TextView) view.findViewById(R.id.txt_content);
        tv.setText("mine");
        return view;
    }
}
