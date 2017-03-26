package hssychargingpole.xpg.com.baidumapdemo.bean;

import java.io.Serializable;

/**
 * Article 优易圈Like 点赞用户 By Mazoh
 */
public class Like implements Serializable {
	private String userid = null;
	private String avatar = null;
	private String userName = null;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
		return userName == null?"匿名":userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Like) {
			Like like = (Like) o;
			return like.getUserid().equals(userid);
		} else {
			return false;
		}
	}
}
