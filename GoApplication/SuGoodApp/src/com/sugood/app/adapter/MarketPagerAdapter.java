package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.sugood.app.activity.HomeActivity;
import com.sugood.app.activity.ShopDetailActivity;
import com.sugood.app.activity.TakeawayMarketDetailActivity;
import com.sugood.app.activity.TakeawayMarketShopDetailActivity;
import com.sugood.app.activity.TakeawayShopDetailActivity;
import com.sugood.app.activity.TuanGouActivity;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.util.ToastUtil;

import java.util.List;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/25 21:39.
 */

public class MarketPagerAdapter extends PagerAdapter {

    List<ImageView> mList;
    List<AdEntity> adEntityList;
    Context mContext;

    public MarketPagerAdapter(List<ImageView> mList) {
        this.mList = mList;
    }

    public MarketPagerAdapter(List<ImageView> list, List<AdEntity> adEntityList, Context mContext) {
        this.mList = list;
        this.adEntityList = adEntityList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;
        if (position % mList.size() < 0) {
            view = mList.get(mList.size() + position);

        } else {
            view = mList.get(position % mList.size());
        }
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        ((ViewGroup) container).addView(view);
        if (adEntityList != null) {

            mList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (adEntityList.get(position).getType().equals("1")) {
                        intent.putExtra("tuanId", adEntityList.get(position).getDiyid());
                        intent.putExtra("shopId", "");
                        intent.setClass(mContext, TuanGouActivity.class);
                        mContext.startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("2")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(mContext, ShopDetailActivity.class);
                        mContext.startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("3")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(mContext, TakeawayShopDetailActivity.class);
                        mContext.startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("4")) {
                        intent.setClass(mContext, TakeawayMarketDetailActivity.class);
                        intent.putExtra("goodsId", adEntityList.get(position).getDiyid());
                        mContext.startActivity(intent);
                    }else {
                        ToastUtil.setToast(mContext,"暂不支持");
                    }
//                        intent.putExtra("asa", (Serializable) mShopMainList);
//                    }

                }
            });
//        else if (adEntityList.get(position).getType().equals("5")) {
//            intent.setClass(mContext, TakeawayMarketShopDetailActivity.class);
//            intent.putExtra("cateId", adEntityList.get(position).getDiyid());
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
