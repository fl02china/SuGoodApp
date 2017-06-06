package com.sugood.app.util;

import android.util.Log;

import com.sugood.app.entity.GuessYouLikeProductInfo;
import com.sugood.app.global.Constant;

import java.util.ArrayList;
import java.util.List;

public class SelectData {
	
	/**
	 * 查询猜你喜欢的数据
	 * 
	 */
	public List<GuessYouLikeProductInfo> getAll(){
		List<GuessYouLikeProductInfo> list = new ArrayList<GuessYouLikeProductInfo>();
		list = TuanJsonParser.parse(Constant.COM_URL);
		Log.i("tjx", "所有数据："+list.size());
		return list;
	}

}
