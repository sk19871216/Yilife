package com.jiuan.android.app.yilife.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.MyDianPingAdapter;
import com.jiuan.android.app.yilife.bean.MyDianPing.MyDianPingClient;
import com.jiuan.android.app.yilife.bean.MyDianPing.MyDianPingHandler;
import com.jiuan.android.app.yilife.bean.MyDianPing.MyDianPingResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

public class MyDianPing extends ParentActivity {
    private TextView tv_title;
    private ImageView iv_back;
    private PullToRefreshListView listView;
    private ArrayList<MyDianPingResponse> list;
    private MyDianPingAdapter adapter;
    private String hguid,tooken;
    private ProgressDialog progressDialog;
    private ToastOnly toastOnly;
    private LinearLayout linearLayout_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dian_ping);
        toastOnly = new ToastOnly(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载...");
        list = new ArrayList<MyDianPingResponse>();
        adapter = new MyDianPingAdapter(list,MyDianPing.this);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("我的点评");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = (PullToRefreshListView) findViewById(R.id.lv_mydianpinglist);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        linearLayout_background = (LinearLayout) findViewById(R.id.linearlayout_background);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        SharedPreferences sharedPreferences  =getSharedPreferences("self",0);
        hguid = sharedPreferences.getString("HGUID","");
        tooken = sharedPreferences.getString("AccessToken","");
        MyDianPingClient.request(MyDianPing.this,hguid,tooken,new MyDianPingHandler(){
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                progressDialog.dismiss();
                FailMessage.showfail(MyDianPing.this,value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                progressDialog.dismiss();
                FailMessage.showfail(MyDianPing.this,msg);
            }

            @Override
            public void onLoginSuccess(MyDianPingResponse[] response) {
                super.onLoginSuccess(response);
                list.clear();
                if (response.length != 0){
                    linearLayout_background.setBackgroundColor(android.graphics.Color.parseColor("#f1f1f1"));
                    for (int i = 0; i < response.length; i++) {
                        list.add(response[i]);
                        if (list.size() == response.length) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    linearLayout_background.setBackgroundResource(R.drawable.nopinglun);
//                    toastOnly.toastShowShort("暂无数据 ");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                toastOnly.toastShowShort("网络超时");
                progressDialog.dismiss();
            }
        }, TestOrNot.isTest);
    }
}
