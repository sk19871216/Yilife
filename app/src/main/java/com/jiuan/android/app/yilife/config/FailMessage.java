package com.jiuan.android.app.yilife.config;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.LoginNormal;
import com.jiuan.android.app.yilife.utils.ToastOnly;

/**
 * Created by Administrator on 2015/1/6.
 */
public class FailMessage {
    private static AlertDialog myDialog= null;

    private  static  AlertDialog.Builder builder=null;
    //未注册的手机号或登录动态码错误
    private static final String FAILURE_2011 = "2011";

    //服务器内部错误
    private static final String FAILURE_5000 = "5000";

    //刷新令牌错误
    private static final String FAILURE_2010_3 = "2010.3";

    //访问令牌错误
    private static final String FAILURE_2010_2 = "2010.2";
    private static final String FAILURE_2010_1 = "2010.1";
    //错误的重置密码验证码
    private static final String FAILURE_2009 = "2009";

    // 用户不存在或者密码错误
    private static final String FAILURE_2007 = "2007";
    // 手机号未被注册
    private static final String FAILURE_2008 = "2008";

    // 错误的手机注册验证码
    private static final String FAILURE_2006 = "2006";

    // 发送短信失败
    private static final String FAILURE_2005 = "2005";

    // 错误的手机号格式
    private static final String FAILURE_2004 = "2004";

    // 手机号被占用
    private static final String FAILURE_2003 = "2003";

    // Sc或Sv未授权
    private static final String FAILURE_2002 = "2002";

    // 请求缺少必须参数
    private static final String FAILURE_2001 = "2001";
    //反馈文字内容超长
    private static final String FAILURE_2012_1 = "2012.1";
    //图片大小超过范围
    private static final String FAILURE_2012_2 = "2012.2";
    //图片格式不支持
    private static final String FAILURE_2012_3 = "2012.3";
    //已经评论过app
    private static final String FAILURE_2016_1 = "2016.1";
    //评论app的标题或内容长度超出范围
    private static final String FAILURE_2016_2 = "2016.2";
    //评论app的评分超出范围
    private static final String FAILURE_2016_3 = "2016.3";
    //不存在的app
    private static final String FAILURE_2015 = "2015";
    //帖子标题或内容长度超出范围
    private static final String FAILURE_2013_1 = "2013.1";

    //帖子照片超出限定个数
    private static final String FAILURE_2013_2 = "2013.2";
    //主题帖不存在
    private static final String FAILURE_2014 = "2014";
    //无效的商品id
    private static final String FAILURE_2017_1 = "2017.1";
    //兑换商品库存不足
    private static final String FAILURE_2017_2 = "2017.2";
    //红包余额不足
    private static final String FAILURE_2017_3 = "2017.3";
    //该活动不存在
    private static final String FAILURE_2018 = "2018";
    //论坛版块不存在
    private static final String FAILURE_2019 = "2019";
    //注册失败
    private static final String FAILURE_2020 = "2020";
    //三方注册途径有误
    private static final String FAILURE_2021 = "2021";
    //保存三方登录信息错误
    private static final String FAILURE_2022_1 = "2022.1";
    //同步用户社区资料错误
    private static final String FAILURE_2022_2 = "2022.2";
    //同步用户商城资料错误
    private static final String FAILURE_2022_3 = "2022.3";
    //该三方用户已经存在绑定关系
    private static final String FAILURE_2023 = "2023";
        //解绑失败，用户不可切断所有绑定
    private static final String FAILURE_2024 = "2024";
       //错误的手机绑定验证码
    private static final String FAILURE_2025 = "2025";
    //账号已经存在手机号绑定
    private static final String FAILURE_2026 = "2026";
    //账号已经存在密码
    private static final String FAILURE_2027 = "2027";
    //操作类型错误
    private static final String FAILURE_2028 = "2028";
    //错误的邀请码
    private static final String FAILURE_2029 = "2029";
    //用户已经被邀请
    private static final String FAILURE_2030 = "2030";
    //不能填写自己的邀请码
    private static final String FAILURE_2031 = "2031";
    //该用户不是其1级推荐下线
    private static final String FAILURE_2032 = "2032";

    public static  void showfail(final Context context,String msg){
        ToastOnly toastOnly = new ToastOnly(context);
        if (msg.equals(FAILURE_2007)){
            toastOnly.toastShowShort("用户不存在或者密码错误");
        }else if(msg.equals(FAILURE_2006)){
//            toastOnly.toastShowShort("错误的手机注册验证码");
            toastOnly.toastShowShort("验证码错误");
        }else if(msg.equals(FAILURE_2008)){
            toastOnly.toastShowShort("手机号未被注册");
        }else if(msg.equals(FAILURE_2005)){
            toastOnly.toastShowShort("发送短信失败");
        }else if(msg.equals(FAILURE_2004)){
            toastOnly.toastShowShort("错误的手机号格式");
        }else if(msg.equals(FAILURE_2003)){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            myDialog = new AlertDialog.Builder(context).create();
            myDialog.show();
            WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
            params.width = width * 4 / 5;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            myDialog.getWindow().setAttributes(params);
            myDialog.getWindow().setContentView(R.layout.alert_resg);
            myDialog.getWindow()
                    .findViewById(R.id.gologin)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LoginFrom.setLoginfrom(9);
                            Intent broad = new Intent("" + 100);
                            context.sendBroadcast(broad);
                            Intent intent = new Intent(context, LoginNormal.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            myDialog.dismiss();
                        }
                    });
            myDialog.getWindow().findViewById(R.id.alert_close)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
//            toastOnly.toastShowShort("该手机号已注册 请直接登录");
        }else if(msg.equals(FAILURE_2002)){
            toastOnly.toastShowShort("Sc或Sv未授权");
        }else if(msg.equals(FAILURE_2001)){
            toastOnly.toastShowShort("请求缺少必须参数");
        }else if(msg.equals(FAILURE_2009)){
//            toastOnly.toastShowShort("错误的重置密码验证码");
            toastOnly.toastShowShort("验证码错误");
        }else if(msg.equals(FAILURE_5000)){
            toastOnly.toastShowShort("服务器内部错误");
        }else if(msg.equals(FAILURE_2010_3)){
            toastOnly.toastShowShort("刷新令牌错误");
        }else if(msg.equals(FAILURE_2011)){
            toastOnly.toastShowShort("未注册的手机号或登录动态码错误");
        }else if(msg.equals(FAILURE_2010_1)){
            Intent intent = new Intent("2010.1");
            context.sendBroadcast(intent);
//            toastOnly.toastShowShort("访问令牌错误");
//            if (builder==null) {
//                builder = new AlertDialog.Builder(context);
////                builder.setMessage("您的账号异常,否要重新登录")
//                builder.setMessage("您的账号异地登录")
//                        .setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent = new Intent(context, LoginNormal.class);
//                                LoginFrom.setLoginfrom(7);
//                                context.startActivity(intent);
//                                builder=null;
//                            }
//                        }).setCancelable(false).show();
////                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int id) {
////                                dialog.cancel();
////                                builder=null;
////                            }
////                        })
//
//                AlertDialog alert = builder.create();
//            }

        }else if(msg.equals(FAILURE_2010_2)){
//            toastOnly.toastShowShort("访问令牌过期");
//            if (builder==null) {
//                builder = new AlertDialog.Builder(context.getApplicationContext());
//                builder.setMessage("你的账号长时间未登录已过期")
//                        .setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent = new Intent(context, LoginNormal.class);
//                                LoginFrom.setLoginfrom(7);
//                                context.startActivity(intent);
//                                builder=null;
//                            }
//                        })
//                        .setCancelable(false).show();
//                AlertDialog alert = builder.create();
//            }
            Intent intent = new Intent("2010.2");
            context.sendBroadcast(intent);

        }else if(msg.equals(FAILURE_2012_1)){
            toastOnly.toastShowShort("文字内容超长");
        }else if(msg.equals(FAILURE_2012_2)){
            toastOnly.toastShowShort("图片大小超过范围");
        }else if(msg.equals(FAILURE_2012_3)){
            toastOnly.toastShowShort("图片格式不支持");
        }else if(msg.equals(FAILURE_2016_1)){
            toastOnly.toastShowShort("已经评论过app");
        }else if(msg.equals(FAILURE_2016_2)){
            toastOnly.toastShowShort("评论app的标题或内容长度超出范围");
        }else if(msg.equals(FAILURE_2016_3)){
            toastOnly.toastShowShort("评论app的评分超出范围");
        }else if(msg.equals(FAILURE_2015)){
            toastOnly.toastShowShort("不存在的app");
        }else if(msg.equals(FAILURE_2013_1)){
            toastOnly.toastShowShort("帖子标题或内容长度超出范围");
        }else if(msg.equals(FAILURE_2013_2)){
            toastOnly.toastShowShort("帖子照片超出限定个数");
        }else if(msg.equals(FAILURE_2014)){
            toastOnly.toastShowShort("主题帖不存在");
        }else if(msg.equals(FAILURE_2017_1)){
            toastOnly.toastShowShort("无效的商品id");
        }else if(msg.equals(FAILURE_2017_2)){
            toastOnly.toastShowShort("兑换商品库存不足");
        }else if(msg.equals(FAILURE_2017_3)){
            toastOnly.toastShowShort("红包余额不足");
        }else if(msg.equals(FAILURE_2018)){
            toastOnly.toastShowShort("该活动不存在");
        }else if(msg.equals(FAILURE_2019)){
            toastOnly.toastShowShort("论坛版块不存在");
        }else if(msg.equals(FAILURE_2020)){
            toastOnly.toastShowShort("注册失败");
        }else if(msg.equals(FAILURE_2021)){
            toastOnly.toastShowShort("三方注册途径有误");
        }else if(msg.equals(FAILURE_2022_1)){
            toastOnly.toastShowShort("保存三方登录信息错误");
        }else if(msg.equals(FAILURE_2022_2)){
            toastOnly.toastShowShort("同步用户社区资料错误");
        }else if(msg.equals(FAILURE_2022_3)){
            toastOnly.toastShowShort("同步用户商城资料错误");
        }else if(msg.equals(FAILURE_2023)){
            toastOnly.toastShowShort("该三方用户已经存在绑定关系");
        }else if(msg.equals(FAILURE_2024)){
            toastOnly.toastShowShort("解绑失败，用户不可切断所有绑定");
        }else if(msg.equals(FAILURE_2025)){
//            toastOnly.toastShowShort("错误的手机绑定验证码");
            toastOnly.toastShowShort("验证码错误");
        }else if(msg.equals(FAILURE_2026)){
            toastOnly.toastShowShort("账号已经存在手机号绑定");
        }else if(msg.equals(FAILURE_2027)){
            toastOnly.toastShowShort("账号已经存在密码");
        }else if(msg.equals(FAILURE_2028)){
            toastOnly.toastShowShort("操作类型错误");
        }else if(msg.equals(FAILURE_2029)){
            toastOnly.toastShowShort("错误的邀请码");
        }else if(msg.equals(FAILURE_2030)){
            toastOnly.toastShowShort("已建立关系，不可反复提交");
        }else if(msg.equals(FAILURE_2031)){
            toastOnly.toastShowShort("抱歉，不能与自己建立关系");
        }else if(msg.equals(FAILURE_2032)){
            toastOnly.toastShowShort("该用户不是其1级推荐下线");
        }

    }


}
