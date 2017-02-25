package com.wxhl.core.utils.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.constants.CoreConstants;

public class WxhlHttpClient {

	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(30000);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年2月1日 上午12:17:27
	 * @Description: 取消所有请求，适用于Activity在Destory
	 */
	public static void cancelAllRequests() {
		client.cancelAllRequests(true);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午8:58:23
	 * @Description: 配置RequestParams
	 * @param params
	 */
	private static RequestParams configRequestParams(RequestParams params) {
		if (params == null) {
			params = new RequestParams();
		}
//		params.put("AppName", APP_NAME);
//		params.put("via", VIA);
		return params;
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午9:00:23
	 * @Description: GET请求
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
//		url = CoreRequestAPI.getAbsoluteUrl(url);
		L.e("--URL-->:" + url);

		params = configRequestParams(params);
		if (CoreConstants.IS_DEBUG) {
			String[] array = params.toString().split("&");
			for (String string : array) {
				L.e("--参数-->:" + string);
			}
		}
		L.e("--完整URL-->:" + url+"?"+params.toString());
		client.get(url, params, responseHandler);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午9:00:39
	 * @Description: POST请求
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
//		url = RequestAPI.getAbsoluteUrl(url);
		L.e("--URL-->:" + url);
		params = configRequestParams(params);
		if (CoreConstants.IS_DEBUG) {
			String[] array = params.toString().split("&");
			for (String string : array) {
				L.e("--参数-->:" + string);
			}
		}
		client.post(url, params, responseHandler);
	}

}
