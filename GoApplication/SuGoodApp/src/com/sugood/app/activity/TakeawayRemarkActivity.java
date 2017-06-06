package com.sugood.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.util.ToastUtil;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 23:39.
 */

public class TakeawayRemarkActivity extends BaseActivity {

    private EditText takeaway_remark_ed;
    private Button btnOK;
    private ImageView takeaway_remark_header_back;
    private TextView textCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_remark_activity);
        initView();
    }

    private void initView() {
        String string = getIntent().getStringExtra("remark");
        takeaway_remark_ed = (EditText) findViewById(R.id.takeaway_remark_ed);
        takeaway_remark_ed.setText(string);
        btnOK = (Button) findViewById(R.id.btn_ok);
        takeaway_remark_ed.addTextChangedListener(watcher);
        takeaway_remark_header_back = (ImageView) findViewById(R.id.takeaway_remark_header_back);
        textCount = (TextView) findViewById(R.id.textCount);
        takeaway_remark_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("remark", takeaway_remark_ed.getText().toString());
                setResult(1, intent);
                finish();
            }
        });


    }

    private TextWatcher watcher = new TextWatcher() {


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

            // TODO Auto-generated method stub
            Editable editable = takeaway_remark_ed.getText();
            int len = editable.length();

            if (len > 50) {
                ToastUtil.setToast(TakeawayRemarkActivity.this, "已超出字数限制");
                int selEndIndex = Selection.getSelectionEnd(editable);
                String str = editable.toString();
                //截取新字符串
                String newStr = str.substring(0, 50);
                takeaway_remark_ed.setText(newStr);
                editable = takeaway_remark_ed.getText();

                //新字符串的长度
                int newLen = editable.length();
                //旧光标位置超过字符串长度
                if (selEndIndex > newLen) {
                    selEndIndex = editable.length();
                }
                //设置新光标所在的位置
                Selection.setSelection(editable, selEndIndex);

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub


        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            textCount.setText(takeaway_remark_ed.getText().toString().length() + "/50");


        }
    };
}
