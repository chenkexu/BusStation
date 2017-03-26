package hssychargingpole.xpg.com.baidumapdemo.manager;

import android.content.Context;
import android.util.Log;

import com.easy.util.ToastUtil;
import com.wxhl.core.utils.L;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;


/**
 * Created by black-Gizwits on 2015/09/16.
 *
 *用shareSDK要把官方Demo中所有资源拷贝到自己的项目中，包括values里面所有的资源
 *
 *
 */
public class ShareApiManager {
	public static final int USER_TYPE_WECHAT = 3;
	public static final int USER_TYPE_QQ = 4;

	public static void oneKeyShareDownloadImage(Context context, String imageUrl, ShareListener shareListener) {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(context.getString(R.string.app_name));
//		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(imageUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我在使用站级监控app，欢迎使用");
		oks.setUrl(imageUrl);
		oks.setImageUrl("http://sweetystory.com/Public/ttwebsite/theme1/style/img/special-1.jpg");
		oks.setShareListener(shareListener);
		oks.show(context);
	}



	public static void oneKeyShareLocalImage(Context context, String imagePath) {
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		oks.setTitle(context.getString(R.string.app_name));
//		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// text是分享文本，所有平台都需要这个字段
//		oks.setText(context.getString(R.string.share_text));
		oks.setImagePath(imagePath);
		oks.show(context);
//		oks.setCallback(callback);
	}


	/**
	 * QQ和Qzone第三方登录
	 *
	 * @param context
	 */
	public static void qqLogin(Context context) {
		qqLogin(context, null);
	}



	public static void qqLogin(Context context, final ActionListener<LoginInfo> listener) {

		Platform qzone = ShareSDK.getPlatform(context, QQ.NAME);
		qzone.removeAccount(true);
		qzone.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

				L.e("-----------onComplete---------------------");
				Set<Map.Entry<String, Object>> entries = hashMap.entrySet();
//				for (Map.Entry entry : entries) {
//					Log.e("entry", entry.getKey().toString() + " : " + entry.getValue().toString());
//				}
				for (String str : hashMap.keySet()) {
					Log.e("entry", str + " : " + hashMap.get(str));
				}
				//登录信息存储在platform里面
				Log.e("userId", "userId: " + platform.getDb().getUserId());
				Log.e("userId", "Token: " + platform.getDb().getToken());
				Log.e("userId", "TokenSecret: " + platform.getDb().getTokenSecret());
				Log.e("userId", "UserName: " + platform.getDb().getUserName());
				Log.e("userId", "UserIcon: " + platform.getDb().getUserIcon());


				Object avater = hashMap.get("figureurl_qq_2");
				LoginInfo loginInfo = new LoginInfo();
				loginInfo.setGender(hashMap.get("gender").toString().equals("男") ? 1 : 2);
				loginInfo.setNickName(hashMap.get("nickname").toString());
				loginInfo.setToken(platform.getDb().getToken());
				loginInfo.setUserId(platform.getDb().getUserId());
				loginInfo.setUserAvaterUrl(avater == null ? "" : avater.toString());
				loginInfo.setUserType(USER_TYPE_QQ);

				if (listener != null) {
					listener.onComplete(loginInfo);
				}
			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
				L.e(i);
				L.e("-----------onError---------------------"+throwable.toString());
				if (listener != null) {
					listener.onError();
				}
			}

			@Override
			public void onCancel(Platform platform, int i) {
				L.e("-----------onCancel---------------------");
				if (listener != null) {
					listener.onCancel();
				}
			}
		});
//		qzone.
		qzone.SSOSetting(false); //设置false表示使用SSO授权方式，使用了SSO授权后，有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权。
		qzone.showUser(null);
	}

	/**
	 * 微信第三方登录
	 *
	 * @param context
	 */

	public static void wechatLogin(final Context context) {
		wechatLogin(context, null);
	}

	public static boolean isInstallWechat(Context context) {
		ShareSDK.initSDK(context);
		Platform weChat = ShareSDK.getPlatform(context, Wechat.NAME);
		return weChat.isClientValid();
	}

	public static void wechatLogin(final Context context, final ActionListener<LoginInfo> listener) {
		ShareSDK.initSDK(context);
		final Platform weChat = ShareSDK.getPlatform(context, Wechat.NAME); //得到微信操作平台
		weChat.removeAccount();//取消授权
		if (weChat.isAuthValid()) {//判断授权码是否处于有效状态
			String userId = weChat.getDb().getUserId();
			ToastUtil.show(context, userId);
		} else {
			//平台操作成功的回调
			weChat.setPlatformActionListener(new PlatformActionListener() {
				@Override//平台操作成功的回调
				public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
					for (String key : hashMap.keySet()) {
						Log.e("entry", key + " : " + hashMap.get(key));
					}
					Log.e("userId", "userId: " + platform.getDb().getUserId());
					Object avater = hashMap.get("headimgurl");

					LoginInfo loginInfo = new LoginInfo();
					loginInfo.setGender((Integer) hashMap.get("sex"));
					loginInfo.setNickName(hashMap.get("nickname").toString());
					loginInfo.setUserAvaterUrl(avater == null ? "" : avater.toString());
					loginInfo.setToken(platform.getDb().getToken());
					loginInfo.setUserId(platform.getDb().getUserId());
					loginInfo.setUserType(USER_TYPE_WECHAT);

					if (listener != null) {
						listener.onComplete(loginInfo);//授权成功
					}
				}

				@Override//平台操作失败的回调
				public void onError(Platform platform, int i, Throwable throwable) {
					if (listener != null) {
						listener.onError();
					}
				}

				@Override
				public void onCancel(Platform platform, int i) {
					if (listener != null) {
						listener.onCancel();
					}
				}
			});
			weChat.removeAccount(true);
			weChat.SSOSetting(false); //设置false表示使用SSO授权方式，使用了SSO授权后，有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权。
			weChat.showUser(null);//授权并获取用户信息
		}
	}

	public static String getPlatformName(Platform platform) {
		String platForName = "";
		switch (platform.getName()) {
			case "QQ": {
				platForName = "QQ";
				break;
			}
			case "QZone": {
				platForName = "QQ空间";
				break;
			}
			case "Wechat": {
				platForName = "微信";
				break;
			}
			case "WechatMoments": {
				platForName = "微信朋友圈";
				break;
			}
			default: {
				platForName = platform.getName();
				break;
			}
		}
		return platForName;
	}



	public interface ActionListener<T> {
		//授权成功
		void onComplete(T t);

		void onError();

		void onCancel();
	}
}
