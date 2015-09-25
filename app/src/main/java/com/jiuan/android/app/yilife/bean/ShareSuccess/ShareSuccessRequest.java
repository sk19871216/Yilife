package com.jiuan.android.app.yilife.bean.ShareSuccess;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ShareSuccessRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/record_campaign_operation";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private int id, type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setCampaignID(id);
        body.setType(type);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_60_record_campaign_operation);
        }
        @SerializedName("CampaignID")
        private int campaignID;

        @SerializedName("Type")
        private int type;

        public int getCampaignID() {
            return campaignID;
        }

        public void setCampaignID(int campaignID) {
            this.campaignID = campaignID;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
