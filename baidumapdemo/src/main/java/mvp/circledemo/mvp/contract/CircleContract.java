package mvp.circledemo.mvp.contract;


import java.util.List;

import mvp.circledemo.bean.CircleItem;
import mvp.circledemo.bean.CommentConfig;
import mvp.circledemo.bean.CommentItem;
import mvp.circledemo.bean.FavortItem;

/**
 * Created by suneee on 2016/7/15.
 *
 * 定义所有需要的接口，要操作的所有的方法。写在这里，Presenter去实现这些方法
 */
public interface CircleContract {

    interface View extends BaseView{            //-------定义接口

        //实现接口的CircleActivity实现具体的方法
        void update2DeleteCircle(String circleId);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleItem> datas); //声明一个方法

}
    interface Presenter extends BasePresenter{
        //定义接口的CircleActivity调用这些方法
        void loadData(int loadType);
        void deleteCircle(final String circleId);
        void addFavort(final int circlePosition);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);
    }
}
