package com.jiuan.android.app.yilife.bean.Huodong;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class HuodonglistClient {
    public static RequestHandle request(Context context,String name,int pagesize, HuodonglistHandler responseHandler, boolean isTest) {
        HuodonglistRequest helper = new HuodonglistRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setCampaignName(name);
        helper.setPageSize(pagesize);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = HuodonglistRequest.PATH_TEST;
        if (!isTest) {
            path = HuodonglistRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
