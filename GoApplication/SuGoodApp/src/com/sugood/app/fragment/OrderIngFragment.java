package com.sugood.app.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.OrderAdapter;
import com.sugood.app.adapter.UserOrderAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.entity.UserOrder;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.view.RecycleViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class OrderIngFragment extends OrderAllFragment {
    @Override
    protected int getStatus() {
        return 2;
    }
}