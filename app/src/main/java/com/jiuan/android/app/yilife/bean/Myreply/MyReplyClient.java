package com.jiuan.android.app.yilife.bean.Myreply;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyReplyClient {
    public static RequestHandle request(Context context,String phone,String tooken,int pagesize,long ts, MyReplyHandler responseHandler, boolean isTest) {
        MyReplyRequest helper = new MyReplyRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setPagesize(pagesize);
        helper.setTs(ts);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = MyReplyRequest.PATH_TEST;
        if (!isTest) {
            path = MyReplyRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
