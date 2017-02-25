package hssychargingpole.xpg.com.baidumapdemo.bean.events;

import java.util.List;


public class EventVo {

    /**
     * id : 8369105
     * target_type : null
     * target_id : null
     * title : null
     * data : {"before":"3fa8ab8796195dd6df8a1d27a50f054a124fc7ba","after":"42b82baba71bfecd87aec646711df44854d813ef","ref":"refs/heads/master","user_id":107458,"user_name":"keyle_xiao","repository":{"name":"UnityExcel2JsonGenCSharper","url":"git@git.oschina.net:keyle/UnityExcel2JsonGenCSharper.git","description":"给游戏策划与程序最好的礼物~ excel转json文件并且生成cs代码文件，加速开发进程","homepage":"http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper"},"commits":[{"id":"42b82baba71bfecd87aec646711df44854d813ef","message":"Update Tools Des","timestamp":"2015-08-27T18:08:48+08:00","url":"http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper/commit/42b82baba71bfecd87aec646711df44854d813ef","author":{"name":"keyle","email":"keyle_xiao@hotmail.com"}}],"total_commits_count":1}
     * project_id : 506776
     * created_at : 2015-08-27T18:09:47+08:00
     * updated_at : 2015-08-27T18:09:47+08:00
     * action : 5
     * author_id : 107458
     * public : true
     * project : {"id":506776,"name":"UnityExcel2JsonGenCSharper","description":"给游戏策划与程序最好的礼物~ excel转json文件并且生成cs代码文件，加速开发进程","owner":{"id":107458,"username":"keyle","email":"keyle_xiao@hotmail.com","name":"keyle_xiao","state":"active","created_at":"2014-08-03T13:11:03+08:00","portrait":"uploads/58/107458_keyle.png?1440667845","new_portrait":"http://git.oschina.net/uploads/58/107458_keyle.png?1440667845"}}
     * author : {"id":107458,"username":"keyle","email":"keyle_xiao@hotmail.com","name":"keyle_xiao","state":"active","created_at":"2014-08-03T13:11:03+08:00","portrait":"uploads/58/107458_keyle.png?1440667845","new_portrait":"http://git.oschina.net/uploads/58/107458_keyle.png?1440667845"}
     * events : {"note":null,"issue":null,"pull_request":null}
     */

    private int id;
    private String target_type;
    private String target_id;
    private String title;
    private DataEntity data;
    private int project_id;
    private String created_at;
    private String updated_at;
    private int action;
    private int author_id;
    private boolean publicX;
    private ProjectEntity project;
    private AuthorEntity author;
    private EventsEntity events;

    public void setId(int id) {
        this.id = id;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setPublicX(boolean publicX) {
        this.publicX = publicX;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setEvents(EventsEntity events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public Object getTarget_type() {
        return target_type;
    }

    public Object getTarget_id() {
        return target_id;
    }

    public Object getTitle() {
        return title;
    }

    public DataEntity getData() {
        return data;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getAction() {
        return action;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public boolean getPublicX() {
        return publicX;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public EventsEntity getEvents() {
        return events;
    }

    public static class DataEntity {
        /**
         * before : 3fa8ab8796195dd6df8a1d27a50f054a124fc7ba
         * after : 42b82baba71bfecd87aec646711df44854d813ef
         * ref : refs/heads/master
         * user_id : 107458
         * user_name : keyle_xiao
         * repository : {"name":"UnityExcel2JsonGenCSharper","url":"git@git.oschina.net:keyle/UnityExcel2JsonGenCSharper.git","description":"给游戏策划与程序最好的礼物~ excel转json文件并且生成cs代码文件，加速开发进程","homepage":"http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper"}
         * commits : [{"id":"42b82baba71bfecd87aec646711df44854d813ef","message":"Update Tools Des","timestamp":"2015-08-27T18:08:48+08:00","url":"http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper/commit/42b82baba71bfecd87aec646711df44854d813ef","author":{"name":"keyle","email":"keyle_xiao@hotmail.com"}}]
         * total_commits_count : 1
         */

        private String before;
        private String after;
        private String ref;
        private int user_id;
        private String user_name;
        private RepositoryEntity repository;
        private int total_commits_count;
        private List<CommitsEntity> commits;

        public void setBefore(String before) {
            this.before = before;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setRepository(RepositoryEntity repository) {
            this.repository = repository;
        }

        public void setTotal_commits_count(int total_commits_count) {
            this.total_commits_count = total_commits_count;
        }

        public void setCommits(List<CommitsEntity> commits) {
            this.commits = commits;
        }

        public String getBefore() {
            return before;
        }

        public String getAfter() {
            return after;
        }

        public String getRef() {
            return ref;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public RepositoryEntity getRepository() {
            return repository;
        }

        public int getTotal_commits_count() {
            return total_commits_count;
        }

        public List<CommitsEntity> getCommits() {
            return commits;
        }

        public static class RepositoryEntity {
            /**
             * name : UnityExcel2JsonGenCSharper
             * url : git@git.oschina.net:keyle/UnityExcel2JsonGenCSharper.git
             * description : 给游戏策划与程序最好的礼物~ excel转json文件并且生成cs代码文件，加速开发进程
             * homepage : http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper
             */

            private String name;
            private String url;
            private String description;
            private String homepage;

            public void setName(String name) {
                this.name = name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setHomepage(String homepage) {
                this.homepage = homepage;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }

            public String getDescription() {
                return description;
            }

            public String getHomepage() {
                return homepage;
            }
        }

        public static class CommitsEntity {
            /**
             * id : 42b82baba71bfecd87aec646711df44854d813ef
             * message : Update Tools Des
             * timestamp : 2015-08-27T18:08:48+08:00
             * url : http://git.oschina.net/keyle/UnityExcel2JsonGenCSharper/commit/42b82baba71bfecd87aec646711df44854d813ef
             * author : {"name":"keyle","email":"keyle_xiao@hotmail.com"}
             */

            private String id;
            private String message;
            private String timestamp;
            private String url;
            private AuthorEntity author;

            public void setId(String id) {
                this.id = id;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setAuthor(AuthorEntity author) {
                this.author = author;
            }

            public String getId() {
                return id;
            }

            public String getMessage() {
                return message;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public String getUrl() {
                return url;
            }

            public AuthorEntity getAuthor() {
                return author;
            }

            public static class AuthorEntity {
                /**
                 * name : keyle
                 * email : keyle_xiao@hotmail.com
                 */

                private String name;
                private String email;

                public void setName(String name) {
                    this.name = name;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getName() {
                    return name;
                }

                public String getEmail() {
                    return email;
                }
            }
        }
    }

    public static class ProjectEntity {
        /**
         * id : 506776
         * name : UnityExcel2JsonGenCSharper
         * description : 给游戏策划与程序最好的礼物~ excel转json文件并且生成cs代码文件，加速开发进程
         * owner : {"id":107458,"username":"keyle","email":"keyle_xiao@hotmail.com","name":"keyle_xiao","state":"active","created_at":"2014-08-03T13:11:03+08:00","portrait":"uploads/58/107458_keyle.png?1440667845","new_portrait":"http://git.oschina.net/uploads/58/107458_keyle.png?1440667845"}
         */

        private int id;
        private String name;
        private String description;
        private OwnerEntity owner;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setOwner(OwnerEntity owner) {
            this.owner = owner;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public OwnerEntity getOwner() {
            return owner;
        }

        public static class OwnerEntity {
            /**
             * id : 107458
             * username : keyle
             * email : keyle_xiao@hotmail.com
             * name : keyle_xiao
             * state : active
             * created_at : 2014-08-03T13:11:03+08:00
             * portrait : uploads/58/107458_keyle.png?1440667845
             * new_portrait : http://git.oschina.net/uploads/58/107458_keyle.png?1440667845
             */

            private int id;
            private String username;
            private String email;
            private String name;
            private String state;
            private String created_at;
            private String portrait;
            private String new_portrait;

            public void setId(int id) {
                this.id = id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setState(String state) {
                this.state = state;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public void setNew_portrait(String new_portrait) {
                this.new_portrait = new_portrait;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }

            public String getEmail() {
                return email;
            }

            public String getName() {
                return name;
            }

            public String getState() {
                return state;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getPortrait() {
                return portrait;
            }

            public String getNew_portrait() {
                return new_portrait;
            }
        }
    }

    public static class AuthorEntity {
        /**
         * id : 107458
         * username : keyle
         * email : keyle_xiao@hotmail.com
         * name : keyle_xiao
         * state : active
         * created_at : 2014-08-03T13:11:03+08:00
         * portrait : uploads/58/107458_keyle.png?1440667845
         * new_portrait : http://git.oschina.net/uploads/58/107458_keyle.png?1440667845
         */

        private int id;
        private String username;
        private String email;
        private String name;
        private String state;
        private String created_at;
        private String portrait;
        private String new_portrait;

        public void setId(int id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public void setNew_portrait(String new_portrait) {
            this.new_portrait = new_portrait;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getPortrait() {
            return portrait;
        }

        public String getNew_portrait() {
            return new_portrait;
        }
    }

    public static class EventsEntity {
        /**
         * note : null
         * issue : null
         * pull_request : null
         */

        private Object note;
        private Issue issue;
        private Pull_Request pull_request;

        public void setNote(Object note) {
            this.note = note;
        }

        public void setIssue(Issue issue) {
            this.issue = issue;
        }

        public void setPull_request(Pull_Request pull_request) {
            this.pull_request = pull_request;
        }

        public Object getNote() {
            return note;
        }

        public Issue getIssue() {
            return issue;
        }

        public Pull_Request getPull_request() {
            return pull_request;
        }
    }
}
