package com.sugood.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sugood.app.R;


public class ZuheFragment extends Fragment{

	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zuhe, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		
		
	}
}
