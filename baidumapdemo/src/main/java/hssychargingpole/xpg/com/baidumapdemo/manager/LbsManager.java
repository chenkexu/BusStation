package hssychargingpole.xpg.com.baidumapdemo.manager;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * LocationManager
 *
 * @author Joke Huang
 * @version 1.0.0
 * @Description  百度地图的位置管理类
 * @createDate 2014年7月24日
 */

/**
 * 百度地图的位置管理类
 */
public class LbsManager {
	private volatile static LbsManager instance;

	private Context mContext;

	//定位的配置信息
	private LocationClientOption mLocationClientOption;

	private LbsManager() {
	}

	public static LbsManager getInstance() {
		if (instance == null) {
			synchronized (LbsManager.class) {
				if (instance == null) {
					instance = new LbsManager();
				}
			}
		}
		return instance;
	}

	public void init(Context context) {
		mContext = context;
		mLocationClientOption = new LocationClientOption();
		mLocationClientOption.setOpenGps(true);// 打开gps
		mLocationClientOption.setCoorType("bd09ll"); // 设置坐标类型
		mLocationClientOption.setIsNeedAddress(true);// 需要地址信息
		mLocationClientOption.setScanSpan(0);// 定位的间隔
		mLocationClientOption.setLocationMode(LocationMode.Hight_Accuracy);// 高精度（GPS+基站+WiFi）
	}

	private LocationClient newLocationClient() {
		LocationClient locationClient = new LocationClient(mContext);
		locationClient.setLocOption(mLocationClientOption);
		return locationClient;
	}

	/**
	 * 获取当前位置的核心类
	 */
	public LocationClient locationClient = null;

	/**
	 * 信息回调监听器的集合
	 */
	public List<BDLocationListener> locationListeners = new ArrayList<BDLocationListener>();

	public void getLocation(BDLocationListener locationListener) {
		synchronized (locationListeners) {
			if (locationClient == null) {
				locationClient = newLocationClient();
			}

			locationListeners.add(locationListener);
			if (locationClient.isStarted()) {
				locationClient.requestLocation();
			} else {
				locationClient.registerLocationListener(ll);
				locationClient.start();
			}
		}
	}


	/**
	 * 实现成功定位的回调
	 */
	private BDLocationListener ll = new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation location) {
			synchronized (locationListeners) {
				locationClient.stop();
				for (BDLocationListener listener : locationListeners) {
					if (listener != null) {
						listener.onReceiveLocation(location);
					}
				}
				locationListeners.clear();
			}
		}
	};

	/**
	 * 根据城市地址 获取坐標
	 */
	public void getLocationByAddress(String city, String address, final OnGetGeoCoderResultListener onGetGeoCoderResultListener) {

		final GeoCoder geoCoder = GeoCoder.newInstance();

		geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			//地理坐标转换成具体的地址信息的回调
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			}

			//地址信息转换为地理坐标的回调
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (onGetGeoCoderResultListener != null) {
					onGetGeoCoderResultListener.onGetGeoCodeResult(result);
				}
				geoCoder.destroy();
			}
		});
		GeoCodeOption option = new GeoCodeOption().city(city).address(address);
		geoCoder.geocode(option);
	}


	/**
	 * 根据坐标获取地址
	 */
	public void getAddressByLocation(LatLng latLng, final OnGetGeoCoderResultListener onGetGeoCoderResultListener) {

		final GeoCoder geoCoder = GeoCoder.newInstance(); //新建地理编码查询
		geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			//将地理坐标转换成具体的地址信息的回调
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (onGetGeoCoderResultListener != null) {
					onGetGeoCoderResultListener.onGetReverseGeoCodeResult(result);
				}
				geoCoder.destroy();
			}

			  //将地址信息转换成地理坐标的回调
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
			}
		});
		ReverseGeoCodeOption option = new ReverseGeoCodeOption().location(latLng);
		geoCoder.reverseGeoCode(option);// 发起反地理编码请求(经纬度->地址信息)
	}
}
