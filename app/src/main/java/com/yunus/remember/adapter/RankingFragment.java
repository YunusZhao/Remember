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

import com.yunus.remember.R;
import com.yunus.remember.activity.ranking.RankingActivity;

/**
 * Created by yun on 2018/3/29.
 */

public class RankingFragment extends Fragment {

    Button all;

    public RankingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_ranking, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        all = getActivity().findViewById(R.id.ranking_all);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RankingActivity.class);
                intent.putExtra("model", "all");
                startActivity(intent);
            }
        });
    }
}
