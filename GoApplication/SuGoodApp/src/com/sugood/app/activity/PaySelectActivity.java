package com.sugood.app.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.sugood.app.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class PaySelectActivity  extends BaseActivity{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_submit_order_activity);
        RadioGroup sexRroup = (RadioGroup) findViewById(R.id.radiogroup);
        final RadioButton weixin=(RadioButton)findViewById(R.id.radio_weixin);
        final RadioButton ali=(RadioButton)findViewById(R.id.radio_alipay);

        sexRroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==weixin.getId()){



                }
            }
        });
    }

}
