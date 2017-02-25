package hssychargingpole.xpg.com.baidumapdemo.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.easy.util.ToastUtil;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.ChargeStation;
import hssychargingpole.xpg.com.baidumapdemo.dialog.WaterBlueDialogComfirmNav;
import hssychargingpole.xpg.com.baidumapdemo.utils.CalculateUtil;

import static hssychargingpole.xpg.com.baidumapdemo.R.id.btn_book_charge;
import static hssychargingpole.xpg.com.baidumapdemo.R.id.btn_navigation;
import static hssychargingpole.xpg.com.baidumapdemo.R.id.tv_distance;

/**
 * Created by Administrator on 2016/12/20.
 */

/**
 * 底部弹窗
 */

public class StationInfoMapPop extends BasePop implements View.OnClickListener {


    private RelativeLayout rlStationInfo;
    private TextView tvName;
    private TextView tvStationProgres;
    private ImageView ivPileImage;
    private TextView tvLocation;
    private TextView tvDistance;
    private View topSpliteLine;
    private LinearLayout llNavigation;
    private Button btnBookCharge;
    private Button btnNavigation;
    private Context context;
    private BDLocation mLocation;//当前的位置
    private ChargeStation chargeStation;//popWindow中要显示的充电站


    public StationInfoMapPop(Context context, BDLocation mLocation, ChargeStation chargeStation) {
        super(context);// 必须调用父类的构造函数
        this.context = context;
        this.mLocation = mLocation;
        this.chargeStation = chargeStation;
    }


    @Override
    protected void initUI() {
        super.initUI();
        View v = LayoutInflater.from(mActivity).inflate(R.layout.layout_station_info_map_pop, null);//popWindow的布局

        // ps，关于pop，我们也可以在构造函数中传入view，而不必setContentView，因为构造函数中的view，其实最终也要setContentView
        setContentView(v);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(false);
        setFocusable(true);// 取得焦点
        //设置可以点击
        setTouchable(true);

        rlStationInfo = (RelativeLayout) v.findViewById(R.id.rl_station_info);
        tvName = (TextView) v.findViewById(R.id.tv_name);
        tvStationProgres = (TextView) v.findViewById(R.id.tv_station_progres);
        ivPileImage = (ImageView) v.findViewById(R.id.iv_pile_image);
        tvLocation = (TextView) v.findViewById(R.id.tv_location);
        tvDistance = (TextView) v.findViewById(tv_distance);//距离
        topSpliteLine = (View) v.findViewById(R.id.top_splite_line);
        llNavigation = (LinearLayout) v.findViewById(R.id.ll_navigation);
        btnBookCharge = (Button) v.findViewById(btn_book_charge);
        btnNavigation = (Button) v.findViewById(btn_navigation);

    }

    /**
     * 初始化popWindow上面的数据
     */
    public  void init(){
        // 计算距离
        if (chargeStation.getLatitude() != null && chargeStation.getLongitude() != null) {
            tvDistance.setVisibility(View.VISIBLE);
            LatLng eLatLng = new LatLng(chargeStation.getLongitude(), chargeStation.getLatitude());
            CalculateUtil.infuseDistance(context, tvDistance, eLatLng);
            // 设定距离文本,利用html文本用橙色颜色标记距离数
//			String distanceInfo = context.getString(R.string.to_tag_distance, String.format("%.1f", distance));
//			tv_distance.setText(Html.fromHtml(String.format("%.1f", distance) + "km"));
        } else {
            tvDistance.setText("距离未知");
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnNavigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_navigation:
                callNavigation();
                break;
        }
    }

    //打开导航
    private void callNavigation() {
        //初始化导航弹窗
        WaterBlueDialogComfirmNav waterBlueDialogComfirmNav = new WaterBlueDialogComfirmNav(context);
        if (mLocation != null) {
            //显示弹窗
            waterBlueDialogComfirmNav.show(mLocation.getLatitude(), mLocation.getLongitude(), chargeStation.getLatitude(),chargeStation.getLongitude());
        } else {
            ToastUtil.show(context, "尚未定位您的位置,请稍候");
        }
        dismiss();

    }
}
