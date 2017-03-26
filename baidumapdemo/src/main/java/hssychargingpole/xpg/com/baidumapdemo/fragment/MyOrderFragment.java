package hssychargingpole.xpg.com.baidumapdemo.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.core.BaseTabViewPageFragment2;

/**
 * Created by lenovo on 2017/3/15.
 */

public class MyOrderFragment extends BaseTabViewPageFragment2 {

    @Override
    protected void initViewpagerFragmentList(List<Fragment> fragmentList, List<String> tagList) {
        tagList.add("待支付");
        tagList.add("已完成");
        tagList.add("已失效");
        tagList.add("全部");
        fragmentList.add(MyOrderSubFragment.getMemeberInstance(MyOrderSubFragment.OrderType.order_wait_pay));
        fragmentList.add(MyOrderSubFragment.getMemeberInstance(MyOrderSubFragment.OrderType.order_Complete));
        fragmentList.add(MyOrderSubFragment.getMemeberInstance(MyOrderSubFragment.OrderType.order_unused));
        fragmentList.add(MyOrderSubFragment.getMemeberInstance(MyOrderSubFragment.OrderType.order_all));
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserInvisible() {

    }
}
