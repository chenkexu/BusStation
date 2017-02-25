package hssychargingpole.xpg.com.baidumapdemo.bean.featured;

import java.io.Serializable;

/**
 * Created by caibing.zhang on 2016/10/9.
 */

public class OwnerBean implements Serializable{
    private int id;
    private String username;
    private String email;
    private String name;
    private String state;
    private String created_at;
    private String portrait;
    private String new_portrait;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this.new_portrait = new_portrait;
    }
}
