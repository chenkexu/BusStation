package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.easy.util.ToastUtil;
import com.wxhl.core.utils.T;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import hssychargingpole.xpg.com.baidumapdemo.DB.DbHelper;
import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.utils.UIUtils;

import static hssychargingpole.xpg.com.baidumapdemo.R.id.get_valid_num;


/**
 * 绑定手机号的页面
 */
public class BindPhoneNumActivity extends AbstractActivity {
    private static String APPKEY = "1bea50960d46b";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "403d65787b6e770ca808c8126df692f7";
    private TextInputLayout phone;
    private TextInputLayout vaild;
    private Button getValidNum;
    private Button next;
    private EventHandler eventHandler;
    private String phoneStr;
    private String vaildStr;
    private TimeCount time;//计时器
    private boolean voildNumIsTrue = true;
    private LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册界面");

        // 初始化短信SDK
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) { //验证码校验正确
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.d("TAG", "填写验证码正确");
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                T.showShort(getApplication(),"填写验证码正确");
                                phoneStr = phone.getEditText().getText().toString();//手机号
                                registerAfter(phoneStr);
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                time.start();// 开始计时
                            }
                        });
                        //获取验证码成功
                        Log.d("TAG", "获取验证码成功");
                    }
                } else {  //校验码验证错误
                    final Throwable throwable = (Throwable) data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showLong(getApplication(),throwable.toString());
                            T.showLong(getApplication(),"验证码错误");
                        }
                    });
                }
            }
        };
        //注册短信验证的监听
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void init() {
        //获取loginInfo
        loginInfo = (LoginInfo) getIntent().getSerializableExtra(IntentConstants.LOGINFO_THIRDCOUNT);
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象，一共60秒，每隔一秒变化一次
        phone = (TextInputLayout) findViewById(R.id.et_phone);//名字
        vaild = (TextInputLayout) findViewById(R.id.vaild);//验证码
        getValidNum = (Button) findViewById(get_valid_num);//获取验证码
        next = (Button) findViewById(R.id.next);//下一步
        getValidNum.setOnClickListener(this);
        next.setOnClickListener(this);
        phoneStr = phone.getEditText().getText().toString();//手机号
        vaildStr = vaild.getEditText().getText().toString();//验证码
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case get_valid_num://获取验证码
                getValidNum();
                break;
            case R.id.next://下一步
                next();//注册
                break;
        }
    }

    private void next() {
        phoneStr = phone.getEditText().getText().toString();//手机号
        vaildStr = vaild.getEditText().getText().toString();//验证码
        if (phoneStr == null || "".equals(phoneStr)
                || !phoneStr.subSequence(0, 1).equals("1")) {
            ToastUtil.show(this, "手机号不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.show(this, "请输入11位手机号");
            return;
        } else if (vaildStr == null || "".equals(vaildStr)) {
            ToastUtil.show(this, "验证码不能为空");
            return;
        }
        //对验证码进行验证->回调函数
        SMSSDK.submitVerificationCode("86", phoneStr, vaildStr);
    }

    private void registerAfter(String phoneStr){
        if(loginInfo!=null){ //使用第三方登录的
            //保存用户信息
            loginInfo.setPhone(phoneStr);
            loginInfo.saveOrUpdate("phone=?", phoneStr);
            getMyApplication().saveMember(loginInfo);
            startActivity(RegisterActivitySuccessfully.class);
            addAnim();
        }else { //不是第三方登录，去注册个人信息
            Intent intent = new Intent(BindPhoneNumActivity.this,RegisterActivity2.class);
            intent.putExtra("phone",phoneStr);
            startActivity(intent);
        }
        finish();
    }



    //获取短信验证码
     private void getValidNum(){
         phoneStr = phone.getEditText().getText().toString();//手机号

         if (DbHelper.findUserByPhone(phoneStr)){
             SMSSDK.getVerificationCode("86", phoneStr);//获取验证码
         }else{
             T.showShort(getApplication(),"此号码已经注册，请返回直接登录");
             return;
         }

    }


    /**
     * @author Mazoh 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getValidNum.setText("获取验证码");
            getValidNum.setEnabled(true);
            vaild.getEditText().setText("");//验证码填"";
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getValidNum.setEnabled(false);
            getValidNum.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }
}
