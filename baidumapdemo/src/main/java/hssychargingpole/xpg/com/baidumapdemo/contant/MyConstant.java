package hssychargingpole.xpg.com.baidumapdemo.contant;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class MyConstant {

	public static List<Activity> activitys = new ArrayList<Activity>();

	// 本应用对应的外部目录（sd卡）
	public static final String PATH = "HSSY";
	public static final String PHOTO_DIR = "/SHARE";
	// 最后一次连接的蓝牙mac地址
	public static final String BT_MAC_LAST = "bt_mac_last";
	// 列表每次加载多少行
	public static final int PAGE_SIZE = 20;

	public static final int CAR_PAGE_SIZE = 10;

	public static final int AMENITIES_TYPE_WIFI = 1;
	public static final int AMENITIES_TYPE_STORE = 4;
	public static final int AMENITIES_TYPE_PARKING_SPACE = 3;
	public static final int AMENITIES_TYPE_CAMERA = 2;
	public static final int AMENITIES_TYPE_MARKET = 5;
	public static final int AMENITIES_TYPE_RESTAURANT = 6;
	public static final int AMENITIES_TYPE_HOTEL = 7;

	//铁沿线+交通枢纽为“ 1”、住宅小区为“ 2”、大型商超为“ 3”、 4S 店为“ 4”、科技园区+学校为“ 5”、景区为“ 6”、高速公路服务区“ 7”、办公场所为“ 8”、其他为“ 9”
	public static final int FUNCTION_TYPE_TRAFFIC = 1;
	public static final int FUNCTION_TYPE_LIVING = 2;
	public static final int FUNCTION_TYPE_MARKET= 3;
	public static final int FUNCTION_TYPE_4S_SHOP = 4;
	public static final int FUNCTION_TYPE_SCHOOL = 5;
	public static final int FUNCTION_TYPE_SCENIC = 6;
	public static final int FUNCTION_TYPE_SERVICE_AREA = 7;
	public static final int FUNCTION_TYPE_OFFICE = 8;
	public static final int FUNCTION_TYPE_OTHER = 9;

	public static final int PAYWAY_NONE = 0;
	public static final int PAYWAY_BAIFUBAO = 1;
	public static final int PAYWAY_ALIPAY = 2;
	public static final int PAYWAY_BAIFUBAO_WITHHOLDING = 3; //第一次签约代扣
	public static final int PAYWAY_REMAIN_COIN = 4; //使用
	public static final int PAYWAY_WECHAT = 5; //微信支付

	public static final String EZO_APP_KEY = "b11f6c3ce9ca4e8e9b9665ed5456f734";
	public static final String WEIMAO_URL = "http://www.wemart.cn/mobile/?chanId=&sellerId=4081&a=shelf&m=index";

	public static final String SYSTEM_ADMIN_ID = "1014429971935514502";

}
