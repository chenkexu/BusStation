package com.wxhl.core.activity.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * BaseRecyclerAdapter基类
 * Created by CaiBingZhang on 15/8/24.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter{

    protected List<T> mData;
//    protected Context context;

    public BaseRecyclerAdapter(List<T> mData){
//        this.context=context;
        this.mData=mData;
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 将所有数据添加到集合最前面
     * @param list
     */
    public void addDataToStart(List<T> list) {
        mData.addAll(0, list);
        notifyDataSetChanged();
    }

    /**
     * 将所有数据添加到集合中（后面）
     * @param list
     */
    public void addDataToEnd(List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 单加单条数据
     * @param position
     * @param t
     */
    public void add(int position,T t){
        mData.add(position,t);
        notifyItemInserted(position); //通知适配器更新视图
    }

    /**
     * 删除单条数据
     * @param position
     */
    public void remove(int position){
        mData.remove(position);
        notifyItemRemoved(position);  //通知适配器更新视图
    }

    /**
     * 添加集合数据
     * @param list
     */
    public void setmData(List<T> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 获取集合数据
     * @return
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 获取Adapter大小
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}
