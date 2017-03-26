package hssychargingpole.xpg.com.baidumapdemo.DB;

import org.litepal.crud.DataSupport;

import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.bean.LoginInfo;

/**
 * Created by lenovo on 2017/3/9.
 */

public class DbHelper {

    public static boolean isBindPhone(String userId){
        List<LoginInfo> allUser = DataSupport.findAll(LoginInfo.class);
        if (allUser.size()==0){
            return  false;  //没有绑定过手机号
        }
        for(LoginInfo loginInfo: allUser){
            if (loginInfo.getUserId().equals(userId)){
                return true;//绑定过手机号
            }else{
                return false;
            }
        }
        return false;
    }

    public static boolean findUserByPhone(String phoneStr){

         List<LoginInfo> mloginInfos = DataSupport.where("phone = ?", phoneStr).find(LoginInfo.class);
         if (mloginInfos.size()==0){ //没有该手机号，可以注册
             return true;
         }else {
             return false;
         }
    }


}
