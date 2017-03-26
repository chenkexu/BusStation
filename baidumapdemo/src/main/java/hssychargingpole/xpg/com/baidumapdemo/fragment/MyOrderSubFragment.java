package hssychargingpole.xpg.com.baidumapdemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.MapActivity;
import hssychargingpole.xpg.com.baidumapdemo.adapter.MyOrderSubAdapter;
import hssychargingpole.xpg.com.baidumapdemo.bean.FoodOrder;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseLazyFragment;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


/**
 * Created by lenovo on 2017/3/15.
 */
public class MyOrderSubFragment extends BaseLazyFragment implements View.OnClickListener{

    private LinearLayout yuyueChargeLayout;  //没有任何的订单
    private Button btnYuyueCharge; //立即去购买
    private RecyclerView recyclerView;
    private int orderType; //订单的类型
    private String userId;
    private MyOrderSubAdapter myOrderSubAdapter;
    private List<FoodOrder> foodOrders = new ArrayList<>();


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_yuyue_charge:
                //去购买
                getAbstractActivity().startActivity(MapActivity.class);
                break;
        }
    }


    public interface OrderType{
        int order_wait_pay = 0;         //等待付款的订单
        int order_Complete = 1;         //已经完成的订单
        int order_unused = 2;           //失效的订单
        int order_all = 3;              //全部的订单
    }

    public static MyOrderSubFragment getMemeberInstance(int orderType){
        MyOrderSubFragment myOrderSubFragment = new MyOrderSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConstants.ORDER_TYPE,orderType);
        myOrderSubFragment.setArguments(bundle);
        return myOrderSubFragment;
    }


    @Override
    public void onFirstUserVisible() {
        Bundle bundle = getArguments();
        orderType = bundle.getInt(IntentConstants.ORDER_TYPE);
        loadData();
    }


    private void loadData(){
       switch (orderType){
           case OrderType.order_all:
               break;
           case OrderType.order_Complete:
               break;
           case OrderType.order_unused:
               break;
           case OrderType.order_wait_pay:
               break;
       }

    }








    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myorder_tab,null);
    }

    @Override
    public void builderView(View rootView) {
        yuyueChargeLayout = (LinearLayout) rootView.findViewById(R.id.yuyue_charge_layout);
        btnYuyueCharge = (Button) rootView.findViewById(R.id.btn_yuyue_charge);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        if (getMyApplication().isMemberLogin()){
            String userId = getMyApplication().getMember().getUserId();
            this.userId = userId;
        }
        foodOrders.add(new FoodOrder());
        myOrderSubAdapter = new MyOrderSubAdapter(foodOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAbstractActivity(), VERTICAL,false));
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myOrderSubAdapter);
    }










    @Override
    public void onUserVisible() {
        onFirstUserVisible();
    }

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserInvisible() {

    }




}
