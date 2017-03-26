package mvp.circledemo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hssychargingpole.xpg.com.baidumapdemo.R;
import mvp.circledemo.widgets.CommentListView;
import mvp.circledemo.widgets.ExpandTextView;
import mvp.circledemo.widgets.PraiseListView;
import mvp.circledemo.widgets.SnsPopupWindow;



/**
 * Created by yiw on 2016/8/16.
 *
 * 说说的ViewHolder
 */
public abstract class CircleViewHolder extends RecyclerView.ViewHolder  {

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);//头像
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);//名字
        digLine = itemView.findViewById(R.id.lin_dig);//点赞和发表评论的分割线

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);//说说的内容
        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);//分享的链接
        timeTv = (TextView) itemView.findViewById(R.id.timeTv); //时间
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn); //删除按钮
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn); //评论或点赞的图片
        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());//点击弹出的popwindow

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

}
