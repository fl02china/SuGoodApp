package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.fragment.GongJiJinFragment;
import com.sugood.app.fragment.ShangyeFragment;
import com.sugood.app.fragment.ZuheFragment;
import com.sugood.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 房贷计算器
 * @author wtb
 */
public class FangDaiCalActivity extends FragmentActivity{
	
	private Context mContext;
	
	
	private ViewPager vpContainer;
	private ImageView iv_fangdai_back;
	
	private TextView gong_ji_jin;
	private TextView shang_ye;
	private TextView zuhe_daikuan;
	
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_fang_dai_cal);
		
		mContext = FangDaiCalActivity.this;
		initView();
	}



	private void initView() {
		iv_fangdai_back = (ImageView) findViewById(R.id.iv_fangdai_back);
		iv_fangdai_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		gong_ji_jin = (TextView) findViewById(R.id.gong_ji_jin);
		gong_ji_jin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initFragmentPosition(0);
				vpContainer.setCurrentItem(0);
			}

		});

		shang_ye= (TextView) findViewById(R.id.shang_ye);
		shang_ye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initFragmentPosition(1);
				vpContainer.setCurrentItem(1);
			}

		});
		
		zuhe_daikuan= (TextView) findViewById(R.id.zuhe_daikuan);
		zuhe_daikuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initFragmentPosition(2);
				vpContainer.setCurrentItem(2);
			}

		});
		
		vpContainer = (ViewPager) findViewById(R.id.vp_container);
		
		initViewpager();
	}
	
	private void initViewpager() {
		final GongJiJinFragment gongjijinFragement = new GongJiJinFragment();
		fragments.add(gongjijinFragement);
		final ShangyeFragment shangyeFragment = new ShangyeFragment();
		fragments.add(shangyeFragment);
		final ZuheFragment zuheFragment = new ZuheFragment();
		fragments.add(zuheFragment);
		
		myViewpager = new MyViewpager(getSupportFragmentManager(), fragments);
		vpContainer.setAdapter(myViewpager);
	}

	private MyViewpager myViewpager = null;
	class MyViewpager extends FragmentPagerAdapter implements OnPageChangeListener{
		List<Fragment> dataFragment;
		public MyViewpager(FragmentManager fm, List<Fragment> dataFragment) {
			super(fm);
			this.dataFragment = dataFragment;
		}

		@Override
		public Fragment getItem(int position) {
			return dataFragment.get(position);
		}

		@Override
		public int getCount() {
			return dataFragment==null? 0:dataFragment.size();
		}

		@Override
		public void onPageScrollStateChanged(int position) {
//			initFragmentPosition(position);

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//			initFragmentPosition(position);
		}

		@Override
		public void onPageSelected(int position) {
			initFragmentPosition(position);
			ToastUtil.setToast(mContext,"是"+position);
		}

	}
	
	private FragmentManager fManager = null;
	private void initFragmentPosition(int position) {
		
		switch (position) {
		case 0:
			gong_ji_jin.setBackgroundResource(R.color.fangdai_selected);
			shang_ye.setBackgroundResource(R.color.fangdai_unselect);
			zuhe_daikuan.setBackgroundResource(R.color.fangdai_unselect);
			break;
		case 1:
			gong_ji_jin.setBackgroundResource(R.color.fangdai_unselect);
			shang_ye.setBackgroundResource(R.color.fangdai_selected);
			zuhe_daikuan.setBackgroundResource(R.color.fangdai_unselect);
			break;
		case 2:
			gong_ji_jin.setBackgroundResource(R.color.fangdai_unselect);
			shang_ye.setBackgroundResource(R.color.fangdai_unselect);
			zuhe_daikuan.setBackgroundResource(R.color.fangdai_selected);
			break;
		default:
			break;
		}
	}
}
