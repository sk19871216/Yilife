package com.jiuan.android.app.yilife.wxapi;


import com.google.gson.JsonElement;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.ShowFromWXActivity;
import com.jiuan.android.app.yilife.activity.BBSinfoActivity;
import com.jiuan.android.app.yilife.activity.BangDingOrNot;
import com.jiuan.android.app.yilife.activity.HTML5Activity;
import com.jiuan.android.app.yilife.activity.Huodong;
import com.jiuan.android.app.yilife.activity.MyBBsNote;
import com.jiuan.android.app.yilife.activity.MyRedbag;
import com.jiuan.android.app.yilife.activity.PingLun;
import com.jiuan.android.app.yilife.activity.SendNote;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessClient;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessHandler;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareClient;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareHandler;
import com.jiuan.android.app.yilife.bean.isredbagshare.IsRedbagShareResponse;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.config.WeiXinGet;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.VolleUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	
	private Button gotoBtn, regBtn, launchBtn, checkBtn;
	
	// IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
    private IWXAPI api;
    private SharedPreferences sharedPreferences;
	private String unicode;
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                ThreadLoginClient.verfityopenid(WXEntryActivity.this,unicode,new VerfityOpenIdHandler(){
                    @Override
                    public void onLoginSuccess(VerfityOpenIdResponse response) {
                        super.onLoginSuccess(response);
                        int isExist = response.getIsExist();
                        if (isExist==1){
                            SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mysharedPreferences.edit();
                            editor.putInt("isLogin", 1).commit();
                            SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor_self = sharedPreferences.edit();
                            editor_self.putString("AccessToken", response.getToken().getAccessToken());
                            editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                            editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                            editor_self.putString("HGUID", response.getHGUID());
                            editor_self.commit();
                            Log.d("结果123g",response.getHGUID());
                            Intent broad = new Intent("" + 100);
                            sendBroadcast(broad);
                            if (LoginFrom.getLoginfrom() == 0) {
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 1) {
                                Intent gointent = new Intent(WXEntryActivity.this, MyBBsNote.class);
                                startActivity(gointent);
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 2) {
                                Intent gointent = new Intent(WXEntryActivity.this, SendNote.class);
                                startActivity(gointent);
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 3) {
//                        Intent gointent = new Intent(LoginNormal.this,MyBBsNote.class);
//                        startActivityForResult(gointent, 5);
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 4) {
                                Intent gointent = new Intent(WXEntryActivity.this, PingLun.class);
                                startActivity(gointent);
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 5) {
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 6) {
                                Intent gointent = new Intent(WXEntryActivity.this, MyRedbag.class);
                                startActivity(gointent);
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 7) {
                                WXEntryActivity.this.finish();
                            } else if (LoginFrom.getLoginfrom() == 8) {
                                Intent gointent = new Intent(WXEntryActivity.this, BBSinfoActivity.class);
                                startActivity(gointent);
                                WXEntryActivity.this.finish();
                            }
//                            editor_self.putString("NikeName", response.getHGUID());
//                            editor_self.putString("HGUID", response.getHGUID());

                            WXEntryActivity.this.finish();


                        }else if(isExist == 0){
                            Intent intent = new Intent(WXEntryActivity.this, BangDingOrNot.class);
                            startActivity(intent);
                            WXEntryActivity.this.finish();
                        }
                    }

                    @Override
                    public void onInnovationFailure(String msg, JsonElement value) {
                        super.onInnovationFailure(msg, value);
                        Toast.makeText(WXEntryActivity.this,"网络不好，请稍后重试",Toast.LENGTH_SHORT).show();
                        WXEntryActivity.this.finish();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        Toast.makeText(WXEntryActivity.this,"网络不好，请稍后重试",Toast.LENGTH_SHORT).show();
                        WXEntryActivity.this.finish();            }
                },TestOrNot.isTest);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        
        // ͨ��WXAPIFactory��������ȡIWXAPI��ʵ��
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
//    	regBtn = (Button) findViewById(R.id.reg_btn);
//    	regBtn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// ����appע�ᵽ΢��
//			    api.registerApp(Constants.APP_ID);
//			}
//		});
//
//        gotoBtn = (Button) findViewById(R.id.goto_send_btn);
//        gotoBtn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
////		        startActivity(new Intent(WXEntryActivity.this, SendToWXActivity.class));
//		        finish();
//			}
//		});
//
//        launchBtn = (Button) findViewById(R.id.launch_wx_btn);
//        launchBtn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(WXEntryActivity.this, "launch result = " + api.openWXApp(), Toast.LENGTH_LONG).show();
//			}
//		});
//
//        checkBtn = (Button) findViewById(R.id.check_timeline_supported_btn);
//        checkBtn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				int wxSdkVersion = api.getWXAppSupportAPI();
//				if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
//					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline supported", Toast.LENGTH_LONG).show();
//				} else {
//					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline not supported", Toast.LENGTH_LONG).show();
//				}
//			}
//		});

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	// ΢�ŷ������󵽵�����Ӧ��ʱ����ص����÷���
	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			goToGetMsg();		
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}

	// ������Ӧ�÷��͵�΢�ŵ�����������Ӧ�������ص����÷���
	@Override
	public void onResp(BaseResp resp) {


//        SendAuth.Resp sendResp = (SendAuth.Resp) resp;

//        (SendAuth.Resp)resp;
		int result = 0;
		Log.e("分享","123");
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
            ScAndSv.issuccess = true;
            if (ScAndSv.WX.equals("login")) {
                SendAuth.Resp authresp = (SendAuth.Resp) resp;
                String code = authresp.code;
                Log.e("codeR", code);
                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                editor_self.putString("weixincode", code).commit();
                String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constants.APP_ID+"&secret="
                        +Constants.SERECT+"&code="+code+"&grant_type=authorization_code";
                if (!code.equals("")){
                    VolleUtils.initVolley(WXEntryActivity.this, path, new VolleUtils.ResultCallBack() {
                        public void result(JSONObject response) {
                            try {
                                Log.e("code_r", response + "");
                                String access_token = response.getString("access_token");
                                String refresh_token = response.getString("refresh_token");
                                String openid = response.getString("openid");
                                String expires_in = response.getString("expires_in");
                                String scope = response.getString("scope");
                                unicode = response.getString("unionid");
//                                unicode = "5584728304";
                                handler.sendEmptyMessage(1);
                                String path2 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
                                VolleUtils.initVolley(WXEntryActivity.this, path2, new VolleUtils.ResultCallBack() {
                                    @Override
                                    public void result(JSONObject response) {
                                        try {
                                            Log.e("code_n", response + "");
                                            String name = response.getString("nickname");
                                            WeiXinGet.nikename = response.getString("nickname");
                                            WeiXinGet.openid = response.getString("openid");
                                            WeiXinGet.gender = response.getInt("sex");
                                            WeiXinGet.avatar = response.getString("headimgurl");
                                            WeiXinGet.avatarHd = response.getString("headimgurl");
                                            WeiXinGet.unicode = response.getString("unionid");
                                            WeiXinGet.qq_openid = "";
                                            WeiXinGet.sina_uid = "";
                                            WeiXinGet.sourceType = 3;
                                            Log.e("code_na", name + "");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }else if(ScAndSv.WX.equals("bangding")) {
                SendAuth.Resp authresp = (SendAuth.Resp) resp;
                String code = authresp.code;

                String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APP_ID + "&secret="
                        + Constants.SERECT + "&code=" + code + "&grant_type=authorization_code";
                if (!code.equals("")) {
                    VolleUtils.initVolley(WXEntryActivity.this, path, new VolleUtils.ResultCallBack() {
                        public void result(JSONObject response) {
                            try {
                                Log.e("code_r", response + "");
                                String access_token = response.getString("access_token");
                                String refresh_token = response.getString("refresh_token");
                                String openid = response.getString("openid");
                                String expires_in = response.getString("expires_in");
                                String scope = response.getString("scope");
                                unicode = response.getString("unionid");
                                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("unicode", unicode).commit();
                                Intent broad = new Intent("" + 200);
                                sendBroadcast(broad);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }else if (ScAndSv.WX.equals("huodong")){
                ShareSuccessClient.request(WXEntryActivity.this,ScAndSv.hdid,2, new ShareSuccessHandler(){
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
                        ScAndSv.hdid=0;
                        ScAndSv.WX="";
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                    }
                },TestOrNot.isTest);
            }else{
                result = R.string.errcode_success;
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
            }
//            sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor_self = sharedPreferences.edit();
//            editor_self.putBoolean("isshared", true);
//            editor_self.commit();

			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:

			result = R.string.errcode_cancel;
            ScAndSv.issuccess = false;
//            sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor_self1 = sharedPreferences.edit();
//            editor_self1.putBoolean("isshared", false);
//            editor_self1.commit();
            changtonoshared();
            finish();
            if (ScAndSv.WX.equals("login")){
            }else if(ScAndSv.WX.equals("bangding")){
            }else if(ScAndSv.WX.equals("huodong")){
                SharedPreferences mysharedPreferences = getSharedPreferences("huodong", 0);
                int id = mysharedPreferences.getInt("huodongid",-1);
                ShareSuccessClient.request(WXEntryActivity.this,id , 2, new ShareSuccessHandler() {
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
                }, TestOrNot.isTest);
                ScAndSv.WX = "";
            }else{
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
            break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
            ScAndSv.issuccess = false;
//            sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor_self2 = sharedPreferences.edit();
//            editor_self2.putBoolean("isshared", false);
//            editor_self2.commit();
            changtonoshared();
            finish();
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            break;
		default:
            ScAndSv.issuccess = false;
			result = R.string.errcode_unknown;
//            sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor_self3 = sharedPreferences.edit();
//            editor_self3.putBoolean("isshared", false);
//            editor_self3.commit();
            changtonoshared();
            finish();
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            break;
		}
		
	}

    protected void changtonoshared(){
        SharedPreferences mysharedPreferenceslogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        String phone = mySharedPreferences.getString("phone","");
        String tooken = mySharedPreferences.getString("AccessToken","");
        if (mysharedPreferenceslogin.getInt("isLogin",-1)==1) {
            if (ScAndSv.place.equals("H5")) {
                IsRedbagShareClient.requestLogin(WXEntryActivity.this, phone, tooken, "GPRSBP",0, new IsRedbagShareHandler() {
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
        }
    }
	private void goToGetMsg() {
//		Intent intent = new Intent(this, GetFromWXActivity.class);
//		intent.putExtras(getIntent());
//		startActivity(intent);
		finish();
	}
	
	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		
		StringBuffer msg = new StringBuffer(); // ��֯һ������ʾ����Ϣ����
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		
		Intent intent = new Intent(this, ShowFromWXActivity.class);
		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
		startActivity(intent);
		finish();
	}
}