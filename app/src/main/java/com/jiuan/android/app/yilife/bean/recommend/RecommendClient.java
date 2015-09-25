package com.jiuan.android.app.yilife.bean.recommend;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.android.app.yilife.activity.RecommendDetail;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendClient {
    public static RequestHandle totaltebate(Context context,String un,String tooken,RecommendHandler responseHandler, boolean isTest) {
        RecommendRequest helper = new RecommendRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);




        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = RecommendRequest.PATH_TEST;
        if (!isTest) {
            path = RecommendRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle tebatedetail(Context context,String un,String tooken,int pagesize,long ts, RecommendDetailHandler responseHandler, boolean isTest) {
        RecommendDetailRequest helper = new RecommendDetailRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setOrderTS(ts);
        helper.setPageSize(pagesize);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = RecommendDetailRequest.PATH_TEST;
        if (!isTest) {
            path = RecommendDetailRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle directreferrals(Context context,String un,String tooken,int pagesize,long ts, RecommendReferralsHandler responseHandler, boolean isTest) {
        RecommendReferralsRequest helper = new RecommendReferralsRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setTs(ts);
        helper.setPagesize(pagesize);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = RecommendReferralsRequest.PATH_TEST;
        if (!isTest) {
            path = RecommendReferralsRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle referralsdetail(Context context,String un,String tooken,String hguid, ReferralsDetailHandler responseHandler, boolean isTest) {
        ReferralsDetailRequest helper = new ReferralsDetailRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);
        helper.setHguid(hguid);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ReferralsDetailRequest.PATH_TEST;
        if (!isTest) {
            path = ReferralsDetailRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
