package com.jiuan.android.app.yilife.bean.shownote;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ShownoteRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/bbs/show_topic";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private int topicid;

    private int pagesize;

    private int pageIndex;

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setTopicID(topicid);
        body.setPageSize(pagesize);
        body.setPageIndex(pageIndex);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_26_SHOW_TOPIC);
        }
        @SerializedName("TopicID")
        private int topicID;

        @SerializedName("PageIndex")
        private int pageIndex;

        @SerializedName("PageSize")
        private int pageSize;

        public int getTopicID() {
            return topicID;
        }

        public void setTopicID(int topicID) {
            this.topicID = topicID;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

}
