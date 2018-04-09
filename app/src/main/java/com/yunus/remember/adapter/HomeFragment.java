package com.yunus.remember.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.activity.home.TestActivity;
import com.yunus.remember.activity.mine.ProgressActivity;

/**
 * Created by yun on 2018/3/29.
 */

public class HomeFragment extends Fragment {

    TextView doneDay;
    TextView newNum;
    TextView todayNum;
    TextView remainNum;
    TextView mineNum;
    TextView btMineNum;
    Button startStudy;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        doneDay = getActivity().findViewById(R.id.done_day);
        newNum = getActivity().findViewById(R.id.home_num_new);
        todayNum = getActivity().findViewById(R.id.home_num_today);
        remainNum = getActivity().findViewById(R.id.home_num_remain);
        mineNum = getActivity().findViewById(R.id.home_num_mine);
        btMineNum = getActivity().findViewById(R.id.home_bt_mine);
        startStudy = (Button) getActivity().findViewById(R.id.start_study);

        initText();

        startStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });

        btMineNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProgressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initText() {
        doneDay.setText("6");
        newNum.setText("60");
        todayNum.setText("300");
        remainNum.setText("254");
        mineNum.setText("671");
    }
}
