package com.jiuan.android.app.yilife.bean.sendnote;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SendNoteTextRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/bbs/post_topic";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;

    private String tooken;
    private int forumsid;
    private String title;
    private String message;
    private int[] aids;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public int getForumsid() {
        return forumsid;
    }

    public void setForumsid(int forumsid) {
        this.forumsid = forumsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int[] getAids() {
        return aids;
    }

    public void setAids(int[] aids) {
        this.aids = aids;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setForumID(forumsid);
        body.setTitle(title);
        body.setMessage(message);
        body.setAids(aids);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_25_POST_TOPIC);
        }

        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("ForumID")
        private int forumID;

        @SerializedName("Title")
        private String title;

        @SerializedName("Message")
        private String message;

        @SerializedName("Aids")
        private int[] aids;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTooken() {
            return tooken;
        }

        public void setTooken(String tooken) {
            this.tooken = tooken;
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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int[] getAids() {
            return aids;
        }

        public void setAids(int[] aids) {
            this.aids = aids;
        }
    }

}
