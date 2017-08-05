package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sugood.app.view.LoadingDialog;

/**
 * Created by wilk on 17/2/20 11:21
 * ganweib@gmail.com
 * describe:
 */
public class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
    }

    public void showLoading(String msg) {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
            loadingDialog.setText(msg);
        }
    }

    public void closeLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    public void closeKeyboard(EditText ed) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);
    }
    /**
     * 展示一个toast
     * @param msg
     */
    public void tip(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
