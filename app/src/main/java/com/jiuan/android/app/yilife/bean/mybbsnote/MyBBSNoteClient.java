package com.jiuan.android.app.yilife.bean.mybbsnote;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyBBSNoteClient {
    public static RequestHandle request(Context context,String phone,String token ,int type,int pagesize,long ts, MyBBSNoteHandler responseHandler, boolean isTest) {
        MyBBSNoteRequest helper = new MyBBSNoteRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(token);
        helper.setType(type);
        helper.setPagesize(pagesize);
        helper.setTs(ts);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = MyBBSNoteRequest.PATH_TEST;
        if (!isTest) {
            path = MyBBSNoteRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle requestold(Context context,String phone,String token ,int type,int pagesize,long ts, MyBBSNoteHandler responseHandler, boolean isTest) {
        MyBBSNoteoldRequest helper = new MyBBSNoteoldRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(token);
        helper.setType(type);
        helper.setPagesize(pagesize);
        helper.setTs(ts);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = MyBBSNoteoldRequest.PATH_TEST;
        if (!isTest) {
            path = MyBBSNoteoldRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
