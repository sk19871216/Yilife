package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.PinglunAdapter2;
import com.jiuan.android.app.yilife.bean.getpinglunlist.GetpinglunlistClient;
import com.jiuan.android.app.yilife.bean.getpinglunlist.GetpinglunlistHandler;
import com.jiuan.android.app.yilife.bean.getpinglunlist.GetpinglunlistResponse;
import com.jiuan.android.app.yilife.bean.getpinglunlist.PinglunlistBean;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

public class PingLunList extends ParentActivity {
    private PullToRefreshListView lv;
    private String name;
    private long ts;
    private ToastOnly toastOnly;
    private ArrayList<PinglunlistBean> list;
    private PinglunAdapter2 adapter;
    private ProgressDialog dialog;
    private ImageView iv_back,tv_setting,iv_totop;
    private int leftsize=-1;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun_list);
        toastOnly = new ToastOnly(PingLunList.this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        iv_totop = (ImageView) findViewById(R.id.iv_totop_pinglunlist);
        iv_totop.getBackground().setAlpha(100);
        list = new ArrayList<PinglunlistBean>();
        lv = (PullToRefreshListView) findViewById(R.id.lv_pinlgun_list);
        iv_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.getRefreshableView().setSelection(0);
                iv_totop.setVisibility(View.INVISIBLE);
            }
        });
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        tv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        tv_setting.setImageResource(R.drawable.icon_edit);
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_title.setText("评论");
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter = new PinglunAdapter2(this,list);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) {
                    iv_totop.setVisibility(View.VISIBLE);
                }else{
                    iv_totop.setVisibility(View.INVISIBLE);
                }
//                if (leftcount<=0 && (firstVisibleItem+visibleItemCount) == totalItemCount){
                if (leftsize==0 && firstVisibleItem!=0 && (firstVisibleItem+visibleItemCount) == totalItemCount){
                    toastOnly.toastShowShort("暂无其他数据");
                }else{
                }
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetWorkInfo.isNetworkAvailable(PingLunList.this)) {
                    ts = list.get(list.size() - 1).getCreateTime();
                    GetpinglunlistClient.request(PingLunList.this, name, 10, ts, new GetpinglunlistHandler() {
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(PingLunList.this, msg);
                            lv.onRefreshComplete();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(PingLunList.this, value);
                            lv.onRefreshComplete();
                        }

                        @Override
                        public void onLoginSuccess(GetpinglunlistResponse response) {
                            super.onLoginSuccess(response);
                            leftsize = response.getLeftSize();
                            if (response.getLeftSize() != 0) {

                            } else {
                                lv.onRefreshComplete();
                                lv.setMode(PullToRefreshBase.Mode.DISABLED);
                            }
                            for (int i = 0; i < response.getBean().length; i++) {
                                list.add(response.getBean()[i]);
                            }
                            lv.onRefreshComplete();
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            lv.onRefreshComplete();
                            toastOnly.toastShowShort("网络超时");
                        }
                    }, TestOrNot.isTest);
                }else{
                    dialog.dismiss();
                    toastOnly.toastShowShort("请检查您的网络环境");
                }
            }
        });

    }
    public void upload() {
        SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
        if (mysharedPreferences.getInt("isLogin", -1) == 1){
            Intent intent = new Intent(PingLunList.this, PingLun.class);
            startActivity(intent);
        }else{
            Intent intent_login = new Intent(PingLunList.this,LoginNormal.class);
            LoginFrom.setLoginfrom(4);
            Toast.makeText(PingLunList.this, "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(intent_login);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        list.clear();
        Intent intent = getIntent();
        name = intent.getStringExtra("AppUniqueName");
        ts=0;
        if (NetWorkInfo.isNetworkAvailable(PingLunList.this)) {
            GetpinglunlistClient.request(PingLunList.this, name, 10, ts, new GetpinglunlistHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(PingLunList.this, msg);
                    dialog.dismiss();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(PingLunList.this, value);
                    dialog.dismiss();
                }

                @Override
                public void onLoginSuccess(GetpinglunlistResponse response) {
                    super.onLoginSuccess(response);
                    if (response.getBean().length != 0) {
                        leftsize = response.getLeftSize();
                        for (int i = 0; i < response.getBean().length; i++) {
                            list.add(response.getBean()[i]);
                            float a = (float) response.getBean()[i].getRate();
                        }
                        if (response.getLeftSize() == 0) {
                            lv.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                    } else {
                        toastOnly.toastShowShort("暂无数据");
                    }
                    adapter.notifyDataSetChanged();
                    lv.getRefreshableView().setSelection(0);
                    dialog.dismiss();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    toastOnly.toastShowShort("网络超时");
                    dialog.dismiss();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
}
