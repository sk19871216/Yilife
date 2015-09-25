package com.jiuan.android.app.yilife.bean.getnotelist;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetnotelistoldRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/bbs/get_forums_older";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private int forumID;

    private int pageSize;
    private long ts;

    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setForumID(forumID);
        body.setPageSize(pageSize);
        body.setTs(ts);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_22_GET_FORUMS_OLD);
        }

        @SerializedName("ForumID")
        private int forumID;

        @SerializedName("PageSize")
        private int pageSize;

        @SerializedName("TS")
        private long ts;


        public int getForumID() {
            return forumID;
        }

        public void setForumID(int forumID) {
            this.forumID = forumID;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }
    }

}
