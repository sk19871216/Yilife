package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.DuihuanAdapter;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanBean1;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanClient;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanHandler;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanResponse;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbagClient;
import com.jiuan.android.app.yilife.bean.getredbag.GetredbagHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

public class Duihuan extends ParentActivity {
    private ListView lv;
    private Button button;
    private TextView tv_money,tv_phone,tv_setting,tv_title;
    private String phone,tooken,hguid;
    private double money;
    private ProgressDialog dialog;
    private ArrayList<DuihuanBean1> list;
    private DuihuanAdapter adapter;
    private int list_position=-1;
    private ImageView iv_back;
    private ToastOnly toastOnly = new ToastOnly(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuan);
        list = new ArrayList<DuihuanBean1>();

        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = mySharedPreferences.getString("phone","");
        tooken = mySharedPreferences.getString("AccessToken","");
        hguid = mySharedPreferences.getString("HGUID","");
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        Intent intent =getIntent();
        money = intent.getDoubleExtra("money",0);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("兑换");
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        lv = (ListView) findViewById(R.id.lv_duihuan);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position",""+position);

                    view.setBackgroundResource(R.drawable.icon_unclick);
                    list_position=position;
                    for (int i =0;i<list.size();i++) {
                        if (i==position) {
                            if (list.get(position).getPrice()>money){
                                toastOnly.toastShowShort("金额不足");
                                DuihuanAdapter.a=-1;
                            }else {
                                list.get(position).setP(position);
                            }
                        }else{
                            list.get(i).setP(-1);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

        });

        adapter = new DuihuanAdapter(this,list,lv,money);
        lv.setAdapter(adapter);

        tv_money = (TextView) findViewById(R.id.tv_duihuan_money);
        tv_money.setText(""+money+"元");
        tv_phone = (TextView) findViewById(R.id.tv_duihuan_phone);

        tv_phone.setText(phone);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        dialog.setCancelable(false);
        list.clear();
        if (NetWorkInfo.isNetworkAvailable(Duihuan.this)) {
            DuihuanClient.requestLogin(Duihuan.this, new DuihuanHandler() {
                @Override
                public void onLoginFailure(String msg) {
                    super.onLoginFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(Duihuan.this, msg);
                }

                @Override
                public void onLoginSuccess(DuihuanResponse response) {
                    super.onLoginSuccess(response);
//                    dialog.dismiss();
                    for (int i = 0; i < response.getDatas().length; i++) {
                        DuihuanBean1 duihuan = new DuihuanBean1();
                        duihuan.setProductID(response.getDatas()[i].getProductID());
                        duihuan.setDescription(response.getDatas()[i].getLogo());
                        duihuan.setLogo(response.getDatas()[i].getLogo());
                        duihuan.setName(response.getDatas()[i].getName());
                        duihuan.setPrice(response.getDatas()[i].getPrice());
                        duihuan.setP(-1);
                        list.add(duihuan);
                    }
                    adapter.notifyDataSetChanged();
                    getmoney();
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
                    FailMessage.showfail(Duihuan.this, value);
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }

    }
    protected void getmoney(){
        if (NetWorkInfo.isNetworkAvailable(Duihuan.this)) {
            GetredbagClient.requestbalance(Duihuan.this, hguid, tooken, new GetredbagHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(Duihuan.this, msg);
                    dialog.dismiss();
                }

                @Override
                public void onLoginSuccess(double response) {
                    super.onLoginSuccess(response);
                    tv_money.setText(response + "元");
                    money = response;
                    list.get(0).setMymoney(money);
                    dialog.dismiss();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(Duihuan.this, value);
                    dialog.dismiss();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            Toast.makeText(Duihuan.this, "请检查您的网络环境", Toast.LENGTH_SHORT).show();
        }
    }
}

