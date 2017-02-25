package com.wxhl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wxhl.core.network.ListenNetStateService;

import java.util.ArrayList;

/**
 * @author caibing.zhang
 * @createdate 2013-12-15 下午1:18:57
 * @Description: 核心工具类
 */
public class CoreUtil {
	
	public static ArrayList<Activity> ALL_ACTIVITY=new ArrayList<Activity>();
	
	/**
	 * @Description: 添加Activity到列表中
	 * @param activity
	 */
	public static void addAppActivity(Activity activity){
		if(!ALL_ACTIVITY.contains(activity)){
			ALL_ACTIVITY.add(activity);
		}
	}
	
	/**
	 * @Description: 从列表移除Activity
	 * @param activity
	 */
	public static void removeAppActivity(Activity activity){
		if(ALL_ACTIVITY.contains(activity)){
			ALL_ACTIVITY.remove(activity);
		}
	}
	
	/**
	 * @Description: 退出应用程序
	 */
	public static void exitApp(Context context){
		context.stopService(new Intent(context, ListenNetStateService.class));

		L.e("--- 销毁 Activity size--->>:" + ALL_ACTIVITY.size());
		for (Activity ac : ALL_ACTIVITY) {
			if(!ac.isFinishing()){
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();

		android.os.Process.killProcess(android.os.Process.myPid());
//		HttpClient httpClient = CoreHttpClient.getInstance();
//		if (httpClient != null && httpClient.getConnectionManager() != null) {
//			httpClient.getConnectionManager().shutdown();
//		}
	}

	/**
	 * @Description: 重新运行应用程序
	 */
	public static void restartApp(Context context){
		context.stopService(new Intent(context, ListenNetStateService.class));

		L.e("--- 销毁 Activity size--->>:" + ALL_ACTIVITY.size());
		for (Activity ac : ALL_ACTIVITY) {
			if(!ac.isFinishing()){
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();

	}
}
