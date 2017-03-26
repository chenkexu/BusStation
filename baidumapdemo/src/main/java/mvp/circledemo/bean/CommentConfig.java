package mvp.circledemo.bean;

/**
 * Created by yiwei on 16/3/2.
 */
public class CommentConfig {

    public int circlePosition; //评论的说说的位置
    public int commentPosition;
    public Type commentType;
    public User replyUser;

    public static enum Type{
        PUBLIC("public"), REPLY("reply");
        private String value;
        private Type(String value){
            this.value = value;
        }
    }


    @Override
    public String toString() {
        String replyUserStr = "";
        if(replyUser != null){
            replyUserStr = replyUser.toString();
        }
        return "circlePosition = " + circlePosition
                + "; commentPosition = " + commentPosition
                + "; commentType ＝ " + commentType
                + "; replyUser = " + replyUserStr;
    }
}
