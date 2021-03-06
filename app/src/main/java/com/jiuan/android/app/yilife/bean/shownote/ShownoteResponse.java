package com.jiuan.android.app.yilife.bean.shownote;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.ShownoteBean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ShownoteResponse {
    @SerializedName("TopicID") private int topicID;
    @SerializedName("ShareLink") private String shareLink;
    @SerializedName("ForumID") private int  forumID;
    @SerializedName("ForumName") private String  forumName;
    @SerializedName("Title") private String title;
    @SerializedName("OriginalContent") private String originalContent;
    @SerializedName("Attributes") private int[] attributes;
    @SerializedName("PosterID") private int posterID;
    @SerializedName("Poster") private String poster;
    @SerializedName("PostTime") private long time;
    @SerializedName("Reply") private int reply;
    @SerializedName("Content") private String neirong;
    @SerializedName("Posts") private ShownoteBean[] bean;
    @SerializedName("HasAttach") private boolean hasAttach;

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

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }

    public int getPosterID() {
        return posterID;
    }

    public void setPosterID(int posterID) {
        this.posterID = posterID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public ShownoteBean[] getBean() {
        return bean;
    }

    public void setBean(ShownoteBean[] bean) {
        this.bean = bean;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public boolean isHasAttach() {
        return hasAttach;
    }

    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
    }
}
