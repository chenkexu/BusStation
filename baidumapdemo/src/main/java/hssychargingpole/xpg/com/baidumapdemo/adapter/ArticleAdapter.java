package hssychargingpole.xpg.com.baidumapdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.Article;

/**
 * Created by lenovo on 2017/3/16.
 */

public class ArticleAdapter extends BaseQuickAdapter<Article> {

    public ArticleAdapter(List data) {
        super(R.layout.item_comment_circle, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Article article) {
          baseViewHolder.setText(R.id.tv_name,"哈哈");
    }
}
