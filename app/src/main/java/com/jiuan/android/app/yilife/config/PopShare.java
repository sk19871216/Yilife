package com.jiuan.android.app.yilife.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by kai on 2015/6/12.
 */
public class PopShare {
//    private MListener listener;
//    private PopupWindow popupWindow;
//    private Context context;
//    private LinearLayout layout_sesseion,layout_timeline;

    public  static  void creatpopwindow(View view,final String logopath,final String link,
                                        final String title,final String message,final Bitmap bitmap,final Context context){
//        final PopupWindow popupWindow= null;
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = v.getId();
//                switch (id){
//                    case R.id.linearlayout_session:
//                        popupWindow.dismiss();
//                }
//            }
//        };
        final PopupWindow popupWindow;
        final WXWebpageObject webpage;
        final WXMediaMessage msg;
        final IWXAPI api;
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);
        LinearLayout layout_sesseion,layout_timeline;
        int[] location = new int[2];
        view.getLocationOnScreen(location);// 获得指定控件的坐标
        View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.pop_share, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
        layout_sesseion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                msg.title = title;
//                msg.description = "参与活动领红包";
                webpage.webpageUrl = link;
                msg.description = title;
//                msg.description = desc_s;
                Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_apps_yilife);
                RequestQueue queue = Volley.newRequestQueue(context);
                ImageRequest imageRequest = new ImageRequest(logopath,new Response.Listener<Bitmap>(){
                    public void onResponse(Bitmap response) {
                        Bitmap bitmap1 =response;
                        msg.thumbData = Util.bmpToByteArray(bitmap1, true);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();

                        req.transaction = buildTransaction("webpage");
                        req.message = msg;
                        api.sendReq(req);
                    }
                },300,300,Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {

                            }

                        });
                queue.add(imageRequest);
            }
        });
        layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
        layout_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]+view.getHeight() - popupWindow.getHeight());

    }
    private static  String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
