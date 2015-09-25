package com.jiuan.android.app.yilife.bean.gettopnote;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetTopnotelistClient {
    public static RequestHandle request(Context context,int forums,GetTopnotelistHandler responseHandler, boolean isTest) {
        GetTopnotelistRequest helper = new GetTopnotelistRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setForumID(forums);





        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetTopnotelistRequest.PATH_TEST;
        if (!isTest) {
            path = GetTopnotelistRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
//    public static RequestHandle requestold(Context context,int forums,int pagesize,long ts,GetTopnotelistHandler responseHandler, boolean isTest) {
//        GetTopnotelistoldRequest helper = new GetTopnotelistoldRequest();
//        HeadInfo.Builder builder = new HeadInfo.Builder(context);
//        builder.app("IEMyLife");
//        builder.appID(Installation.id(context));
//        helper.setHeadInfo(builder.build());
//        helper.setForumID(forums);
//        helper.setPageSize(pagesize);
//        helper.setTs(ts);
//
//
//
//
//        InnovationClient client = InnovationClient.getInstance();
//        client.setSSLSocketFactory();
//
//        String path = GetTopnotelistRequest.PATH_TEST;
//        if (!isTest) {
//            path = GetTopnotelistRequest.PATH;
//        }
//
//        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
//    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
