package hssychargingpole.xpg.com.baidumapdemo;


import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.wxhl.core.activity.CoreApplication;
import com.wxhl.core.utils.FileLocalCache;

import org.litepal.LitePal;

import java.util.logging.Level;

import cn.sharesdk.framework.ShareSDK;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.contant.Constants;
import hssychargingpole.xpg.com.baidumapdemo.manager.LbsManager;


public class MyApplication extends CoreApplication {
    private LoginInfo loginInfo;   //会员对象


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();




        // 百度地图在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        // 初始化百度定位服务
        LbsManager.getInstance().init(this);
        LitePal.initialize(this);
        //初始化ShareSDK
        ShareSDK.initSDK(this);

        //OkGo必须调用初始化
        OkGo.init(this);

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//                  .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                                //方法一：信任所有证书,不安全有风险
//                    .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//                    .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//                    //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//                    .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

                    //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//                    .setHostnameVerifier(new SafeHostnameVerifier())

                    //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })
//                    //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存用户信息
     * @param loginInfo
     */
    public void saveMember(LoginInfo loginInfo){
        this.loginInfo = loginInfo;
        FileLocalCache.setSerializableData(Constants.CACHE_DIR_SYSTEM,loginInfo,Constants.login_info);
//        //发送登录成功的广播
//        Intent intent = new Intent(IntentConstants.MEMBER_BROADCAST_RECEIVER);
//        sendBroadcast(intent);
    }

    /**
     * 注销用户
     */
    public void logOutMember(){
        loginInfo = null;
        FileLocalCache.delSerializableData(Constants.CACHE_DIR_SYSTEM,Constants.login_info);
//        //发送注销用户的广播
//        Intent intent = new Intent(IntentConstants.MEMBER_BROADCAST_RECEIVER);
//        Bundle bundle = new Bundle();
//        bundle.putInt(IntentConstants.MEMBER_BROADCAST_TYPE,IntentConstants.MEMBER_BROADCAST_TYPE_LOGOUT);
//        intent.putExtras(bundle);
//        sendBroadcast(intent);
    }


    /**
     * 判断用户是否登录
     * @return
     */
    public boolean isMemberLogin(){
        getMember();
        if(loginInfo != null){
            return true;
        }
        return false;
    }


    /**
     * 获取用户详细信息
     * @return
     */
    public LoginInfo getMember(){
        if(loginInfo != null){
            return loginInfo;
        }
        loginInfo = (LoginInfo) FileLocalCache.getSerializableData(Constants.CACHE_DIR_SYSTEM,Constants.login_info);
        return loginInfo;
    }




    public boolean isMethodsCompat(int versionCode) {
        //获取当前系统版本号
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion > versionCode;
    }




    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

}
