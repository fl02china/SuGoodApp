package com.sugood.app.util;

/**
 * Created by Administrator on 2016/5/4 0004.
 */

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * 对android async http的简单封装
 */
public class HttpUtil {

	private static String sessionId = null;

	private static AsyncHttpClient client = new AsyncHttpClient();

	private static SyncHttpClient syncHttpClient = new SyncHttpClient();

	private static PersistentCookieStore cookieStore;


	static {
		//设置网络超时时间
		syncHttpClient.setTimeout(3000);
		client.setConnectTimeout(3000);
		client.setTimeout(3000);
		client.setMaxRetriesAndTimeout(1,3000);
//		client.setProxy("192.168.1.58",8888);
	}

	/**
	 *
	 * @param url
	 * @param params
	 * @param responseHandler
     */

	public static void syspost(String url,RequestParams params, ResponseHandlerInterface responseHandler){
		syncHttpClient.post(url,params,responseHandler);
	}

	/**
	 *
	 * @param url
	 * @param responseHandler
	 */
	public static void get(String url, ResponseHandlerInterface responseHandler) {

		client.get(url, responseHandler);
	}

	/**
	 *
	 * @param context
	 * @param url
	 * @param responseHandler
	 */
	public static void get(Context context, String url, ResponseHandlerInterface responseHandler) {

		client.get(context, url, responseHandler);
	}

	/**
	 *
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {

		client.get(url, params, responseHandler);
	}

	/**
	 *
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {

		client.get(context, url, params, responseHandler);
	}

	/**
	 *
	 * @param context
	 * @param url
	 * @param headers
	 * @param params
	 * @param responseHandler
	 */
	public static void get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {

		client.get(context, url, headers, params, responseHandler);
	}

	/**
	 *
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {

		client.post(url, params, responseHandler);
	}

	/**
	 *
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {

		client.post(context, url, params, responseHandler);
	}

	/**
	 *
	 * @return
	 */
	public static AsyncHttpClient getClient() {

		return client;
	}

	public static SyncHttpClient getSyncHttpClient(){
		return syncHttpClient;
	}

	/**
	 *
	 * @return
	 */
	public static String getSessionId() {

		return sessionId;
	}

	/**
	 *
	 * @param sessionId
	 */
	public static void setSessionId(String sessionId) {

		HttpUtil.sessionId = sessionId;
	}

	/**
	 *
	 * @return
	 */
	public static PersistentCookieStore getCookieStore() {

		return cookieStore;
	}

	/**
	 *
	 * @param cookieStore
	 */
	public static void setCookieStore(PersistentCookieStore cookieStore) {

		HttpUtil.cookieStore = cookieStore;

		client.setCookieStore(cookieStore);
	}

}