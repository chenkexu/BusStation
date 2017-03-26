package mvp.circledemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import mvp.circledemo.adapter.CircleAdapter;
import mvp.circledemo.bean.CircleItem;
import mvp.circledemo.bean.CommentConfig;
import mvp.circledemo.bean.CommentItem;
import mvp.circledemo.bean.FavortItem;
import mvp.circledemo.mvp.contract.CircleContract;
import mvp.circledemo.mvp.presenter.CirclePresenter;
import mvp.circledemo.utils.CommonUtils;
import mvp.circledemo.widgets.CommentListView;
import mvp.circledemo.widgets.DivItemDecoration;
import mvp.circledemo.widgets.TitleBar;

/**
 * 
* @ClassName: MainActivity 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午4:21:18
 */
public class CircleActivity extends YWActivity implements CircleContract.View {

	protected static final String TAG = CircleActivity.class.getSimpleName();
	private CircleAdapter circleAdapter;
	private LinearLayout edittextbody;
	private EditText editText;
	private ImageView sendIv;

	private int screenHeight;
	private int editTextBodyHeight;
	private int currentKeyboardH;
	private int selectCircleItemH;
	private int selectCommentItemOffset;

	private CirclePresenter presenter;//presenter
	private CommentConfig commentConfig;
	private SuperRecyclerView recyclerView;
	private RelativeLayout bodyLayout;
	private LinearLayoutManager layoutManager;
    private TitleBar titleBar;
    private final static int TYPE_PULLREFRESH = 1;   //刷新
    private final static int TYPE_UPLOADREFRESH = 2; //加载更多
    private SwipeRefreshLayout.OnRefreshListener refreshListener;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		presenter = new CirclePresenter(this);
		initView();
        //实现自动下拉刷新功能
        recyclerView.getSwipeToRefresh().post(new Runnable(){
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });
	}




	@Override
    protected void onDestroy() {
        if(presenter !=null){
            presenter.recycle();
        }
        super.onDestroy();
    }

    @SuppressLint({ "ClickableViewAccessibility", "InlinedApi" })
	private void initView() {
        initTitle();
		recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

		/**
		 * 点击RecyclerView，键盘消失
		 */
		recyclerView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (edittextbody.getVisibility() == View.VISIBLE) {
					updateEditTextBodyVisible(View.GONE, null);
					return true;
				}
				return false;
			}
		});

		/**
		 * 下拉刷新
		 */
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadData(TYPE_PULLREFRESH);
                    }
                }, 2000);
            }
        };
        recyclerView.setRefreshListener(refreshListener);

		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if(newState == RecyclerView.SCROLL_STATE_IDLE){
					Glide.with(CircleActivity.this).resumeRequests();
				}else{
					Glide.with(CircleActivity.this).pauseRequests();
				}
			}
		});

		circleAdapter = new CircleAdapter(this);
		circleAdapter.setCirclePresenter(presenter);
        recyclerView.setAdapter(circleAdapter);
		edittextbody = (LinearLayout) findViewById(R.id.editTextBodyLl); //评论框的整体布局
		editText = (EditText) findViewById(R.id.circleEt);//编辑要发表的评论
		sendIv = (ImageView) findViewById(R.id.sendIv);

		//发表评论
		sendIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (presenter != null) {
					//发布评论
					String content =  editText.getText().toString().trim();
					if(TextUtils.isEmpty(content)){
						Toast.makeText(CircleActivity.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
						return;
					}
					//发布评论
					presenter.addComment(content, commentConfig);
				}
				//键盘消失
				updateEditTextBodyVisible(View.GONE, null);
			}
		});

		setViewTreeObserver();
	}



	/**
	 * 初始化标题栏
	 */
    private void initTitle() {

        titleBar = (TitleBar) findViewById(R.id.main_title_bar);
        titleBar.setTitle("朋友圈");
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.title_bg));
    }


    private void setViewTreeObserver() {
		bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
		final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
		swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
            public void onGlobalLayout() {
            	
                Rect r = new Rect();
				bodyLayout.getWindowVisibleDisplayFrame(r);
				int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
				if(r.top != statusBarH ){
					//在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
					r.top = statusBarH;
				}
                int keyboardH = screenH - (r.bottom - r.top);
				Log.d(TAG, "screenH＝ "+ screenH +" &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
                	return;
                }

				currentKeyboardH = keyboardH;
            	screenHeight = screenH;//应用屏幕的高度
            	editTextBodyHeight = edittextbody.getHeight();

                if(keyboardH<150){//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
				//偏移listview
				if(layoutManager!=null && commentConfig != null){
					layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(commentConfig));
				}
            }
        });
	}

	/**
	 * 获取状态栏高度
	 * @return
	 */
	private int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
           if(edittextbody != null && edittextbody.getVisibility() == View.VISIBLE){
        	   //edittextbody.setVisibility(View.GONE);
			   updateEditTextBodyVisible(View.GONE, null);
        	   return true;
           }
        }
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 加载所有的评论
	 * @param loadType
	 * @param datas
	 */
	@Override
	public void update2loadData(int loadType, List<CircleItem> datas) {
		if (loadType == TYPE_PULLREFRESH){ //下拉刷新
			recyclerView.setRefreshing(false);
			circleAdapter.setDatas(datas);
		}else if(loadType == TYPE_UPLOADREFRESH){ //加载更多
			circleAdapter.getDatas().addAll(datas);
		}
		circleAdapter.notifyDataSetChanged();

		if(circleAdapter.getDatas().size()< 45 + CircleAdapter.HEADVIEW_SIZE){
			recyclerView.setupMoreListener(new OnMoreListener() {
				@Override
				public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							presenter.loadData(TYPE_UPLOADREFRESH);
						}
					}, 2000);

				}
			}, 1);
		}else{
			recyclerView.removeMoreListener();
			recyclerView.hideMoreProgress();
		}

	}




	/**
	 * 删除一条动态
	 * @param circleId
     */
	@Override
	public void update2DeleteCircle(String circleId) {
		List<CircleItem> circleItems = circleAdapter.getDatas();
		for(int i=0; i<circleItems.size(); i++){
			if(circleId.equals(circleItems.get(i).getId())){
				circleItems.remove(i);
				circleAdapter.notifyDataSetChanged();
				//circleAdapter.notifyItemRemoved(i+1);
				return;
			}
		}
	}

	/**
	 * 增加一个赞
	 * @param circlePosition
	 * @param addItem
     */
	@Override
	public void update2AddFavorite(int circlePosition, FavortItem addItem) {
		if(addItem != null){
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
			circleAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
		}
	}

	/**
	 * 删除一个赞
	 * @param circlePosition
	 * @param favortId
     */
	@Override
	public void update2DeleteFavort(int circlePosition, String favortId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
		List<FavortItem> items = item.getFavorters();
		for(int i=0; i<items.size(); i++){
			if(favortId.equals(items.get(i).getId())){
				items.remove(i);
				circleAdapter.notifyDataSetChanged();
                //circleAdapter.notifyItemChanged(circlePosition+1);
				return;
			}
		}
	}

	/**
	 * 增加一个评论
	 * @param circlePosition
	 * @param addItem
     */
	@Override
	public void update2AddComment(int circlePosition, CommentItem addItem) {
		if(addItem != null){
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition); //根据position获得说说的条目
            item.getComments().add(addItem); //增加一个评论的条目
			circleAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
		}
		//清空评论文本
		 editText.setText("");
	}

	/**
	 * 删除一个评论
	 * @param circlePosition
	 * @param commentId
     */
	@Override
	public void update2DeleteComment(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
		List<CommentItem> items = item.getComments();
		for(int i=0; i<items.size(); i++){
			if(commentId.equals(items.get(i).getId())){
				items.remove(i);
				circleAdapter.notifyDataSetChanged();
                //circleAdapter.notifyItemChanged(circlePosition+1);
				return;
			}
		}
	}

	/**
	 * 更新键盘的界面
	 * @param visibility
	 * @param commentConfig
     */
	@Override
	public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
		this.commentConfig = commentConfig;
		edittextbody.setVisibility(visibility);

		measureCircleItemHighAndCommentItemOffset(commentConfig);

		if(View.VISIBLE==visibility){
			 editText.requestFocus();
			//弹出键盘
			CommonUtils.showSoftInput( editText.getContext(),  editText);

		}else if(View.GONE==visibility){
			//隐藏键盘
			CommonUtils.hideSoftInput( editText.getContext(),  editText);
		}
	}




    /**
	 * 测量偏移量
	 * @param commentConfig
	 * @return
	 */
	private int getListviewOffset(CommentConfig commentConfig) {
		if(commentConfig == null)
			return 0;
		//这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
		//int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight - titleBar.getHeight();
		if(commentConfig.commentType == CommentConfig.Type.REPLY){
			//回复评论的情况
			listviewOffset = listviewOffset + selectCommentItemOffset;
		}
        Log.i(TAG, "listviewOffset : " + listviewOffset);
		return listviewOffset;
	}

	private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
		if(commentConfig == null)
			return;

		int firstPosition = layoutManager.findFirstVisibleItemPosition();
		//只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);

		if(selectCircleItem != null){
			selectCircleItemH = selectCircleItem.getHeight();
		}

		if(commentConfig.commentType == CommentConfig.Type.REPLY){
			//回复评论的情况
			CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
			if(commentLv!=null){
				//找到要回复的评论view,计算出该view距离所属动态底部的距离
				View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
				if(selectCommentItem != null){
					//选择的commentItem距选择的CircleItem底部的距离
					selectCommentItemOffset = 0;
					View parentView = selectCommentItem;
					do {
						int subItemBottom = parentView.getBottom();
						parentView = (View) parentView.getParent();
						if(parentView != null){
							selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
						}
					} while (parentView != null && parentView != selectCircleItem);
				}
			}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

		} else {
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(CircleActivity.this, "RESULT_CANCELED", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void showLoading(String msg) {

	}

	@Override
	public void hideLoading() {

	}

	@Override
	public void showError(String errorMsg) {

	}

}
