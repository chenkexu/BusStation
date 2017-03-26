package hssychargingpole.xpg.com.baidumapdemo.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.WriterException;
import com.lzy.okgo.OkGo;
import com.wxhl.core.utils.L;

import net.okgo.callback.StringDialogCallback;

import org.litepal.crud.DataSupport;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.CodeActivity;
import hssychargingpole.xpg.com.baidumapdemo.activity.LoginActivity;
import hssychargingpole.xpg.com.baidumapdemo.activity.Main2Activity;
import hssychargingpole.xpg.com.baidumapdemo.activity.MapActivity;
import hssychargingpole.xpg.com.baidumapdemo.api.RequestAPI;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseLazyFragment;
import hssychargingpole.xpg.com.baidumapdemo.manager.ShareApiManager;
import hssychargingpole.xpg.com.baidumapdemo.zxing.encoding.EncodingHandler;
import mvp.circledemo.activity.CircleActivity;
import okhttp3.Call;
import okhttp3.Response;


public class FragmentFind extends BaseLazyFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
	private static final String TAG = FragmentFind.class.getSimpleName();
	private TextView install,show;
    private Button allStation;
	private Button loginPage,btn_share,okgo_test,codeTest,circle;
    private ImageView iv_code;

	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_find,null);
	}

	@Override
	public void builderView(View rootView) {

	 	install = (TextView)rootView. findViewById(R.id.install);
		show = (TextView)rootView. findViewById(R.id.show);
	 	allStation = (Button)rootView. findViewById(R.id.all_station);
		loginPage = (Button)rootView. findViewById(R.id.bt_loginPage);
		btn_share = (Button)rootView. findViewById(R.id.btn_share);
		okgo_test = (Button)rootView. findViewById(R.id.okgo_test);
		codeTest = (Button) rootView.findViewById(R.id.codeTest);
		circle = (Button) rootView.findViewById(R.id.circle);
		iv_code = (ImageView) rootView.findViewById(R.id.iv_code);

		install.setOnClickListener(this);
		allStation.setOnClickListener(this);
		loginPage.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		okgo_test.setOnClickListener(this);
		codeTest.setOnClickListener(this);
		circle.setOnClickListener(this);
	}



	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMoreRequested() {

	}



	@Override
	public void onFirstUserVisible() {
//		Log.e(TAG, "FragmentFindonFirstUserVisible: 第一次用户可见");
	}

	@Override
	public void onUserVisible() {

//		Log.e(TAG, "FragmentFindonUserVisible: 用户可见");
	}

	@Override
	public void onFirstUserInvisible() {
//		Log.e(TAG, "FragmentFindonFirstUserInvisible: 第一次用户不可见");
	}

	@Override
	public void onUserInvisible() {
//		Log.e(TAG, "FragmentFindonUserInvisible: 用户不可见");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.install:
				startActivity(new Intent(getActivity(), Main2Activity.class));
				break;
			case R.id.all_station:
				startActivity(new Intent(getActivity(), MapActivity.class));
				break;
			case R.id.bt_loginPage:
				startActivity(new Intent(getActivity(), LoginActivity.class));
				break;
			case R.id.btn_share:
				ShareApiManager.oneKeyShareDownloadImage(getActivity(),"www.baidu.com",null);
				break;
			case R.id.okgo_test:
				testJsonByokgo();
				break;
			case R.id.codeTest:
			    startActivity(new Intent(getActivity(), CodeActivity.class));
				break;
			case R.id.circle:
			    startActivity(new Intent(getActivity(), CircleActivity.class));
				break;
		}
	}


	private void testJsonByokgo(){
		String showStr = "";
		List<LoginInfo> all = DataSupport.findAll(LoginInfo.class);
		for (LoginInfo login:all){
			String s = login.toString();
			showStr = showStr+s+"\n";
		}
		show.setText(showStr);

		OkGo.get(RequestAPI.getAbsoluteUrl(RequestAPI.FEATURED))
			.tag(this)
		    .params("page",2)
			.execute(new StringDialogCallback(getActivity()) {
				@Override
				public void onSuccess(String s, Call call, Response response) {
					super.onSuccess(s,call,response);
					L.e("-------s-:"+s);
					try {
						Bitmap rqCodeBitmap = EncodingHandler.createQRCode("1234567890",280);
						iv_code.setImageBitmap(rqCodeBitmap);
					} catch (WriterException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
				}

				@Override
				public String convertSuccess(Response response) throws Exception {
					return super.convertSuccess(response);
				}
			});
	}
}
