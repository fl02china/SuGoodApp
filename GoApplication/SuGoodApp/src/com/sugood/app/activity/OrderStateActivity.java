package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sugood.app.R;
import com.sugood.app.adapter.OrderStateAdapter;
import com.sugood.app.entity.OrderState;

import java.util.ArrayList;
import java.util.List;

/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 09:46.
 */

public class OrderStateActivity extends BaseActivity {

    Context mContext;
    XRecyclerView mXRecyclerView;
    List<OrderState> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_state_activity);
        mContext = this;
        initView();
        initData();

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mList.add(new OrderState());
        }
        mXRecyclerView.setHasFixedSize(true);
        OrderStateAdapter adapter = new OrderStateAdapter(mContext, mList);
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mXRecyclerView.setAdapter(adapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    private void initView() {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.order_state_rv);

    }
}
