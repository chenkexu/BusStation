package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.fragment.MyOrderFragment;

/**
 * Created by lenovo on 2017/3/15.
 * 我的订单的页面
 */

public class MyorderActivity extends AbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_framelayout);
        setActionBarTitle("我的订单");
    }

    @Override
    protected void init() {
        if (getIntent()!=null){
            Intent intent = getIntent();
            String userId = intent.getStringExtra(IntentConstants.USER_ID);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_id,new MyOrderFragment()).commitAllowingStateLoss();
    }

    @Override
    public void widgetClick(View v) {

    }
}
