package com.jiuan.android.app.yilife.bean.BangdingPhone;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class BangdingPhoneClient {
    public static RequestHandle getphonecode(Context context,String un,String tooken, String phone, int type, GetPhoneCodeHandler responseHandler, boolean isTest) {
        GetPhoneCodeRequest helper = new GetPhoneCodeRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setPhone(phone);
        helper.setType(type);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetPhoneCodeRequest.PATH_TEST;
        if (!isTest) {
            path = GetPhoneCodeRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle checkphonecode(Context context, String phone, String code, ChangePhoneCodeHandler responseHandler, boolean isTest) {
        CheckPhoneCodeRequest helper = new CheckPhoneCodeRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());

        helper.setPhone(phone);
        helper.setCode(code);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = CheckPhoneCodeRequest.PATH_TEST;
        if (!isTest) {
            path = CheckPhoneCodeRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle changephonecode(Context context, String un, String tooken,String newPhone,String oldcode,String newcode,
                                                ChangePhoneCodeHandler responseHandler, boolean isTest) {
        ChangePhoneCodeRequest helper = new ChangePhoneCodeRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setAccessToken(tooken);
        helper.setNewPhone(newPhone);
        helper.setOldCode(oldcode);
        helper.setNewCode(newcode);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ChangePhoneCodeRequest.PATH_TEST;
        if (!isTest) {
            path = ChangePhoneCodeRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle newphone(Context context, String un,String tooken,String phone,
                                         String code,GetPhoneCodeHandler responseHandler, boolean isTest) {
        NewPhoneRequest helper = new NewPhoneRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setPhone(phone);
        helper.setCode(code);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = NewPhoneRequest.PATH_TEST;
        if (!isTest) {
            path = NewPhoneRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
