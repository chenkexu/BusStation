package hssychargingpole.xpg.com.baidumapdemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easy.adapter.EasyAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.manager.ImageLoaderManager;



/**
 * Created by black-Gizwits on 2015/10/14.
 * 评论和赞的布局
 */
public class LikeAndCommentView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {

	private String tag = getClass().getSimpleName();

	private RelativeLayout rl_like_avatar_content;
	private LinearLayout ll_like_avatar_line;
	private ImageView iv_points;
	private FixHeightListView fhlv_comment_list;
	private View split_line;
	private List<String> user_avatarList;
	private DisplayImageOptions options;

	private EasyAdapter commentListAdapter;

	private OnClickListener likeLineOnclickListener;
	private AdapterView.OnItemClickListener commentItemOnClickListener;

	private int totalLength = 0;
	private int contentLength = 0;

	public LikeAndCommentView(Context context) {
		this(context, null);
	}

	public LikeAndCommentView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LikeAndCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(getContext()).inflate(R.layout.layout_like_and_comment_view, this, true);
		initAttribute(context, attrs);
		initChild();
		initData();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public LikeAndCommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		LayoutInflater.from(getContext()).inflate(R.layout.layout_like_and_comment_view, this, true);
		initAttribute(context, attrs);
		initChild();
		initData();
	}

	private void initAttribute(Context context, AttributeSet attrs) {
	}

	private void initChild() {
		rl_like_avatar_content = (RelativeLayout) findViewById(R.id.rl_like_avatar_content);
		ll_like_avatar_line = (LinearLayout) findViewById(R.id.ll_like_avatar_line);
		iv_points = (ImageView) findViewById(R.id.iv_points);
		fhlv_comment_list = (FixHeightListView) findViewById(R.id.fhlv_comment_list);
		split_line = findViewById(R.id.split_line);
		ll_like_avatar_line.setOnClickListener(this);
		fhlv_comment_list.setOnItemClickListener(this);
	}

	private void initData() {
		user_avatarList = new ArrayList<>();
		if (!isInEditMode()) {
			options = new DisplayImageOptions.Builder().cloneFrom(ImageLoaderManager.getInstance().getDefaultDisplayOptions()).showImageForEmptyUri(R.drawable
					.touxiang).showImageOnLoading(R.drawable.touxiang).showImageOnFail(R.drawable.touxiang).displayer(new RoundedBitmapDisplayer((int)
					getResources().getDimension(R.dimen.h18))).build();

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (user_avatarList.size() == 0) {
			rl_like_avatar_content.setVisibility(GONE);
		} else {
			rl_like_avatar_content.setVisibility(VISIBLE);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	@Override
	public void onClick(View v) {
		if (likeLineOnclickListener != null) likeLineOnclickListener.onClick(v);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (commentItemOnClickListener != null) commentItemOnClickListener.onItemClick(parent, view, position, id);
	}

	public OnClickListener getLikeLineOnclickListener() {
		return likeLineOnclickListener;
	}

	public void setLikeLineOnclickListener(OnClickListener likeLineOnclickListener) {
		this.likeLineOnclickListener = likeLineOnclickListener;
	}

	public AdapterView.OnItemClickListener getCommentItemOnClickListener() {
		return commentItemOnClickListener;
	}

	public void setCommentItemOnClickListener(AdapterView.OnItemClickListener commentItemOnClickListener) {
		this.commentItemOnClickListener = commentItemOnClickListener;
	}

	public void setLlLikeVisible(boolean visible) {
		if (rl_like_avatar_content != null) {
			if (visible) {
				rl_like_avatar_content.setVisibility(VISIBLE);
			} else {
				rl_like_avatar_content.setVisibility(GONE);
			}
		}
	}

	public void addLikeUserAvatar(String url) {
//		Log.i(tag, "addLikeUserAvatar");
		user_avatarList.add(url);
		if (!user_avatarList.isEmpty()) {
			rl_like_avatar_content.setVisibility(VISIBLE);
			ll_like_avatar_line.invalidate();
			ll_like_avatar_line.post(new Runnable() {
				@Override
				public void run() {
					updateLikeAvaterLineUI();
				}
			});
		}
	}

	public void addLikeUserAvatarList(List<String> urls) {
//		Log.i(tag, "addLikeUserAvatar");
		user_avatarList.addAll(urls);
		if (!user_avatarList.isEmpty()) {
			rl_like_avatar_content.setVisibility(VISIBLE);
			ll_like_avatar_line.invalidate();
			ll_like_avatar_line.post(new Runnable() {
				@Override
				public void run() {
					updateLikeAvaterLineUI();
				}
			});
		}
	}

	public void removeLikeUserAvatar(String url) {
		int index = -1;
		for (int i = 0; i < user_avatarList.size(); i++) {
			if (user_avatarList.get(i).equals(url)) {
				index = i;
				break;
			}
		}
		if (index != -1) removeLikeUserAvatar(index);
	}

	public void removeAllLikeUserAvatar() {
		if (user_avatarList != null && user_avatarList.size() > 0) {
			user_avatarList.clear();
			ll_like_avatar_line.removeAllViews();
			rl_like_avatar_content.setVisibility(GONE);
		}
		totalLength = 0;
	}

	public void removeLikeUserAvatar(int index) {
		View child = ll_like_avatar_line.getChildAt(index);
		if (child != null) {
			totalLength -= child.getWidth();
			ll_like_avatar_line.removeView(child);
			user_avatarList.remove(index);
			if (user_avatarList.size() == 0) {
				rl_like_avatar_content.setVisibility(GONE);
			}
			ll_like_avatar_line.post(new Runnable() {
				@Override
				public void run() {
					updateLikeAvaterLineUI();
				}
			});
//			Log.i(tag, "removeLikeUserAvatar");
		}
	}

	public Adapter getCommentListAdapter() {
		return commentListAdapter;
	}

	public void setCommentListAdapter(EasyAdapter commentListAdapter) {
		this.commentListAdapter = commentListAdapter;
		if (commentListAdapter != null) {
			fhlv_comment_list.setAdapter(commentListAdapter);
			commentListAdapter.notifyDataSetChanged();
		}
	}

	public void setLikeAvatarSplitLineVisible(int visible) {
		split_line.setVisibility(visible);
	}

	public void addComment(Comment comment) {
		if (this.commentListAdapter != null && comment != null) {
			this.commentListAdapter.add(comment);
		}
	}

	public void removeAllComments(List<Comment> comments) {
		if (this.commentListAdapter != null) {
			this.commentListAdapter.remove(comments);
		}
	}

	private void updateLikeAvaterLineUI() {
		contentLength = ll_like_avatar_line.getWidth() - ll_like_avatar_line.getPaddingRight() - ll_like_avatar_line.getPaddingLeft();
		if (totalLength < contentLength) {
			ll_like_avatar_line.removeAllViews();
			totalLength = 0;
			for (String url : user_avatarList) {
				ImageView avatar = new ImageView(getContext(), null);
				LayoutParams parames = new LayoutParams((int) getResources().getDimension(R.dimen.h30), (int) getResources().getDimension(R.dimen.h30));
				avatar.setLayoutParams(parames);
				avatar.setPadding(5, 5, 5, 5);
				avatar.setImageResource(R.drawable.touxiang);
				totalLength += (int) getResources().getDimension(R.dimen.h30) + 10;
				ll_like_avatar_line.addView(avatar);
				ImageLoaderManager.getInstance().displayImage(url, avatar, options, true);
				if (totalLength >= contentLength) {
					avatar.setVisibility(GONE);
					break;
				}
			}
		}
		if (totalLength >= contentLength) {
			iv_points.setVisibility(VISIBLE);
		} else {
			iv_points.setVisibility(INVISIBLE);
		}
	}

}
