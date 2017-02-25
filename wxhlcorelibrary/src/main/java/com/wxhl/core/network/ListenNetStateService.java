package com.wxhl.core.network;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import com.wxhl.core.utils.L;
import com.wxhl.core.utils.NetWorkUtil;


//监听手机网络状态（包括GPRS，WIFI， UMTS等)
public class ListenNetStateService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                L.e("网络状态已经改变");
                ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
                NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
                NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();  
                if(!wifiInfo.isConnected() && !mobileInfo.isConnected()){
                	NetWorkUtil.NETWORK=false;
                	L.e("没有可用网络");
                }else{
                	NetWorkUtil.NETWORK=true;
                    L.e("当前网络名称：" + activeInfo.getTypeName());
                }

            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册网络广播
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        L.e("网络状态——监听：开始");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        L.e("网络状态——监听：结束");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
