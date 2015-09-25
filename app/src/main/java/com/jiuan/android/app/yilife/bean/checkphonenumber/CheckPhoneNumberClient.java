package com.jiuan.android.app.yilife.bean.checkphonenumber;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CheckPhoneNumberClient {
    public static RequestHandle requestLogin(Context context, String phone, String password, CheckPhoneNumberHandler responseHandler, boolean isTest) {
        CheckPhoneNumberRequest helper = new CheckPhoneNumberRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = CheckPhoneNumberRequest.PATH_TEST;
        if (!isTest) {
            path = CheckPhoneNumberRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
