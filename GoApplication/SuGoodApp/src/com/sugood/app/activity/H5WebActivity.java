package com.sugood.app.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;

import java.util.HashMap;

/**
 * Created by wilk on 17/4/11 14:37
 * ganweib@gmail.com
 * describe:
 */

public class H5WebActivity extends BaseActivity {

    private String url;
    private String title;
    private ImageView iv_back;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5web);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.title);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
        initWebView();


    }


    private void initWebView() {

//        String url = Url.HOST + "/wb/limit.html?location=" + ((MyApplication) getApplication()).getSelectLocation()+"&pt=android&version="+ AppUtil.getVersionName(this)+"&userID="+MyApplication.user.getUserID()+"&channel="+AppUtil.getChannel(this);
        WebView web_view = (WebView) findViewById(R.id.web_third);

        web_view.loadUrl(url);
        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
//        web_view.addJavascriptInterface(new MyJavaScriptInterface(), "jump");
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.contains("limit_result.html")) {
//                    LogUtil.d("***************************limit_result.html**************************");
//                    /*****************友盟统计 begin*********************/
//                    HashMap<String, String> map = new HashMap<>();
//                    map.put("name", "测测信用查看结果");
//                    onMobEvent("getCard", map);
//
//                    HashMap<String, String> map1 = new HashMap<>();
//                    map1.put("creditTag", "");
//                    onMobEvent("testCreditGetCard", map1);
//                    /*****************友盟统计 end*********************/
//                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }
}
