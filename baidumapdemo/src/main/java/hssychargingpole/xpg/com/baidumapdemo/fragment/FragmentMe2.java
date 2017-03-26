package hssychargingpole.xpg.com.baidumapdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxhl.core.utils.L;

import org.litepal.crud.DataSupport;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.LoginActivity;
import hssychargingpole.xpg.com.baidumapdemo.activity.MyorderActivity;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.core.BaseLazyFragment;
import hssychargingpole.xpg.com.baidumapdemo.utils.ImageLoaderUtils;

import static hssychargingpole.xpg.com.baidumapdemo.R.id.btn_setting_ll;
import static hssychargingpole.xpg.com.baidumapdemo.R.id.tv_username;
import static hssychargingpole.xpg.com.baidumapdemo.R.id.wallet_rl;

/**
 * Created by lenovo on 2017/3/13.
 */

public class FragmentMe2 extends BaseLazyFragment implements View.OnClickListener{
    private RelativeLayout llUserInfo;
    private ImageView ivUsericon;
    private TextView tvUsername;
    private LinearLayout badgeView;
    private Button btnMessage;
    private LinearLayout llytMe;
    private LinearLayout myAppointRl;
    private TextView btnOrder;
    private LinearLayout walletRl;
    private TextView btnWallet;
    private LinearLayout circleRl;
    private TextView btnCircle;
    private LinearLayout collectRl;
    private TextView btnCollect;
    private LinearLayout llMyEvaluate;
    private TextView btnEvluate;
    private LinearLayout btnSettingLl;
    private TextView btnSetting;
    private LinearLayout aboutAppRl;
    private TextView aboutAppTv;


    @Override
    public void onFirstUserVisible() {
        L.e("--------FragmentMe2,-用户第一次可见-----------");
        if (getMyApplication().isMemberLogin()){
            LoginInfo loginInfo = getMyApplication().getMember();
            ImageLoaderUtils.displayCircle(getAbstractActivity(),ivUsericon,loginInfo.getUserAvaterUrl());
            tvUsername.setText(loginInfo.getNickName());
        }
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


    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.e("-------------FragmentMe2---getRootView---------------");
        return inflater.inflate(R.layout.fragment_me2,null);
    }

    @Override
    public void builderView(View rootView) {
        L.e("-------------FragmentMe2-builderView---------------");
        getAbstractActivity().setActionBarTitle("我的");
        llUserInfo = (RelativeLayout) rootView.findViewById(R.id.ll_userInfo);
        ivUsericon = (ImageView) rootView.findViewById(R.id.iv_usericon);
        tvUsername = (TextView) rootView.findViewById(tv_username);
        badgeView = (LinearLayout) rootView.findViewById(R.id.badge_view);
        btnMessage = (Button) rootView.findViewById(R.id.btn_message);
        llytMe = (LinearLayout) rootView.findViewById(R.id.llyt_me);
        myAppointRl = (LinearLayout) rootView.findViewById(R.id.my_appoint_rl);
        btnOrder = (TextView) rootView.findViewById(R.id.btn_order);
        walletRl = (LinearLayout) rootView.findViewById(wallet_rl);
        btnWallet = (TextView) rootView.findViewById(R.id.btn_wallet);
        circleRl = (LinearLayout) rootView.findViewById(R.id.circle_rl);
        btnCircle = (TextView) rootView.findViewById(R.id.btn_circle);
        collectRl = (LinearLayout) rootView.findViewById(R.id.collect_rl);
        btnCollect = (TextView) rootView.findViewById(R.id.btn_collect);
        llMyEvaluate = (LinearLayout) rootView.findViewById(R.id.ll_my_evaluate);
        btnEvluate = (TextView) rootView.findViewById(R.id.btn_evluate);
        btnSettingLl = (LinearLayout) rootView. findViewById(btn_setting_ll);
        btnSetting = (TextView) rootView.findViewById(R.id.btn_setting);
        aboutAppRl = (LinearLayout) rootView.findViewById(R.id.about_app__rl);
        aboutAppTv = (TextView) rootView.findViewById(R.id.about_app_tv);

        btnSettingLl.setOnClickListener(this);
        myAppointRl.setOnClickListener(this);
        circleRl.setOnClickListener(this);
        collectRl.setOnClickListener(this);
        llMyEvaluate.setOnClickListener(this);
        btnSettingLl.setOnClickListener(this);
        aboutAppRl.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_appoint_rl: //我的订单
                if(getMyApplication().isMemberLogin()){
                    String userId = getMyApplication().getMember().getUserId();
                    Intent intent = new Intent(getAbstractActivity(), MyorderActivity.class);
                    intent.putExtra(IntentConstants.USER_ID,userId);
                    getAbstractActivity().startActivity(intent);
                }else{
                    tologin();
                }

                break;
            case R.id.wallet_rl: //我的钱包
                DataSupport.deleteAll(LoginInfo.class);
                break;

            case R.id.personal_sex: //我的收藏

                break;
            case R.id.btn_setting_ll: //我的设置
//                DataSupport.deleteAll(LoginInfo.class);
                break;
            case R.id.circle_rl: //个人中心

                break;
            case R.id.about_app__rl: //关于战级监控

                break;
            case R.id.ll_my_evaluate://我的评价

                break;






        }
    }

    private void tologin() {
        getAbstractActivity().startActivity(new Intent(getAbstractActivity(), LoginActivity.class));
    }
}
