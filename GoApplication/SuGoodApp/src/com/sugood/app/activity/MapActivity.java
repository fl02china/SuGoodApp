package com.sugood.app.activity;

import android.app.Activity;
import android.os.Bundle;

//import com.baidu.mapapi.map.MapView;
import com.sugood.app.R;

public class MapActivity extends Activity {
   // private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //initView();

    }

//    private void initView() {
//        mapView = (MapView) findViewById(R.id.bmapView);
//    }
//
//    @Override
//    protected void onPause() {
//        /**
//         *  mapView的生命周期与Activity同步，当activity挂起时需调用mapView.onPause()
//         */
//        mapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        /**
//         *  mapView的生命周期与Activity同步，当activity恢复时需调用mapView.onResume()
//         */
//        mapView.onResume();
//        super.onResume();
//
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        /**
//         *  mapView的生命周期与Activity同步，当activity销毁时需调用mapView.destroy()
//         */
//        mapView.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }


}
