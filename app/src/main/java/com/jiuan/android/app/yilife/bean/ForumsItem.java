package com.jiuan.android.app.yilife.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/1/9.
 */
public class ForumsItem implements Serializable {

    @SerializedName("ForumID") private int  forumID;
    @SerializedName("ForumName") private String title;
    @SerializedName("Icon") private String icon;


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
}
