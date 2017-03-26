package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.util.BitmapUtil;
import com.easy.util.ToastUtil;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.wxhl.core.utils.T;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.contant.MyConstant;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.dialog.DatePickDialog2;

import static hssychargingpole.xpg.com.baidumapdemo.R.id.iv_add_icon;
import static hssychargingpole.xpg.com.baidumapdemo.R.id.iv_picture;

public class Main2Activity extends AbstractActivity {
    private EditText etAdTheme; //主题信息
    private ImageView ivAddIcon;//上传图片的 "+"按钮
    private ImageView ivPicture;//显示选择的图片
    private TextView tvInstructionLength;
    private TextView etStartTime;//选择的开始时间
    private TextView etEndTime;//选择的结束时间
    private EditText etContactPhone;//电话
    private Button btnApply;//提交按钮
    private SimpleDateFormat sdf;
    private Calendar currentCalendar;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int REQUEST_CAMERA_CODE = 10;// 选择照片
    private static final int REQUEST_PREVIEW_CODE = 20;//预览
    private static final int PHOTO_REQUEST_CUT = 1002;// 剪切的结果

    private ArrayList<String> imagePaths = new ArrayList<>(); //选择的照片的地址
    private ImageView image;
    private static final String SDKPICPATH = Environment.getExternalStorageDirectory() + "/" + MyConstant.PATH + "/";
    private String cropPath;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_ad);
        setTitle("提交表单");
    }


    @Override
    protected void init() {
        etAdTheme = (EditText) findViewById(R.id.et_ad_theme);
        ivAddIcon = (ImageView) findViewById(iv_add_icon);
        ivPicture = (ImageView) findViewById(iv_picture);
        image = (ImageView) findViewById(R.id.image);
        tvInstructionLength = (TextView) findViewById(R.id.tv_instruction_length);
        etStartTime = (TextView) findViewById(R.id.et_start_time);
        etEndTime = (TextView) findViewById(R.id.et_end_time);
        etContactPhone = (EditText) findViewById(R.id.et_contact_phone);
        btnApply = (Button) findViewById(R.id.btn_apply);

        btnApply.setOnClickListener(this);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        ivAddIcon.setOnClickListener(this);
        ivPicture.setOnClickListener(this);


        sdf = new SimpleDateFormat(DATE_FORMAT);
        currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);
        String date = sdf.format(new Date());
        etStartTime.setText(date);//开始时间
        etEndTime.setText(date);//结束时间都设为今天
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.et_start_time://选择开始时间
                long startTime = getTime(etStartTime.getText().toString());
                if(startTime > 0){
                    showDatePicker(startTime, TYPE_START);
                }else{
                    showDatePicker(currentCalendar.getTimeInMillis(), TYPE_START);
                }
                break;
            case R.id.et_end_time: //选择结束的时间
                long endTime =  getTime(etEndTime.getText().toString());
                if(endTime > 0){
                    showDatePicker(endTime, TYPE_END);
                }else{
                    showDatePicker(currentCalendar.getTimeInMillis(), TYPE_END);
                }
                break;
            case iv_add_icon://这个跟下面的行为是一样的,所以放在一起
            case iv_picture: //选择图片
                selectImages();
                break;
            case R.id.btn_apply: //点击提交
                break;
        }
    }


    /**
     * 去图库选择照片
     */
    private void selectImages(){
        PhotoPickerIntent intent = new PhotoPickerIntent(Main2Activity.this);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(true); // 是否显示拍照
        intent.setMaxTotal(1); // 最多选择照片数量，默认为6
        intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }


    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    //设置比例
    @SuppressLint("SdCardPath")
    private void startPhotoZoom(Uri uri1, int size) {
        cropPath = SDKPICPATH + getNowTime() + ".jpg";
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
//        intent.putExtra("outputX", size);
//        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cropPath)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    /**
     * 返回到原来的界面
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:// 选择照片
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size() + list.get(0));

//                    ivPicture.setVisibility(View.VISIBLE);
//
//                    Glide.with(Main2Activity.this)
//                            .load(list.get(0))
//                            .placeholder(R.mipmap.default_error)
//                            .error(R.mipmap.default_error)
//                            .centerCrop()
//                            .crossFade()
//                            .into(ivPicture);
                    Uri uri2 = Uri.fromFile(new File(list.get(0)));
                    startPhotoZoom(uri2, 1080);
                    break;

                case PHOTO_REQUEST_CUT: { //裁剪返回的最终的结果
                    Bitmap bm = BitmapUtil.limitSize(cropPath, 1080);//根据路径获得图片并压缩返回bitmap用于显示
                    if (bm != null) {
                        ivAddIcon.setVisibility(View.GONE);
                        ivPicture.setVisibility(View.VISIBLE);
                        path = SDKPICPATH + getNowTime() + ".jpeg";

                        path = BitmapUtil.save(path, bm, Bitmap.CompressFormat.JPEG, 100);

                        Log.i("PHOTO_REQUEST_CUT", "图片大小： " + bm.getByteCount() + "");
                        Log.i("PHOTO_REQUEST_CUT", "图片长宽： " + bm.getWidth() + "---" + bm.getHeight());
                        bm.recycle();

                        Glide.with(Main2Activity.this)
                            .load(path)
                            .placeholder(R.mipmap.default_error)
                            .error(R.mipmap.default_error)
                            .centerCrop()
                            .crossFade()
                            .into(ivPicture);  //显示最终图片id
                    } else {
                        cropPath = null;
                        ToastUtil.show(this, "图片数据错误,请重新选择!");
                    }
                    break;
                }
            }
        }
    }


    //提交
    private void commit() {

    }

    private static final int TYPE_START = 0;
    private static final int TYPE_END = 1;

    private void showDatePicker(long time, final int type){
        DatePickDialog2 dialog = new DatePickDialog2(this);
        dialog.setTime(time);
        dialog.show();
        dialog.setOnOkListener(new DatePickDialog2.OnOkListener() {
            @Override
            public void onOk(DatePickDialog2 tpd, int year, int month, int day) {
                switch (type){
                    case TYPE_START://开始时间
                        String time = year+"-"+month+"-"+day; //控件上显示的字符串
                        long selectedTime = getTime(time);  //把控件上显示的字符串转换成时间
                        if(selectedTime < currentCalendar.getTimeInMillis()){
                            T.showShort(getApplication(),"选择日期不能小于当前日期");
                            return;
                        }
                        etStartTime.setText(time); //显示到界面上选择的时间
                        if(selectedTime > getTime(etEndTime.getText().toString())){
                            etEndTime.setText(time);
                        }
                        break;
                    case TYPE_END:
                        time = year+"-"+month+"-"+day;
                        selectedTime = getTime(time);
                        if(selectedTime < currentCalendar.getTimeInMillis()){
                            T.showShort(getApplication(),"选择日期不能小于当前日期");
                            return;
                        }
                        etEndTime.setText(time);
                        if(selectedTime < getTime(etStartTime.getText().toString())){
                            etStartTime.setText(time);
                        }
                        break;
                }
            }
        });
    }

    /**
     * 把字符串转换成时间
     * @param time
     * @return
     */
    private long getTime(String time){
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }
}
