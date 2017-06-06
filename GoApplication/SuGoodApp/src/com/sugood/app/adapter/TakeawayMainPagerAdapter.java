package com.sugood.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 17:54.
 */

public class TakeawayMainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public TakeawayMainPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }




}
