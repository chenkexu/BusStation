package hssychargingpole.xpg.com.baidumapdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.loopj.android.http.RequestParams;
import com.wxhl.core.api.EloadType;
import com.wxhl.core.utils.CollectionUtil;
import com.wxhl.core.utils.EStyle;
import com.wxhl.core.utils.JSONParseUtil;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.T;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.adapter.NewsAdapter;
import hssychargingpole.xpg.com.baidumapdemo.api.RequestAPI;
import hssychargingpole.xpg.com.baidumapdemo.bean.featured.FeaturedVo;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseFragment;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static com.wxhl.core.api.EloadType.FIRST;
import static com.wxhl.core.api.EloadType.PAGE;
import static com.wxhl.core.api.EloadType.REFRESH;

public class FragmentWeixin extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener,
		BaseQuickAdapter.RequestLoadMoreListener {
	private RecyclerView mRecyclerView;
	private NewsAdapter newsAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
//	private TipInfoLayout tipInforLayout;
	private View errorView;
	private MultiStateView mMultiStateView;
	private List<FeaturedVo> list = new ArrayList<>();
	private int page = 1;
	private View notLoadingView;

	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_weixin, container, false);
	}

	@Override
	public void builderView(View rootView) {
		L.e("builderView_FragmentMe执行了！！");
		getAbstractActivity().setActionBarTitle("新闻列表");

		mMultiStateView = (MultiStateView) rootView;

		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);

		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
		mSwipeRefreshLayout.setOnRefreshListener(this);//允许下拉刷新

		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getAbstractActivity(), VERTICAL,false));
		//设置Item增加、移除动画
//		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		newsAdapter = new NewsAdapter(list, getAbstractActivity());
		newsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		newsAdapter.isFirstOnly(false);//第一次加载的时候才有动画
		newsAdapter.setOnLoadMoreListener(this);//允许下拉加载更多
		mRecyclerView.setAdapter(newsAdapter);
		loadData(EloadType.FIRST);

		//RecyclerView子条目的点击事件
		mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

			@Override
			public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int positnion) {
				T.show(getMyApplication(),"条目被点击了一下",1);
			}

			//整个条目条目的长按事件
			@Override
			public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
				T.show(getMyApplication(),"条目被长按了一下",1);
			}

			//子控件的短按事件
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				switch (view.getId()){
					case R.id.tv_description:
						T.show(getMyApplication(),"描述信息被点击了一下",1);
						break;
					case R.id.iv_portrait:
						T.show(getMyApplication(),"头像被点击了一下",1);
						break;
				}
			}


			//子控件的长按事件
			@Override
			public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

				switch (view.getId()){
					case R.id.tv_description:
						T.show(getMyApplication(),"描述信息被长按了一下",1);
						break;
					case R.id.iv_portrait:
						T.show(getMyApplication(),"头像被长按了一下",1);
						break;
				}
			}


		});
	}

	/**
	 * 加载网络数据、、、
	 */
	private void loadData(final EloadType eloadType) {
		AbstractActivity.WxhlAsyncTask wxhlAsyncTask = getAbstractActivity().new WxhlAsyncTask(false) {
			@Override
			public void loadSuccess(String responseInfo) { //成功请求到数据
//				mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);//不显示状态，显示内容
				List<FeaturedVo> list = JSONParseUtil.parseArray(responseInfo, FeaturedVo.class);
				switch (eloadType) {
					case FIRST:
						newsAdapter.setNewData(list);
						break;
					case PAGE: //成功分页加载
						if(CollectionUtil.listIsNull(list)){ //---没有的更多数据
							newsAdapter.loadComplete();//不加载数据了
							if (notLoadingView == null) {
								notLoadingView = getAbstractActivity().getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
							}
							newsAdapter.addFooterView(notLoadingView);//显示“End”
						}else{
							//分页返回数据
							newsAdapter.addData(list);
						}
						break;
					case REFRESH:
						if(CollectionUtil.listIsNull(list)){
							getAbstractActivity().showSnackbarMessage(EStyle.INFO,"暂无更新");
						}

						//1: 增量更新【需要单独接口支持，将列表最新一条数据的主键ID传递给服务器，由服务器去判断此id后更新数据并返回】
						/**
						 if(CollectionUtil.listIsNull(list)){
						 T.showLong(getAbstractActivity(),"暂无更新");
						 }else{
						 //将更新数据，放入最顶部
						 adapter.addAll(0,list);
						 }
						 */
						//2：重新赋值
//                        adapter.setData(list);
						//3：将数据全部添加到列表最顶部
//                        adapter.addAll(0,list);
						//4：假更新， 假设：服务器返回主键ID且ID是自增长
						//当前列表最新id的值
						List<FeaturedVo> tempList = new ArrayList<>();
						int oldId = newsAdapter.getItem(0).getId();
						for (FeaturedVo featuredVo: list){
							//服务器返回最新数据id
							int newId = featuredVo.getId();
							if (newId>oldId){
								//添加到新集合中
								tempList.add(featuredVo);
							}
						}

						if(CollectionUtil.listIsNull(tempList)){
//                            T.showLong(getAbstractActivity(),"暂无更新");
							getAbstractActivity().showSnackbarMessage(EStyle.INFO,"暂无更新");
						}else{
//                            T.showLong(getAbstractActivity(),"更新了"+tempList.size()+"条数据");
							getAbstractActivity().showSnackbarMessage(EStyle.INFO,"更新了"+tempList.size()+"条数据");
							//将更新数据，放入最顶部
							newsAdapter.addAll(0,tempList);
						}
						//完成下拉刷新
						mSwipeRefreshLayout.setRefreshing(false);
						break;
				}

			}

			@Override
			public void exception() {
				switch (eloadType) {
					case PAGE:
						newsAdapter.showLoadMoreFailedView();
						page--;
						break;
					case FIRST://第一次加载异常
						mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
						mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										Toast.makeText(getMyApplication(), "Fetching Data", Toast.LENGTH_SHORT).show();
									    loadData(FIRST);//再进行第一次加载
									}
								});
						break;
					case REFRESH:
						mSwipeRefreshLayout.setRefreshing(false);
						break;
				}
				//异常操作
				getAbstractActivity().showSnackbarMessage(EStyle.ALERT,"网络异常，请检查网络！");
			}
		};

		RequestParams requestParams = new RequestParams();

		if(eloadType == REFRESH){
			requestParams.put("page",1);
		}else{  //FIRST ,PAGE
			requestParams.put("page",page);
		}

//		wxhlAsyncTask.setTipInfoLayout(tipInforLayout);
        wxhlAsyncTask.setMultiStateView(mMultiStateView);
		wxhlAsyncTask.get(eloadType == FIRST ?true:false, RequestAPI.FEATURED, requestParams);
	}

	@Override
	public void onRefresh() {
		loadData(REFRESH);
	}

	@Override
	public void onLoadMoreRequested() {
		page++;
		loadData(PAGE);
	}
}




