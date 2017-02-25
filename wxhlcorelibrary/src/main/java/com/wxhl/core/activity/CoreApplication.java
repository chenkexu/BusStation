package com.wxhl.core.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.wxhl.core.utils.FileUtil;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.imageloder.VolleyBitmapCache;

public class CoreApplication extends Application{
	
	private static Context context;
	public static boolean IS_EXIST_SDCARD;
	public static String CACHE_DIR_SD;                      //SD卡缓存目录
	public static String CACHE_DIR_SYSTEM;                  //系统目录
	public static String IMAGE_DIR;                   		//图片目录
	public static String FILE_DIR;                     	    //文件目录
	public static String LOG_DIR;                     	    //日志目录
	public static String IMAGE_UPLOAD_TEMP;   				//上传图片临时目录
	public static String LOG;                           //日志保存的SD卡的目录
	public static String AllLOG;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = CoreApplication.this;
		init();

		requestQueue = Volley.newRequestQueue(this);
		getImageLoader();
	}

	public static Context getContext() {
		return context;
	}
	
	/**
	 * @Description: 初始化
	 */
	private void init(){
		if(FileUtil.isExistSD()){
			//SD存在
			CACHE_DIR_SD = FileUtil.getCacheDirectory(context);
			IS_EXIST_SDCARD=true;
		}else{
			//不存在则使用系统目录
			CACHE_DIR_SD = context.getCacheDir().getPath();
		}
		CACHE_DIR_SD += "/";
		L.e("----SD卡目录---->>>:" + CACHE_DIR_SD);
		LOG=CACHE_DIR_SD+"cache.log";
    	AllLOG=CACHE_DIR_SD+"allcache.log";
		IMAGE_DIR=CACHE_DIR_SD+"image/";
		FILE_DIR=CACHE_DIR_SD+"file/";
		LOG_DIR=CACHE_DIR_SD+"log/";
    	IMAGE_UPLOAD_TEMP=CACHE_DIR_SD+"imageUploadTemp/";
    	CACHE_DIR_SYSTEM=context.getCacheDir().getPath()+"/file/";//手机的缓存目录

		FileUtil.checkDir(CACHE_DIR_SD,IMAGE_DIR,
				FILE_DIR,LOG_DIR,IMAGE_UPLOAD_TEMP,CACHE_DIR_SYSTEM);
	}

	/**
	 * 返回RequestQueue，全局共享
	 * @return
	 */
	public RequestQueue getRequestQueue(){
		if(requestQueue == null){
			requestQueue = Volley.newRequestQueue(this);
		}
		return requestQueue;
	}

	/**
	 * 返回ImageLoader，全局共享
	 * @return
	 */
	public ImageLoader getImageLoader(){
		if(imageLoader == null){
			imageLoader = new ImageLoader(requestQueue,new VolleyBitmapCache());
		}
		return imageLoader;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
}
