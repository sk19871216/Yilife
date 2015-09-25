package com.jiuan.android.app.yilife.bean.splashimage;

/**
 * Created by Administrator on 2015/4/27.
 */
public class MyReplyBean {
    private int TopicID;
    private int ForumID;
    private String Title;
    private String Icon;
    private String Poster;
    private long posttime;
    private int Reply;
    private long SortTS;
    private String PostMessage;

    public int getTopicID() {
        return TopicID;
    }

    public void setTopicID(int topicID) {
        TopicID = topicID;
    }

    public int getForumID() {
        return ForumID;
    }

    public void setForumID(int forumID) {
        ForumID = forumID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public long getPosttime() {
        return posttime;
    }

    public void setPosttime(long posttime) {
        this.posttime = posttime;
    }

    public int getReply() {
        return Reply;
    }

    public void setReply(int reply) {
        Reply = reply;
    }

    public long getSortTS() {
        return SortTS;
    }

    public void setSortTS(long sortTS) {
        SortTS = sortTS;
    }

    public String getPostMessage() {
        return PostMessage;
    }

    public void setPostMessage(String postMessage) {
        PostMessage = postMessage;
    }
}
