package hssychargingpole.xpg.com.baidumapdemo;

import android.os.Build;

import com.baidu.mapapi.SDKInitializer;
import com.wxhl.core.activity.CoreApplication;

import org.litepal.LitePal;

import hssychargingpole.xpg.com.baidumapdemo.manager.LbsManager;


public class MyApplication extends CoreApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 百度地图在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        // 初始化百度定位服务
        LbsManager.getInstance().init(this);
        LitePal.initialize(this);
    }

    public boolean isMethodsCompat(int versionCode) {
        //获取当前系统版本号
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion > versionCode;
    }



}
