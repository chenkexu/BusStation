package hssychargingpole.xpg.com.baidumapdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.FoodOrder;




/**
 * Created by lenovo on 2017/3/15.
 */
public class MyOrderSubAdapter extends BaseQuickAdapter<FoodOrder> {


    public MyOrderSubAdapter(List<FoodOrder> data) {
        super(R.layout.item_order_info_view,data);
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, FoodOrder foodOrder) {

    }
}
