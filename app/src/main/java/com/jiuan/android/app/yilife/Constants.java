package com.jiuan.android.app.yilife;

public class Constants {
    //微信
	// APP_ID �滻Ϊ���Ӧ�ôӹٷ���վ���뵽�ĺϷ�appId
//    public static final String APP_ID = "wxeee2a7791ce72ed6";//测试
    public static final String APP_ID = "wxa9d2e19d1800f3d8";//体验站
//    public static final String APP_ID = "wx3c55557e76a79737";//VIP
//    public static final String APP_ID = "wxd4a6e13843f1903f";//正式

//    public static final String APP_ID = "wxd930ea5d5a258f4f";

    public static final String SERECT = "091efc3585ad6700e2b0c7226690c9e8";//体验站
//    public static final String SERECT = "60dc6dac61813d0c6c6ee2ed5f0a2f1d";//VIP
//    public static final String SERECT = "0e4ad3a3333c5909353a88ad1ee7a09b";//正式
//    public static final String SERECT = "";
//sina
    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String APP_KEY      = "1415677324";//测试
//    public static final String APP_KEY      = "1042894933";  //VIP
//    public static final String APP_KEY      = "3265655809"; //正式

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
//    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
}
