package com.sugood.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sugood.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.sugood.app.fragment
 * Description :
 * Author :Rc3
 * Created at :2017/04/19 15:02.
 */

public class WillPayFragment extends Fragment {

    List list = new ArrayList();
    @BindView(R.id.will_pay_rv)
    XRecyclerView mXRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_will_pay, container, false);
        ButterKnife.bind(this, view);
        initData(view);
        return view;
    }

    private void initData(View view) {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setAdapter(new WillpayAdapter());
        mXRecyclerView.setPullRefreshEnabled(false);

    }

    class WillpayAdapter extends RecyclerView.Adapter<WillpayAdapter.MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.will_pay_rv_item, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public MyHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
