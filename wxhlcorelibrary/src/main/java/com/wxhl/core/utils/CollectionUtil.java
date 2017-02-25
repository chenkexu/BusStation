package com.wxhl.core.utils;

import java.util.Collection;

/**
 * 集合工具类
 * Created by CaiBingZhang on 15/8/28.
 */
public class CollectionUtil {

    /**
     * @Description: 判断List集合是否为空
     * @param list
     * @return
     */
    public static boolean listIsNull(Collection<? extends Object> list){
        if(null ==list || list.size()==0){
            return true;
        }
        return false;
    }

    /**
     * @Description: 判断List集合是否为空
     * @param list
     * @return
     */
    public static boolean listIsNotNull(Collection<? extends Object> list){
        if(null !=list && list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 清除集合
     * @param list
     */
    public static void clearList(Collection<? extends Object> list){
        if(list!=null){
            list.clear();
            list=null;
        }
    }
}
