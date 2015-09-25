package com.jiuan.android.app.yilife.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/1/9.
 */
public class JinghuaItem implements Serializable {
     @SerializedName("TopicID") private int topicID;
     @SerializedName("ForumName") private String forumName;

    @SerializedName("ForumID") private int  forumID;
    @SerializedName("Title") private String title;
    @SerializedName("Icon") private String icon;
    @SerializedName("Color") private String color;
    @SerializedName("Posttime") private long time;
    @SerializedName("Reply") private int Reply;

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getReply() {
        return Reply;
    }

    public void setReply(int reply) {
        Reply = reply;
    }

}
