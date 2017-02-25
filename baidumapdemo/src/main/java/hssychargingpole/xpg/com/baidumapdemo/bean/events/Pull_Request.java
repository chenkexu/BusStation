package hssychargingpole.xpg.com.baidumapdemo.bean.events;

import java.io.Serializable;


public class Pull_Request implements Serializable {
    private int iid;
    private String title;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
