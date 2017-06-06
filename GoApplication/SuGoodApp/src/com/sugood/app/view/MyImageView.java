package com.sugood.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sugood.app.R;


/**
 * Package :com.android.supermarket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 13:47.
 */

public class MyImageView extends LinearLayout {


    public MyImageView(Context context) {
        super(context);
        init(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.image_view_layout, this,false);
        this.addView(view);

    }


}
