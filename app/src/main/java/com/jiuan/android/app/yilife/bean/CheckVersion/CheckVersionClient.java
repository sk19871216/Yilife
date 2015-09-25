package com.jiuan.android.app.yilife.bean.CheckVersion;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CheckVersionClient {
    public static RequestHandle requestLogin(Context context, String versionnumb,String systemEnvironments,String versionParameter, CheckVersionHandler responseHandler, boolean isTest) {
        CheckVersionRequest helper = new CheckVersionRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setVersionNumber(versionnumb);
        helper.setSystemEnvironments(systemEnvironments);
        helper.setVersionParameter(versionParameter);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = CheckVersionRequest.PATH_TEST;
        if (!isTest) {
            path = CheckVersionRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
