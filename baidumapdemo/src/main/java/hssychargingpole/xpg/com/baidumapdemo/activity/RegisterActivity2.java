package hssychargingpole.xpg.com.baidumapdemo.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.util.ToastUtil;
import com.wxhl.core.utils.T;
import com.wxhl.core.utils.TextUtil;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.view.EditDeleteText;

public class RegisterActivity2 extends AbstractActivity {
    private LinearLayout top;
    private LinearLayout content;
    private TextView tvTips;
    private TextView etPhone;
    private EditDeleteText etPwd;
    private EditDeleteText etNickname;
    private RelativeLayout contents;
    private ImageView personalSex;
    private TextView sex;
    private RadioGroup sexTabNew;
    private RadioButton sexMale;
    private RadioButton sexFemale;
    private RelativeLayout read;
    private TextView readBt;
    private TextView readTv;
    private LinearLayout bottom;
    private Button next;
    private int gender = 1; // 1为男，2为女
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        setTitle("进一步完善信息");

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                registerCount();//注册
                break;
        }
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        top = (LinearLayout) findViewById(R.id.top);
        content = (LinearLayout) findViewById(R.id.content);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        etPhone = (TextView) findViewById(R.id.et_phone);
        etPwd = (EditDeleteText) findViewById(R.id.et_pwd);
        etNickname = (EditDeleteText) findViewById(R.id.et_nickname);
        contents = (RelativeLayout) findViewById(R.id.contents);
        personalSex = (ImageView) findViewById(R.id.personal_sex);
        sex = (TextView) findViewById(R.id.sex);
        sexTabNew = (RadioGroup) findViewById(R.id.sex_tab_new);
        sexMale = (RadioButton) findViewById(R.id.sex_male);
        sexFemale = (RadioButton) findViewById(R.id.sex_female);
        read = (RelativeLayout) findViewById(R.id.read);
        readBt = (TextView) findViewById(R.id.read_bt);
        readTv = (TextView) findViewById(R.id.read_tv);
        bottom = (LinearLayout) findViewById(R.id.bottom);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        if (phone != null) {
            etPhone.setText(phone + "");
        }
        sexTabNew.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // 获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                // 根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) RegisterActivity2.this.findViewById(radioButtonId);
                // 更新文本内容，以符合选中项
                gender = rb.getText().toString().equals("男") ? 1 : 2;
            }
        });

    }

    private void registerCount() {
        String pwd = etPwd.getText().toString();//密码
        String nickname = etNickname.getText().toString();//用户名

        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show(this, "请设置登录密码");
            return;
        }
        if (TextUtil.isChinese(pwd)) {
            ToastUtil.show(this, "密码不能为中文");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            ToastUtil.show(this, "密码长度为6到16位");
            return;
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPwd(pwd);
        loginInfo.setNickName(nickname);
        loginInfo.setGender(gender);
        loginInfo.setPhone(phone);
        boolean save =   loginInfo.saveOrUpdate("phone=?", phone);
        if (save){
            T.showShort(getApplication(),"注册成功");
            startActivity(RegisterActivitySuccessfully.class);
            getMyApplication().saveMember(loginInfo);
            addAnim();
        }else{
            T.showShort(getApplication(),"注册失败");
        }
    }


}
