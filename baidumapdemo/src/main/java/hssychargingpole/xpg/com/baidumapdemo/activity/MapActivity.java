package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.wxhl.core.utils.NetWorkUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.utils.T;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.bean.ChargeStation;
import hssychargingpole.xpg.com.baidumapdemo.contant.IntentConstants;
import hssychargingpole.xpg.com.baidumapdemo.contant.KEY;
import hssychargingpole.xpg.com.baidumapdemo.manager.LbsManager;
import hssychargingpole.xpg.com.baidumapdemo.popwindow.StationInfoMapPop;



public class MapActivity extends AppCompatActivity implements View.OnClickListener, BaiduMap.OnMapClickListener, OnGetGeoCoderResultListener {


    private static final double BAIDU_LAT_LON_DEFAULT_VALUE = 4.9E-324;
    private MapView mapView;
    private TextView tv_location_message; //显示当前地图中心点的位置
    private RelativeLayout rl_map;
    private ImageButton btLocation;
    private BaiduMap baiduMap;
    private BDLocation mYBDlocation;
    private Double mCurrentLongitude = null;//我的精度
    private Double mCurrentLantitude = null;//我的维度
    private boolean isFirstLoc = true;// 是否首次定位
    private LocationClient mLocClient;
    private MyLocationListenner myListener;
    private List<ChargeStation> chargeStations = new ArrayList<>();
    private static final int CLICKBLE_FALSE_MARKER = 999;

    private View markView;

    private InfoWindow mInfoWindow;
    private LayoutInflater mInflater;

    //存放所有的覆盖物
    private List<Marker> markerList;

    public static final float ZOOM_LEVE_CITY = 18;
    private static final float ZOOM_LEVE_POINT = 10;//地图的缩放范围
    private static final float ZOOM_LEVE_MID = 15;
    private StationInfoMapPop stationInfoMapPop; //底部信息弹窗
    private ImageView iv_map_center_mark;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        iv_map_center_mark = (ImageView) findViewById(R.id.iv_map_center_mark);
        iv_map_center_mark.setEnabled(false);
        iv_map_center_mark.setVisibility(View.VISIBLE);

        tv_location_message = (TextView) findViewById(R.id.tv_location_message);
        btLocation = (ImageButton) findViewById(R.id.btn_Location);  //点击回到我的位置的按钮
        btLocation.setOnClickListener(this);


        rl_map = (RelativeLayout) findViewById(R.id.rl_map);
        Log.i("--oncreate--", "onCreate: ----------------------------------");
        // ------------------------创建百度地图
        final BaiduMapOptions mapOptions = new BaiduMapOptions()
                .zoomControlsEnabled(false).scaleControlEnabled(false)
                .compassEnabled(false).overlookingGesturesEnabled(false);
        mapView = new MapView(this, mapOptions);
        baiduMap = mapView.getMap(); //得到地图操作类

        //定位配置初始化
        initLocation();

        //添加覆盖物初始化
        mapViewPostDelayed();


        mInflater = LayoutInflater.from(this);

        initLister();
        /**
         * 拖动地图的事件
         */
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //地图状态开始变化
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                if (baiduMap.getProjection() == null) {
                    return;
                }
                iv_map_center_mark.setVisibility(View.VISIBLE);
            }

            //地图状态移动结束之后
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (baiduMap.getProjection() == null) {
                    return;
                }

                //mapStatus.target 地图操作的中心点
//                mCurrentLongitude = mapStatus.target.longitude;
//                mCurrentLantitude = mapStatus.target.latitude;
                Log.i("longitude:", "longitude:" + mCurrentLongitude + "");
                Log.i("latitude:", "latitude:" + mCurrentLantitude + "");
                LbsManager.getInstance().getAddressByLocation(mapStatus.target, MapActivity.this);
                Log.d("endXY", mapStatus.targetScreen.x + " " + mapStatus.targetScreen.y);
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {

            }
        });

        /**
         * 点击覆盖物的事件
         */
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (marker.getZIndex() != CLICKBLE_FALSE_MARKER) {

                    if (baiduMap != null && mInfoWindow != null) {
                        baiduMap.hideInfoWindow();
                        mInfoWindow = null;
                    }
                    if (markView != null) {
                        markView = null;
                    }


                    markView = mInflater.inflate(R.layout.map_pile_mark_infowindow, null);
                    TextView tv_title = (TextView) markView.findViewById(R.id.tv_title);
                    tv_title.setText(marker.getTitle());


                    Bundle bundle2 = marker.getExtraInfo();
                    final ChargeStation chargeStation = (ChargeStation) bundle2.getSerializable(IntentConstants.STATION_CONTENT_KEY);

                    InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            //1。移动到地图的中心
                            moveMapTo(chargeStation.getLongitude(), chargeStation.getLatitude(), true);
                            //2.底部popWindow弹出//
                            showMapStationInfo(chargeStation);
                        }
                    };

                    //初始化显示的位置（1.窗口的内容，2位置，3.y轴偏移量）
//                    mInfoWindow = new InfoWindow(markView, marker.getPosition(), -47,listener);
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(markView), marker.getPosition(), -47, listener);
                    //显示当前的infowindow
                    baiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
    }

    private void initLister() {
        baiduMap.setOnMapClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
        rl_map.addView(mapView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (!NetWorkUtil.NETWORK){
            T.showShort(getApplication(),"请检查网络设置");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(true);
            if (mInfoWindow != null) {
                baiduMap.hideInfoWindow();
                mInfoWindow = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);//设置是否允许定位图层
        }
    }

    @Override
    public void onStop() {
        rl_map.removeView(mapView);
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
    }


    /**
     * 定位初始化
     */
    private void initLocation() {
        /****定位初始化*************************************************/
        // -----------------------定位初始化
        mLocClient = new LocationClient(this); //声明LocationClient类
        myListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myListener);  //注册定位监听

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000000000);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mLocClient.setLocOption(option);

        baiduMap.setMyLocationEnabled(true);    // 开启定位图层
        setMyLocationConfigeration(MyLocationConfiguration.LocationMode.FOLLOWING);
        // 开启定位
        mLocClient.start();

    }

    /**
     * 将地址信息转换为坐标点
     *
     * @param
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    /**
     * 将坐标点转换为地址信息
     *
     * @param result
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (result.getAddressDetail() != null) {
                tv_location_message.setText(result.getAddress());
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (baiduMap != null && mInfoWindow != null) {
            baiduMap.hideInfoWindow();
            mInfoWindow = null;
        }
        iv_map_center_mark.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }




    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude()).longitude(location
                            .getLongitude()).build();

            if (locData != null && baiduMap != null) {
                baiduMap.setMyLocationData(locData); //把定位数据显示到地图上
                if (isFirstLoc) {
                  /*moveMapTo(baiduMap.getLocationData().latitude, baiduMap.getLocationData()
                            .longitude, true);*/

                    mCurrentLongitude = location.getLongitude();
                    mCurrentLantitude = location.getLatitude();
                    mYBDlocation = location;
                    Log.i("longitude:", "longitude:" + mCurrentLongitude + "");
                    Log.i("latitude:", "latitude:" + mCurrentLantitude + "");

                    SharedPreferenceUtil.put(MapActivity.this, KEY.CONFIG.MY_LATITUDE,mCurrentLantitude);
                    SharedPreferenceUtil.put(MapActivity.this, KEY.CONFIG.MY_LONGITUDE,mCurrentLongitude);

                    Log.i("定位的城市为:", "address:" + location.getAddrStr() + "");
                    LbsManager.getInstance().getAddressByLocation(baiduMap.getMapStatus().target,
                            MapActivity.this);

                    moveMapTo(mCurrentLantitude, mCurrentLongitude, true, ZOOM_LEVE_POINT);

                    isFirstLoc = false;
                    //定位成功后，不需要再定位，停止定位
                    mLocClient.stop();
                }
            }
        }
    }


    /**
     * @param latitude
     * @param longitude
     * @param isAnimate
     * @param zoomLevel 调整地图的缩放比例
     */
    private void moveMapTo(double latitude, double longitude, boolean isAnimate, float zoomLevel) {
        // needRefreshAddress = true;

        //MapStatus:定义地图的形态
        MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(latitude, longitude)).zoom(zoomLevel).build();
        //描述地图状态将要发生的变化
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        if (null != msu) {
            if (isAnimate) { //如果设置有动画
                // 设置中心点,移动到中心点
                baiduMap.animateMapStatus(msu);
            } else {
                baiduMap.setMapStatus(msu);
            }
        }
    }


    /**
     * 地图移动到指定的位置
     *
     * @param latitude
     * @param longitude
     * @param isAnimate
     */
    private void moveMapTo(double latitude, double longitude, boolean isAnimate) {
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        if (baiduMap == null) {
            return;
        }
        if (isAnimate) {
            baiduMap.animateMapStatus(msu);//移动到原来的位置
        } else {
            baiduMap.setMapStatus(msu);
        }
    }

    /**
     * 设置定位图层的配置
     */
    private void setMyLocationConfigeration(
            MyLocationConfiguration.LocationMode mode) {
        boolean enableDirection = true;    // 设置允许显示方向
        BitmapDescriptor customMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);    // 自定义定位的图标
        MyLocationConfiguration config = new MyLocationConfiguration(mode, enableDirection, customMarker);
        baiduMap.setMyLocationConfigeration(config); //设置定位地图上显示的方式
    }

    /**
     * 地图移动到我的位置,此处可以重新发定位请求，然后定位；
     * 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
     */
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(u);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Location://回到我的位置
                if (baiduMap == null
                        || baiduMap.getLocationData() == null
                        || baiduMap.getLocationData().latitude == BAIDU_LAT_LON_DEFAULT_VALUE
                        || baiduMap.getLocationData().longitude == BAIDU_LAT_LON_DEFAULT_VALUE) {
                    Toast.makeText(this, "无法定位，请打开无线网络或GPS", Toast.LENGTH_SHORT).show();
                    return;
                }
                center2myLoc();
                break;
            default:
                break;
        }
    }


    /**
     * 添加覆盖物
     */
    private void initMarker() {
        markerList = new ArrayList<>();

//        存储到数据库
//        chargeStations = DataSupport.findAll(ChargeStation.class);
//        for(ChargeStation c:chargeStations){
//            L.e(c.toString()+"--------------------");
//        }


            ChargeStation chargeStation1 = new ChargeStation();
            ChargeStation chargeStation2 = new ChargeStation();
            ChargeStation chargeStation3 = new ChargeStation();
            chargeStation1.setName("石景山衙门口308路");
            chargeStation1.setLatitude(116.22439);
            chargeStation1.setLongitude(39.9095375);

            chargeStation2.setName("丰台区83路");
            chargeStation2.setLatitude(116.304083);
            chargeStation2.setLongitude(39.839634);

            chargeStation3.setName("朝阳区653路");
            chargeStation3.setLatitude(116.435118);
            chargeStation3.setLongitude(40.047461);


            if (chargeStations.size() == 0) {
                chargeStations.add(chargeStation1);
                chargeStations.add(chargeStation2);
                chargeStations.add(chargeStation3);
            }

//        DataSupport.saveAll(chargeStations);//存储到数据库



        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_personal);

            for (int i = 0; i < chargeStations.size(); i++) {
//                L.e(chargeStations.get(i).getName()+chargeStations.get(i).getLatLng());
                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(chargeStations.get(i).getLongitude(),chargeStations.get(i).getLatitude()))        // 位置
                        .title(chargeStations.get(i).getName())        // title
                        .icon(icon)            // 图标
                        .anchor(0.5f, 0.5f);//设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐
                // 掉下动画
                options.animateType(MarkerOptions.MarkerAnimateType.drop);
                Marker mMarker = (Marker) (baiduMap.addOverlay(options));
                markerList.add(mMarker);

                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConstants.STATION_CONTENT_KEY, chargeStations.get(i));
                mMarker.setExtraInfo(bundle);

            }
    }


    /**
     * 点击inforWindow 底部显示popWindow
     *
     * @param chargeStation
     */
    private void showMapStationInfo(ChargeStation chargeStation) {
        iv_map_center_mark.setVisibility(View.GONE);
        if (baiduMap != null && chargeStation != null) {
            stationInfoMapPop = new StationInfoMapPop(this, mYBDlocation, chargeStation);

            stationInfoMapPop.init();
            //进入退出的动画
            stationInfoMapPop.setAnimationStyle(R.style.mypopwindow_anim_style);
            //显示pop
            stationInfoMapPop.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);
        }
    }


    private void mapViewPostDelayed() {
        mapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (baiduMap == null || baiduMap.getProjection() == null) {
                    mapView.postDelayed(this, 500);
                    return;
                }
//                // 缩放地图
//                doOnMapStatusChange(baiduMap.getMapStatus());
                initMarker();
            }
        }, 2500);

    }


    private void recycleRes() {
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);
        }
        if (mLocClient != null) {
            // 结束定位
            mLocClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleRes();
        if (mapView != null) {
//            mapView.onDestroy();
            mapView = null;
            baiduMap = null;
        }
    }



}
