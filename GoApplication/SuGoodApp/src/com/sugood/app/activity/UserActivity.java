package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.ToastUtil;
import com.sugood.app.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 15:52.
 */

public class UserActivity extends BaseActivity {
    private static final String TAG = UserActivity.class.getSimpleName();
    private final static int CHOOSEIMAGE = 1;
    private final static int CROPIMAGE = 2;
    RelativeLayout mPhotolayout;//头像layout
    RelativeLayout mNameLayout;//昵称layout
    RelativeLayout mAddressLayout;//修改地址layout
    Context mContext;
    View mView;
    SimpleDraweeView sdv_avatar;
    TextView tv_nickName;
    String mCurrentPhotoPath;
    File imgFile;
    ImageView user_header_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        mContext = this;

        initView();

        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SugoodApplication.user!=null){
        tv_nickName.setText(SugoodApplication.user.getNickname());}

    }

    private void initData() {

        mPhotolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(UserActivity.this, PhotoPickerActivity.class);
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
                startActivityForResult(intent, CHOOSEIMAGE);


            }
        });


        mNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, UserNameActivity.class));
            }
        });
        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ManageAddressActivity.class));

            }
        });
    }

    private void initView() {
        mPhotolayout = (RelativeLayout) findViewById(R.id.user_header_rl);
        mView = findViewById(R.id.user_header_view);
        mNameLayout = (RelativeLayout) findViewById(R.id.user_nickname_rl);
        mAddressLayout = (RelativeLayout) findViewById(R.id.user_address_rl);
        sdv_avatar = (SimpleDraweeView) findViewById(R.id.sdv_avatar);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        user_header_back = (ImageView) findViewById(R.id.user_header_back);
        user_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(111, intent);
                finish();
            }
        });
        if (SugoodApplication.user != null) {
            sdv_avatar.setImageURI(Constant.PHOTOBASEURL + SugoodApplication.user.getFace());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSEIMAGE:
                if (data != null) {
                    List<String> list = data.getStringArrayListExtra("picker_result");
                    if (list.size() > 0) {
//                            startImageCrop(list.get(0));
                        uploadAvatar(new File(list.get(0)));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
                break;
            case CROPIMAGE:
                if (data != null) {

                }
                break;
        }
    }

    private void startImageCrop(String uri) throws IOException {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(uri)), "image/*");//设置Uri及类型
        intent.putExtra("crop", "true");//
        intent.putExtra("aspectX", 1);//X方向上的比例
        intent.putExtra("aspectY", 1);//Y方向上的比例
        intent.putExtra("outputX", 300);//裁剪区的X方向宽
        intent.putExtra("outputY", 300);//裁剪区的Y方向宽
        intent.putExtra("scale", true);//是否保留比例
        // intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", false);//是否将数据保留在Bitmap中返回dataParcelable相应的Bitmap数据

        if (Build.VERSION.SDK_INT >= 24) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.sugood.app.fileprovider",
                    createImageFile());
            intent.putExtra("outputFormat", photoURI);
        } else {
            intent.putExtra("outputFormat", Uri.fromFile(createImageFile()));
        }

        startActivityForResult(intent, 2);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imgFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imgFile.getAbsolutePath();
        return imgFile;
    }

    private void uploadAvatar(File file) {
        showLoading("上传中");
        RequestParams params = new RequestParams();
        try {
            Log.e("ss", "uploadAvatar: " + SugoodApplication.user.getUserId());
            params.put("file", file);
            params.put("userId", SugoodApplication.user.getUserId());
            HttpUtil.post(Constant.UPLOADAVATAR, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            closeLoading();
                            Log.e(TAG, "onFailure: " + responseString);
                            ToastUtil.setToast(UserActivity.this, "更新失败，请重新上传");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            closeLoading();

                            Log.e("ss", "onSuccess: " + response.toString());
                            try {
                                boolean session = response.getBoolean("session");
                                String url = response.getString("stringUrl");
                                if (session) {
                                    ToastUtil.setToast(UserActivity.this, "更新成功");
                                    sdv_avatar.setImageURI(Constant.PHOTOBASEURL + url);
                                    SugoodApplication.user.setFace(url);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
