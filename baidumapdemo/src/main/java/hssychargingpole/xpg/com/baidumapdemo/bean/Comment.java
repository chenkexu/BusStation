package hssychargingpole.xpg.com.baidumapdemo.bean;

import java.io.Serializable;

/**
 * Article 评论实体类  By Mazoh
 */
public class Comment implements Serializable{
	private String id;
	private long time;
	private String userid;
	private String avatar;
	private String userName;
	private String content;
	private String replyUserid;
	private String replyUserName;
	private String replyArtId;
	private long createAt ;

	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplyArtId() {
		return replyArtId;
	}

	public void setReplyArtId(String replyArtId) {
		this.replyArtId = replyArtId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyUserid() {
		return replyUserid;
	}

	public void setReplyUserid(String replyUserid) {
		this.replyUserid = replyUserid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


}
