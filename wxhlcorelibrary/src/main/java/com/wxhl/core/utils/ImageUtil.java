package com.wxhl.core.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @createdate 2013-12-16 下午7:56:03
 * @Description: 图片工具类
 */
public class ImageUtil {

    /**
     * @author caibing.zhang
     * @createdate 2012-8-31 下午4:25:58
     * @Description: 更新相册的更新图片记录，
     * @param context
     * @param imagePath
     */
    public static  void updateContentResolverImage(Context context,String imagePath){
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_data", imagePath);
        localContentValues.put("description", "save image ---");
        localContentValues.put("mime_type", "image/jpeg");
        ContentResolver localContentResolver = context.getContentResolver();
        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        localContentResolver.insert(localUri, localContentValues);
    }

	/**
	 * @author miaoxin.ye
	 * @createdate 2014-1-20 下午3:26:14
	 * @Description: 保存图片到文件
	 * @param bitmap
	 * @param _file
	 * @throws IOException
	 */
	public static void saveBitmapToFile(Bitmap bitmap, String _file, BitmapUtil.BitmapType bitmapType) {
        BufferedOutputStream os = null;
        try {
            File file = new File(_file);
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            if(bitmapType == BitmapUtil.BitmapType.png){
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            }else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
        } catch (Exception e) {
			e.printStackTrace();
		}finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
