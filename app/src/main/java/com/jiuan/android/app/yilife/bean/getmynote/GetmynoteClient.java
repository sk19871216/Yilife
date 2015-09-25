package com.jiuan.android.app.yilife.bean.getmynote;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetmynoteClient {
    public static RequestHandle request(Context context, GetmynoteHandler responseHandler, boolean isTest) {
        GetmynoteRequest helper = new GetmynoteRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetmynoteRequest.PATH_TEST;
        if (!isTest) {
            path = GetmynoteRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
