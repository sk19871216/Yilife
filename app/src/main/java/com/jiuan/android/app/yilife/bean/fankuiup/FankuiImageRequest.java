package com.jiuan.android.app.yilife.bean.fankuiup;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class FankuiImageRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/user_feedback_img";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;

    private String tooken;

    private String ext;

    private String feedid;

    private String imagedata;


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

    public String getFeedid() {
        return feedid;
    }

    public void setFeedid(String feedid) {
        this.feedid = feedid;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setExt(ext);
        body.setFeedid(feedid);
        body.setImagedata(imagedata);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_16_FANKUI_IMAGE);
        }

        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("Ext")
        private String ext;

        @SerializedName("FeedBackID")
        private String feedid;

        @SerializedName("ImageData")
        private String imagedata;

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

        public String getFeedid() {
            return feedid;
        }

        public void setFeedid(String feedid) {
            this.feedid = feedid;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getImagedata() {
            return imagedata;
        }

        public void setImagedata(String imagedata) {
            this.imagedata = imagedata;
        }
    }

}
