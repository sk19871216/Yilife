package com.jiuan.android.app.yilife.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.jiuan.android.app.yilife.activity.BBSinfoActivity;
import com.jiuan.android.app.yilife.activity.FriendRecommend;
import com.jiuan.android.app.yilife.activity.LoginNormal;
import com.jiuan.android.app.yilife.activity.MyBBsNote;
import com.jiuan.android.app.yilife.activity.MyDetail;
import com.jiuan.android.app.yilife.activity.MyDianPing;
import com.jiuan.android.app.yilife.activity.MyRecommend;
import com.jiuan.android.app.yilife.activity.MyRedbag;
import com.jiuan.android.app.yilife.activity.ResgActivity;
import com.jiuan.android.app.yilife.activity.Setting;
import com.jiuan.android.app.yilife.bean.GetLink.GetLinkClient;
import com.jiuan.android.app.yilife.bean.GetLink.GetLinkHandler;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoClient;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoHandler;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoResponse;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class WoFragment extends Fragment implements IWeiboHandler.Response{
    private Button button_zhuce,button_login;
    private RelativeLayout relativeLayout_login_before,relativeLayout_login_after,relativeLayout_fankui,relativeLayout_bbsinfo,relativeLayout_redbag,layout_version,relativeLayout_wolist,relativeLayout_touxiang_nicheng;
    private RelativeLayout relativeLayout_wo_setting,relativelayout_gerenxinxi,relativeLayout_aboutUs;
    private View.OnClickListener listener;
    private TextView tv_version,tv_name,tv_phone,tv_invitedcode;
    private ImageView iv_setting,iv_red_point,tv_touxiang,iv_myshoucang,iv_my_recommend,iv_myreply;
    private ToastOnly toastOnly;
    private Boolean isnew =false;
    private String url,downloadname,version;
    private DownloadManager downloadManager;
    private SharedPreferences prefs;
    private int status;
    private static final String DL_ID = "downloadId";
    private  static  AlertDialog.Builder builder=null;
    private RequestQueue queue;
    private ProgressDialog dialog;
    private long id1;
    private static Tencent mTencent;
    private final static String mAppid = ""+1104215150;//正式
//    private final static String mAppid = ""+1104698415;//vip
    private PopupWindow popupWindow;
    private LinearLayout layout_us,layout_sesseion,layout_timeline,layout_qq,layout_qqzone,layout_sina,layout_message,layout_email,layout_copylink;
    private IWXAPI api;
    private WXWebpageObject webpage;
    private WXMediaMessage msg;
    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    private SharedPreferences sharedPreferences;
    private String message = "更多创意产品等你体验，精彩活动、给力奖品正向你袭来！",
            title = "推荐一个创新产品应用平台",
            link_test="http://int.iemylife.com",
//            link_notest="http://www.iemylife.com"//正式
            link_notest="http://vip.iemylife.com"//vip
            ,link_total = "",
            link = "/mobile/shareapps?referrer=",
            finalLink="http://int.iemylife.com/mobile/shareapps?referrer=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_wo_new, container, false);
        tv_invitedcode = (TextView) view.findViewById(R.id.wo_invitedcode);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", 0);
        int islogin = sharedPreferences.getInt("isLogin", -1);
        if (islogin==1){
            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("self", 0);
            String invitationCode = mSharedPreferences.getString("InvitationCode","");
            if (TestOrNot.isTest){
                link_total = link_test+link+invitationCode;
            }else{
                link_total = link_notest+link+invitationCode;
            }
        }else{
            if (TestOrNot.isTest){
                link_total = link_test+link;
            }else{
                link_total = link_notest+link;
            }
        }

        mTencent = Tencent.createInstance(mAppid, getActivity());
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), Constants.APP_KEY);
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);

        toastOnly = new ToastOnly(getActivity());
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        queue = Volley.newRequestQueue(getActivity());
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Intent intent = new Intent();
                switch (id){
                    case R.id.linearlayout_session:
                        popupWindow.dismiss();
                        webpage.webpageUrl = finalLink;
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
                        webpage.webpageUrl = finalLink;
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
                        //vip
//                        toastOnly.toastShowShort("分享失败");
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  message);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, finalLink);
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://iemylifestorageint.blob.core.chinacloudapi.cn/app/logo/IEMyLife_logo.png");
                        mTencent.shareToQQ(getActivity(), params, new BaseUiListener());
                        break;
                    case R.id.linearlayout_qqzone_share:
                        Log.e("QQ","朋友圈");
                        popupWindow.dismiss();
                        final Bundle params1 = new Bundle();
                        params1.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                        params1.putString(QzoneShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                        params1.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
                        params1.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  finalLink);
                        params1.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                        params1.putString(QzoneShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                        mTencent.shareToQQ(getActivity(), params1, new BaseUiListener());
                        break;
                    case R.id.linearlayout_sina_share:
                        popupWindow.dismiss();
                        if (isAvilible(getActivity(),"com.sina.weibo")) {
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
                            mWeiboShareAPI.sendRequest(getActivity(), request);

                        }else{
                            Toast.makeText(getActivity(),"请下载新浪微博客户端",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.linearlayout_message_share:
                        popupWindow.dismiss();
                        Uri smsToUri = Uri.parse("smsto:");
                        Intent intentshare = new Intent(Intent.ACTION_SENDTO, smsToUri);
                        intentshare.putExtra("sms_body", message + finalLink);
                        startActivity(intentshare);
                        break;
                    case R.id.linearlayout_email_share:
                        popupWindow.dismiss();
                        Intent data=new Intent(Intent.ACTION_SENDTO);
                        data.setData(Uri.parse("mailto:"));
                        data.putExtra(Intent.EXTRA_SUBJECT, title);
                        data.putExtra(Intent.EXTRA_TEXT, message+finalLink);
//                        startActivity(data);
                        if (data.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(data);
                        }else{
                            toastOnly.toastShowShort("请先绑定一个邮箱");
                        }
                        break;
                    case R.id.linearlayout_copylink:
                        popupWindow.dismiss();
                        ClipboardManager clip = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.getText(); // 粘贴
                        clip.setText(finalLink); // 复制
                        Toast.makeText(getActivity(),"已复制到剪切板",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.login_before_denglu :
                        intent.setClass(getActivity(),LoginNormal.class);
                        LoginFrom.setLoginfrom(0);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.relativelayout_wo_list:
                        toastOnly.toastShowShort("功能暂未开放");
                        break;
                    case R.id.relativelayout_aboutUs:
//                        intent.setClass(getActivity(),AboutUs.class);
//                        getActivity().startActivity(intent);
                        SharedPreferences msharedPreferences= getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                        int isLogin = msharedPreferences.getInt("isLogin", 0);
                        if (isLogin==1) {
                            Intent intent_invitedcode = new Intent(getActivity(), FriendRecommend.class);
                            intent_invitedcode.putExtra("invitationcode",tv_invitedcode.getText().toString().trim());
                            getActivity().startActivity(intent_invitedcode);
                        }else{
                            creatpopwindow();
                        }
                        break;
                    case R.id.login_before_zhuce :
                        intent.setClass(getActivity(),ResgActivity.class);
//                        intent.putExtra("request","woFragment");
//                        LoginFrom.setLoginfrom(0);
                        getActivity().startActivity(intent);
                        break;
//                    case R.id.relativelayout_gerenxinxi:
//                        break;
                    case R.id.touxiang_nicheng :
                        intent.setClass(getActivity(),MyDetail.class);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.iv_wo_myreply :
                        intent.setClass(getActivity(),MyBBsNote.class);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.iv_wo_myrecommend :
                        intent.setClass(getActivity(),MyRecommend.class);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.relativelayout_wo_setting:

                        intent.setClass(getActivity(),Setting.class);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.iv_wo_myshoucang :
//                        toastOnly.toastShowShort("功能暂未开放");
                        SharedPreferences mysharedPreferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                        if (mysharedPreferences.getInt("isLogin",-1)==1) {
                            intent.setClass(getActivity(), MyRedbag.class);
                            getActivity().startActivity(intent);
                        }else{
                            intent.setClass(getActivity(), LoginNormal.class);
                            LoginFrom.setLoginfrom(6);
                            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                            getActivity().startActivity(intent);
                        }
                        break;
//                    case R.id.relativelayout_fankui:
//                        intent.setClass(getActivity(),Fankui.class);
//                        getActivity().startActivity(intent);
//                        break;
                    case R.id.relativelayout_bbs_info:
//                        intent.setClass(getActivity(),BBSinfoActivity.class);
//                        getActivity().startActivity(intent);
                        SharedPreferences mysharedPreferences1 = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                        if (mysharedPreferences1.getInt("isLogin",-1)==1) {
                            intent.setClass(getActivity(),BBSinfoActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            intent.setClass(getActivity(), LoginNormal.class);
                            LoginFrom.setLoginfrom(8);
                            Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                            getActivity().startActivity(intent);
                        }
                        break;
                    case R.id.relativelayout_redbag:
//                        SharedPreferences mysharedPreferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
//                        if (mysharedPreferences.getInt("isLogin",-1)==1) {
//                            intent.setClass(getActivity(), MyRedbag.class);
//                            getActivity().startActivity(intent);
//                        }else{
//                            intent.setClass(getActivity(), LoginNormal.class);
//                            LoginFrom.setLoginfrom(6);
//                            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//                            getActivity().startActivity(intent);
//                        }
                        break;
                    /*case R.id.relativelayout_version:
                        if (isnew){
                            builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("您是否要要下载新版本")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //开始下载
                                            if(!prefs.contains(DL_ID)) {
                                                if (url != null && !url.equals("")) {
                                                    Uri resource = Uri.parse(encodeGB(url));
                                                    DownloadManager.Request request = new DownloadManager.Request(resource);
                                                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                                                    request.setAllowedOverRoaming(false);
                                                    //在通知栏中显示
                                                    request.setShowRunningNotification(true);
                                                    request.setVisibleInDownloadsUi(true);
                                                    //sdcard的目录下的download文件夹
//                                            downloadname = response.getAppName();
                                                    request.setDestinationInExternalPublicDir("/download/", downloadname + ".apk");
                                                    request.setTitle(downloadname);
                                                      id1 = downloadManager.enqueue(request);
                                                    prefs.edit().putLong(DL_ID, id1).commit();
                                                }
                                            }else{
                                                queryDownloadStatus();
                                                if (status==DownloadManager.STATUS_SUCCESSFUL){
                                                    String path = Environment.getExternalStorageDirectory() + "/download/" + downloadname + ".apk";
                                                    Uri uri = Uri.fromFile(new File(path)); //这里是APK路径
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(uri,"application/vnd.android.package-archive");
                                                    startActivity(intent);
                                                    android.os.Process.killProcess(android.os.Process.myPid());
                                                }
                                            }
                                            getActivity().registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                            builder=null;
                                        }
                                    })
                                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            builder=null;
                                        }
                                    }).show();
                            AlertDialog alert = builder.create();
                        }
                        break;*/
                }
            }
        };
//        tv_version = (TextView) view.findViewById(R.id.tv_version);
//        getVersion();
//        iv_red_point = (ImageView) view.findViewById(R.id.iv_red_point);
        tv_touxiang = (ImageView) view.findViewById(R.id.image_mytouxiang);
        tv_phone = (TextView) view.findViewById(R.id.textview_phone_wo);
        sharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("name","").equals("")) {
            if (sharedPreferences.getString("phone", "").length()==11) {
                String pp = sharedPreferences.getString("phone", "").substring(0, 3) + "****" + sharedPreferences.getString("phone", "").substring(7, 11);
                tv_phone.setText(pp);
            }else{
                tv_phone.setText("");
            }
        }else{
            tv_phone.setText(sharedPreferences.getString("name", ""));
        }
        if (!sharedPreferences.getString("bitmap","").equals("")) {
            tv_touxiang.setImageBitmap(base64ToBitmap(sharedPreferences.getString("bitmap","")));
        }else{
            tv_touxiang.setImageResource(R.drawable.touxiang);
        }


        button_login = (Button) view.findViewById(R.id.login_before_denglu);
        button_login.setOnClickListener(listener);
        button_zhuce = (Button) view.findViewById(R.id.login_before_zhuce);
        button_zhuce.setOnClickListener(listener);
//        layout_version= (RelativeLayout) view.findViewById(R.id.relativelayout_version);
//        layout_version.setOnClickListener(listener);
//        iv_wo_myshoucang = (RelativeLayout) view.findViewById(R.id.relativelayout_redbag);
//        relativeLayout_redbag.setOnClickListener(listener);
//        relativeLayout_wolist = (RelativeLayout) view.findViewById(R.id.relativelayout_redbag);
//        relativeLayout_wolist.setOnClickListener(listener);
//        relativeLayout_fankui = (RelativeLayout) view.findViewById(R.id.relativelayout_fankui);
//        relativeLayout_fankui.setOnClickListener(listener);
        relativeLayout_wo_setting = (RelativeLayout) view.findViewById(R.id.relativelayout_wo_setting);
        relativeLayout_wo_setting.setOnClickListener(listener);
        relativeLayout_aboutUs = (RelativeLayout) view.findViewById(R.id.relativelayout_aboutUs);
        relativeLayout_aboutUs.setOnClickListener(listener);
        relativeLayout_bbsinfo = (RelativeLayout) view.findViewById(R.id.relativelayout_bbs_info);
        relativeLayout_bbsinfo.setOnClickListener(listener);
        relativeLayout_login_after = (RelativeLayout) view.findViewById(R.id.relativelayout_login_after);

        relativeLayout_touxiang_nicheng = (RelativeLayout) view.findViewById(R.id.touxiang_nicheng);
        relativeLayout_touxiang_nicheng.setOnClickListener(listener);
        relativeLayout_login_before = (RelativeLayout) view.findViewById(R.id.relativelayout_login_before);
        relativeLayout_login_before.setOnClickListener(listener);
//        relativelayout_gerenxinxi = (RelativeLayout) view.findViewById(R.id.relativelayout_gerenxinxi);
//        relativelayout_gerenxinxi.setOnClickListener(listener);
        iv_myshoucang = (ImageView) view.findViewById(R.id.iv_wo_myshoucang);
        iv_myshoucang.setOnClickListener(listener);

//        iv_my_dianping = (ImageView) view.findViewById(R.id.iv_wo_mydianping);
//        iv_my_dianping.setOnClickListener(listener);

        iv_myreply = (ImageView) view.findViewById(R.id.iv_wo_myreply);
        iv_myreply.setOnClickListener(listener);
        iv_my_recommend = (ImageView) view.findViewById(R.id.iv_wo_myrecommend);
        iv_my_recommend.setOnClickListener(listener);
//        tv_title = (TextView) getActivity().findViewById(R.id.actionbar_title);
//        tv_title.setText("我的");
        SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
        int islogin1 = mySharedPreferences.getInt("isLogin",-1);
        if (islogin1==1){
            relativeLayout_login_before.setVisibility(View.GONE);
            relativeLayout_login_after.setVisibility(View.VISIBLE);
            relativeLayout_login_after.setOnClickListener(listener);
        }else{
            relativeLayout_login_before.setVisibility(View.VISIBLE);
            relativeLayout_login_after.setVisibility(View.GONE);
        }
            return view;
        }

        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            getUserInfo();
    }

    public int getVersion(){
        // 拿到android 中包管理的服务
        PackageManager packageManager = getActivity().getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            version = info.versionName;
//            tv_version.setText(version);
            return info.versionCode;    //返回应用的版本号


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }
    public String encodeGB(String string)
    {
        //转换中文编码
        String split[] = string.split("/");
        for (int i = 1; i < split.length; i++) {
            try {
                split[i] = URLEncoder.encode(split[i], "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            split[0] = split[0]+"/"+split[i];
        }
        split[0] = split[0].replaceAll("\\+", "%20");//处理空格
        return split[0];
    }
    @Override
    public void onResume() {

        super.onResume();
        GetLinkClient.requestLogin(getActivity(), "",
                new GetLinkHandler() {
                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
//                            toastOnly.toastShowShort("更新数据失败");
                    }

                    @Override
                    public void onLoginSuccess(String response) {
                        super.onLoginSuccess(response);
                        Log.e("结果地址",response);
                            finalLink = response;
                    }
                }, TestOrNot.isTest);
        sharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("name","").equals("")) {
            if (sharedPreferences.getString("phone", "").length()==11) {
                String pp = sharedPreferences.getString("phone", "").substring(0, 3) + "****" + sharedPreferences.getString("phone", "").substring(7, 11);
                tv_phone.setText(pp);
            }else{
                tv_phone.setText("");
            }
        }else{
            tv_phone.setText(sharedPreferences.getString("name", ""));
        }
        if (!sharedPreferences.getString("bitmap","").equals("")) {
            tv_touxiang.setImageBitmap(base64ToBitmap(sharedPreferences.getString("bitmap","")));
        }else{
            tv_touxiang.setImageResource(R.drawable.touxiang);
        }
        TextView tv_title = (TextView) getActivity().findViewById(R.id.blue_icon_title);
//        RelativeLayout relativeLayout =(RelativeLayout) getActivity().findViewById(R.id.layout_blue_cion);
        SharedPreferences mysharedPreferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
        if (mysharedPreferences.getInt("isLogin",-1)==1) {
            getUserInfo();
            relativeLayout_login_before.setVisibility(View.GONE);
            relativeLayout_login_after.setVisibility(View.VISIBLE);
            relativeLayout_login_after.setOnClickListener(listener);
        }else{
            tv_invitedcode.setText("");
            relativeLayout_login_before.setVisibility(View.VISIBLE);
            relativeLayout_login_after.setVisibility(View.GONE);
        }

    }
    public void getUserInfo(){
        Log.e("结果222","aaaa");
        SharedPreferences msharedPreferences= getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
        int isLogin = msharedPreferences.getInt("isLogin", 0);
        if (isLogin==1) {

            sharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
            String tooken = sharedPreferences.getString("AccessToken", "");
            String phone = sharedPreferences.getString("phone", "");
            String hguid = sharedPreferences.getString("HGUID", "");
            if (sharedPreferences.getString("name","").equals("")) {
                if (sharedPreferences.getString("phone", "").length()==11) {
                    String pp = sharedPreferences.getString("phone", "").substring(0, 3) + "****" + sharedPreferences.getString("phone", "").substring(7, 11);
                    tv_phone.setText(pp);
                }else{
                    tv_phone.setText("");
                }
            }else{
                tv_phone.setText(sharedPreferences.getString("name", ""));
            }
            Log.d("结果phone",phone);
            Log.d("结果tooken",tooken);
            Log.d("结果hguid",hguid);

            GetuserinfoClient.requestLogin(getActivity(), hguid, tooken,
                    new GetuserinfoHandler() {
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
//                            toastOnly.toastShowShort("更新数据失败");
                        }

                        @Override
                        public void onLoginSuccess(GetuserinfoResponse response) {
                            super.onLoginSuccess(response);
                            if (response.getUserName().equals("")) {
                                if (response.getPhone().length() == 11){
                                    String pp = response.getPhone().substring(0, 3) + "****" + response.getPhone().substring(7, 11);
                                    tv_phone.setText(pp);
                                }else{
                                    tv_phone.setText("");
                                }
                            }else{
                                tv_phone.setText(response.getUserName());
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("InvitationCode",response.getInvitationCode());
                                editor_self.putString("name",response.getUserName()).commit();
                            }
                            tv_invitedcode.setText(response.getInvitationCode());
                            //得到网络图片
                            final String logopath = response.getLogo();
                            String localpath = sharedPreferences.getString("logopath","");
                            Log.e("结果pp1p",localpath);
                            if (!response.getLogo().equals("") && !localpath.equals(logopath)) {
                                Log.e("结果ppp","asdppp");
                                ImageRequest imageRequest = new ImageRequest(
                                        response.getLogo().replace("https", "http"),
//                                    "http://developer.android.com/images/home/aw_dac.png",
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                dialog.dismiss();
//                                            tv_touxiang.setImageBitmap(toRoundCorner(response,90.0f));
                                                tv_touxiang.setImageBitmap(response);
                                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                                editor_self.putString("logopath",logopath);
                                                editor_self.putString("bitmap", bitmapToBase64(response)).commit();
                                            }
                                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        tv_touxiang.setImageResource(R.drawable.touxiang);
                                        SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                        editor_self.putString("bitmap", "").commit();
                                    }
                                });
                                queue.add(imageRequest);
                            }else if (logopath.equals("")){
                                tv_touxiang.setImageResource(R.drawable.touxiang);
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("bitmap", "").commit();
                                editor_self.putString("logopath", "").commit();
                            }
                        }
                    }, TestOrNot.isTest);
        }else{
            tv_invitedcode.setText("");
        }
    }
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        System.out.println("图片是否变成圆角模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        System.out.println("pixels+++++++"+pixels);
        return output;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    private void creatpopwindow(){
        int[] location = new int[2];
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.lab);
        linearLayout.getLocationOnScreen(location);// 获得指定控件的坐标
        View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.pop_share_new, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //微信朋友
        layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
        layout_sesseion.setOnClickListener(listener);
        //微信朋友圈
        layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
        layout_timeline.setOnClickListener(listener);
        //QQ
        layout_qq = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqfriend_share);
        layout_qq.setOnClickListener(listener);
        //QQ空间
        layout_qqzone = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqzone_share);
        layout_qqzone.setOnClickListener(listener);
        //sina微博
        layout_sina = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_sina_share);
        layout_sina.setOnClickListener(listener);
        //短信
        layout_message = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_message_share);
        layout_message.setOnClickListener(listener);
        //邮件
        layout_email = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_email_share);
        layout_email.setOnClickListener(listener);
        //复制链接
        layout_copylink = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_copylink);
        layout_copylink.setOnClickListener(listener);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);

        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, location[0], location[1]+linearLayout.getHeight() - popupWindow.getHeight());
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:

                Toast.makeText(getActivity(),"成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(getActivity(),
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
//                Toast.makeText(getActivity(),"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
//                Toast.makeText(getActivity(),"返回为空",Toast.LENGTH_SHORT).show();
                return;
            }
//            Util.showResultDialog(LoginNormal.this, response.toString(), "登录成功");
//            Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(getActivity(),"onError:"+ "code:" + e.errorCode + ", msg:"
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
        mediaObject.actionUrl = finalLink;
//        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }



    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = message;
        return textObject;
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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
}
