package com.jiuan.android.app.yilife.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.constant.WBConstants;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AboutUs extends ParentActivity implements View.OnClickListener,IWeiboHandler.Response{
    private RelativeLayout layout_xuke,layout_pingjia,layout_share;
    private TextView tv_title,tv_fuwutiaokuan,tv_mianzeshengming,tv_version;
    private ImageView iv_back;
    private static Tencent mTencent;
    private final static String mAppid = ""+1104215150;
    private String uid,sina_tooken,version;
    private PopupWindow popupWindow;
    private LinearLayout layout_us,layout_sesseion,layout_timeline,layout_qq,layout_qqzone,layout_sina,layout_message,layout_email,layout_copylink;
    private IWXAPI api;
    private WXWebpageObject webpage;
    private WXMediaMessage msg;
    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    private Oauth2AccessToken mAccessToken;
    private String message = "更多创意产品等你体验，精彩活动、给力奖品正向你袭来！",
                    title = "推荐一个创新产品应用平台",
                    link_test="http://int.iemylife.com",link_notest="http://www.iemylife.com",link_total = "",
                    link = "/mobilepages/shareapps.aspx?referrer=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        SharedPreferences sharedPreferences = getSharedPreferences("login",0);
        int islogin = sharedPreferences.getInt("isLogin",-1);
        if (islogin==1){
            SharedPreferences mSharedPreferences = getSharedPreferences("self",0);
            String hguid = mSharedPreferences.getString("HGUID","");
            if (TestOrNot.isTest){
                link_total = link_test+link+hguid;
            }else{
                link_total = link_notest+link+hguid;
            }
        }else{
            if (TestOrNot.isTest){
                link_total = link_test+link;
            }else{
                link_total = link_notest+link;
            }
        }
        tv_version = (TextView) findViewById(R.id.aboutus_version);
        getVersion();
        tv_version.setText("宜生活  "+version);
        mTencent = Tencent.createInstance(mAppid, this);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        api = WXAPIFactory.createWXAPI(AboutUs.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);
        layout_us = (LinearLayout) findViewById(R.id.us_linearlayout);
        tv_fuwutiaokuan = (TextView) findViewById(R.id.tv_us_fuwutiaokuan);
        tv_fuwutiaokuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this,Text.class);
                intent.putExtra("from",2);
                startActivity(intent);
            }
        });
        tv_mianzeshengming = (TextView) findViewById(R.id.tv_us_mianzeshengming);
        tv_mianzeshengming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this,Text.class);
                intent.putExtra("from",3);
                startActivity(intent);
            }
        });
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("关于我们");
        layout_xuke = (RelativeLayout) findViewById(R.id.relativelayout_xieyi_us);
        layout_xuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this,Text.class);
                intent.putExtra("from",1);
                startActivity(intent);
            }
        });
        layout_pingjia = (RelativeLayout) findViewById(R.id.relativelayout_dafen_us);
        layout_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        layout_share = (RelativeLayout) findViewById(R.id.relativelayout_share_us);
//        layout_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                creatpopwindow();
////                PopShare.creatpopwindow(layout_us,"","","","",AboutUs.this);
//            }
//        });
    }
    private void creatpopwindow(){
        int[] location = new int[2];
        AboutUs.this.layout_us.getLocationOnScreen(location);// 获得指定控件的坐标
        View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_share_new, null,false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //微信朋友
        layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
        layout_sesseion.setOnClickListener(AboutUs.this);
        //微信朋友圈
        layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
        layout_timeline.setOnClickListener(AboutUs.this);
        //QQ
        layout_qq = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqfriend_share);
        layout_qq.setOnClickListener(AboutUs.this);
        //QQ空间
        layout_qqzone = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqzone_share);
        layout_qqzone.setOnClickListener(AboutUs.this);
        //sina微博
        layout_sina = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_sina_share);
        layout_sina.setOnClickListener(AboutUs.this);
        //短信
        layout_message = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_message_share);
        layout_message.setOnClickListener(AboutUs.this);
        //邮件
        layout_email = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_email_share);
        layout_email.setOnClickListener(AboutUs.this);
        //复制链接
        layout_copylink = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_copylink);
        layout_copylink.setOnClickListener(AboutUs.this);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);

        popupWindow.showAtLocation(layout_us, Gravity.NO_GRAVITY, location[0], location[1]+layout_us.getHeight() - popupWindow.getHeight());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.linearlayout_session:
                popupWindow.dismiss();
                webpage.webpageUrl = link_total;
                msg.description = message;
                msg.title = title;

//                msg.description = desc_s;
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                msg.thumbData = Util.bmpToByteArray(thumb, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();

                req.transaction = buildTransaction("webpage");
                req.message = msg;
                api.sendReq(req);
                break;
            case R.id.linearlayout_timeline:
                popupWindow.dismiss();
                webpage.webpageUrl = link_total;
                msg.title = title;
                msg.description = message;
               Bitmap thumb1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                msg.thumbData = Util.bmpToByteArray(thumb1, true);
                SendMessageToWX.Req req1 = new SendMessageToWX.Req();
                req1.transaction = buildTransaction("webpage");
                req1.message = msg;
                req1.scene =SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req1);
                break;
            case R.id.linearlayout_qqfriend_share:
                Log.e("QQ","朋友");
                popupWindow.dismiss();
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  message);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, link_total);
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "int.iemylife.com/mobilepages/shareapps.aspx?referrer=");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://iemylifestorageint.blob.core.chinacloudapi.cn/app/logo/IEMyLife_logo.png");
//                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                mTencent.shareToQQ(AboutUs.this, params, new BaseUiListener());
                break;
            case R.id.linearlayout_qqzone_share:
                Log.e("QQ","朋友圈");
                popupWindow.dismiss();
                final Bundle params1 = new Bundle();
                params1.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params1.putString(QzoneShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params1.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
                params1.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  link_total);
                params1.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params1.putString(QzoneShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                mTencent.shareToQQ(AboutUs.this, params1, new BaseUiListener());
                break;
            case R.id.linearlayout_sina_share:
                popupWindow.dismiss();
                if (isAvilible(AboutUs.this,"com.sina.weibo")) {
                    mWeiboShareAPI.registerApp();
                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                    weiboMessage.mediaObject = getWebpageObj();
                    weiboMessage.textObject = getTextObj();
                    SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                    // 用transaction唯一标识一个请求
                    request.transaction = String.valueOf(System.currentTimeMillis());
                    request.multiMessage = weiboMessage;
               /* WeiboMessage weiboMessage = new WeiboMessage();
                weiboMessage.mediaObject = getWebpageObj();

                // 2. 初始化从第三方到微博的消息请求
                SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
                // 用transaction唯一标识一个请求
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.message = weiboMessage;*/

                    // 3. 发送请求消息到微博，唤起微博分享界面
                    mWeiboShareAPI.sendRequest(AboutUs.this, request);

                }else{
                    Toast.makeText(AboutUs.this,"请下载新浪微博客户端",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.linearlayout_message_share:
                popupWindow.dismiss();
                Uri smsToUri = Uri.parse("smsto:");

                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

                intent.putExtra("sms_body", "我正在用@宜生活体验站，看看\n" +
                        "小伙伴的点评和推荐，在你使用\n" +
                        "创意产品还蛮管用的呦，哈哈！\n" +
                        "你也来加入我们吧："+link_total);

                startActivity(intent);
                break;
            case R.id.linearlayout_email_share:
                popupWindow.dismiss();
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:"));
                data.putExtra(Intent.EXTRA_SUBJECT, title);
                data.putExtra(Intent.EXTRA_TEXT, message+link_total);
                startActivity(data);

                break;
            case R.id.linearlayout_copylink:
                popupWindow.dismiss();
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clip.getText(); // 粘贴
                clip.setText(link_total); // 复制
                Toast.makeText(AboutUs.this,"已复制到剪切板",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:

                Toast.makeText(this,"成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this,
                        "失败" + "Error Message: " + baseResp.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
//                Toast.makeText(AboutUs.this,"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
//                Toast.makeText(AboutUs.this,"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
//            Util.showResultDialog(LoginNormal.this, response.toString(), "登录成功");
//            Toast.makeText(AboutUs.this,response.toString(),Toast.LENGTH_SHORT).show();
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }
        protected void doComplete(JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
//            showResult("onError:", "code:" + e.errorCode + ", msg:"
////                    + e.errorMessage + ", detail:" + e.errorDetail);
//            Toast.makeText(AboutUs.this,"onError:"+ "code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail,Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onCancel() {
//            showResult("onCancel", "");
        }
    }
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = message;

        // 设置 Bitmap 类型的图片到视频对象里
        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
        mediaObject.setThumbImage(bp);
//        mediaObject.actionUrl = "www.baidu.com";
        mediaObject.actionUrl = link_total;
//        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, AboutUs.this);
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
    public int getVersion(){
        // 拿到android 中包管理的服务
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
            version = info.versionName;
//            tv_version.setText(version);
            return info.versionCode;    //返回应用的版本号


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
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
}
