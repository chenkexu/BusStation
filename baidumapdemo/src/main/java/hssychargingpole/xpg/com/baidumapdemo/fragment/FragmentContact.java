package hssychargingpole.xpg.com.baidumapdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.wxhl.core.api.EloadType;
import com.wxhl.core.utils.EStyle;
import com.wxhl.core.utils.L;
import com.wxhl.core.widget.TipInfoLayout;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.Main2Activity;
import hssychargingpole.xpg.com.baidumapdemo.adapter.LauageAdapter;
import hssychargingpole.xpg.com.baidumapdemo.api.RequestAPI;
import hssychargingpole.xpg.com.baidumapdemo.bean.language.LanguageVo;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseFragment;




public class FragmentContact extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
	private RecyclerView mRecyclerView;
    private LauageAdapter lauageAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private TipInfoLayout tipInforLayout;
	private View errorView;
    private List<LanguageVo> list = new ArrayList<>();
	private MultiStateView mMultiStateView;

	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.e("getRootView执行了！！");
		return inflater.inflate(R.layout.fragment_weixin,container,false);
	}

	@Override
	public void builderView(View rootView) {
		L.e("builderView执行了！！");
		getAbstractActivity().setActionBarTitle("语言列表");
//		tipInforLayout = (TipInfoLayout) rootView.findViewById(R.id.fl_panent_id_contact);
		mMultiStateView = (MultiStateView) rootView;
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);

		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
		//设置Item增加、移除动画
//		mRecyclerView.setItemAnimator(new DefaultItemAnimator());



//		lauageAdapter = new LauageAdapter(R.layout.fragment_language_item,list);
//		mRecyclerView.setAdapter(lauageAdapter);
//		loadData(EloadType.FIRST);
        //RecyclerView子条目的点击事件
		mRecyclerView.addOnItemTouchListener(new OnItemClickListener( ){

			@Override
			public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int positnion) {
				L.e("position"+positnion);
				getAbstractActivity().startActivity(Main2Activity.class);
			}
		});
	}

	/**
	 * ------------------->>start:Android之取消ViewPage+Fragment的预加载——数据    OnCreateView之前调用
	 */
	private boolean mHasLoadedOnce = true;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && mHasLoadedOnce
				&& getActivity()!=null&& lauageAdapter==null) { //第一次加载的时候才会执行
			mHasLoadedOnce = false;
			lauageAdapter = new LauageAdapter(R.layout.fragment_language_item,list);
			lauageAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
			lauageAdapter.isFirstOnly(false);//第一次加载的时候才有动画
			
			mRecyclerView.setAdapter(lauageAdapter);
			loadData(EloadType.FIRST);
			L.e("setUserVisibleHint执行了if............");
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		L.e("onActivityCreated执行了");
		setUserVisibleHint(getUserVisibleHint());
	}
	/*** ------------------->>end--------------------------------*/



	private void loadData(final EloadType eloadType) {

		getAbstractActivity().new WxhlAsyncTask(false) {
			@Override
			public void loadSuccess(String responseInfo) {
				List<LanguageVo> list = new ArrayList<>(
						JSONArray.parseArray(responseInfo, LanguageVo.class));//获得网络返回的数据
				switch (eloadType){
					case FIRST:{  //第一次加载成功
						lauageAdapter.setNewData(list);
						//完成下拉刷新
						mSwipeRefreshLayout.setRefreshing(false);
					}
					    break;
					case REFRESH:{  //下拉刷新成功后
                        lauageAdapter.setNewData(list);
						//完成下拉刷新
						mSwipeRefreshLayout.setRefreshing(false);
					}
						break;
					case PAGE:{ //加载更多

					}
						break;
				}

			}

			@Override
			public void exception() {
				switch (eloadType){
					case  FIRST://第一次加载失败，
//                        lauageAdapter.showLoadMoreFailedView();
						break;
					case REFRESH://下拉刷新失败
						//下拉刷新结束
						mSwipeRefreshLayout.setRefreshing(false);
						break;
				}
                //异常操作
				getAbstractActivity().showSnackbarMessage(EStyle.ALERT,"网络异常");
			}
		}.setMultiStateView(mMultiStateView)
		 .get(lauageAdapter == null ? true : false, RequestAPI.LANGUAGES_List, null);
	}


	//下拉刷新
	@Override
	public void onRefresh() {
		loadData(EloadType.REFRESH);
	}

	//上拉加载更多
	@Override
	public void onLoadMoreRequested() {

	}
}
