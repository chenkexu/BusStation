package hssychargingpole.xpg.com.baidumapdemo.bean;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 发表的文章
 */
public class Article implements Serializable {
	public final static int LIKE_NO = 0x0;
	public final static int LIKE_YES = 0x1;
	public final static int TYPE_STATION = 0x1;
	public final static int TYPE_PILE = 0x2;
	public final static int TYPE_TIME_LINE = 0x3;
	public final static int TYPE_SYSTEM_TOPIC = 10;

	private String articleId = null;
	private String userid = null;
	private String avatar = null;
	private String userName = null;
	private long createAt;
	private String content = null;
	private List<String> images = null;
	private Double longitude = null;
	private Double latitude = null;
	private String location = null;
	private int likeCount;
	private List<Like> likes = null;
	private int commentCount;
	private List<Comment> comments = null;
	private String pileId = null;
	private String pileName = null;
	private Double pileScore = null;
	private int type;
	private Boolean circleJoined;

	private boolean isLike;

	private boolean isPackup;

	private boolean isTop;

	private String circleId;
	private String circleName;
	private Boolean isExpert;

	public Boolean getExpert() {
		return isExpert;
	}

	public void setExpert(Boolean expert) {
		isExpert = expert;
	}

	public Boolean getCircleJoined() {
		return circleJoined;
	}

	public void setCircleJoined(Boolean circleJoined) {
		this.circleJoined = circleJoined;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public boolean isPackup() {
		return isPackup;
	}

	public void setIsPackup(boolean isPackup) {
		this.isPackup = isPackup;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUserName() {
		return userName == null ? "匿名":userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Double getPileScore() {
		return pileScore;
	}

	public void setPileScore(Double pileScore) {
		this.pileScore = pileScore;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPileName() {
		return pileName;
	}

	public void setPileName(String pileName) {
		this.pileName = pileName;
	}

	public String getPileId() {
		return pileId;
	}

	public void setPileId(String pileId) {
		this.pileId = pileId;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean islike) {
		this.isLike = islike;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}

	public void updateLikeStateByUserId(String userId) {
		setLike(false);
		if (likes != null) {
			for (Like like : likes) {
				if (like.getUserid().equals(userId)) {
					setLike(true);
					break;
				}
			}
		}
	}

	public void filterLikes() {
		List<Like> tempLikes = new ArrayList<>();
		HashSet<Like> set = new HashSet<>();
		for (Like like : likes) {
			if (!set.contains(like)) {
				set.add(like);
				tempLikes.add(like);
			}
		}
		this.likes = tempLikes;
		setLikeCount(likes.size());
	}
}
