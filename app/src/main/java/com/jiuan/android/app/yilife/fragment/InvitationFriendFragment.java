package com.jiuan.android.app.yilife.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.jiuan.android.app.yilife.activity.BBSinfoActivity;
import com.jiuan.android.app.yilife.activity.LoginNormal;
import com.jiuan.android.app.yilife.activity.MyBBsNote;
import com.jiuan.android.app.yilife.activity.MyDetail;
import com.jiuan.android.app.yilife.activity.MyRecommend;
import com.jiuan.android.app.yilife.activity.MyRedbag;
import com.jiuan.android.app.yilife.activity.NoteList;
import com.jiuan.android.app.yilife.activity.ResgActivity;
import com.jiuan.android.app.yilife.activity.Setting;
import com.jiuan.android.app.yilife.bean.ForumsItem;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaClient;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaHandler;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaResponse;
import com.jiuan.android.app.yilife.bean.getForums.GetForumsClient;
import com.jiuan.android.app.yilife.bean.getForums.GetForumsHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/1/8.
 */
public class InvitationFriendFragment extends Fragment {
    private AlertDialog myDialog= null;
    private ProgressDialog dialog;
    private Bitmap bitmap_erweima;
    private PopupWindow popupWindow;
    private View.OnClickListener listener;
    private LinearLayout layout_us,layout_sesseion,layout_timeline,layout_qq,layout_erweima,layout_sina,layout_message,layout_email,layout_copylink;
    private RequestQueue queue;
    private SharedPreferences sharedPreferences;
    private ToastOnly toastOnly;
    private String un,tooken,code,str1="将您的邀请码",str2,str3 = "分享给好友，好友注册或输入您的邀请码，都会有奖励哦！";
    private TextView textView_code , textView_blue;
    public static boolean isInvited = false;

    private static Tencent mTencent;
    private final static String mAppid = ""+1104215150;//正式
    private IWXAPI api;
    private WXWebpageObject webpage;
    private WXMediaMessage msg;
    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    private String message = "更多创意产品等你体验，精彩活动、给力奖品正向你袭来！",
            title = "推荐一个创新产品应用平台"
            ,link_total = "http://www.iemylife.com/mobile/shareapps?referrer=";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitationfriend, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        queue = Volley.newRequestQueue(getActivity());
        toastOnly = new ToastOnly(getActivity());


        mTencent = Tencent.createInstance(mAppid, getActivity());
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), Constants.APP_KEY);
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        webpage = new WXWebpageObject();
        msg = new WXMediaMessage(webpage);

        textView_code = (TextView) view.findViewById(R.id.invitation_text2);
        textView_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clip = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clip.getText(); // 粘贴
                clip.setText(textView_code.getText().toString().trim()); // 复制
                toastOnly.toastShowShort("复制成功");
                return false;
            }
        });
        textView_blue = (TextView) view.findViewById(R.id.invitation_text3);


        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Intent intent = new Intent();
                switch (id){
                    case R.id.innivationfriend_erweima_session:
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
                    case R.id.innivationfriend_timeline:
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
                    case R.id.innivationfriend_qq:
                        Log.e("QQ", "朋友");
                        //vip
//                        toastOnly.toastShowShort("分享失败");
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  message);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, link_total);
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://iemylifestorageint.blob.core.chinacloudapi.cn/app/logo/IEMyLife_logo.png");
                        mTencent.shareToQQ(getActivity(), params, new BaseUiListener());
                        break;
                    case R.id.innivationfriend_sina:
                        if (isAvilible(getActivity(),"com.sina.weibo")) {
                            mWeiboShareAPI.registerApp();
                            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                            weiboMessage.mediaObject = getWebpageObj();
                            weiboMessage.textObject = getTextObj();
                            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                            // 用transaction唯一标识一个请求
                            request.transaction = String.valueOf(System.currentTimeMillis());
                            request.multiMessage = weiboMessage;
                            mWeiboShareAPI.sendRequest(getActivity(), request);
                        }else{
                            Toast.makeText(getActivity(),"请下载新浪微博客户端",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.innivationfriend_erweima:
                        creatalert();
                        break;
                }
            }
        };
        //微信朋友
        layout_sesseion = (LinearLayout) view.findViewById(R.id.innivationfriend_erweima_session);
        layout_sesseion.setOnClickListener(listener);
        //微信朋友圈
        layout_timeline = (LinearLayout) view.findViewById(R.id.innivationfriend_timeline);
        layout_timeline.setOnClickListener(listener);
        //QQ
        layout_qq = (LinearLayout) view.findViewById(R.id.innivationfriend_qq);
        layout_qq.setOnClickListener(listener);
        //QQ空间
        layout_erweima = (LinearLayout) view.findViewById(R.id.innivationfriend_erweima);
        layout_erweima.setOnClickListener(listener);
        //sina微博
        layout_sina = (LinearLayout) view.findViewById(R.id.innivationfriend_sina);
        layout_sina.setOnClickListener(listener);
        return view;
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    @Override
    public void onResume() {
        super.onResume();
        dialog.show();
        Intent intent = getActivity().getIntent();
        code = intent.getStringExtra("invitationcode");
        textView_code.setText(code);
        sharedPreferences = getActivity().getSharedPreferences("self", 0);
        un = sharedPreferences.getString("HGUID", "");
        tooken = sharedPreferences.getString("AccessToken", "");
        MyErWeiMaClient.request(getActivity(), un, tooken, new MyErWeiMaHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                dialog.dismiss();
                FailMessage.showfail(getActivity(), value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                dialog.dismiss();
                FailMessage.showfail(getActivity(), msg);
            }

            @Override
            public void onLoginSuccess(MyErWeiMaResponse response) {
                super.onLoginSuccess(response);
                dialog.dismiss();
                isInvited = response.isInvited();
                link_total  = response.getqRCodeContent();
//                code = response.getInvitationCode();
//                textView_code.setText(code);
//                String str = str1+code+str3;
//                SpannableStringBuilder style=new SpannableStringBuilder(str);
//                style.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor("#209df3")),6,12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                textView_blue.setText(style);
                ImageRequest imageRequest = new ImageRequest(
                        response.getqRCodeUrl(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                dialog.dismiss();
                                bitmap_erweima =response;
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                    }
                });
                queue.add(imageRequest);
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                dialog.dismiss();
                Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
            }
        }, TestOrNot.isTest);
    }
    private void creatalert(){
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        myDialog = new AlertDialog.Builder(getActivity()).create();
        myDialog.show();
        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
        params.width = width * 4 / 5;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        myDialog.getWindow().setAttributes(params);
        myDialog.getWindow().setContentView(R.layout.pop_share_witherweima);
        ImageView imageView_erweima = (ImageView) myDialog.getWindow()
                .findViewById(R.id.share_erweima);
        imageView_erweima.setImageBitmap(bitmap_erweima);

        myDialog.getWindow().findViewById(R.id.alert_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
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
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            doComplete((JSONObject)response);
        }
        protected void doComplete(JSONObject values) {
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
        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
        mediaObject.setThumbImage(bp);
//        mediaObject.actionUrl = "www.baidu.com";
        mediaObject.actionUrl = link_total;
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
}
