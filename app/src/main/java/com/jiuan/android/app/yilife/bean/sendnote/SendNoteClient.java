package com.jiuan.android.app.yilife.bean.sendnote;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SendNoteClient {
    public static RequestHandle sendimage(Context context, String phone,String tooken,String ext,String imagedata, SendNoteHandler responseHandler, boolean isTest) {
        SendNoteRequest helper = new SendNoteRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setExt(ext);
        helper.setImagedata(imagedata);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = SendNoteRequest.PATH_TEST;
        if (!isTest) {
            path = SendNoteRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle sendtext(Context context, String phone,String tooken,int forumsid,String title,String message,int aids[], SendNoteTextHandler responseHandler, boolean isTest) {
        SendNoteTextRequest helper = new SendNoteTextRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setForumsid(forumsid);
        helper.setTitle(title);
        helper.setMessage(message);
        helper.setAids(aids);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = SendNoteTextRequest.PATH_TEST;
        if (!isTest) {
            path = SendNoteTextRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
