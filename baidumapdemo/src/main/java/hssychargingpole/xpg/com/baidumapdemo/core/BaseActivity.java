package hssychargingpole.xpg.com.baidumapdemo.core;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.kennyc.view.MultiStateView;
import com.wxhl.core.utils.CoreUtil;
import com.wxhl.core.utils.EStyle;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.NetWorkUtil;
import com.wxhl.core.utils.T;

import java.lang.reflect.Method;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.activity.MainActivity;

/**
 * Created by CaiBingZhang on 15/8/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public String TAG = this.getClass().getSimpleName();
    protected boolean isTemplate=true; //是否使用模板
    private Toolbar toolbar;
    public LinearLayout mainBody;            //主体内容的显示
//    protected TipInfoLayout mTipInfoLayout;  //正在加载中的提示信息
    protected MultiStateView mMultiStateView;
    // 是否允许全屏
    private boolean mAllowFullScreen = false;
    private CoordinatorLayout coordinatorLayout;
    protected RadioGroup radioGroup;
    protected FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        }

        if(!NetWorkUtil.NETWORK){
            T.show(getApplicationContext(),"请检查网络设置",2);
        }

        CoreUtil.addAppActivity(this);//把每一个打开的activity添加到App列表集合中，易于维护
        if(isTemplate){ //如果使用模板
            setContentView(R.layout.core_template);//带toolbar的activity
            initWidget();//初始化控件
        }

        if(this instanceof MainActivity){   //首页不需要返回键,需要radioGroup
            radioGroup.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
        } else { //添加返回键
            if(getSupportActionBar()!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //给左上角图标的左边加上一个返回的图标
            }
            radioGroup.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        }
    }

    /**
     * 初化控件
     */
    protected void initWidget(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_id);
        mainBody = (LinearLayout) findViewById(R.id.view_mainBody_id);//中间的内容
        toolbar= (Toolbar) findViewById(R.id.id_toolbar);//toolBar控件
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));//小标题的颜色
        setSupportActionBar(toolbar);
        mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);//主体模板中的提示语信息
        radioGroup = (RadioGroup) findViewById(R.id.rg_bottom);;//主体模板中的提示语信息
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);;//主体模板中的提示语信息
//        mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);//主体模板中的提示语信息
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(layoutResID == R.layout.core_template){  //如果写的是是核心布局
            super.setContentView(layoutResID);
        }else{
            if(mainBody!=null){ //如果初始化了控件
                mainBody.removeAllViews();//主体内容区域清空
                View view = getLayoutInflater().inflate(layoutResID,null);
                mainBody.addView(view,
                        new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            }else{  //如果沒有使用模板
                super.setContentView(layoutResID);
                initWidget();
            }

            init();
        }
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }
    public abstract void widgetClick(View v);

    /**
     * 初始化控件
     */
    protected abstract void init();

    /**
     * 解决Toolbar中Menu中图标不显示的问题， onCreateOptionsMenu中无效
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    L.e(getClass().getSimpleName() + "onMenuOpened...unable to set icons for overflow menu" + e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回Toolbar
     * @return
     */
    public Toolbar getToolbar(){
        return toolbar;
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 设置正标题
     * @param titleName
     */
    public void setActionBarTitle(String titleName){
        getSupportActionBar().setTitle(titleName);
    }

    /**
     * 设置标题
     * @param strResId
     */
    public void setActionBarTitle(@StringRes int strResId){
        getSupportActionBar().setTitle(getString(strResId));
    }

    /**
     * 设置副标题
     * @param subtitleName
     */
    public void setActionBarSubtitleName(String subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }

    /**
     * 设置副标题
     * @param subtitleName
     */
    public void setActionBarSubtitleName(@StringRes int subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }

    private static final int red = 0xfff44336;
    private static final int green = 0xff4caf50;
    private static final int blue = 0xff2195f3;
    private static final int orange = 0xffffc107;

    /**
     * @author caibing.zhang
     * @createdate 2012-9-24 上午9:10:36
     * @Description: 显示Snackbar消息
     * @param message
     */
    public void showSnackbarMessage(EStyle style, String message){
        int colorResId;
        switch (style){
            case ALERT:
                colorResId=red;
                break;
            case WARNING:
                colorResId=orange;
                break;
            case CONFIRM:
                colorResId=green;
                break;
            case INFO:
                colorResId=blue;
                break;
            default:
                colorResId=blue;
                break;
        }
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        //设置显示文件的颜色
//        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(colorResId);
//        view.setBackgroundColor(AttrUtil.getValueOfColorAttr(this,R.styleable.Theme,R.styleable.Theme_colorPrimary));
        view.setBackgroundColor(colorResId);

        snackbar.setAction(R.string.btn_sure_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(android.R.color.black));

        snackbar.show();
    }

    public void showSnackbarMessage(EStyle style, @StringRes int resString){
        showSnackbarMessage(style, getString(resString));
        T.show(getApplicationContext(),"请检查网络设置",2);
    }






    public void startActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
    }

    public void startActivity(Class<?> cls,Intent intent){
        startActivity(intent.setClass(this, cls));
    }

    public void startActivityForResult(Intent intent, int requestCode){
        super.startActivityForResult(intent, requestCode);
    }



    /**
     * @Description: 跳转前判断网络
     * @param cls
     */
    public void startActivityNetWork(Class<?> cls){
        if(!NetWorkUtil.NETWORK){
//            showSnackbarMessage(EStyle.ALERT,R.string.not_network);
            return;
        }
        startActivity(cls);
    }

    /**
     * @Description: 跳转前判断网络
     * @param intent
     */
    public void startActivityNetWork(Intent intent){
        if(!NetWorkUtil.NETWORK){
//            showSnackbarMessage(EStyle.ALERT,R.string.not_network);
            return;
        }
        startActivity(intent);
    }

    /**
     * @Description: 跳转前判断网络
     * @param cls
     * @param intent
     */
    public void startActivityNetWork(Class<?> cls,Intent intent){
        if(!NetWorkUtil.NETWORK){
//            showSnackbarMessage(EStyle.ALERT,R.string.not_network);
            return;
        }
        startActivity(cls, intent);
    }

    /**
     * @Description: 跳转前判断网络,有返回值
     * @param intent
     * @param requestCode
     */
    public void startActivityForResultNetWork(Intent intent,int requestCode){
        if(!NetWorkUtil.NETWORK){
//            showSnackbarMessage(EStyle.ALERT,R.string.not_network);
            return;
        }
        startActivityForResult(intent, requestCode);
    }




    /** 子类可以重写决定是否使用透明状态栏 */
    protected boolean translucentStatusBar() {
        return false;
    }

    /** 子类可以重写改变状态栏颜色 */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /** 获取主题色 */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        CoreUtil.removeAppActivity(this);
        super.onDestroy();
    }




}
