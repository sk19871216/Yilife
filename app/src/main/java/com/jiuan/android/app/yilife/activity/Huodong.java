package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.jiuan.android.app.yilife.adapter.HuodongAdapter;
import com.jiuan.android.app.yilife.bean.Huodong.HuodongBean;
import com.jiuan.android.app.yilife.bean.Huodong.HuodonglistClient;
import com.jiuan.android.app.yilife.bean.Huodong.HuodonglistHandler;
import com.jiuan.android.app.yilife.bean.Huodong.HuodonglistResponse;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessClient;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.config.WeiXinGet;
import com.jiuan.android.app.yilife.utils.AccessTokenKeeper;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.jiuan.android.app.yilife.utils.VolleUtils;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Huodong extends Activity implements View.OnClickListener,IWeiboHandler.Response {
    private PullToRefreshListView listView;
    private HuodongAdapter adapter;
    private ProgressDialog progressDialog;
    private ToastOnly toastOnly;
    private ArrayList<HuodongBean> list;
    private ImageRequest imageRequest;
    private TextView tv_title;
    private ImageView iv_back;
    private int positionx=-1;
    private Bitmap bp,thumb;
    private PopupWindow popupWindow;
    private LinearLayout layout_us,layout_sesseion,layout_timeline,layout_qq,layout_qqzone,layout_sina,layout_message,layout_email,layout_copylink;
    private IWXAPI api;
    private WXWebpageObject webpage;
    private WXMediaMessage msg;
    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    private static Tencent mTencent;
    private AuthInfo mAuthInfo;
    private final static String mAppid = ""+1104215150;//正式
    //    private final static String mAppid = ""+1104698415;//vip
    private String message = "",icon_url="",
            title = "",
            link_total = "";
    private RequestQueue queue;
    private static final String TAG = "Huodong";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huodong);
        registerBoradcastReceiver();

        queue = Volley.newRequestQueue(Huodong.this);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("活动");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTencent = Tencent.createInstance(mAppid, Huodong.this);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(Huodong.this, Constants.APP_KEY);
        mWeiboShareAPI.registerApp();
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL,Constants.SCOPE);

        api = WXAPIFactory.createWXAPI(Huodong.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);

        if (savedInstanceState != null) {
            Log.e("222savedInstanceState","savedInstanceState");
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
        toastOnly = new ToastOnly(this);
        list = new ArrayList<HuodongBean>();
        adapter = new HuodongAdapter(list,Huodong.this);
        listView = (PullToRefreshListView) findViewById(R.id.hd_lv);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.getRefreshableView().setSelector(new BitmapDrawable());
//        listView.setSelected(false);
//        listView.setSelector(new BitmapDrawable());
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getnewhuodong();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getoldhuodong();
            }
        });
        listView.setAdapter(adapter);
    }
    private void getnewhuodong(){
        HuodonglistClient.request(this, "", 5, new HuodonglistHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                progressDialog.dismiss();
                listView.onRefreshComplete();
                FailMessage.showfail(Huodong.this, value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                progressDialog.dismiss();
                listView.onRefreshComplete();
                FailMessage.showfail(Huodong.this, msg);
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                progressDialog.dismiss();
                listView.onRefreshComplete();
                toastOnly.toastShowShort("网络超时");
            }

            @Override
            public void onLoginSuccess(HuodonglistResponse response) {
                super.onLoginSuccess(response);
                progressDialog.dismiss();
                list.clear();
                for (int i = 0; i < response.getDatas().length; i++) {
                    list.add(response.getDatas()[i]);
                }
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete();
            }
        }, TestOrNot.isTest);
    }
    private void getoldhuodong(){
        String hdname ="";
        if (list.size()!=0) {
            hdname = list.get(list.size() - 1).getCamName();
        }
        HuodonglistClient.request(this, hdname, 5, new HuodonglistHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                FailMessage.showfail(Huodong.this, value);
                listView.onRefreshComplete();
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                FailMessage.showfail(Huodong.this, msg);
                listView.onRefreshComplete();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                toastOnly.toastShowShort("网络超时");
                listView.onRefreshComplete();
            }

            @Override
            public void onLoginSuccess(HuodonglistResponse response) {
                super.onLoginSuccess(response);
                for (int i = 0; i < response.getDatas().length; i++) {
                    list.add(response.getDatas()[i]);
                }
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete();
            }
        }, TestOrNot.isTest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        getnewhuodong();
    }
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(""+23);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("broad",action);
            if(action.equals(""+23)){
               positionx =  intent.getIntExtra("hd_position", -1);
                title = list.get(positionx).getDisplayName();
                message = list.get(positionx).getCamDesc();
                link_total = list.get(positionx).getCamH5Url();
//                link_total = "http://int.iemylife.com/mobile/cam-BABY/camindex";
                icon_url = list.get(positionx).getCamIcon();
                creatpopwindow();
            }
        }

    };
    private void creatpopwindow(){
        int[] location = new int[2];
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.hd_linearlayout);
        linearLayout.getLocationOnScreen(location);// 获得指定控件的坐标
        View popupWindow_view =getLayoutInflater().inflate(R.layout.pop_share_new, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //微信朋友
        layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
        layout_sesseion.setOnClickListener(this);
        //微信朋友圈
        layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
        layout_timeline.setOnClickListener(this);
        //QQ
        layout_qq = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqfriend_share);
        layout_qq.setOnClickListener(this);
        //QQ空间
        layout_qqzone = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqzone_share);
        layout_qqzone.setOnClickListener(this);
        //sina微博
        layout_sina = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_sina_share);
        layout_sina.setOnClickListener(this);
        //短信
        layout_message = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_message_share);
        layout_message.setOnClickListener(this);
        //邮件
        layout_email = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_email_share);
        layout_email.setOnClickListener(this);
        //复制链接
        layout_copylink = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_copylink);
        layout_copylink.setOnClickListener(this);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);

        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, location[0], location[1]+linearLayout.getHeight() - popupWindow.getHeight());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.linearlayout_session:
                ScAndSv.WX = "huodong";
                ScAndSv.hdid = list.get(positionx).getCampaignID();
                popupWindow.dismiss();
                webpage.webpageUrl = link_total;
                msg.description = message;
                msg.title = title;
                imageRequest = new ImageRequest(icon_url,new Response.Listener<Bitmap>(){
                    public void onResponse(Bitmap response) {
                        bp =response;
                        msg.thumbData = Util.bmpToByteArray(bp, true);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();

                        req.transaction = buildTransaction("webpage");
                        req.message = msg;
                        api.sendReq(req);
                    }
                },300,300,Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                                msg.thumbData = Util.bmpToByteArray(thumb, true);
                                SendMessageToWX.Req req = new SendMessageToWX.Req();

                                req.transaction = buildTransaction("webpage");
                                req.message = msg;
                                api.sendReq(req);
                            }

                        });
                queue.add(imageRequest);

                break;
            case R.id.linearlayout_timeline:
                ScAndSv.WX = "huodong";
                ScAndSv.hdid = list.get(positionx).getCampaignID();
                popupWindow.dismiss();
                webpage.webpageUrl = link_total;
                msg.description = message;
                msg.title = title;
                imageRequest = new ImageRequest(icon_url,new Response.Listener<Bitmap>(){
                    public void onResponse(Bitmap response) {
                        bp =response;
                        msg.thumbData = Util.bmpToByteArray(bp, true);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();

                        req.transaction = buildTransaction("webpage");
                        req.message = msg;
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        api.sendReq(req);
                    }
                },300,300,Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                                msg.thumbData = Util.bmpToByteArray(thumb, true);
                                SendMessageToWX.Req req = new SendMessageToWX.Req();

                                req.transaction = buildTransaction("webpage");
                                req.message = msg;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                api.sendReq(req);
                            }

                        });
                queue.add(imageRequest);

                break;
            case R.id.linearlayout_qqfriend_share:
                Log.e("QQ", "朋友");
                popupWindow.dismiss();
//                toastOnly.toastShowShort("分享失败");
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, message);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, link_total);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, icon_url);
                mTencent.shareToQQ(Huodong.this, params, new BaseUiListener());
                break;
            case R.id.linearlayout_qqzone_share:
                Log.e("QQ", "朋友圈");
                popupWindow.dismiss();
                final Bundle params1 = new Bundle();
                params1.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params1.putString(QzoneShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params1.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
                params1.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, link_total);
                params1.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, icon_url);
                params1.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                mTencent.shareToQQ(Huodong.this, params1, new BaseUiListener());
                break;
            case R.id.linearlayout_sina_share:
                popupWindow.dismiss();
                if (isAvilible(Huodong.this, "com.sina.weibo")) {
//                    mWeiboShareAPI.registerApp();
                     sinashare();




                } else {
                    Toast.makeText(Huodong.this, "请下载新浪微博客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.linearlayout_message_share:
                popupWindow.dismiss();
                Uri smsToUri = Uri.parse("smsto:");

                Intent intentshare = new Intent(Intent.ACTION_SENDTO, smsToUri);
                Log.e("结果p", message + link_total);
                intentshare.putExtra("sms_body", message + link_total);

                startActivity(intentshare);
                break;
            case R.id.linearlayout_email_share:
                popupWindow.dismiss();
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:"));
                data.putExtra(Intent.EXTRA_SUBJECT, title);
                data.putExtra(Intent.EXTRA_TEXT, message + link_total);
//                        startActivity(data);
                if (data.resolveActivity(getPackageManager()) != null) {
                    startActivity(data);
                } else {
                    toastOnly.toastShowShort("请先绑定一个邮箱");
                }
                break;
            case R.id.linearlayout_copylink:
                popupWindow.dismiss();
                ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip.getText(); // 粘贴
                clip.setText(link_total); // 复制
                Toast.makeText(Huodong.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
        };
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        Log.e("2222BaseResponse","baseResponse");
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                huodongsharesuccess(2);
//                Toast.makeText(Huodong.this,"成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(Huodong.this, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(Huodong.this,
                        "失败" + "Error Message: " + baseResponse.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }


//    public void onResponse(BaseResponse baseResp) {
//        toastOnly.toastShowShort("weibo2" + "__baseResp.errCode");
//        switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//
//                Toast.makeText(Huodong.this,"成功", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//                Toast.makeText(Huodong.this, "取消", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//                Toast.makeText(Huodong.this,
//                        "失败" + "Error Message: " + baseResp.errMsg,
//                        Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

        private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
//                Toast.makeText(Huodong.this,"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
//                Toast.makeText(Huodong.this,"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
//            Util.showResultDialog(LoginNormal.this, response.toString(), "登录成功");
//            Toast.makeText(Huodong.this,response.toString(),Toast.LENGTH_SHORT).show();
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }
        protected void doComplete(JSONObject values) {
            huodongsharesuccess(2);
        }
        @Override
        public void onError(UiError e) {
        }
        @Override
        public void onCancel() {
        }
    }
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = message;

        // 设置 Bitmap 类型的图片到视频对象里
        imageRequest = new ImageRequest(icon_url,new Response.Listener<Bitmap>(){
            public void onResponse(Bitmap response) {
                thumb =response;
            }
        },300,300,Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                    }

                });
        queue.add(imageRequest);
        mediaObject.setThumbImage(thumb);
        mediaObject.actionUrl = link_total;
        return mediaObject;
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = message;
        return textObject;
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
    private boolean isAvilible(Context context, String packageName){
        PackageInfo packageInfo = new PackageInfo();
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if(pinfo != null){
            for(int i = 0; i < pinfo.size(); i++){
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("222NewIntent","NewIntent");
        mWeiboShareAPI.handleWeiboResponse(intent, Huodong.this);
    }

    private  void sinashare(){
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = getWebpageObj();
        weiboMessage.textObject = getTextObj();
        SendMultiMessageToWeiboRequest wiborequest = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        wiborequest.transaction = String.valueOf(System.currentTimeMillis());
        wiborequest.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(Huodong.this, wiborequest);

    }
    public void huodongsharesuccess(int type){
        ShareSuccessClient.request(Huodong.this,list.get(positionx).getCampaignID(),type, new ShareSuccessHandler(){
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
            }

            @Override
            public void onLoginSuccess(String response) {
                super.onLoginSuccess(response);
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
            }
        },TestOrNot.isTest);
    }
}
