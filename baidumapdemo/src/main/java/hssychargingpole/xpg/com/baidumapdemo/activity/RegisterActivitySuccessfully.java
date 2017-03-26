package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;

public class RegisterActivitySuccessfully extends AbstractActivity {
    private TextView tvSuccess;
    private Button btnCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_successfully);
        setTitle("成功");
    }

    @Override
    public void widgetClick(View v) {
            switch (v.getId()){
                case R.id.btn_charge:
                  startActivity(MainActivity.class);
                break;
            }
    }

    @Override
    protected void init() {
        tvSuccess = (TextView) findViewById(R.id.tv_success);
        btnCharge = (Button) findViewById(R.id.btn_charge);
        btnCharge.setOnClickListener(this);
    }
}
