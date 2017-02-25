package hssychargingpole.xpg.com.baidumapdemo.utils;

import android.content.Context;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;

import hssychargingpole.xpg.com.baidumapdemo.contant.KEY;


/**
 * Created by black-Gizwits on 2015/08/21.
 * 封装了一些应用中通用的计算方法
 */
public final class CalculateUtil {

	private CalculateUtil() {
		throw new Error("don't instantiation util !");
	}

	private static final String MONEY_FORMAT = "%.2f元";
	private static final String DISTANCE_FORMAT_M = "%.1fm";
	private static final String DISTANCE_FORMAT_KM = "%.1fkm";
	private static final String PRICE_FORMAT = "￥ %.2f/kWh";
	private static final String PARKING_PRICE_FORMAT = "￥ %.2f/小时";
	private static final String QUANTITY_FORMAT = "%.2f kWh";
	private static final String PERIOD_PRICE_FORMAT = "%.2f/kWh";
	private static final String BILL_FORMAT = "￥ %.2f";
	private static final String DEFAULT_NUMBER_FORMAT = "%.2f";
	private static final String RATIO_FORMAT = "%.3f";
	private static final String RECHARGE_MONEY = "￥ %.2f";
	private static final String PERIOD_PRICE_STRING_FORMAT = "￥ %1$s/kWh";

	private static final String FORMAT_A_DECIMAL = "%.1f";
	private static final String FORMAT_QUANTITY = "%d kWh";

	public enum DistanceType {
		baseMapCenter,
		baseUserLocation
	}

	private static DistanceType defaultDistanceType = DistanceType.baseUserLocation;

	public static double calculateDistance(Context context, LatLng location) {
		return calculateDistance(context, defaultDistanceType, location);
	}

	/**
	 * 根据输入和距离类型计算距离
	 *
	 * @param context
	 * @param distanceType
	 * @param location
	 * @return
	 */
	public static double calculateDistance(Context context, DistanceType distanceType, LatLng location) {
//		SPFile sp = new SPFile(context, "config");
//		SharedPreferenceUtil.get(context,)
		double latitude;
		double longitude;
		switch (distanceType) {
			case baseMapCenter: {
//				latitude = Double.valueOf(sp.getString(KEY.CONFIG.LATITUDE, "-1"));
//				longitude = Double.valueOf(sp.getString(KEY.CONFIG.LONGITUDE, "-1"));
				latitude = Double.valueOf((Double) SharedPreferenceUtil.get(context,KEY.CONFIG.LATITUDE,"-1"));
				longitude = Double.valueOf((Double) SharedPreferenceUtil.get(context,KEY.CONFIG.LONGITUDE,"-1"));
				break;
			}
			case baseUserLocation: {
				latitude = Double.valueOf((String) SharedPreferenceUtil.get(context,KEY.CONFIG.MY_LATITUDE,"-1"));
				longitude = Double.valueOf((String) SharedPreferenceUtil.get(context,KEY.CONFIG.MY_LONGITUDE,"-1"));
				break;
			}
			default: {
				latitude = -1;
				longitude = -1;
			}
		}
		if (latitude == -1 || longitude == -1) return -1;
		LatLng sLatLng = new LatLng(latitude, longitude);
		double distance = DistanceUtil.getDistance(sLatLng, location);
		return distance;
	}

	public static void infuseDistance(Context context, TextView textView, LatLng location) {
		infuseDistance(context, textView, defaultDistanceType, location);
	}

	public static void infuseDistance(Context context, TextView textView, DistanceType distanceType, LatLng location) {
		double distance = calculateDistance(context, distanceType, location);
		infuseDistance(textView, distance);
	}

	/**
	 * 根据输入的距离确定填充的字符串格式和单位
	 * 小于0m,显示距离未知,大于0小于1000m,单位是m,大于1000m,转换成km
	 *
	 * @param textView
	 * @param distance
	 */
	public static void infuseDistance(TextView textView, double distance) {
		if (distance > 0) {
			if (distance < 1000) {

				textView.setText(String.format(DISTANCE_FORMAT_M, distance));
			} else {
				distance = distance / 1000;
				textView.setText(String.format(DISTANCE_FORMAT_KM, distance));
			}
		} else {
			textView.setText("距离未知");
		}
	}

	/**
	 * 格式化输出的价格,并注入到textView中,例子: ￥ 1.20/kWh
	 *
	 * @param textView
	 * @param price
	 */
	public static void infusePrice(TextView textView, double price) {
		textView.setText(String.format(PRICE_FORMAT, price));
	}

	public static void infusePrice(TextView textView, String price) {
		textView.setText(String.format(PERIOD_PRICE_STRING_FORMAT, price));
	}

	public static String formatPirce(double price) {
		return String.format(PRICE_FORMAT, price);
	}

	public static String formatParkingPirce(double price) {
		return String.format(PARKING_PRICE_FORMAT, price);
	}

	public static String formatPirce(String price) {
		return String.format(PERIOD_PRICE_STRING_FORMAT, price);
	}

	public static String formatMoney(double money) {
		return String.format(MONEY_FORMAT, money);
	}

	public static String formatQuantity(double quantity) {
		return String.format(QUANTITY_FORMAT, quantity);
	}

	public static String formatPeriodPrice(double quantity) {
		return String.format(PERIOD_PRICE_FORMAT, quantity);
	}

	public static String formatBill(double bill) {
		return String.format(BILL_FORMAT, bill);
	}

	public static String formatDefaultNumber(double number) {
		return String.format(DEFAULT_NUMBER_FORMAT, number);
	}

	public static String formatRatio(double number) {
		return String.format(RATIO_FORMAT, number);
	}

	public static String formatRechargeMoney(float money){
		return String.format(RECHARGE_MONEY, money);
	}

	public static String formatADecimal(float score){
		return String.format(FORMAT_A_DECIMAL, score);
	}

	public static String formatQuantity2(float quantity){
		return String.format(FORMAT_QUANTITY, quantity);
	}

}
