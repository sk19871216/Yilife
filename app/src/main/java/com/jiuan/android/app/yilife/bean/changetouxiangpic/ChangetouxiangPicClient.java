package com.jiuan.android.app.yilife.bean.changetouxiangpic;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangetouxiangPicClient {
    public static RequestHandle requestLogin(Context context, String phone,String tooken,String ext, String imagedata,
                                             ChangetouxiangPicHandler responseHandler, boolean isTest) {
        ChangetouxiangPicRequest helper = new ChangetouxiangPicRequest();
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

        String path = ChangetouxiangPicRequest.PATH_TEST;
        if (!isTest) {
            path = ChangetouxiangPicRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
    public static void cancelAllRequests(boolean mayInterruptIfRunning){
        InnovationClient.getInstance().cancelAllRequests(mayInterruptIfRunning);
    }

    public static void shutdown(){
        InnovationClient.getInstance().shutdown();
    }
}
