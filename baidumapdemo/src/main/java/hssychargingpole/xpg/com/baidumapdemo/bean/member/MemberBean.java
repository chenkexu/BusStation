package hssychargingpole.xpg.com.baidumapdemo.bean.member;

import java.io.Serializable;

/**
 * Created by CaiBingZhang on 15/8/30.
 */
public class MemberBean implements Serializable{
    private static final long serialVersionUID = 6628181445326614625L;

    /**
     * id : 364840
     * username : zhangkai281
     * name : zhangkai281
     * bio : null
     * weibo : null
     * blog : null
     * theme_id : 1
     * state : active
     * created_at : 2015-04-17T15:50:06+08:00
     * portrait : null
     * email : zhangkai281@sina.com
     * new_portrait : http://secure.gravatar.com/avatar/8f31c5b43f0e35e61aacabca78530623?s=40&d=mm
     * follow : {"followers":0,"starred":2,"following":0,"watched":0}
     * private_token : UPj9Mg43AfBxm2g92GBP
     * is_admin : false
     * can_create_group : true
     * can_create_project : true
     * can_create_team : true
     */

    private int id;
    private String username;
    private String name;
    private String bio;
    private String weibo;
    private String blog;
    private int theme_id;
    private String state;
    private String created_at;
    private String portrait;
    private String email;
    private String new_portrait;
    private MemberFollowBean follow;
    private String private_token;
    private boolean is_admin;
    private boolean can_create_group;
    private boolean can_create_project;
    private boolean can_create_team;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this.new_portrait = new_portrait;
    }

    public MemberFollowBean getFollow() {
        return follow;
    }

    public void setFollow(MemberFollowBean follow) {
        this.follow = follow;
    }

    public String getPrivate_token() {
        return private_token;
    }

    public void setPrivate_token(String private_token) {
        this.private_token = private_token;
    }

    public boolean is_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isCan_create_group() {
        return can_create_group;
    }

    public void setCan_create_group(boolean can_create_group) {
        this.can_create_group = can_create_group;
    }

    public boolean isCan_create_project() {
        return can_create_project;
    }

    public void setCan_create_project(boolean can_create_project) {
        this.can_create_project = can_create_project;
    }

    public boolean isCan_create_team() {
        return can_create_team;
    }

    public void setCan_create_team(boolean can_create_team) {
        this.can_create_team = can_create_team;
    }
}
