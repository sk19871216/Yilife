package com.jiuan.android.app.yilife.bean.getadnote;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetadnotelistRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/bbs/get_forum_essences";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private int forumID;

    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setForumID(forumID);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_23_GET_FORUMS_ESS);
        }

        @SerializedName("ForumID")
        private int forumID;

        public int getForumID() {
            return forumID;
        }

        public void setForumID(int forumID) {
            this.forumID = forumID;
        }

    }

}
