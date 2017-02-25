package com.wxhl.core.utils.imageloder;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.wxhl.core.R;

/**
 * Created by caibing.zhang on 2016/8/19.
 */
public class CustomImageLoader {

    /**
     * 加载图片，
     * @param imageLoader Volley中的ImageLoader
     * @param imageView
     * @param url 图片地址
     */
    public static void loadImage(ImageLoader imageLoader, final ImageView imageView, final String url){
        imageView.setTag(url);
        imageView.setImageResource(R.mipmap.img_core_loading);
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //获取返回的图片Bitmap
                Bitmap bitmap = response.getBitmap();
                if(bitmap != null){
                    if(imageView.getTag().toString().equals(url)){
                        imageView.setImageBitmap(bitmap);
                    }else{
                        imageView.setImageResource(R.mipmap.pictures_no);
                    }
                }else{
//                    imageView.setImageResource(R.mipmap.pictures_no);
                    imageView.setImageResource(R.mipmap.img_core_loading);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.mipmap.img_def_error);
            }
        });
    }

    /**
     * 加载头像图片，
     * @param imageLoader Volley中的ImageLoader
     * @param imageView
     * @param url 图片地址
     */
    public static void loadheaderImage(ImageLoader imageLoader, final ImageView imageView, final String url){
        imageView.setTag(url);
        imageView.setImageResource(R.mipmap.img_header);
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //获取返回的图片Bitmap
                Bitmap bitmap = response.getBitmap();
                if(bitmap != null){
                    if(imageView.getTag().toString().equals(url)){
                        imageView.setImageBitmap(bitmap);
                    }else{
                        imageView.setImageResource(R.mipmap.img_header);
                    }
                }else{
                    imageView.setImageResource(R.mipmap.img_header);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.mipmap.img_header);
            }
        });
    }
}
