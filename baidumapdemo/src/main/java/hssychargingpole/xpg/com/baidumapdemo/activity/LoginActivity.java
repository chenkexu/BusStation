package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.easy.util.ToastUtil;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.T;

import hssychargingpole.xpg.com.baidumapdemo.DB.DbHelper;
import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.dialog.LoadingDialog;
import hssychargingpole.xpg.com.baidumapdemo.manager.ShareApiManager;


public class LoginActivity extends AbstractActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    private Button btLogin;
    private ImageView ivWeixinLogin;
    private ImageView ivQqLogin;
    private Button btnForgetPwd;
    private Button btnCreateAccount;
    private LoadingDialog loadingDialog;
    private LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    protected void init() {
        username = (TextInputLayout) findViewById(R.id.username);
        password = (TextInputLayout) findViewById(R.id.password);
        btLogin = (Button) findViewById(R.id.bt_login);
        ivWeixinLogin = (ImageView) findViewById(R.id.iv_weixin_login);
        ivQqLogin = (ImageView) findViewById(R.id.iv_qq_login);
        btnForgetPwd = (Button) findViewById(R.id.btn_forgetPwd);
        btnCreateAccount = (Button) findViewById(R.id.btn_createAccount);
        btLogin.setOnClickListener(this);
        ivWeixinLogin.setOnClickListener(this);
        ivQqLogin.setOnClickListener(this);
        btnForgetPwd.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
        setActionBarTitle("登录");
    }


    @Override
    public void widgetClick(View v) {
            switch (v.getId()){
                case R.id.bt_login://登录
                    login();
                    break;
                case R.id.btn_forgetPwd://忘记密码

                    break;
                case R.id.btn_createAccount://注册
                    //先绑定手机号
                    Intent registerIntent = new Intent(LoginActivity.this, BindPhoneNumActivity.class);
                    startActivity(registerIntent);
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                    break;
                case R.id.iv_qq_login: {
                    qqLogin();
                    break;
                }
                case R.id.iv_weixin_login: {
                    weChatLogin();
                    break;
                }
            }
       }

    //微信登录
    private void weChatLogin() {
        if (ShareApiManager.isInstallWechat(this)) {
            dismissLoadingDialog();
            loadingDialog = new LoadingDialog(LoginActivity.this, R.string.waiting);
            loadingDialog.showDialog();
            ShareApiManager.wechatLogin(this, new ShareApiManager.ActionListener<LoginInfo>() {
                @Override
                public void onComplete(LoginInfo loginInfo) {
                    LoginActivity.this.loginInfo = loginInfo;//设置loginfo,logInfo不为空了就。
                    LoginActivity.this.loginInfo = loginInfo;
                    Log.e("getToken", loginInfo.getToken());
                    Log.e("getNickName", loginInfo.getNickName());
                    Log.e("getUserId", loginInfo.getUserId());
                    Log.e("getGender", loginInfo.getGender() + "");
                    Log.e("getUserType", loginInfo.getUserType() + "");
                    //调用登录的接口
                }

                @Override
                public void onError() {
                    dismissLoadingDialog();
                    T.showShort(getApplication(),"第三方登录失败请重试");
                }

                @Override
                public void onCancel() {
                    dismissLoadingDialog();
                    T.showShort(getApplication(),"第三方登录取消");

                }
            });
        } else {
            ToastUtil.show(this, R.string.please_install_wechat);
        }
    }

    //QQ登录
    private void qqLogin() {
        dismissLoadingDialog();
        loadingDialog = new LoadingDialog(LoginActivity.this, R.string.waiting);
        loadingDialog.showDialog();
        ShareApiManager.qqLogin(this, new ShareApiManager.ActionListener<LoginInfo>() {

            @Override
            public void onComplete(LoginInfo loginInfo) {
                L.e("*****************onComplete******");
                LoginActivity.this.loginInfo = loginInfo;
                Log.e("getToken", loginInfo.getToken());
                Log.e("getNickName", loginInfo.getNickName());
                Log.e("getUserId", loginInfo.getUserId());
                Log.e("getGender", loginInfo.getGender() + "");
                Log.e("getUserType", loginInfo.getUserType() + "");
                // TODO: 2017/3/1   调用qq进行登录的接口使用登录的接口
                if(DbHelper.isBindPhone(loginInfo.getUserId())){ //如果绑定过手机号
                    getMyApplication().saveMember(loginInfo);//保存用户信息到文件中
                    startActivity(MainActivity.class);
                    addAnim();
                }else{ //没有绑定过手机号
                    //去绑定手机号页面
                    Intent intent = new Intent(LoginActivity.this,BindPhoneNumActivity.class);
                    intent.putExtra(IntentConstants.LOGINFO_THIRDCOUNT, loginInfo);
                    startActivity(intent);
                    addAnim();
                }
//                Intent intent = new Intent(LoginActivity.this,BindPhoneNumActivity.class);
//                intent.putExtra(IntentConstants.LOGINFO_THIRDCOUNT, loginInfo);
//                startActivity(intent);
//                addAnim();
//                dismissLoadingDialog();
            }

            @Override
            public void onError() {
                dismissLoadingDialog();
                T.showShort(getApplication(),"第三方登录失败请重试");
            }

            @Override
            public void onCancel() {
                dismissLoadingDialog();
                T.showShort(getApplication(),"登录取消");
            }
        });
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }




    private void login() {
        String phone = username.getEditText().toString();
        String pwd = password.getEditText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
            T.showShort(this,"请输入用户名和密码。");
            return;
        }
        // TODO: 2017/3/7  调用输入用户名密码就行登录的接口；
    }
}



