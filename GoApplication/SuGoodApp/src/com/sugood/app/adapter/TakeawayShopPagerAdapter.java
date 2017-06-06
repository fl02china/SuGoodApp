package com.sugood.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Package :com.android.supermarket.takeaway.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/2/28 15:54.
 */

public class TakeawayShopPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mList;

    String[] name = {"点  餐", "评  价", "详  情"};

    public TakeawayShopPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;

    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }

}
