package com.jiuan.android.app.yilife.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getappdetail.GetAppsDetailClient;
import com.jiuan.android.app.yilife.bean.getappdetail.GetAppsDetailHandler;
import com.jiuan.android.app.yilife.bean.getappdetail.GetAppsDetailResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AppsDetail extends ParentActivity {
    private ProgressDialog dialog;
    private DownloadManager downloadManager;
    private SharedPreferences prefs;
    private static final String DL_ID = "downloadId";
    private ToastOnly toastOnly;
    private NetworkImageView iv_icon;
    private TextView apps_count,shortDescription,ver_detail,version_xx,pinglun_count,version,size,time,language,support,tv_title,tv_download;
    private RatingBar ratingBar,ratingBar1;
    private RelativeLayout relativeLayout;
    private long id;
    private ArrayList<String> list_imagepath;
    private String name,downloadname,pkname;
    private ImageView iv_back;
    private ArrayList<NetworkImageView> list_iv;
    private ArrayList<ImageView> list_iv_point;
    private ViewPager vp;
    private PagerAdapter adapter_vp;
    private LinearLayout linearLayout;
    private TextView tv_tool_title,tv_setting;
    private int status=0,versionCode=0;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_detail);
        queue = Volley.newRequestQueue(AppsDetail.this);
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_undervp);
        list_imagepath = new ArrayList<String>();
        list_iv =new ArrayList<NetworkImageView>();
        list_iv_point =new ArrayList<ImageView>();
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_gotopinglun);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentgoto = new Intent(AppsDetail.this,PingLunList.class);
                intentgoto.putExtra("AppUniqueName",name);
                startActivity(intentgoto);
            }
        });
        vp = (ViewPager) findViewById(R.id.vp_apps_main_vp);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (list_iv_point.size()!=0){
                    for (int i = 0;i<list_iv_point.size();i++) {
                        if (i==position) {
                            list_iv_point.get(i).setImageResource(R.drawable.page_now);
                        }else{
                            list_iv_point.get(i).setImageResource(R.drawable.page);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter_vp = new PagerAdapter() {
            @Override
            public int getCount() {
                return list_iv.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(list_iv.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list_iv.get(position));
                return list_iv.get(position);

            }
        };
        vp.setAdapter(adapter_vp);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_tool_title = (TextView) findViewById(R.id.blue_title);
        tv_tool_title.setText("详情");
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        iv_icon = (NetworkImageView) findViewById(R.id.item_apps_icon);
        tv_title = (TextView) findViewById(R.id.text_apps_title);
        tv_download = (TextView) findViewById(R.id.textview_donwload_detail);

        apps_count = (TextView) findViewById(R.id.tv_apps_detail_downloadcount);//下载数量
        shortDescription = (TextView) findViewById(R.id.tv_apps_detail_msg);//描述
        version_xx = (TextView) findViewById(R.id.tv_apps_detail_ver1);//版本XX的功能
        ver_detail = (TextView) findViewById(R.id.tv_apps_detail_ver_detail);//版本功能
        pinglun_count = (TextView) findViewById(R.id.tv_apps_detail_pingluncount);//评论数
        ratingBar = (RatingBar) findViewById(R.id.room_ratingbar_detail_goto);//星星数量
        ratingBar1 = (RatingBar) findViewById(R.id.room_ratingbar_detail);//上方星星数量
        version = (TextView) findViewById(R.id.tv_apps_detail_ver);//详细-版本
        size = (TextView) findViewById(R.id.tv_apps_detail_size);//详细-大小
        time = (TextView) findViewById(R.id.tv_apps_detail_time);//详细-时间
        language = (TextView) findViewById(R.id.tv_apps_detail_lag);//详细-语言
        support = (TextView) findViewById(R.id.tv_apps_detail_xitong);//详细-系统

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        toastOnly = new ToastOnly(this);
        dialog.show();
        Intent intent =getIntent();
        String iconpath = intent.getStringExtra("iconpath");
        name = intent.getStringExtra("AppUniqueName");
        ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache(

        ) {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }
        });
//        iv_icon.setDefaultImageResId(R.drawable.icon_apps_yilife);
        iv_icon.setImageUrl(iconpath,loader);
        if (NetWorkInfo.isNetworkAvailable(this)) {
            GetAppsDetailClient.requestLogin(AppsDetail.this, name, new GetAppsDetailHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(AppsDetail.this, msg);
                }

                @Override
                public void onresponseSuccess(final GetAppsDetailResponse response) {
                    super.onresponseSuccess(response);


                    if (response.getScreenshots().length != 0) {
                        vp.setVisibility(View.VISIBLE);
                        vp.removeAllViews();
                        linearLayout.removeAllViews();
                        linearLayout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.getScreenshots().length; i++) {
                            ImageView iv_point = new ImageView(AppsDetail.this);
                            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            iv_point.setPadding(7, 15, 7, 10);
                            iv_point.setLayoutParams(params1);
                            if (i == 0) {
                                iv_point.setImageResource(R.drawable.page_now);
                            } else {
                                iv_point.setImageResource(R.drawable.page);
                            }
                            linearLayout.addView(iv_point);
                            list_iv_point.add(iv_point);
                            NetworkImageView iv = new NetworkImageView(AppsDetail.this);
                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            iv.setLayoutParams(params);

                            ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache(

                            ) {
                                @Override
                                public Bitmap getBitmap(String url) {
                                    return null;
                                }

                                @Override
                                public void putBitmap(String url, Bitmap bitmap) {
                                }
                            });
                            iv.setImageUrl(response.getScreenshots()[i], loader);
                            list_iv.add(iv);
                            adapter_vp.notifyDataSetChanged();
                        }
                    } else {
                        vp.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    toastOnly.toastShowShort("网络超时");
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(AppsDetail.this, value);
                }

            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
    private void getinfo(){
        if (NetWorkInfo.isNetworkAvailable(this)) {
            GetAppsDetailClient.requestLogin(AppsDetail.this, name, new GetAppsDetailHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(AppsDetail.this, msg);
                }

                @Override
                public void onresponseSuccess(final GetAppsDetailResponse response) {
                    super.onresponseSuccess(response);

                    Log.d("结果app", response.getAppIdentifier());

//                    if (isAppInstalled("com.jiuan.oa.android.app.wms",AppsDetail.this)) {
                    if (isAppInstalled(response.getAppIdentifier(), AppsDetail.this)) {
//                        if (versionCode < Integer.parseInt(response.getVersionname())) {
//                            if (versionCode < 100) {
//                                tv_download.setText("更新");
//                        } else {
                            tv_download.setText("打开");

//                        }
                    } else {
//                            pkname = response.getAppIdentifier();
//                            String path = Environment.getExternalStorageDirectory() + "/download/" + response.getAppName() + ".apk";
//                            File file = new File(path);
//                            if (file.exists()) {
//                                Log.d("ddddd存在", "ddddd");
//                                downloadManager.remove(prefs.getLong(DL_ID, 0));
//                                prefs.edit().clear().commit();
////                        if (isAppInstalled(response.getAppIdentifier())) {
////                        if (isAppInstalled("com.jiuan.oa.android.app.wms",AppsDetail.this)) {
////                            tv_download.setText("打开");
////                            Log.d("ddddd打开","ddddd");
////                        } else {
//                                tv_download.setText("安装");
//                                Log.d("ddddd安装", "ddddd");
//                            } else {
//                                Log.d("ddddd下载", "ddddd");
                        tv_download.setText("下载");
//                            }
                    }
//                    }
                    tv_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            if (!prefs.contains(DL_ID)) {
 /*                               if (tv_download.getText().toString().trim().equals("安装")) {
                                    String path = Environment.getExternalStorageDirectory() + "/download/" + response.getAppName() + ".apk";
                                    Uri uri = Uri.fromFile(new File(path)); //这里是APK路径
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                                    startActivity(intent);*/
//                                android.os.Process.killProcess(android.os.Process.myPid());
                            if (tv_download.getText().toString().trim().equals("打开")) {
                                openOtherApp(response.getAppIdentifier(), AppsDetail.this);
//                                openOtherApp("com.jiuan.oa.android.app.wms",AppsDetail.this);
                            } else if (tv_download.getText().toString().trim().equals("下载")) {
                                String url = response.getDownloadUrl(); // web address
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                                  /*   tv_download.setText("下载中");

                                    String url = response.getDownloadUrl();
                                    //开始下载
                                    if (url != null && !url.equals("")) {
                                        Uri resource = Uri.parse(encodeGB(url));
                                        DownloadManager.Request request = new DownloadManager.Request(resource);
                                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                                        request.setAllowedOverRoaming(false);
                                        //在通知栏中显示
                                        request.setShowRunningNotification(true);
                                        request.setVisibleInDownloadsUi(true);
                                        //sdcard的目录下的download文件夹
                                        downloadname = response.getAppName() + ".apk";
                                        request.setDestinationInExternalPublicDir("/download/", response.getAppName() + ".apk");
                                        request.setTitle(response.getAppName());
                                        id = downloadManager.enqueue(request);
                                        //保存id
                                        prefs.edit().putLong(DL_ID, id).commit();*/
//                            }
//                                }
/*                            } else {
                                //下载已经开始，检查状态
                                queryDownloadStatus();
                                if (status == DownloadManager.STATUS_RUNNING) {
                                    downloadManager.remove(prefs.getLong(DL_ID, 0));
                                    tv_download.setText("下载");
                                    prefs.edit().clear().commit();
                                }*/
//                        }
//                            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                    }
                }

                );
//                    if (response.getScreenshots().length != 0) {
//                        vp.setVisibility(View.VISIBLE);
//                        vp.removeAllViews();
//                        linearLayout.removeAllViews();
//                        linearLayout.setVisibility(View.VISIBLE);
//                        for (int i = 0; i < response.getScreenshots().length; i++) {
//                            ImageView iv_point = new ImageView(AppsDetail.this);
//                            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            iv_point.setPadding(7, 15, 7, 10);
//                            iv_point.setLayoutParams(params1);
//                            if (i == 0) {
//                                iv_point.setImageResource(R.drawable.page_now);
//                            } else {
//                                iv_point.setImageResource(R.drawable.page);
//                            }
//                            linearLayout.addView(iv_point);
//                            list_iv_point.add(iv_point);
//                            NetworkImageView iv = new NetworkImageView(AppsDetail.this);
//                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            iv.setLayoutParams(params);
//
//                            ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache(
//
//                            ) {
//                                @Override
//                                public Bitmap getBitmap(String url) {
//                                    return null;
//                                }
//
//                                @Override
//                                public void putBitmap(String url, Bitmap bitmap) {
//                                }
//                            });
//                            iv.setImageUrl(response.getScreenshots()[i], loader);
//                            list_iv.add(iv);
//                            adapter_vp.notifyDataSetChanged();
//                        }
//                    } else {
//                        vp.setVisibility(View.GONE);
//                        linearLayout.setVisibility(View.GONE);
//                    }
                Log.d("apps",response.toString());
                tv_title.setText(""+response.getAppName());
                apps_count.setText(""+response.getDownloadCounter()+"次下载");
                shortDescription.setText(response.getShortDescription());
                version_xx.setText("版本"+response.getVersion()+"的功能");
                ver_detail.setText(response.getFullDescription().

                replace("<br>","\n")

                );
                pinglun_count.setText(response.getComments()+"条评论");
                version.setText(response.getVersion());
                size.setText(response.getSize());
                String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(response.getPublishTS()));
                time.setText(date);
                language.setText(response.getLanguage());
                support.setText("\u2000\u2000\u2000\u2000\u2000\u2000\u2000\u2000\u2000\u2000\u2000"+response.getSupporting());
                ratingBar.setRating((float)response.getRate());
                ratingBar1.setRating((float)response.getRate());
                for(
                int i = 0;
                i<response.getScreenshots().length;i++)

                {
                    list_imagepath.add(response.getScreenshots()[i]);
                }

                dialog.dismiss();

            }

            @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    toastOnly.toastShowShort("网络超时");
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(AppsDetail.this, value);
                }

            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
    /**
     * 如果服务器不支持中文路径的情况下需要转换url的编码。
     * @param string
     * @return
     */
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
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Log.v("intent", ""+intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
            queryDownloadStatus();
        }
    };

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = downloadManager.query(query);
        if(c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch(status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("down", "STATUS_PAUSED");
                    tv_download.setText("暂停");
                    break;
                case DownloadManager.STATUS_PENDING:
                    Log.v("down", "STATUS_PENDING");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("down", "STATUS_RUNNING");
                    tv_download.setText("下载中");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
//                    Log.v("down", "下载完成");
//                    tv_download.setText("完成");
                    prefs.edit().clear().commit();
                    Cursor c1 = downloadManager.query(new DownloadManager.Query().setFilterById(id));
                    if (c1.moveToFirst()) {

                        String file = c1.getString(c1.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        Uri uri = Uri.fromFile(new File(file)); //这里是APK路径
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setDataAndType(uri, "application/vnd.android.package-archive");
                        startActivity(intent1);
                    }
//                    String path = Environment.getExternalStorageDirectory()+"/download/"+downloadname;
//                    File file = new File(path) ;
//                    if(file.exists())
//                    {
//                        downloadManager.remove(prefs.getLong(DL_ID, 0));
//                        prefs.edit().clear().commit();
//                        tv_download.setText("安装");
//                        Uri uri = Uri.fromFile(new File(path)); //这里是APK路径
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(uri,"application/vnd.android.package-archive");
//                        startActivity(intent);
////                        android.os.Process.killProcess(android.os.Process.myPid());
////存在
//
//                        //判断是否安装
////                        openOtherApp(pkname,AppsDetail.this);
//                        if (isAppInstalled(pkname,AppsDetail.this)){
//                            tv_download.setText("打开");
//                        }
//                    }else{
//                        tv_download.setText("下载");
//                    }
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("down", "STATUS_FAILED");
                    tv_download.setText("下载");
                    downloadManager.remove(prefs.getLong(DL_ID, 0));
                    prefs.edit().clear().commit();
                    break;
            }
        }
    }
//    public boolean isinstall(String apppackage){
//        if (apppackage == null || "".equals(apppackage))
//            return false;
//            try {
//                ApplicationInfo info = AppsDetail.this.getPackageManager()
//                        .getApplicationInfo(apppackage,
//                                PackageManager.GET_UNINSTALLED_PACKAGES);
//                return  true;
//            } catch (PackageManager.NameNotFoundException e) {
//                return false;
//            }
//    }
    private boolean isAppInstalled(String uri,Context context) {
//        PackageManager pm = getPackageManager();
//        boolean installed =false;
//        try {
//            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
//            installed =true;
//        } catch(PackageManager.NameNotFoundException e) {
//            installed =false;
//        }
        Log.d("结果123","asd");
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;
        boolean installed =false;
        try {

            pi = packageManager.getPackageInfo(uri, 0);
            versionCode = pi.versionCode;
            installed =true;
        } catch (PackageManager.NameNotFoundException e) {
            installed =false;
            Log.d("结果no","asd");
        }
        return installed;
    }
    public  void openOtherApp(String packageName,Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;

        try {

            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("android.intent.action.DOWNLOAD_COMPLETE")){
//                toastOnly.toastShowShort("123");
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getinfo();
    }
}
