package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.DuihuanDone.DuihuanDoneClient;
import com.jiuan.android.app.yilife.bean.DuihuanDone.DuihuanDoneHandler;
import com.jiuan.android.app.yilife.bean.DuihuanDone.DuihuanDoneResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class DuihuanDone extends ParentActivity {
    private TextView tv,tv_title;
    private ImageView iv_back;
    private String path="http://int.iemylife.com/mobile/policy";
    private  AlertDialog.Builder builder;
    private  AlertDialog dialog;
    private String phone,tooken,hguid;
    private ToastOnly toastOnly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duihuan_done);
        toastOnly = new ToastOnly(this);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("提交成功");
        tv = (TextView) findViewById(R.id.tv_yinsitiaokuan);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = mySharedPreferences.getString("phone","");
        tooken = mySharedPreferences.getString("AccessToken","");
        hguid = mySharedPreferences.getString("HGUID","");
        Intent intent =getIntent();
        int productid = intent.getIntExtra("productid",-1);
        if (NetWorkInfo.isNetworkAvailable(DuihuanDone.this)) {
            if (productid!=-1) {
                DuihuanDoneClient.requestLogin(DuihuanDone.this, hguid, tooken, productid, 1, new DuihuanDoneHandler() {
                    @Override
                    public void onLoginSuccess(DuihuanDoneResponse response) {
                        super.onLoginSuccess(response);
//                        dialog.dismiss();
//                        Intent intent = new Intent(DuihuanDone.this, DuihuanDone.class);
//                        startActivity(intent);
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        dialog.dismiss();
                        FailMessage.showfail(DuihuanDone.this, msg);
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
                        FailMessage.showfail(DuihuanDone.this, value);
                    }
                }, TestOrNot.isTest);
            }
//            else{
//                toastOnly.toastShowShort("hahaa");
//            }
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkInfo.isNetworkAvailable(DuihuanDone.this)) {
                    WebView webView;
                    ImageView iv;
                    LayoutInflater inflater = getLayoutInflater();
                    View view = LayoutInflater.from(DuihuanDone.this).inflate(R.layout.item_secret, null);
                    webView = (WebView) view.findViewById(R.id.webview_secort);
                    webView.loadUrl(path);
                    iv = (ImageView) view.findViewById(R.id.iv_back);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    builder = new AlertDialog.Builder(DuihuanDone.this);
                    dialog = builder.create();
                    dialog.setView(view);
                    dialog.show();
                }else{
                    dialog.dismiss();
                    ToastOnly toastOnly = new ToastOnly(DuihuanDone.this);
                    toastOnly.toastShowShort("请检查您的网络环境");
                }
            }
        });
    }

}
