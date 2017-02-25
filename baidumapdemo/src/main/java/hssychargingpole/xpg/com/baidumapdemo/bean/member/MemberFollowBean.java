package hssychargingpole.xpg.com.baidumapdemo.bean.member;

import java.io.Serializable;

/**
 * Created by CaiBingZhang on 15/8/30.
 */
public class MemberFollowBean implements Serializable{
    private static final long serialVersionUID = -9220080218669119637L;
    private int followers;
    private int starred;
    private int following;
    private int watched;

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getFollowers() {
        return followers;
    }

    public int getStarred() {
        return starred;
    }

    public int getFollowing() {
        return following;
    }

    public int getWatched() {
        return watched;
    }
}
