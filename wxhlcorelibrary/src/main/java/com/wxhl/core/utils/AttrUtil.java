package com.wxhl.core.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by CaiBingZhang on 15/9/2.
 */
public class AttrUtil {

    /**
     * color = AttrUtil.getValueOfColorAttr(getActivity(),R.styleable.Theme,R.styleable.Theme_colorPrimaryDark);
     * 获取属性颜色值
     * @param context
     * @param attrs
     * @param attrValue
     * @return
     */
    public static int getValueOfColorAttr(Context context,int[] attrs,int attrValue){
        TypedArray a = context.obtainStyledAttributes(attrs);
		int color=a.getColor(attrValue, 0);
        return color;
    }
}


