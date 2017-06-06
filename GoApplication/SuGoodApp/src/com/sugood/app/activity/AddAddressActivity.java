package com.sugood.app.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.ProvinceBean;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/9 15:25.
 */

public class AddAddressActivity extends BaseActivity {

    private static final String TAG = AddAddressActivity.class.getSimpleName();
    TextView mDistrictTV;
    Context mContext;
    TextView mSave;
    EditText user_add_address_name_ed;
    EditText user_add_address_phone_ed;
    EditText user_add_address_this_ed;
    EditText user_add_address_postcode_ed;
    CheckBox cb_default;
    ImageView user_add_address_header_back;

    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_activity);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mDistrictTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });

        String province_data_json = getJson("province_data.json");
        Log.i(TAG, "initData: " + province_data_json);

        perjson(province_data_json);

        //  设置三级联动效果
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);
        pvOptions.setTitle("选择省市");

        //  设置选择的三级单位
        //pvOptions.setLabels("省", "市", "区");
        //pvOptions.setTitle("选择城市");

        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);


        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + cityList.get(options1).get(option2)
                            + " " + districtList.get(options1).get(option2).get(options3);
                }
                mDistrictTV.setText(address);
                mDistrictTV.setTextColor(getResources().getColor(R.color.black_de));

            }
        });


    }

    private void perjson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        user_add_address_header_back = (ImageView) findViewById(R.id.user_add_address_header_back);
        mDistrictTV = (TextView) findViewById(R.id.user_add_address_district_tv);
        mSave = (TextView) findViewById(R.id.user_add_address_save_tv);
        user_add_address_name_ed = (EditText) findViewById(R.id.user_add_address_name_ed);
        user_add_address_phone_ed = (EditText) findViewById(R.id.user_add_address_phone_ed);
        user_add_address_postcode_ed = (EditText) findViewById(R.id.user_add_address_postcode_ed);
        user_add_address_this_ed = (EditText) findViewById(R.id.user_add_address_this_ed);
        cb_default = (CheckBox) findViewById(R.id.cb_default);
        user_add_address_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(user_add_address_name_ed.getText().toString())) {
                    ToastUtil.setToast(AddAddressActivity.this, "请输入用户名称");
                    return;
                }

                if (TextUtils.isEmpty(user_add_address_phone_ed.getText().toString())) {
                    ToastUtil.setToast(AddAddressActivity.this, "请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mDistrictTV.getText().toString())) {
                    ToastUtil.setToast(AddAddressActivity.this, "请选择收货地址");
                    return;
                }
                if (TextUtils.isEmpty(user_add_address_this_ed.getText().toString())) {
                    ToastUtil.setToast(AddAddressActivity.this, "请输入详细地址地址");
                    return;
                }

                RequestParams params = new RequestParams();
                if (cb_default.isChecked()) {
                    params.put("defaults", "1");
                } else {
                    params.put("defaults", "2");
                }
                params.put("userId", SugoodApplication.user.getUserId());
                params.put("xm", user_add_address_name_ed.getText().toString());
                params.put("tel", user_add_address_phone_ed.getText().toString());
                params.put("areaStr", mDistrictTV.getText().toString());
                params.put("info", user_add_address_this_ed.getText().toString());
                HttpUtil.post(Constant.SUGOODADDADD, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e(TAG, "onSuccess: " + response.toString());

                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e(TAG, "onFailure: " + responseString);
                        Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public String getJson(String filename) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(filename);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }

}
