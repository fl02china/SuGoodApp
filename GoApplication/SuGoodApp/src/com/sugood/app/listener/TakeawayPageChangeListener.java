package com.sugood.app.listener;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;


import com.sugood.app.R;

import java.util.List;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 00:06.
 */

public class TakeawayPageChangeListener implements ViewPager.OnPageChangeListener {

    List<Fragment> mList;
    List<ImageView> mDotList;

    public TakeawayPageChangeListener(List<Fragment> imgList, List<ImageView> dotList) {
        this.mList = imgList;
        this.mDotList = dotList;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub

        changeDotsBg(position % mList.size());
    }

    private void changeDotsBg(int currentitem) {
        for (int i = 0; i < mDotList.size(); i++) {
            mDotList.get(i).setBackgroundResource(R.drawable.circle_uncheck_bg
            );
        }
        mDotList.get(currentitem).setBackgroundResource(
                R.drawable.circle_check_bg);
    }

}
