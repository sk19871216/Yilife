package com.jiuan.android.app.yilife.bean.Huodong;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class HuodonglistRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/campaign/get_campaigns";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;
    private String campaignName;

    private int  pageSize;

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setCampaignName(campaignName);
        body.setPageSize(pageSize);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_59_campaign_get_campaigns);
        }
        @SerializedName("CampaignName")
        private String campaignName;

        @SerializedName("PageSize")
        private int pageSize;

        public String getCampaignName() {
            return campaignName;
        }

        public void setCampaignName(String campaignName) {
            this.campaignName = campaignName;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

}
