package com.jiuan.android.app.yilife.bean.reply;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ReplyRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/bbs/post_reply";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;
    private String tooken;
    private int topicid;
    private String neirong;
    private int replyfloor;
//    private String replyname;
    private int replynameid;

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

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public int getReplyfloor() {
        return replyfloor;
    }

    public void setReplyfloor(int replyfloor) {
        this.replyfloor = replyfloor;
    }

//    public String getReplyname() {
//        return replyname;
//    }
//
//    public void setReplyname(String replyname) {
//        this.replyname = replyname;
//    }

    public int getReplynameid() {
        return replynameid;
    }

    public void setReplynameid(int replynameid) {
        this.replynameid = replynameid;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setTopicID(topicid);
        body.setNeirong(neirong);
        body.setReplyFloor(replyfloor);
        body.setReplyUserID(replynameid);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_27_POST_REPLY);
        }
        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("TopicID")
        private int topicID;

        @SerializedName("Content")
        private String neirong;

        @SerializedName("ReplyFloor")
        private int replyFloor;

        @SerializedName("ReplyUserID")
        private int replyUserID;

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

        public int getTopicID() {
            return topicID;
        }

        public void setTopicID(int topicID) {
            this.topicID = topicID;
        }

        public String getNeirong() {
            return neirong;
        }

        public void setNeirong(String neirong) {
            this.neirong = neirong;
        }

        public int getReplyFloor() {
            return replyFloor;
        }

        public void setReplyFloor(int replyFloor) {
            this.replyFloor = replyFloor;
        }

//        public String getReplyName() {
//            return replyName;
//        }
//
//        public void setReplyName(String replyName) {
//            this.replyName = replyName;
//        }

        public int getReplyUserID() {
            return replyUserID;
        }

        public void setReplyUserID(int replyUserID) {
            this.replyUserID = replyUserID;
        }
    }

}
