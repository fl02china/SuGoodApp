package com.sugood.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

public class TabPageAdapter extends FragmentPagerAdapter {
	
	/**
	 * Tab 信息
	 * @author holmes
	 *
	 */
    private static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }
    
	private SparseArray<TabInfo> mTabs;
	private FragmentManager mFm;
	private Context mContext;

	public TabPageAdapter(FragmentManager fm, Context context) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = context;
		mFm = fm;
		mTabs = new SparseArray<TabInfo>();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		TabInfo tab = mTabs.get(arg0);
		ensureFragment(tab);
		return tab.fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTabs == null ? 0 : mTabs.size();
	}
	
	private void ensureFragment(TabInfo tab){
		if (tab.fragment == null){
			tab.fragment = Fragment.instantiate(mContext, tab.clss.getName(), tab.args);
		}
	}

	/**
	 * 按顺序添加一个Fragment, 无tag
	 * @param clss
	 * @param args
	 */
	public void addTabFragment(Class<?> clss, Bundle args){
		addTabFragment(null, clss, args);
	}
	
	/**
	 * 按顺序添加一个Fragment
	 * @param tag
	 * @param clss
	 * @param args
	 */
	public void addTabFragment(String tag, Class<?> clss, Bundle args){
		int tabPos = mTabs.size();
		Bundle bundle = new Bundle();
		bundle.putString("status",tag);

		TabInfo tab = new TabInfo(tag, clss, bundle);
		if (tag != null){
			tab.fragment = mFm.findFragmentByTag(tag);
			if (tab.fragment != null && !tab.fragment.isDetached()){
				FragmentTransaction ft = mFm.beginTransaction();
				ft.detach(tab.fragment);
				ft.commit();
			}
		}
		mTabs.put(tabPos, tab);
	}

}
