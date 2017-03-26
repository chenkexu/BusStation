package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.adapter.ArticleAdapter;
import hssychargingpole.xpg.com.baidumapdemo.bean.Article;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class ArticleActivity extends AbstractActivity {
    private RecyclerView mRecyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
    }



    @Override
    protected void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL,false));
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articles.add(new Article());
        articleAdapter = new ArticleAdapter(articles);

        mRecyclerView.setAdapter(articleAdapter);
    }

    @Override
    public void widgetClick(View v) {

    }
}
