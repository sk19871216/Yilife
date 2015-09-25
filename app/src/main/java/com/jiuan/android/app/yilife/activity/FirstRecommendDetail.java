package com.jiuan.android.app.yilife.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.RecommendDetailAdapter;
import com.jiuan.android.app.yilife.bean.recommend.RecommendClient;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsHandler;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsResponse;
import com.jiuan.android.app.yilife.bean.recommend.ReferralsDetailHandler;
import com.jiuan.android.app.yilife.bean.recommend.ReferralsDetailResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class FirstRecommendDetail extends ParentActivity {
    private String step2_money,step3_money,un,tooken,recommend_hguid;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    private ToastOnly toastOnly;
    private int count_step2,count_step3;
    private ImageView imageView_icon,iv_back;
    private RequestQueue queue;
    private TextView tv_step2_count,tv_step3_count,tv_step2_money,tv_step3_money,tv_title,
            tv_name,tv_phone,tv_resg_ts,tv_straight_money,tv_from_step2,tv_from_step1;
    private RecommendDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_recommend_detail);
        imageView_icon = (ImageView) findViewById(R.id.firstrecommend_icon);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("获益信息");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        queue = Volley.newRequestQueue(FirstRecommendDetail.this);
        Intent intent = getIntent();
        step2_money = intent.getStringExtra("step2_money");
        step3_money = intent.getStringExtra("step3_money");
        count_step2 = intent.getIntExtra("count_step2", -1);
        count_step3 = intent.getIntExtra("count_step3", -1);
        recommend_hguid = intent.getStringExtra("recommend_hguid");
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        sharedPreferences = getSharedPreferences("self", 0);
        un = sharedPreferences.getString("HGUID", "");
        tooken = sharedPreferences.getString("AccessToken","");
        tv_step2_count.setText(count_step2 + "人");
        tv_step3_count.setText(count_step3 + "人");
        tv_step2_money.setText(step2_money);
        tv_step3_money.setText(step3_money);
        RecommendClient.referralsdetail(FirstRecommendDetail.this, un, tooken, recommend_hguid, new ReferralsDetailHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                progressDialog.dismiss();
                FailMessage.showfail(FirstRecommendDetail.this, value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                progressDialog.dismiss();
                FailMessage.showfail(FirstRecommendDetail.this, msg);
            }

            @Override
            public void onLoginSuccess(ReferralsDetailResponse response) {
                super.onLoginSuccess(response);
                if (response.getNickName().equals("")) {
                    tv_name.setText("昵称：" + "宜生活用户");
                } else {
                    tv_name.setText("昵称：" + response.getNickName());
                }
                if (!response.getMobile().equals("")) {
                    String phone_xxx = response.getMobile().substring(0, 3) + "****" + response.getMobile().substring(7, 11);
                    tv_phone.setText("用户名：" + phone_xxx);
                }
                if (response.getRegisterTS() != 0) {
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(response.getRegisterTS()));
                    tv_resg_ts.setText("注册时间：" + date.substring(0, 10) + "  " + date.substring(11, 16));
                } else {
                    tv_resg_ts.setText("注册时间：" + "未注册");
                }
                DecimalFormat df = new DecimalFormat("##0.00");
                tv_straight_money.setText(df.format(response.getDirectRebate()) + "元");
                tv_from_step1.setText(df.format(response.getLevel1Rebate()) + "元");
                tv_from_step2.setText(df.format(response.getLevel2Rebate()) + "元");
                if (!response.getPhotoUrl().equals("")) {
                    ImageRequest imageRequest = new ImageRequest(response.getPhotoUrl(), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView_icon.setImageBitmap(response);
                        }
                    },
                            0,
                            0,
                            Bitmap.Config.RGB_565,
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError arg0) {
                                    imageView_icon.setImageResource(R.drawable.touxiang);
                                }
                            });
                    queue.add(imageRequest);
                } else {
                    imageView_icon.setImageResource(R.drawable.touxiang);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                progressDialog.dismiss();
                toastOnly.toastShowShort("网络超时，请稍后重试");
            }
        }, TestOrNot.isTest);
    }

    private void init(){

        toastOnly = new ToastOnly(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");

        tv_step2_count = (TextView) findViewById(R.id.firstcommend_detail_setp2_count);
        tv_step3_count = (TextView) findViewById(R.id.firstcommend_detail_setp3_count);
        tv_step2_money = (TextView) findViewById(R.id.firstcommend_detail_setp2_money);
        tv_step3_money = (TextView) findViewById(R.id.firstcommend_detail_setp3_money);
        tv_name = (TextView) findViewById(R.id.firstrecommend_name);
        tv_phone = (TextView) findViewById(R.id.firstrecommend_user);
        tv_resg_ts = (TextView) findViewById(R.id.firstrecommend_resgtime);
        tv_straight_money = (TextView) findViewById(R.id.straight_money);
        tv_from_step1 = (TextView) findViewById(R.id.get_from_step1_money);
        tv_from_step2 = (TextView) findViewById(R.id.get_from_step2_money);
    }
}
