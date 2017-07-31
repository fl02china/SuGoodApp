package com.sugood.app.fragment;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class OrderCancleFragment extends OrderAllFragment {
    @Override
    protected int getStatus() {
        return 5;
    }
}
