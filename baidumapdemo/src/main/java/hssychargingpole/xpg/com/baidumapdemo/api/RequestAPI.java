package hssychargingpole.xpg.com.baidumapdemo.api;

public class RequestAPI {

//    public static final String BASE_URL="http://git.oschina.net/api/v3/";   //生产环境
    public static final String BASE_URL="http://git.oschina.net/api/v3/";  //开发环境
//    public static final String BASE_URL="http://git.oschina.net/api/v3/";  //测试环境1
//    public static final String BASE_URL="http://git.oschina.net/api/v3/";  //测试环境2

    public static final String FEATURED="projects/featured";    //推荐项目
    public static final String POPULAR="projects/popular";      //热门项目
    public static final String LATEST="projects/latest";        //最近更新

    public static final String EVENTS="events/user/";        //动态

    public static final String LANGUAGES_List="projects/languages"; //语言列表

    public static final String MEMBER_LOGIN="session";    //会员登录

    public static final String SHAKE="projects/random?luck=1";    //摇一摇

    public static String getReadmeURL(int projectId) {
        return "projects/"+projectId + "/readme";
    }

    /**
     * URL拼接
     * @param relativeUrl
     * @return
     */
    public static String getAbsoluteUrl(String relativeUrl) {
        return RequestAPI.BASE_URL + relativeUrl;
    }
}
