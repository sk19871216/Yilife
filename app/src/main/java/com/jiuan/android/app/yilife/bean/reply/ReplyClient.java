package com.jiuan.android.app.yilife.bean.reply;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ReplyClient {
    public static RequestHandle request(Context context,String phone,String tooken,int topicid,String neirong,int floor,int  nameid, ReplyHandler responseHandler, boolean isTest) {
        ReplyRequest helper = new ReplyRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setTopicid(topicid);
        helper.setNeirong(neirong);
        helper.setReplyfloor(floor);
        helper.setReplynameid(nameid);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ReplyRequest.PATH_TEST;
        if (!isTest) {
            path = ReplyRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
