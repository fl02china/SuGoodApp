package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sugood.app.R;
import com.sugood.app.adapter.MarketAddressAdapter;
import com.sugood.app.entity.MarketAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 15:02.
 */


public class SuperMarketAddressActivity extends BaseActivity {

    XRecyclerView mRecyclerView;
    Context mContext;
    List<MarketAddress> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_address_activity);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<MarketAddress>();

        for (int i = 0; i < 4; i++) {
            mList.add(new MarketAddress("上坡北60号楼102", "梁先生", "13838888888"));
        }

        MarketAddressAdapter adapter = new MarketAddressAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void initView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.market_address_rv);

    }
}
