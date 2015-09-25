package com.jiuan.android.app.yilife.config;

/**
 * Created by Administrator on 2015/1/27.
 */
public class LoginFrom {
    //loginfrom=0 从我的跳转到登录
    //loginfrom=1 从我的帖子跳转到登录
    //loginfrom=2 从发帖跳转到登录
    //loginfrom=3 从发表回复跳转到登录
    //loginfrom=4 从发表评论跳转到登录
    //loginfrom=5 从H5跳转到登录
    //loginfrom=6 从红包跳转到登录
    //loginfrom=7 访问令牌过期跳转到登录
    //loginfrom=8 社区资料跳转到登录
    //loginfrom=9 从已注册的alert跳转到登录页
    //
    private static  String huifu="";

    public static String getHuifu() {
        return huifu;
    }

    public static void setHuifu(String huifu) {
        LoginFrom.huifu = huifu;
    }

    private static  int loginfrom=0;

    public static int getLoginfrom() {
        return loginfrom;
    }

    public static void setLoginfrom(int loginfrom) {
        LoginFrom.loginfrom = loginfrom;
    }
}
