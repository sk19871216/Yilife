package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.MyRedbagAdapter;
import com.jiuan.android.app.yilife.bean.getredbag.GetRedBagListBean;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbagClient;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbagHandler;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbagResponse;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbaglistHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;

import java.util.ArrayList;

import static com.jiuan.android.app.yilife.R.id.lv_redbaglist;

public class MyRedbag extends ParentActivity {
    private TextView tv_money,tv_duihuan,tv_title,tv_setting;
    private ListView lv;
    private ImageView iv_back;
    private ProgressDialog dialog;
    private MyRedbagAdapter adapter;
    private String tooken,phone,hguid;
    private long ts=0;
    private double double_money=0;
    private ArrayList<GetRedBagListBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redbag);

        dialog= new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.show();
        list = new ArrayList<GetRedBagListBean>();
        adapter = new MyRedbagAdapter(this,list);
        tv_money = (TextView) findViewById(R.id.tv_allmoney);
        tv_duihuan = (TextView) findViewById(R.id.tv_duihuan);
        tv_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRedbag.this,Duihuan.class);
                intent.putExtra("money",double_money);
                startActivity(intent);
            }
        });
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("我的钱包");
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        lv = (ListView) findViewById(lv_redbaglist);
        lv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = mySharedPreferences.getString("phone","");
        tooken = mySharedPreferences.getString("AccessToken","");
        hguid = mySharedPreferences.getString("HGUID","");
        if (NetWorkInfo.isNetworkAvailable(MyRedbag.this)) {
            GetredbagClient.requestbalance(MyRedbag.this, hguid, tooken, new GetredbagHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(MyRedbag.this, msg);
                }

                @Override
                public void onLoginSuccess(double response) {
                    super.onLoginSuccess(response);
                    tv_money.setText(response + "");
                    double_money = response;
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(MyRedbag.this, value);
                }
            }, TestOrNot.isTest);
            GetredbagClient.requestlist(MyRedbag.this, hguid, tooken, ts, new GetredbaglistHandler() {
                @Override
                public void onLoginSuccess(GetredbagResponse response) {
                    super.onLoginSuccess(response);
                    dialog.dismiss();
                    list.clear();
                    if (response != null) {
                        for (int i = 0; i < response.getBeans().length; i++) {
                            list.add(response.getBeans()[i]);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(MyRedbag.this, msg);
                    dialog.dismiss();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(MyRedbag.this, value);
                    dialog.dismiss();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    Toast.makeText(MyRedbag.this, "网络超时", Toast.LENGTH_SHORT).show();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            Toast.makeText(MyRedbag.this,"请检查您的网络环境",Toast.LENGTH_SHORT).show();
        }
    }
}
