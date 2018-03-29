package com.yunus.remember.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yunus.remember.activity.chief.MainActivity;

/**
 * Created by yun on 2018/3/29.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private HomeFragment homeFragment = null;
    private RankingFragment rankingFragment = null;
    private MineFragment mineFragment = null;


    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        rankingFragment = new RankingFragment();
        mineFragment = new MineFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = homeFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = rankingFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = mineFragment;
                break;
            default:
                fragment = homeFragment;
        }
        return fragment;
    }
}
