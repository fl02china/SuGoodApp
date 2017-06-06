package com.ifthink.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dec on 2017/1/4.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        initViews();
        initNetDatas();
        setClick();
    }

    public abstract void setLayout();
    public abstract void initViews();
    public abstract void initNetDatas();
    public abstract void setClick();




}