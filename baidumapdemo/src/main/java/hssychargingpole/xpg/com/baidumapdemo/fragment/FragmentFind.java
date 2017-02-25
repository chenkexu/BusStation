package hssychargingpole.xpg.com.baidumapdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.Main2Activity;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseLazyFragment;




public class FragmentFind extends BaseLazyFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
	private static final String TAG = FragmentFind.class.getSimpleName();
	private TextView install;




	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_find,null);
	}

	@Override
	public void builderView(View rootView) {
	 	install = (TextView)rootView. findViewById(R.id.install);
        install.setOnClickListener(this);
	}


	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMoreRequested() {

	}



	@Override
	public void onFirstUserVisible() {
		Log.e(TAG, "FragmentFindonFirstUserVisible: 第一次用户可见");
	}

	@Override
	public void onUserVisible() {
		Log.e(TAG, "FragmentFindonUserVisible: 用户可见");
	}

	@Override
	public void onFirstUserInvisible() {
		Log.e(TAG, "FragmentFindonFirstUserInvisible: 第一次用户不可见");
	}

	@Override
	public void onUserInvisible() {
		Log.e(TAG, "FragmentFindonUserInvisible: 用户不可见");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.install:
				startActivity(new Intent(getActivity(), Main2Activity.class));
				break;
		}



	}
}
