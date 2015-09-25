package com.jiuan.android.app.yilife.bean.fankuiup;

import android.content.Context;
import android.util.Log;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class FankuiClient {
    public static RequestHandle requestMessage(Context context, String phone, String tooken,String feedbackid
                                             ,String content,FankuiHandler responseHandler, boolean isTest) {
        FankuiRequest helper = new FankuiRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setFeedid(feedbackid);
        helper.setText(content);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = FankuiRequest.PATH_TEST;
        if (!isTest) {
            path = FankuiRequest.PATH;
        }
        Log.d("内容",""+helper.getRequestParams());
        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle requestImage(Context context, String phone, String tooken,String ext,String feedbackid,
                                             String imagedata,FankuiImageHandler responseHandler, boolean isTest) {
        FankuiImageRequest helper = new FankuiImageRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setExt(ext);
        helper.setFeedid(feedbackid);
        helper.setImagedata(imagedata);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = FankuiImageRequest.PATH_TEST;
        if (!isTest) {
            path = FankuiRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
