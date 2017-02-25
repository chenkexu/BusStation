package hssychargingpole.xpg.com.baidumapdemo.popwindow;

import android.app.Activity;
import android.content.Context;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/12/20.
 */

public class BasePop extends PopupWindow {
    protected Activity mActivity;

    public BasePop() {
    }

    public BasePop(Context context) {
        mActivity = (Activity) context;
        initData();
        initUI();
        initEvent();
    }

    //数据
    protected void initData() {

    }

    //布局
    protected void initUI() {

    }

    //点击事件
    protected void initEvent() {

    }
}