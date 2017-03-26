package mvp.circledemo.mvp.presenter;


import android.view.View;

import java.util.List;

import mvp.circledemo.bean.CircleItem;
import mvp.circledemo.bean.CommentConfig;
import mvp.circledemo.bean.CommentItem;
import mvp.circledemo.bean.FavortItem;
import mvp.circledemo.listener.IDataRequestListener;
import mvp.circledemo.mvp.contract.CircleContract;
import mvp.circledemo.mvp.modle.CircleModel;
import mvp.circledemo.utils.DatasUtil;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*  创建一个Presenter，实现定义的借口，然后实现Presenter接口里面的方法。
 */
public class CirclePresenter implements CircleContract.Presenter{
	private CircleModel circleModel;//model层
	private CircleContract.View view;//view层

	public CirclePresenter(CircleContract.View view){
		circleModel = new CircleModel();
		this.view = view; //为接口赋值
	}

	/**
	 * 导入所有的数据（实现方法）
	 * @param loadType
     */
	public void loadData(int loadType){
        List<CircleItem> datas = DatasUtil.createCircleDatas();//创造数据
        if(view!=null){
            view.update2loadData(loadType, datas);//更新界面（实现方法由activity实现，这是方法的调用）
        }
	}


	/***
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId){
		circleModel.deleteCircle(new IDataRequestListener() {
			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
					view.update2DeleteCircle(circleId);
				}
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition){
		circleModel.addFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				FavortItem item = DatasUtil.createCurUserFavortItem();//创建点赞的列表
                if(view !=null ){
                    view.update2AddFavorite(circlePosition, item);
                }
			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int circlePosition, final String favortId){
		circleModel.deleteFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view !=null ){
                    view.update2DeleteFavort(circlePosition, favortId);
                }
			}
		});
	}
	
	/**
	 * 发表评论
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		circleModel.addComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) { //发布评论
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					//回复某人
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}
                if(view!=null){
					//
                    view.update2AddComment(config.circlePosition, newItem);
                }
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		circleModel.deleteComment(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteComment(circlePosition, commentId);
                }
			}
			
		});
	}

	/**
	 *显示编辑评论的界面
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
        if(view != null){
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
	}


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
