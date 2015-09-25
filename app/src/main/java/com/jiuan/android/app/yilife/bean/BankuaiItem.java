package com.jiuan.android.app.yilife.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/9.
 */
public class BankuaiItem {
    @SerializedName("TopicID") private int topicID;

    @SerializedName("ForumID") private int  forumID;
    @SerializedName("Title") private String title;
    @SerializedName("Icon") private String icon;
    @SerializedName("Poster") private String poster;
    @SerializedName("Posttime") private long posttime;
    @SerializedName("Reply") private int Reply;

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
}
