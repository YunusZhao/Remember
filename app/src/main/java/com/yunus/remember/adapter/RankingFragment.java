package com.yunus.remember.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yunus.remember.R;
import com.yunus.remember.activity.ranking.RankingActivity;

/**
 * Created by yun on 2018/3/29.
 */

public class RankingFragment extends Fragment implements View.OnClickListener {

    Button allWord;
    Button allTime;
    Button friendWord;
    Button friendTime;

    public RankingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_ranking, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        allWord = getActivity().findViewById(R.id.ranking_all_word);
        allWord.setOnClickListener(this);
        allTime = getActivity().findViewById(R.id.ranking_all_time);
        allTime.setOnClickListener(this);
        friendWord = getActivity().findViewById(R.id.ranking_friend_word);
        friendWord.setOnClickListener(this);
        friendTime = getActivity().findViewById(R.id.ranking_friend_time);
        friendTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RankingActivity.class);
        switch (v.getId()) {
            case R.id.ranking_all_word:
                intent.putExtra("mode", 0);
                break;
            case R.id.ranking_all_time:
                intent.putExtra("mode", 1);
                break;
            case R.id.ranking_friend_word:
                intent.putExtra("mode", 2);
                break;
            case R.id.ranking_friend_time:
                intent.putExtra("mode", 3);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
