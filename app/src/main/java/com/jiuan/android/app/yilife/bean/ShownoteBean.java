package com.jiuan.android.app.yilife.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/19.
 */
public class ShownoteBean {
    @SerializedName("PostID") private int postID;
    @SerializedName("PosterID") private int posterID;
    @SerializedName("Poster") private String poster;

    @SerializedName("PostTime") private long posttime;
    @SerializedName("Floor") private int floor;
    @SerializedName("Title") private String replytitle;
    @SerializedName("Content") private String neirong;
    @SerializedName("ReplyTo") private String replyTo;
    private String ttitle;
    private int count;

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
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

    public long getPosttime() {
        return posttime;
    }

    public void setPosttime(long posttime) {
        this.posttime = posttime;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getReplytitle() {
        return replytitle;
    }

    public void setReplytitle(String replytitle) {
        this.replytitle = replytitle;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getTtitle() {
        return ttitle;
    }

    public void setTtitle(String ttitle) {
        this.ttitle = ttitle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
