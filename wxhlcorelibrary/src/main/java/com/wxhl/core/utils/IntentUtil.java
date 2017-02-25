package com.wxhl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wxhl.core.R;

/**
 * @author Caibing.zhang
 * @createdate 2010-12-15 下午1:03:18
 * @Description: Intent工具类
 */
public class IntentUtil {

	public static void startActivity(Context context,Class<?> cls){
		context.startActivity(new Intent(context, cls));
	}

	public static void startActivity(Context context,Intent intent){
		context.startActivity(intent);
	}
	
	public static void startActivity(Context context,Class<?> cls,Intent intent){
		context.startActivity(intent.setClass(context, cls));
	}
	
	public static void startActivityForResult(Activity activity,Intent intent,int requestCode){
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param cls
	 */
	public static void startActivityNetWork(Context context,Class<?> cls){
		if(!NetWorkUtil.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, cls);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param intent
	 */
	public static void startActivityNetWork(Context context,Intent intent){
		if(!NetWorkUtil.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, intent);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param intent
	 */
	public static void startActivityNetWork(Context context,Class<?> cls,Intent intent){
		if(!NetWorkUtil.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, cls, intent);
	}
	
	/**
	 * @Description: 跳转前判断网络,有返回值
	 * @param activity
	 * @param intent
	 * @param requestCode
	 */
	public static void startActivityForResultNetWork(Activity activity,Intent intent,int requestCode){
		if(!NetWorkUtil.NETWORK){
			T.showLong(activity, activity.getString(R.string.not_network));
			return;
		}
		startActivityForResult(activity, intent, requestCode);
	}
	
	/**
	 * @Description: 返回主页
	 * @param activity
	 * @param cls
	 */
	public static void goHome(Activity activity,Class<?> cls){
		Intent intent=new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(activity, cls, intent);
		activity.finish();
	}
	
}
