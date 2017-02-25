package hssychargingpole.xpg.com.baidumapdemo.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.language.LanguageVo;


/**
 * Created by lenovo on 2017/2/13.
 */

public class LauageAdapter extends BaseQuickAdapter<LanguageVo> {

    public LauageAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, LanguageVo languageVo) {
        baseViewHolder.setText(R.id.language_textview, languageVo.getName());
        baseViewHolder.setText(R.id.languageThemeNum_textview, languageVo.getProjects_count()+"");
    }
}