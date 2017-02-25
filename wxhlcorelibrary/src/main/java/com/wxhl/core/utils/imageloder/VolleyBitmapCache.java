package com.wxhl.core.utils.imageloder;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.toolbox.ImageLoader;

/**\
 * Volley的图片缓存
 * Created by caibing.zhang on 2016/8/19.
 */
public class VolleyBitmapCache  implements ImageLoader.ImageCache{

    private LruCache<String,Bitmap> cacheMap;
    public VolleyBitmapCache(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory();  //应用最大的内存
        int imageCacheSize = maxMemory/8;   //图片缓存内存大小

        cacheMap = new LruCache<String,Bitmap>(imageCacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

   @Override
    public Bitmap getBitmap(String url) {
        return cacheMap.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if(!TextUtils.isEmpty(url) && bitmap !=null){
            cacheMap.put(url,bitmap);
        }
    }
}
