package com.sugood.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.SameCityAdapter;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.SameCityYellowPage;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 同城黄页 界面
 */
public class SameCityActivity extends Activity implements TextView.OnEditorActionListener {

    private static final String TAG = SameCityActivity.class.getSimpleName();
    private Context mContext;
    private RelativeLayout rel_samecity_back;
    private TextView tv_release_text;
    private ListView lv_samecity;
    private ImageView headerImg;
    private ImageView iv_back;
    private SameCityAdapter sameCityAdapter = null;
    private EditText home_search;
    private List<SameCityYellowPage> cityYellowPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_city);
        mContext = SameCityActivity.this;
        initView();
        initDataFromNet();
        setClickListener();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_huangye, null);
        rel_samecity_back = (RelativeLayout) findViewById(R.id.rel_samecity_back);
        tv_release_text = (TextView) findViewById(R.id.tv_release_text);
        lv_samecity = (ListView) findViewById(R.id.lv_samecity);
        headerImg = (ImageView) findViewById(R.id.imageView4);
        lv_samecity.addHeaderView(view);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home_search = (EditText) findViewById(R.id.home_search);
        home_search.setOnEditorActionListener(this);
        sameCityAdapter = new SameCityAdapter(mContext);
        lv_samecity.setAdapter(sameCityAdapter);
    }

    private void initDataFromNet() {

        /**
         *  请求广告图片
         */
        RequestParams photoParams = new RequestParams();
        photoParams.put("siteId", "103");
        HttpUtil.post(Constant.GOODFOODAD, photoParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<AdEntity> EntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                if (EntityList.size() > 0) {
//                    GlideUtil.displayImage(Constant.PHOTOBASEURL + EntityList.get(0).getPhoto(), headerImg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });

        String url = "http://192.168.0.105:8080/Speed/Speed/baoPoisContent/queryPage";
        RequestParams params = new RequestParams();
        params.put("page", "1");
        params.put("pageSize", "999");
        HttpUtil.post(Constant.HUANFGYE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: " + response.toString());
                cityYellowPages = JsonUtil.toList(response.toString(), SameCityYellowPage.class);
                sameCityAdapter.setDataList(cityYellowPages);
                sameCityAdapter.notifyDataSetChanged();
            }
        });


//        sameCityAdapter = new SameCityAdapter(mContext, fakeDataList);
//        lv_samecity.setAdapter(sameCityAdapter);
//		lv_samecity.setOnItemClickListener(new_good AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//			}
//		});

    }

    private void setClickListener() {
        rel_samecity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_release_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "去发布信息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Log.e(TAG, "onEditorAction: " + "sadas");
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) home_search.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    SameCityActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            search();
            return true;
        }
        return false;
    }

    private void search() {
        RequestParams params = new RequestParams();
        params.put("name", home_search.getText().toString());
        params.put("page", "1");
        params.put("pageSize", "999");
        HttpUtil.post(Constant.HUANFGYE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: " + response.toString());
                cityYellowPages.clear();
                cityYellowPages = JsonUtil.toList(response.toString(), SameCityYellowPage.class);
                sameCityAdapter.setDataList(cityYellowPages);
                sameCityAdapter.notifyDataSetChanged();
            }
        });

    }
}
