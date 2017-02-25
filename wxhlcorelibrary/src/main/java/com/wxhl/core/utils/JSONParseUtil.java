package com.wxhl.core.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON解析工具类
 * Created by CaiBingZhang on 15/8/30.
 */
public class JSONParseUtil {

    /**
     * 数组解析器
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        List<T> list = new ArrayList<T>(
                JSONArray.parseArray(json,clazz));
        return list;
    }


    /**
     * 对象解析器
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return JSONObject.parseObject(json,clazz);
    }
}
