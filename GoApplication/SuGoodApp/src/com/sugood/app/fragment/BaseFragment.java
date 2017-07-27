package com.sugood.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public abstract class BaseFragment extends Fragment {



    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutRes = getLayoutRes();
        if (layoutRes > 0){

            mRoot = inflater.inflate(layoutRes, container, false);
        }
        return mRoot;

    }

//    public void  initToolBar(){
//
//    }

    public View findViewById(int id){
        if (mRoot == null){
            return null;
        }
        return mRoot.findViewById(id);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout();
    }

    /**
     * 初始化布局
     */
    protected abstract void initLayout();
    /**
     * 设置布局资源
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 展示一个toast
     * @param msg
     */
    public void tip(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    //public abstract void init();


}
