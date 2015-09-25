package com.jiuan.android.app.yilife.activity;

//import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.CheckVersion.CheckVersionClient;
import com.jiuan.android.app.yilife.bean.CheckVersion.CheckVersionHandler;
import com.jiuan.android.app.yilife.bean.CheckVersion.CheckVersionResponse;
import com.jiuan.android.app.yilife.fragment.FaxianNewFragment;
import com.jiuan.android.app.yilife.fragment.LifeFragment;
import com.jiuan.android.app.yilife.fragment.WoFragment;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.VolleUtils;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity implements IWeiboHandler.Response {
    private ViewPager vp;
    private LifeFragment lifeFragment;
    private FaxianNewFragment faxianFragment;
    private WoFragment woFragment;
    private ActionBar actionBar;
    private ArrayList<Fragment> list;
    private ImageView iv_life,iv_faxian,iv_wo,getIv_setting;
    private TextView tv_bar_title,tv_tab_yishenghuo,tv_tab_faxian,tv_tab_wode;
    private View.OnClickListener onClickListener;
    private FragmentPagerAdapter adapter;
    private ImageView iv_back,iv_setting;
    private RelativeLayout layout_yilife,layout_faxian,layout_wode;
    private boolean isredgab=false;
    private static Boolean isExit = false;
    private String path,hguid,tooken,volley_build;
    public static  int flag = -1;
    private FrameLayout frameLayout;
    private Handler handler;
    private IWXAPI api;
    private IWeiboShareAPI mWeiboShareAPI;
    private  static  AlertDialog.Builder builder=null;
    private String versionName,packageName,downloadpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = WXAPIFactory.createWXAPI(MainActivity.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        Log.e("asdsadas", "asdasdasd");
        frameLayout = (FrameLayout) findViewById(R.id.main_framlayout);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        onClick();//下方tab点击事件

        initialize();//初始化

        getVersion();
        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_bar_title.setText("宜生活");
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setVisibility(View.GONE);
        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setImageResource(R.drawable.icon_redbag_noshare);
        iv_setting.setVisibility(View.GONE);

//        list = new ArrayList<Fragment>();
        lifeFragment = new LifeFragment();
        faxianFragment = new FaxianNewFragment();
        woFragment = new WoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout, lifeFragment).commitAllowingStateLoss();
        flag = 0 ;
   /*     //测试
      VolleUtils.initVolley(this, "http://api.fir.im/apps/latest/55ca9e56748aac5428000043?api_token=5243f0c025c29f0da82cc8a6043fd8ca", new VolleUtils.ResultCallBack() {
        //VIP
//      VolleUtils.initVolley(this, "http://api.fir.im/apps/latest/55ca9e2e748aac51b300001b?api_token=5243f0c025c29f0da82cc8a6043fd8ca", new VolleUtils.ResultCallBack() {
        //正式
//        VolleUtils.initVolley(this, "http://api.fir.im/apps/latest/54bc78547d3dc3cf2c00020f?api_token=3f90c95ce9a5ca1f2d99eb49126a51ac", new VolleUtils.ResultCallBack() {
            @Override
            public void result(JSONObject response) {
                try {
                    volley_build = response.getString("build");
                    int build = getVersion();
                    if (build<Integer.parseInt(volley_build)){
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("您是否要要下载新版本")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //开始下载
                                        String test = "http://fir.im/yilifemytest";
                                        String pub = "http://fir.im/yilife";
                                        String vip = "http://fir.im/yilifeVip";
                                        String url= null;
                                        if (TestOrNot.isTest) {
                                            url = test; // web address 测试
                                        }else{
//                                            url = vip; //vip
                                            url = pub;//正式
                                        }
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        builder=null;
                                    }
                                }).setCancelable(false).show();
                        AlertDialog alert = builder.create();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
        CheckVersionClient.requestLogin(MainActivity.this, versionName.substring(0,5),packageName ,"", new CheckVersionHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);

            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);

            }

            @Override
            public void onLoginSuccess(CheckVersionResponse response) {
                super.onLoginSuccess(response);
                downloadpath = response.getDownLoadUrl();
                if (response.getIsCompulsoryUpgrade()==0){
                    VolleUtils.initVolley(MainActivity.this, "http://api.fir.im/apps/latest/55ca9e56748aac5428000043?api_token=5243f0c025c29f0da82cc8a6043fd8ca", new VolleUtils.ResultCallBack() {
                        //VIP
//      VolleUtils.initVolley(this, "http://api.fir.im/apps/latest/55ca9e2e748aac51b300001b?api_token=5243f0c025c29f0da82cc8a6043fd8ca", new VolleUtils.ResultCallBack() {
                        //正式
//        VolleUtils.initVolley(this, "http://api.fir.im/apps/latest/54bc78547d3dc3cf2c00020f?api_token=3f90c95ce9a5ca1f2d99eb49126a51ac", new VolleUtils.ResultCallBack() {
                        @Override
                        public void result(JSONObject response) {
                            try {
                                volley_build = response.getString("build");
                                int build = getVersion();
                                if (build<Integer.parseInt(volley_build)){
                                    builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("您是否要要下载新版本")
                                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //开始下载
                                                    String test = "http://fir.im/yilifemytest";
                                                    String pub = "http://fir.im/yilife";
                                                    String vip = "http://fir.im/yilifeVip";
                                                    String url= null;
                                                    if (TestOrNot.isTest) {
                                                        url = test; // web address 测试
                                                    }else{
//                                            url = vip; //vip
                                                        url = pub;//正式
                                                    }
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(Uri.parse(url));
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    builder=null;
                                                }
                                            }).setCancelable(false).show();
                                    AlertDialog alert = builder.create();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("您是否要要下载新版本")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
/*                                    //开始下载
                                    String test = "http://fir.im/yilifemytest";
                                    String pub = "http://fir.im/yilife";
                                    String vip = "http://fir.im/yilifeVip";
                                    String url= null;
                                    if (TestOrNot.isTest) {
                                        url = test; // web address 测试
                                    }else{
//                                            url = vip; //vip
                                        url = pub;//正式
                                    }*/
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(downloadpath));
                                    startActivity(intent);
                                }
                            })
//                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    builder=null;
//                                }
//                            })
                            .setCancelable(false).show();
                    AlertDialog alert = builder.create();
                }
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
            }
        }, TestOrNot.isTest);
    }
    public int getVersion(){
        // 拿到android 中包管理的服务
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
//            version = info.versionName;
//            tv_version.setText(version);
            versionName =  info.versionName;
            packageName = info.packageName;
            Log.e("结果",versionName+"++++++"+packageName);
            return info.versionCode;    //返回应用的版本号


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }
    private void  initialize(){

        layout_yilife = (RelativeLayout) findViewById(R.id.layout_tab_yishenghuo);
        layout_yilife.setOnClickListener(onClickListener);
        layout_faxian = (RelativeLayout) findViewById(R.id.layout_tab_faxian);
        layout_faxian.setOnClickListener(onClickListener);
        layout_wode = (RelativeLayout) findViewById(R.id.layout_tab_me);
        layout_wode.setOnClickListener(onClickListener);
        tv_tab_yishenghuo = (TextView) findViewById(R.id.iv_tab_yishenghuo);
        tv_tab_faxian = (TextView) findViewById(R.id.iv_tab_faxian);
        tv_tab_wode = (TextView) findViewById(R.id.iv_tab_wode);
        iv_life = (ImageView) findViewById(R.id.tab_yishenghuo);
        iv_life.setOnClickListener(onClickListener);
        iv_faxian = (ImageView) findViewById(R.id.tab_faxian);
        iv_faxian.setOnClickListener(onClickListener);
        iv_wo = (ImageView) findViewById(R.id.tab_wo);
        iv_wo.setOnClickListener(onClickListener);
//        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("asd",""+flag);
        if (flag==0){
            tv_bar_title.setText("宜生活");
            if (!lifeFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, lifeFragment).commitAllowingStateLoss();
            }else{
                getSupportFragmentManager().beginTransaction().show(lifeFragment).commitAllowingStateLoss();
            }
            if (faxianFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(faxianFragment).commitAllowingStateLoss();
            }
            if (woFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(woFragment).commitAllowingStateLoss();
            }
            iv_life.setImageResource(R.drawable.tab_iemylifed);
            iv_faxian.setImageResource(R.drawable.tab_discover);
            iv_wo.setImageResource(R.drawable.tab_me);
            tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#209df3"));
            tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
            tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
        }else if (flag==1){
            tv_bar_title.setText("发现");
            if (!faxianFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, faxianFragment).commitAllowingStateLoss();
            }else{
                getSupportFragmentManager().beginTransaction().show(faxianFragment).commitAllowingStateLoss();
            }
            if (lifeFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(lifeFragment).commitAllowingStateLoss();
            }
            if (woFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide( woFragment).commitAllowingStateLoss();
            }

            iv_life.setImageResource(R.drawable.tab_iemylife);
            iv_faxian.setImageResource(R.drawable.tab_discoverd);
            iv_wo.setImageResource(R.drawable.tab_me);
            tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
            tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#209df3"));
            tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
        }else if (flag ==2){
            SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
            if (mysharedPreferences.getInt("isLogin",-1)==1) {
                tv_bar_title.setText("我的");
            }else{
                tv_bar_title.setText("");
            }
            if (!woFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, woFragment).commitAllowingStateLoss();
            }else{
                getSupportFragmentManager().beginTransaction().show(woFragment).commitAllowingStateLoss();
            }
            if (faxianFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(faxianFragment).commitAllowingStateLoss();
            }
            if (lifeFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide( lifeFragment).commitAllowingStateLoss();
            }

            iv_life.setImageResource(R.drawable.tab_iemylife);
            iv_faxian.setImageResource(R.drawable.tab_discover);
            iv_wo.setImageResource(R.drawable.tab_med);
            tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
            tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
            tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#209df3"));
        }
    }

    private void onClick(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id){
                    case R.id.tab_yishenghuo :
                    case R.id.layout_tab_yishenghuo :
//                        vp.setCurrentItem(0);
                        tv_bar_title.setText("宜生活");
                        flag = 0;

                        if (!lifeFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, lifeFragment).commitAllowingStateLoss();
                        }else{
                            getSupportFragmentManager().beginTransaction().show(lifeFragment).commitAllowingStateLoss();
                        }
                        if (faxianFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide(faxianFragment).commitAllowingStateLoss();
                        }
                        if (woFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide(woFragment).commitAllowingStateLoss();
                        }
                        iv_life.setImageResource(R.drawable.tab_iemylifed);
                        iv_faxian.setImageResource(R.drawable.tab_discover);
                        iv_wo.setImageResource(R.drawable.tab_me);
                        tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#209df3"));
                        tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        break;
                    case R.id.tab_faxian :
                    case R.id.layout_tab_faxian :
                        flag=1;
                        tv_bar_title.setText("发现");
                        if (!faxianFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, faxianFragment).commitAllowingStateLoss();
                        }else{
                            getSupportFragmentManager().beginTransaction().show(faxianFragment).commitAllowingStateLoss();
                        }
                        if (lifeFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide(lifeFragment).commitAllowingStateLoss();
                        }
                        if (woFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide( woFragment).commitAllowingStateLoss();
                        }

                        iv_life.setImageResource(R.drawable.tab_iemylife);
                        iv_faxian.setImageResource(R.drawable.tab_discoverd);
                        iv_wo.setImageResource(R.drawable.tab_me);
                        tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#209df3"));
                        tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        break;
                    case R.id.tab_wo :
                    case R.id.layout_tab_me :
                        flag=2;
                        SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                        if (mysharedPreferences.getInt("isLogin",-1)==1) {
                            tv_bar_title.setText("我的");
                        }else{
                            tv_bar_title.setText("");
                        }
                        if (!woFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().add(R.id.main_framlayout, woFragment).commitAllowingStateLoss();
                        }else{
                            getSupportFragmentManager().beginTransaction().show(woFragment).commitAllowingStateLoss();
                        }
                        if (faxianFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide(faxianFragment).commitAllowingStateLoss();
                        }
                        if (lifeFragment.isAdded()) {
                            getSupportFragmentManager().beginTransaction().hide( lifeFragment).commitAllowingStateLoss();
                        }

                        iv_life.setImageResource(R.drawable.tab_iemylife);
                        iv_faxian.setImageResource(R.drawable.tab_discover);
                        iv_wo.setImageResource(R.drawable.tab_med);
                        tv_tab_yishenghuo.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        tv_tab_faxian.setTextColor(android.graphics.Color.parseColor("#BEBEBE"));
                        tv_tab_wode.setTextColor(android.graphics.Color.parseColor("#209df3"));
                        break;
                }
            }
        };
    }

    protected void alphaanimotion(ImageView iv){

        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        iv.setAnimation(animation);
    }

        /*
        按下返回键之后
        */
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                exitBy2Click(); //调用双击退出函数
             }  return false;
         }
        /*按一次将标志位置为true，同时开启一个线程，在2.5秒以后再次点击返回键，退出程序
        如果2.5秒内没有点击返回键，重置标志位*/
        private void  exitBy2Click() {
            Timer timer =null;
            if (!isExit){
                isExit =true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                },2500);
            }else{
//                finishFromChild(MainActivity.this);
                MainActivity.this.finish();
//                ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
//                am.restartPackage(getPackageName());
//                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
//                onBackPressed();
            }
        }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, MainActivity.this);
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
}
