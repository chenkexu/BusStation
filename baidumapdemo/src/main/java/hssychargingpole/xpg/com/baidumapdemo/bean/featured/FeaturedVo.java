package hssychargingpole.xpg.com.baidumapdemo.bean.featured;

import java.io.Serializable;

/**
 * Created by caibing.zhang on 2016/10/9.
 */

public class FeaturedVo implements Serializable{

    /**
     * id;//1227786
     * name;//UIBezierPath_CAShapeLayer
     * description;//UIBezierPath和CAShapeLayer绘图

     * default_branch;//master
     * owner;//{"id":727111,"username":"Lee_Jay","email":"leejay_email@163.com","name":"LeeJay","state":"active","created_at":"2016-03-29T14:42:33+08:00","portrait":"uploads/11/727111_Lee_Jay.png?1460379272","new_portrait":"http://git.oschina.net/uploads/11/727111_Lee_Jay.png?1460379272"}
     * public;//true
     * path;//UIBezierPath_CAShapeLayer
     * path_with_namespace;//Lee_Jay/UIBezierPath_CAShapeLayer
     * issues_enabled;//true
     * pull_requests_enabled;//true
     * wiki_enabled;//true
     * created_at;//2016-09-27T14:04:59+08:00
     * namespace;//{"address":"192.168.2.106","avatar":null,"created_at":"2016-03-29T14:42:34+08:00","description":"","email":null,"id":716922,"location":null,"name":"LeeJay","owner_id":727111,"path":"Lee_Jay","public":null,"updated_at":"2016-03-29T14:42:34+08:00","url":null}
     * last_push_at;//2016-10-09T16:07:24+08:00
     * parent_id;//null
     * fork?;//false
     * forks_count;//0
     * stars_count;//0
     * watches_count;//1
     * language;//Objective-C
     * paas;//null
     * stared;//null
     * watched;//null
     * relation;//null
     * recomm;//1
     * parent_path_with_namespace;//null
     */

    private int id;
    private String name;
    private String description;
    private String default_branch;
    /**
     * id;//727111
     * username;//Lee_Jay
     * email;//leejay_email@163.com
     * name;//LeeJay
     * state;//active
     * created_at;//2016-03-29T14:42:33+08:00
     * portrait;//uploads/11/727111_Lee_Jay.png?1460379272
     * new_portrait;//http://git.oschina.net/uploads/11/727111_Lee_Jay.png?1460379272
     */

    private OwnerBean owner;
    private String path;
    private String path_with_namespace;
    private boolean issues_enabled;
    private boolean pull_requests_enabled;
    private boolean wiki_enabled;
    private String created_at;
    /**
     * address;//192.168.2.106
     * avatar;//null
     * created_at;//2016-03-29T14:42:34+08:00
     * description :
     * email;//null
     * id;//716922
     * location;//null
     * name;//LeeJay
     * owner_id;//727111
     * path;//Lee_Jay
     * public;//null
     * updated_at;//2016-03-29T14:42:34+08:00
     * url;//null
     */

    private NamespaceBean namespace;
    private String last_push_at;
    private String parent_id;

    private boolean fork;//false
    private int forks_count;//0
    private int stars_count;//0
    private int watches_count;//1
    private String language;//Objective-C
    private String paas;//null

    private boolean stared;//null
    private boolean watched;//null
    private String relation;//null
    private int recomm;//1
    private String parent_path_with_namespace;//null

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public OwnerBean getOwner() {
        return owner;
    }

    public void setOwner(OwnerBean owner) {
        this.owner = owner;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_with_namespace() {
        return path_with_namespace;
    }

    public void setPath_with_namespace(String path_with_namespace) {
        this.path_with_namespace = path_with_namespace;
    }

    public boolean issues_enabled() {
        return issues_enabled;
    }

    public void setIssues_enabled(boolean issues_enabled) {
        this.issues_enabled = issues_enabled;
    }

    public boolean isPull_requests_enabled() {
        return pull_requests_enabled;
    }

    public void setPull_requests_enabled(boolean pull_requests_enabled) {
        this.pull_requests_enabled = pull_requests_enabled;
    }

    public boolean isWiki_enabled() {
        return wiki_enabled;
    }

    public void setWiki_enabled(boolean wiki_enabled) {
        this.wiki_enabled = wiki_enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public NamespaceBean getNamespace() {
        return namespace;
    }

    public void setNamespace(NamespaceBean namespace) {
        this.namespace = namespace;
    }

    public String getLast_push_at() {
        return last_push_at;
    }

    public void setLast_push_at(String last_push_at) {
        this.last_push_at = last_push_at;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStars_count() {
        return stars_count;
    }

    public void setStars_count(int stars_count) {
        this.stars_count = stars_count;
    }

    public int getWatches_count() {
        return watches_count;
    }

    public void setWatches_count(int watches_count) {
        this.watches_count = watches_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPaas() {
        return paas;
    }

    public void setPaas(String paas) {
        this.paas = paas;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getRecomm() {
        return recomm;
    }

    public void setRecomm(int recomm) {
        this.recomm = recomm;
    }

    public String getParent_path_with_namespace() {
        return parent_path_with_namespace;
    }

    public void setParent_path_with_namespace(String parent_path_with_namespace) {
        this.parent_path_with_namespace = parent_path_with_namespace;
    }
}
