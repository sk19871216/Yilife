package com.jiuan.android.app.yilife.bean.changepasswordnornal;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangepasswordnornalClient {
    public static RequestHandle requestLogin(Context context, String phone,String tooken,String oldpassword, String password,
                                             ChangepasswordnornalHandler responseHandler, boolean isTest) {
        ChangepasswordnornalRequest helper = new ChangepasswordnornalRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setOldpassword(oldpassword);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ChangepasswordnornalRequest.PATH_TEST;
        if (!isTest) {
            path = ChangepasswordnornalRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle creatnewpassword(Context context, String un,String tooken, String password,
                                             CreatNewPassHandler responseHandler, boolean isTest) {
        CreatNewPassRequest helper = new CreatNewPassRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = CreatNewPassRequest.PATH_TEST;
        if (!isTest) {
            path = CreatNewPassRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
