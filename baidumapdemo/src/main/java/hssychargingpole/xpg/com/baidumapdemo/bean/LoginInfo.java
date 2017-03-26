package hssychargingpole.xpg.com.baidumapdemo.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Gunter on 2015/10/22.
 */
public class LoginInfo extends DataSupport implements Serializable {

    private static final long serialVersionUID = 6699411882480904770L;
    private String token;
    private String userId; //用户ID
    private String nickName; //昵称
    private String userAvaterUrl; //用户头像
    private int gender; //性别
    private int userType;//用户类型
    private String phone;
    private int id;
    private String pwd;


    public String getPwd() {
        return pwd;

    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LoginInfo(String token, String userId, int gender, String nickName, int userType) {
        this.token = token;
        this.userId = userId;
        this.gender = gender;
        this.nickName = nickName;
        this.userType = userType;
    }



    public LoginInfo() {
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvaterUrl() {
        return userAvaterUrl;
    }

    public void setUserAvaterUrl(String userAvaterUrl) {
        this.userAvaterUrl = userAvaterUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userAvaterUrl='" + userAvaterUrl + '\'' +
                ", gender=" + gender +
                ", userType=" + userType +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
