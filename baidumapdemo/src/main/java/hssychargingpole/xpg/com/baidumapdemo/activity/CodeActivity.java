package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.easy.util.ToastUtil;
import com.google.zxing.Result;
import com.wxhl.core.utils.ImageUtil;
import com.wxhl.core.utils.NetWorkUtil;
import com.wxhl.core.utils.T;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.dialog.LoadingDialog;
import hssychargingpole.xpg.com.baidumapdemo.zxing.CodeUtils;
import hssychargingpole.xpg.com.baidumapdemo.zxing.ZxingManager;
import hssychargingpole.xpg.com.baidumapdemo.zxing.camera.CameraManager;
import hssychargingpole.xpg.com.baidumapdemo.zxing.view.ViewfinderView;


/**
 * 二维码界面
 */
public class CodeActivity extends AppCompatActivity {
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    private ToggleButton switch_onoff; //打开照明开关
    private ZxingManager mZxingManager; //二维码扫描管理类
    private SurfaceView preview_view;
    private LoadingDialog loadingDialog;
    private SurfaceHolder surfaceHolder;
    private Button openImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
            setTitle("二维码扫描测试");
            preview_view = (SurfaceView) findViewById(R.id.preview_view);
            switch_onoff = (ToggleButton) findViewById(R.id.switch_onoff);
            openImages = (Button) findViewById(R.id.open_images);


            ViewfinderView viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);
            mZxingManager = new ZxingManager(this, viewfinderView, preview_view);
            viewfinderView.setType(ViewfinderView.TYPE_2D_CODE);
            mZxingManager.onCreate();
            surfaceHolder = preview_view.getHolder();
            viewfinderView.setTips(null);

            //扫描二维码后的回调
            mZxingManager.setOnResultListener(new ZxingManager.OnResultListener() {
                @Override
                public void OnResult(Result result, Bitmap barcode) {
                    mZxingManager.onPause();
                    String code = result.getText().trim();
                    if (code == null || code.equals("")) {
                        ToastUtil.show(CodeActivity.this, "二维码格式错误");
                        mZxingManager.onResume();
                        return;
                    }
                    if (!NetWorkUtil.NETWORK) {
                        T.showShort(getApplication(), "没有网络连接");
                        return;
                    }
                    T.showLong(getApplication(), "二维码为：" + code);
                    // TODO: 2017/3/10 拿到Code去请求后台接口
                }
            });

            //打开照明灯
            switch_onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    controlLight(switch_onoff);
                }
            });


            //打开图库
            openImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
            });

    }


    /**
     * 打开闪光灯
     *
     * @param view
     */
    private void controlLight(ToggleButton view) {
        try {
            Camera camera = CameraManager.get().openDriver(surfaceHolder);
            Camera.Parameters parameters = camera.getParameters();
            if (view.isChecked()) {
                CameraManager.get().turnOn(parameters);
            } else {
                CameraManager.get().turnOff(parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择系统图片并解析
         */
         if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(CodeActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(CodeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mZxingManager.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mZxingManager.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZxingManager.onDestroy();
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
