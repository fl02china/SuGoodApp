package com.sugood.app.helper;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabRadioHelper implements OnCheckedChangeListener, OnPageChangeListener {

	private RadioGroup mGroup;
	private SparseArray<RadioButton> mTabItemMap;
	private int mItemCount = 0;
	private OnCheckedChangeListener mOnCheckedChangeListener;
	private ViewPager mViewPager;
	
	private OnPageChangeListener mListener;
	
	public TabRadioHelper(RadioGroup group) {
		// TODO Auto-generated constructor stub
		if (group == null){
			throw new NullPointerException("The RadioGroup is null");
		}
		mGroup = group;
		mTabItemMap = new SparseArray<RadioButton>();
		initItem();
	}
	
	private void initItem(){
		int viewCount = mGroup.getChildCount();
		for (int i = 0; i < viewCount; i ++){
			View v = mGroup.getChildAt(i);
			if (v instanceof RadioButton){
				mItemCount ++;
				mTabItemMap.put(v.getId(), (RadioButton) v);
			}
		}
		mOnCheckedChangeListener = this;
		mGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
	}
	
	public void setViewPager(ViewPager pager){
		mViewPager = pager;
	}
	
	public RadioButton getTabItemById(int id){
		return mTabItemMap.get(id);
	}
	
	public int getTabItemCount(){
		return mItemCount;
	}
	
	public void switchTab(int pos, boolean noCallback){
		int id = mTabItemMap.keyAt(pos);
		checkId(id, noCallback);
	}
	
	public void checkId(int id, boolean noCallback){
		if (mGroup.getCheckedRadioButtonId() == id) return;
		
		if (noCallback && mOnCheckedChangeListener != null){
			mGroup.setOnCheckedChangeListener(null);
		}
		
		mGroup.check(mTabItemMap.get(id).getId());
		
		if (noCallback && mOnCheckedChangeListener != null){
			mGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
		}
	}
	
	public void setOnPageChangeListener(OnPageChangeListener listener){
	    mListener = listener;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int pos = mTabItemMap.indexOfKey(checkedId);
		if (mViewPager != null){
			mViewPager.setCurrentItem(pos, true);
		}
	}

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub
        if (mListener != null){
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        // TODO Auto-generated method stub
        if (mListener != null){
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        switchTab(position, true);
        if (mListener != null){
            mListener.onPageSelected(position);
        }
    }
	
}
