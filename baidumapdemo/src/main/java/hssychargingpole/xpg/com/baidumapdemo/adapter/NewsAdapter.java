package hssychargingpole.xpg.com.baidumapdemo.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.featured.FeaturedVo;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.view.RoundImageView;


/**
 * Created by lenovo on 2017/2/14.
 */

public class NewsAdapter extends BaseQuickAdapter<FeaturedVo> {
    private AbstractActivity abstractActivity;
    private List<FeaturedVo> data;

    //添加到最顶端
    public void addAll(int location, List<FeaturedVo> data){
        this.data.addAll(location,data);
        notifyDataSetChanged();
    }

    public NewsAdapter(List<FeaturedVo> data, AbstractActivity abstractActivity) {
        super(R.layout.fragment_explore_sub_item, data);
        this.abstractActivity = abstractActivity;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, FeaturedVo featuredVo) {
         String title = featuredVo.getOwner()!=null?featuredVo.getOwner().getName():"";
         title = title+ " / " + featuredVo.getName();
         baseViewHolder.setText(R.id.tv_title,title);
         String description = featuredVo.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = "暂无描述";
        }
         baseViewHolder.setText(R.id.tv_description,description);

         baseViewHolder.setText(R.id.tv_watch,featuredVo.getWatches_count()+"");
         baseViewHolder.setText(R.id.tv_star,featuredVo.getStars_count()+"");
         baseViewHolder.setText(R.id.tv_fork,featuredVo.getForks_count()+"");


         String language = featuredVo.getLanguage();


        if (TextUtils.isEmpty(language)) {
            baseViewHolder.setVisible(R.id.tv_language,false);
        } else {
            baseViewHolder.setText(R.id.tv_language,language);
        }


        //显示用户头像
//        if(project.getOwner()!=null){
//            String portraitURL = project.getOwner().getNew_portrait();
//            abstractActivity.loadHeaderImage(viewHelper.ivPrtrait,portraitURL);
        if (featuredVo.getOwner()!=null){
             RoundImageView image = baseViewHolder.getView(R.id.iv_portrait);
             abstractActivity.loadHeaderImage(image,featuredVo.getOwner().getNew_portrait());
        }
        //头像增加点击事件
        baseViewHolder.addOnClickListener(R.id.iv_portrait);
        baseViewHolder.addOnLongClickListener(R.id.iv_portrait);
        baseViewHolder.addOnClickListener(R.id.tv_description).addOnLongClickListener(R.id.tv_description);

    }


}
