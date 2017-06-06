package com.sugood.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/13.
 */
public class BankListPageAdapter extends PagerAdapter {

    private ArrayList<View> viewList;

    public BankListPageAdapter() {

    }
   public void setViewSize(ArrayList<View> viewList){
       this.viewList = viewList;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(viewList.get(position));
//        super.destroyItem(container, position, object);

    }

    @Override
    public int getCount() {
        Log.e("iiii", "getCount: dss"+ viewList.size());
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(viewList.get(position), 0);
        Log.e("TAG", "instantiateItem: "+position);
        return viewList.get(position);
    }



}
