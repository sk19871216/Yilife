package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
//import com.jiuan.android.app.yilife.adapter.AppsAdapter;
import com.jiuan.android.app.yilife.bean.geiallapps.AllappsBean;
import com.jiuan.android.app.yilife.bean.geiallapps.GetAllAppsClient;
import com.jiuan.android.app.yilife.bean.geiallapps.GetAllAppsHandler;
import com.jiuan.android.app.yilife.bean.geiallapps.GetAllAppsResponse;
import com.jiuan.android.app.yilife.bean.getapptoppic.GetAppTopPicClient;
import com.jiuan.android.app.yilife.bean.getapptoppic.GetAppTopPicHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Apps extends ParentActivity {
    private ListView listView;
    private ArrayList<String> namelist;
    private ArrayList<Integer> imageidlist;
    private AppsAdapter adapter;
    private ImageView imageView,iv_back;
    private ViewPager vp;
    private PagerAdapter adapter1;
    private ArrayList<ImageView> list;
    private ArrayList<AllappsBean> listbean;
    private ProgressDialog dialog;
    private Handler handler;
    private long id;
    private int position_click=-1;
    private GetAllAppsResponse getAllAppsResponse;
    private DownloadManager downloadManager;
    private SharedPreferences prefs;
    private static final String DL_ID = "-1";
    private String pkname,downloadname;
    private int status=-1,downposition=-1,versionCode=0;
    private TextView tv_title,tv_setting;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        queue = Volley.newRequestQueue(Apps.this);
//        Intent intent = new Intent();
//                    intent.setAction("android.intent.action.DOWNLOAD_COMPLETE");		//设置Action
//                    sendBroadcast(intent);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("应用");
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().clear().commit();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                if (msg.what==100){
//                    adapter1.notifyDataSetChanged();
                }else{
                    String statu = msg.getData().getString("status");
                    String path = msg.getData().getString("downpath");
                    if (statu.equals("")){
//                    position_click
                    }
                }
            }
        };
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        dialog.show();
        listbean = new ArrayList<AllappsBean>();
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = new ArrayList<ImageView>();
        listView = (ListView) findViewById(R.id.apps_listview);
//        namelist = new ArrayList<String>();
//        imageidlist = new ArrayList<Integer>();
//        namelist.add("宜生活");
//        namelist.add("爱家人");
//        imageidlist.add(R.drawable.icon_apps_yilife);
//        imageidlist.add(R.drawable.icon_apps_aijiaren);
//        adapter = new AppsAdapter(Apps.this,listbean);
        adapter = new AppsAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = listbean.get(position).getDownloadPath(); // web address
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        vp = (ViewPager) findViewById(R.id.vp_apps_main_vp);

        imageView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GetAppTopPicClient.requestLogin(Apps.this,new GetAppTopPicHandler(){
            @Override
            public void onLoginSuccess(String response) {
                super.onLoginSuccess(response);
                ImageRequest imageRequest = new ImageRequest(response,new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                        handler.sendEmptyMessage(100);

                    }
                },
                        0,
                        0,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                imageView.setImageResource(R.drawable.app_back_up);
                                handler.sendEmptyMessage(100);

                            }
                        });
                queue.add(imageRequest);
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                ImageRequest imageRequest = new ImageRequest("https://iemylifestorage.blob.core.chinacloudapi.cn/app/banner.png",new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
//                        BitmapDrawable bd=new BitmapDrawable(response);
                        imageView.setImageBitmap(response);
                        handler.sendEmptyMessage(100);
                    }
                },
                        0,
                        0,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                imageView.setImageResource(R.drawable.app_back_up);
                                handler.sendEmptyMessage(100);

                            }
                        });
                queue.add(imageRequest);
            }
        },TestOrNot.isTest);
        imageView.setImageResource(R.drawable.app_back_up);
        list.add(imageView);
        vp.addView(imageView);
        vp.setAdapter(adapter1);
        adapter1 = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return  list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(list.get(position));
            }
        };

        vp.setAdapter(adapter1);

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (NetWorkInfo.isNetworkAvailable(this)) {
            listbean.clear();
            GetAllAppsClient.requestLogin(Apps.this, new GetAllAppsHandler() {
                @Override
                public void onresponseSuccess(GetAllAppsResponse response) {
                    super.onresponseSuccess(response);
                    for (int i = 0; i < response.getDatas().length; i++) {
                        listbean.add(response.getDatas()[i]);
                        String pp = listbean.get(i).getLogoPath().replace("https","http");
                        listbean.get(i).setLogoPath(pp);
                        listbean.get(i).setIndex(-1);
                        listbean.get(i).setDownstats("下载");
                    }

                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(Apps.this, msg);
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(Apps.this, value);
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    Toast.makeText(Apps.this, "网络超时", Toast.LENGTH_SHORT).show();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            Toast.makeText(Apps.this, "请检查您的网络环境", Toast.LENGTH_SHORT).show();
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

    private boolean isAppInstalled(String uri,Context context) {

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
    class AppsAdapter extends BaseAdapter {


        public int getCount() {
            return listbean.size();
        }

        @Override
        public Object getItem(int position) {
            return listbean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                view = View.inflate(Apps.this, R.layout.item_apps, null);
                holder.text_count = (TextView) view.findViewById(R.id.tv_apps_count);
                holder.text = (TextView) view.findViewById(R.id.text_apps_title);
                holder.imageview_icon = (NetworkImageView) view.findViewById(R.id.item_apps_icon);
                holder.ratingBar = (RatingBar) view.findViewById(R.id.room_ratingbar);

                holder.download = (TextView) view.findViewById(R.id.textview_donwload);


                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.download.setText(listbean.get(position).getDownstats());
                Log.d("ddddd", "ddddd");
                if (isAppInstalled(listbean.get(position).getAppIdentitifier(), Apps.this)) {
                        holder.download.setText("打开");
                        listbean.get(position).setDownstats("打开");
                        String path =Environment.getExternalStorageDirectory() + "/download/" + listbean.get(position).getAppName() + ".apk";
                        File file =new File(path);
                        if (file.exists()) {
                            file.delete();
                        }
//                    }
                }
                else {
                        holder.download.setText("下载");
                        listbean.get(position).setDownstats("下载");
                }

            holder.ratingBar.setRating((float)(listbean.get(position).getRate()));
            holder.text.setText(listbean.get(position).getAppName());
            holder.text_count.setText(""+(position+1));
//            holder.imageview_icon.setDefaultImageResId(R.drawable.icon_apps_yilife);
//            RequestQueue queue = Volley.newRequestQueue(Apps.this);
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
            holder.imageview_icon.setImageUrl(listbean.get(position).getLogoPath(),loader);
            final ViewHolder finalHolder = holder;
            holder.download.setText("免费");
            return view;
        }
        class ViewHolder {
            TextView text_count,text,download;
            ImageView imageview_point;
            RatingBar ratingBar;
            NetworkImageView imageview_icon;
        }
    }


}
