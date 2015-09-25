package com.jiuan.android.app.yilife.bean.registiondone;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RegistionDoneClient {
    public static RequestHandle requestLogin(Context context, String phone,String code,String password,String innivatationcode, RegistionDoneHandler responseHandler, boolean isTest) {
        RegistionDoneRequest helper = new RegistionDoneRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setCode(code);
        helper.setPassword(password);
        helper.setInvitationCode(innivatationcode);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = RegistionDoneRequest.PATH_TEST;
        if (!isTest) {
            path = RegistionDoneRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
