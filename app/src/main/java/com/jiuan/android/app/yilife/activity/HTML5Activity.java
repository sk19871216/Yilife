package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareClient;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareHandler;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareResponse;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.view.ViewGroup.LayoutParams;


/**
 * 因为使用WebView会发生内存无法完全释放的问题
 * 所以采用如下解决方案
 * 1.动态加载WebView,在onDestroy方法中,removeView.
 * 2.HTML5Activity单独启动一个进程.在关闭该Activity时,关闭该进程.
 *
 */
public class HTML5Activity extends ParentActivity implements View.OnClickListener, MyWebView.CompleteListener {
    private String path,phone,tooken,hguid;
    private static final String TAG = "HTML5Activity.class";

    private static final String UNLOGIN_URL = "http://203.195.202.228:8020/GoLogin.aspx";

    private static final String KEY = "appauth";

    private static final String SPACE = "iemylife";

    private static final int LEFT_BACK = 0x00000001;

    private static final int LEFT_CLOSE = 0x00000002;

    private static final int RIGHT_SHARE = 0x00000004;

    private int state;

    private IWXAPI api;

    private Toolbar toolbar;

    private MyWebView webView;

    private String lastUrl;

    private FrameLayout frameLayout,frameLayout_background;
    private RelativeLayout linearLayout_H5;
    private Boolean needlogin=false,isshare = true;
    private ImageView iv_back,iv_setting;
    private TextView tv_bar_title;
    private Menu mMenu;
    private Bitmap bp;
    private PopupWindow popupWindow;
    private LinearLayout layout_sesseion,layout_timeline,layout_sina,layout_qq,layout_other;
    private String title_s,link_s,imgpath_s,desc_s,title_local,stata_local;
    private WXWebpageObject webpage;
    private WXMediaMessage msg;
    private ProgressDialog dialog,loading_dialog;
    private int count=0;
    private boolean isfirst=true,picfinish=false,pagefinish=false,benew =false,progress100 = false,firstlogin=false;
    private Handler handler;
    private int hcount=0,counttime=10,progress=0;
    private Thread mthread;
    private ToastOnly toastOnly;
//    private LinearLayout linearLayout_H5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html5);
        toastOnly = new ToastOnly(this);
        linearLayout_H5 = (RelativeLayout) findViewById(R.id.linearlayout_H5);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
//        dialog.show();
        api = WXAPIFactory.createWXAPI(HTML5Activity.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);

        loading_dialog = new ProgressDialog(this);
        loading_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (progress!=0 && progress!=100){
                    Log.e("2asd","22222");
                    webView.stopLoading();
                }
            }
        });

        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = mySharedPreferences.getString("phone","");
        tooken = mySharedPreferences.getString("AccessToken","");
        hguid = mySharedPreferences.getString("HGUID","");

        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setOnClickListener(this);
        frameLayout_background = (FrameLayout) findViewById(R.id.H5_background);
//        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        Intent intent =getIntent();
        path = intent.getStringExtra("path");
        if (path==null){
            if(TestOrNot.isTest) {
                path = "http://int.iemylife.com/mobile/cam-BABY/camindex";
            }else{
                path = "www.iemylife.com/mobile/cam-BABY/camindex";
            }
        }
        Log.e("结果p",path);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        webView = new MyWebView(this);
        webView.setCompleteListener(this);

//        WebSettings settings = webView.getSettings();
//        settings.setAppCacheEnabled(false);

                CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.getInstance().sync();

        ViewGroup.LayoutParams params =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        params.format= PixelFormat.RGBA_8888;
        webView.setLayoutParams(params);
//        webView.loadUrl("http://int.iemylife.com/mobile/cam-gprsbp/camindex");
        frameLayout.addView(webView);
        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!tv_bar_title.getText().toString().contains("1/2")) {
                    onBackPressed();
//                }else{
//                    webView.goBack();
//                }
            }
        });
//        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//        setSupportActionBar(toolbar);
        // webView = (WebView) findViewById(R.id.webview);
//        init(getIntent().getStringExtra(YSHFragment.HTML5_URL));
       // init(getIntent().getStringExtra("http://203.195.202.228:8020/"));
        init(path);
//        Log.d("pppppppppppp",path);
//        init(path);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                        if (!pagefinish || !picfinish) {
                            dialog.dismiss();
//                            HTML5Activity.this.finish();
                            toastOnly.toastShowShort("网络不给力，请稍后尝试");
                        }
//                    }
                }

                progress = msg.getData().getInt("progress");
                if (progress!=0){
                    loading_dialog.setProgress(progress);
                    if (progress100){
                        loading_dialog.dismiss();
                    }
                }
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        webView.clearCache(true);
//        webView.clearHistory();
        frameLayout.removeView(webView);
        webView.removeAllViews();
        webView.destroy();
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().startSync();
//        CookieManager.getInstance().removeAllCookie();
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().startSync();
//        CookieManager.getInstance().removeAllCookie();
//        CookieSyncManager.getInstance().sync();
//        System.exit(0);
    }

    private void init(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDefaultTextEncodingName("gb2312");
        webView.setWebChromeClient(new MyWebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("结果pro", "" + newProgress);
                if (newProgress == 100) {
                    progress100 = true;
                }
                progress = newProgress;
                Message msg1 = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("progress", newProgress);
                msg1.setData(bundle);
                handler.sendMessage(msg1);
            }

        });

        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress100 = false;
                loading_dialog.setProgress(0);
                loading_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                loading_dialog.setMessage("请稍后");
//                loading_dialog.show();
            }

//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                // 　　//handler.cancel(); 默认的处理方式，WebView变成空白页
//                handler.proceed(); // 接受证书
//                // handleMessage(Message msg); 其他处理
//
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!tv_bar_title.getText().equals(title_local)){
                    tv_bar_title.setText(title_local);
                    Log.e("结果sss","sss");
                    if (stata_local.equals("right_share")){
                        iv_setting.setVisibility(View.VISIBLE);
                        iv_setting.setImageResource(R.drawable.icon_share);
                        iv_back.setImageResource(R.drawable.icon_close);
                    }
                }
//                if (isfirst) {
//                    dialog.dismiss();
//                }
                pagefinish =true;
                checkfinish();
                Log.d("1234","asdasdasd");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                toastOnly.toastShowShort("网络不给力，请稍后尝试");
                HTML5Activity.this.finish();
            }
        });
        webView.addJavascriptInterface(new JavaScriptWeiXinInterface(), SPACE);
        // 已经登录
//        loadUrlWithHeader(url);
        // 未登录

        updateToolbarFromUrl(url);
//        SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
//        if (mysharedPreferences.getInt("isLogin",-1)==1){
//            loadUrlWithHeader(lastUrl);
//        }else {
        Log.d("url",url);
        if (url.contains("share")){
            loadUrlWithHeader(url);
            iv_back.setImageResource(R.drawable.icon_close);
            iv_setting.setVisibility(View.VISIBLE);
            iv_setting.setImageResource(R.drawable.icon_share);
        }else {

            if (count==0) {
//                dialog.setCancelable(false);
//                dialog.setMessage("正在加载...");
//                dialog.show();
                TimeDesc();
//                webView.loadUrl(url);
                loadUrlWithHeader(url);
            }else{
                loadUrlWithHeader(url);
            }
            iv_back.setImageResource(R.drawable.icon_back);
            iv_setting.setVisibility(View.GONE);
        }
//        }
    }

    private void loadUrlWithHeader(String url) {
        Log.e("结果h5","hhhh");
        SharedPreferences mysharedPreferenceslogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        if (mysharedPreferenceslogin.getInt("isLogin",-1)==1) {
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put(KEY, getValue("" + hguid, tooken));
            Log.e("结果aaa", hguid + "------" + tooken);
//        dialog.setCancelable(false);
//        dialog.setMessage("正在加载...");
//        dialog.show();
            webView.loadUrl(url, extraHeaders);
        }else{
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put(KEY, getValue("" + "", ""));
            webView.loadUrl(url, extraHeaders);
        }
    }

    private String getValue(String number, String token) {
        return "un" + "=" + number + "&" + "accesstoken" + "=" + token;
    }

    @Override
    public void onPageComplete() {
//        if (!isfirst) {
////            dialog.dismiss();
//        }
        picfinish =true;
        checkfinish();
        Log.d("1234","comp");

    }

//    private void startLoginActivity() {
//        Log.d(TAG, "打开登录页面");
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, eyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            title_local = title;
            if (title_local.contains("Webpage not available") || title_local.contains("找不到网页")){
                toastOnly.toastShowShort("网络不给力，请稍后尝试");
                HTML5Activity.this.finish();
            }
            if (!tv_bar_title.getText().equals(title)){
                tv_bar_title.setText(title);
            }
            if (title.trim().equals("报名成功")){
                iv_setting.setVisibility(View.VISIBLE);
                iv_setting.setImageResource(R.drawable.icon_share);
                iv_back.setImageResource(R.drawable.icon_close);
            }else{
                iv_setting.setVisibility(View.GONE);
                iv_back.setImageResource(R.drawable.icon_back);
            }
//            setTitle(title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(message).setPositiveButton("确定", null);
            AlertDialog dialog1 = builder.create();
            dialog1.show();
            result.confirm();
            return true;
        }
    }
    private void startLoginActivity() {
//        LogUtils.logD(TAG, "打开登录页面");
        needlogin = true;
        SharedPreferences mysharedPreferences =getSharedPreferences("login", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mysharedPreferences.edit();
        editor.putInt("isLogin", -1).commit();
        benew =true;
            Intent intent = new Intent(this, LoginNormal.class);
            LoginFrom.setLoginfrom(5);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        // TODO 这里直接赋值,正常流程应该监听登录成功的事件
//        loadUrlWithHeader(lastUrl);
    }
    private void startLoginActivity2() {
//        LogUtils.logD(TAG, "打开登录页面");
        needlogin = true;
//        SharedPreferences mysharedPreferences =getSharedPreferences("login", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mysharedPreferences.edit();
//        editor.putInt("isLogin", -1).commit();
        Intent intent =new Intent(this,LoginNormal.class);
        LoginFrom.setLoginfrom(5);
        startActivity(intent);
        // TODO 这里直接赋值,正常流程应该监听登录成功的事件
//        loadUrlWithHeader(lastUrl);
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            isfirst =false;
            picfinish =false;
            pagefinish = false;
            benew =false;
            counttime=10;
            TimeDesc();
            dialog.show();
            count++;
            Log.e("结果url",url);

                if (needLogin(url)) {
//                    if (!firstlogin) {
//                   firstlogin = true;
                    SharedPreferences mysharedPreferences = getSharedPreferences("login", Context.MODE_MULTI_PROCESS);
                    Log.d("asdasdasdasdas111", "" + mysharedPreferences.getInt("isLogin", -1));
                    int a = mysharedPreferences.getInt("isLogin", -1);

                    if (a != 1) {
                        toastOnly.toastShowShort("请先登录");
                    } else {
                        toastOnly.toastShowShort("账号异常，请重新登录");
                    }
                    Log.d("needLogin", "needLogin");

                    startLoginActivity();

                    return true;
                } else if (expiredLogin(url)) {
                    startLoginActivity();
                    Log.d("expiredLogin", "expiredLogin");
                    return true;
                }
//            }
            lastUrl = url;
            updateToolbarFromUrl(url);
            SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE| Context.MODE_MULTI_PROCESS);


//            if (mysharedPreferences.getInt("isLogin",-1)==1){
                loadUrlWithHeader(lastUrl);
//            }else {
//                dialog.setCancelable(false);
//                dialog.setMessage("正在加载...");
//                view.loadUrl(url);
//            }
            return true;
        }

//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            if (url.equals("http://203.195.202.228:8020/Share.aspx?left=close&right=share")) {
//                view.loadUrl("javascript:share()");
//            }
//        }
    }

        /*
     * 通过返回内容判断是否需要让用户登录
     *
     * @param url
     * @return true 需要登录 false 不需要登录
     */
    private boolean needLogin(String url) {
        Pattern p = Pattern.compile(".*?gologin.*?");
        Matcher m = p.matcher(url);
        return m.find();
    }
    /*
       * 通过返回内容判断用户登录信息是否过期
       * @param url
       * @return true 过期 false 未过期
       */
    private boolean expiredLogin(String url) {
        Pattern p = Pattern.compile(".*?goexlogin.*?");
        Matcher m = p.matcher(url);
        return m.find();
    }
    private void updateToolbarFromUrl(String url) {
        state = 0;
        int index = url.indexOf("?");
        if (index == -1) {
            state |= LEFT_BACK;
            stata_local = "left_back";
            iv_setting.setVisibility(View.GONE);
            return;
        }
        url = url.substring(index + 1);
        Pattern p = Pattern.compile(".*?left=back.*?");
        Matcher back = p.matcher(url);
        if (back.find()) {
            state |= LEFT_BACK;
            stata_local = "left_back";
            iv_setting.setVisibility(View.GONE);
            iv_back.setImageResource(R.drawable.icon_back);
        }
        Pattern p2 = Pattern.compile(".*?left=close.*?");
        Matcher close = p2.matcher(url);
        if (close.find()) {
            state |= LEFT_CLOSE;
            stata_local = "left_close";
        }
        Pattern p3 = Pattern.compile(".*?right=share.*?");
        Matcher share = p3.matcher(url);
        if (share.find()) {
            stata_local = "right_share";
            state |= RIGHT_SHARE;
//            tv_bar_title.setText("报名成功");
//            iv_setting.setVisibility(View.VISIBLE);
//            iv_setting.setImageResource(R.drawable.icon_share);
//            iv_back.setImageResource(R.drawable.icon_close);
        }

        // 更新UI

    }
    private class JavaScriptWeiXinInterface {

        @JavascriptInterface
        public void weixinShare(String title, String link, String imgUrl, String desc) {
//            Toast.makeText(HTML5Activity.this, "title" + title + "link" + link + "imgUrl" + imgUrl + "desc" + desc, Toast.LENGTH_SHORT).show();
//              getActionBar().
            title_s =title;
            link_s =link;
            imgpath_s =imgUrl;
            desc_s =desc;

            // HTML5Activity.this.onOptionsItemSelected(menuItem);
//            onMenuOpened(R.menu.menu_show_note,menu);
            Log.d("h5title",title);
            Log.e("h5link", link);
            Log.d("h5imgUrl",imgUrl);
            Log.d("h5desc",desc);
//            onClick(iv_setting);
            if (isshare) {
                isshare = false;
                int[] location = new int[2];
                HTML5Activity.this.frameLayout.getLocationOnScreen(location);// 获得指定控件的坐标
                View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_share, null, false);
                popupWindow = new PopupWindow(popupWindow_view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
                layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
                layout_sesseion.setOnClickListener(HTML5Activity.this);
                layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
                layout_timeline.setOnClickListener(HTML5Activity.this);
                layout_qq = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqfriend_share);
                layout_qq.setOnClickListener(HTML5Activity.this);
                layout_sina = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_sina_share);
                layout_sina.setOnClickListener(HTML5Activity.this);
                layout_other = (LinearLayout) popupWindow_view.findViewById(R.id.pop_share_other);

                layout_other.setVisibility(View.GONE);
                layout_qq.setVisibility(View.INVISIBLE);
                layout_sina.setVisibility(View.INVISIBLE);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new poponDismissListener());
//                backgroundAlpha(0.5f);
//                frameLayout.setAlpha(0.5f);
//                linearLayout_H5.setAlpha(0.5f);

                popupWindow.showAtLocation(frameLayout, Gravity.NO_GRAVITY, location[0], location[1] + frameLayout.getHeight() - popupWindow.getHeight());
            }
        }

    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        HTML5Activity.this.getWindow().setAttributes(lp);
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
//            backgroundAlpha(1f);
//            frameLayout.setAlpha(1f);
            isshare =true;
//            frameLayout_background.setVisibility(View.GONE);
//            linearLayout_H5.setBackgroundColor(android.graphics.Color.parseColor("@null"));
//            linearLayout_H5.setBackgroundColor(@null);
//            linearLayout_H5.setAlpha(140);

        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("asdasdasdasdas", "ffffffff");

        SharedPreferences mysharedPreferences = getSharedPreferences("login", Context.MODE_MULTI_PROCESS);
        Log.d("asdasdasdasdas111",""+mysharedPreferences.getInt("isLogin",-1));
        int a = mysharedPreferences.getInt("isLogin",-1);

        if (needlogin==true && a==1){
            if (!isfirst) {
//                dialog.show();
                pagefinish=false;
                picfinish=false;
                benew =false;
                counttime=10;
                TimeDesc();
            }
//            SharedPreferences share = getSharedPreferences("login",
//                    Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
            SharedPreferences mySharedPreferences = getSharedPreferences("self", Context.MODE_MULTI_PROCESS);
            phone = mySharedPreferences.getString("phone","");
            tooken = mySharedPreferences.getString("AccessToken","");
            hguid = mySharedPreferences.getString("HGUID","");

            loadUrlWithHeader(lastUrl);
            needlogin=false;
            Log.d("asdasdasdasdas","asdasdasd");
            Log.d("asdasdasdasdas",lastUrl);
            Log.d("asdasdasdasdas",""+mysharedPreferences.getInt("isLogin",-1));
        }else{
//            dialog.dismiss();
        }
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
//        if (state==LEFT_BACK){
//            if (!tv_bar_title.getText().toString().contains("1/2")) {
//                webView.goBack();
//            }else{
//                finish();
//            }
//        }else if(state==LEFT_CLOSE){
//            finish();
//        }else if(state==RIGHT_SHARE){
//            finish();
//        }
        if (!tv_bar_title.getText().toString().contains("1/2")) {

            super.onBackPressed();
        }else{
//            webView.goBack();
            loadUrlWithHeader(path);
        }

    }
    public void checkfinish(){
        if (pagefinish && picfinish){
            dialog.dismiss();
            Log.e("checkfinish","checkfinish");
            benew =true;
        }
    }


    private void TimeDesc(){
        hcount=1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TimeDesc","TimeDesc");
                while (!benew) {
                    try {
                        Thread.sleep(1000);
                        counttime--;
                        Log.e("TimeDesc", "" + counttime);
                        if (counttime == 0)
                            handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.linearlayout_session:
                changtoshared();
                ScAndSv.place = "H5";
                ScAndSv.WX = "share";
                popupWindow.dismiss();
//                api = WXAPIFactory.createWXAPI(HTML5Activity.this, Constants.APP_ID);
//                api.registerApp(Constants.APP_ID);
//                WXWebpageObject webpage = new WXWebpageObject();
//                WXMediaMessage msg = new WXMediaMessage(webpage);

                msg.title = title_s;
//                msg.description = "参与活动领红包";
                webpage.webpageUrl = link_s;
                msg.description = desc_s;
//                msg.description = desc_s;
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                RequestQueue queue = Volley.newRequestQueue(HTML5Activity.this);
                ImageRequest imageRequest = new ImageRequest(imgpath_s,new Response.Listener<Bitmap>(){
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

                    }

                    });
                queue.add(imageRequest);

//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
//                req.message = msg;
//                api.sendReq(req);
                 break;
           case R.id.linearlayout_timeline:
               changtoshared();
               ScAndSv.place = "H5";
               ScAndSv.WX = "share";
               popupWindow.dismiss();
//               api = WXAPIFactory.createWXAPI(HTML5Activity.this, Constants.APP_ID);
//               api.registerApp(Constants.APP_ID);
//               WXWebpageObject webpage1 = new WXWebpageObject();
////               webpage1.webpageUrl = "http://int.iemylife.com/mobile/cam-gprsbp/camindex";
//               WXMediaMessage msg1 = new WXMediaMessage(webpage1);
//               msg1.title = "免费体验血压计";
//               msg1.description = "参与活动领红包";
               webpage.webpageUrl = link_s;
               msg.title = desc_s;
//               msg.description = desc_s;
//               msg.description = title_s;
//               Bitmap thumb1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
               RequestQueue queue1 = Volley.newRequestQueue(HTML5Activity.this);
               ImageRequest imageRequest1 = new ImageRequest(imgpath_s,new Response.Listener<Bitmap>(){
                   public void onResponse(Bitmap response) {
                       bp =response;
                       msg.thumbData = Util.bmpToByteArray(bp, true);
                       SendMessageToWX.Req req1 = new SendMessageToWX.Req();
                       req1.transaction = buildTransaction("webpage");
                       req1.message = msg;
                       req1.scene =SendMessageToWX.Req.WXSceneTimeline;
                       api.sendReq(req1);
//                       msg.thumbData = Util.bmpToByteArray(bp, true);
                   }
               },300,300,Bitmap.Config.ARGB_8888,
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError arg0) {

                           }

                       });
               queue1.add(imageRequest1);

            break;
            case R.id.blue_icon_setting:
//                Log.d("imurl",imgpath_s);
                webView.loadUrl("javascript:share()");
                break;
        }
    }
    protected void changtoshared(){
        SharedPreferences mysharedPreferenceslogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        String phone = mySharedPreferences.getString("phone","");
        String tooken = mySharedPreferences.getString("AccessToken","");
        String hguid = mySharedPreferences.getString("HGUID","");

        if (mysharedPreferenceslogin.getInt("isLogin",-1)==1) {
//            if (ScAndSv.place.equals("H5")) {
                IsRedbagShareClient.requestLogin(HTML5Activity.this, hguid, tooken, "GPRSBP", 1, new IsRedbagShareHandler() {
                    @Override
                    public void onLoginSuccess(IsRedbagShareResponse response) {
                        super.onLoginSuccess(response);
//                        Toast.makeText(MainActivity.this,""+response.getIsSuccess(),Toast.LENGTH_SHORT).show();
                        if (response.getIsSuccess() == 1) {
                            Log.e("success", "分享状态修改成功");
                        }
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
//                        Toast.makeText(MainActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
//                        Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
//                        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                }, TestOrNot.isTest);
            }
//        }
    }
}
