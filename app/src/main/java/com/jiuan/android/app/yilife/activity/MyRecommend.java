package com.jiuan.android.app.yilife.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.RecommendFristStepAdapter;
import com.jiuan.android.app.yilife.bean.recommend.RecommendClient;
import com.jiuan.android.app.yilife.bean.recommend.RecommendHandler;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsHandler;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsBean;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsResponse;
import com.jiuan.android.app.yilife.bean.recommend.RecommendResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyRecommend extends ParentActivity {
    private LinearLayout linearLayout_back ;
    private PullToRefreshListView pullToRefreshListView;
    private SharedPreferences sharedPreferences;
    private String un,tooken;
    private int count_step1,count_step2,count_step3;
    private ProgressDialog progressDialog;
    private ToastOnly toastOnly;
    private TextView tv_totle,tv_setp1,tv_step2,tv_step3,tv_title,tv_recommend_list;
    private ImageView iv_back,iv_totop;
    private ArrayList<RecommendReferralsBean> list;
    private RecommendFristStepAdapter adapter;
    private boolean dialogmissing =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("我的收益");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = new ArrayList<RecommendReferralsBean>();
        adapter = new RecommendFristStepAdapter(MyRecommend.this,list);

        toastOnly = new ToastOnly(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        sharedPreferences = getSharedPreferences("self", 0);
        un = sharedPreferences.getString("HGUID", "");
        tooken = sharedPreferences.getString("AccessToken","");
        RecommendClient.totaltebate(MyRecommend.this, un, tooken, new RecommendHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                FailMessage.showfail(MyRecommend.this, value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                FailMessage.showfail(MyRecommend.this, msg);
            }

            @Override
            public void onLoginSuccess(RecommendResponse response) {
                super.onLoginSuccess(response);
                DecimalFormat df = new DecimalFormat("##0.00");
                tv_totle.setText(df.format(response.getTotal()) + "元");
                tv_setp1.setText(df.format(response.getFromLevel1()) + "元");
                tv_step2.setText(df.format(response.getFromLevel2()) + "元");
                tv_step3.setText(df.format(response.getFromLevel3()) + "元");
                count_step1 = response.getLevel1Count();
                count_step2 = response.getLevel2Count();
                count_step3 = response.getLevel3Count();
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                toastOnly.toastShowShort("网络超时，请稍后重试");
            }
        }, TestOrNot.isTest);
        RecommendClient.directreferrals(MyRecommend.this, un, tooken, 10, 0, new RecommendReferralsHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                FailMessage.showfail(MyRecommend.this, value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                FailMessage.showfail(MyRecommend.this, msg);
            }

            @Override
            public void onLoginSuccess(RecommendReferralsResponse response) {
                super.onLoginSuccess(response);
                list.clear();
                for (int i = 0; i < response.getDirectReferrals().length; i++) {
                    list.add(response.getDirectReferrals()[i]);
                }
                adapter.notifyDataSetChanged();
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
//                if (list.size() == 0) {
//                    tv_recommend_list.setCompoundDrawables(null, null, null, null);
//                }
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                if (dialogmissing) {
                    progressDialog.dismiss();
                }else{
                    dialogmissing =true;
                }
                toastOnly.toastShowShort("网络超时，请稍后重试");
            }
        }, TestOrNot.isTest);
    }

    private void init(){
        iv_totop = (ImageView) findViewById(R.id.MyrecommendTotop);
        tv_recommend_list = (TextView) findViewById(R.id.first_recommender_list);
        tv_totle = (TextView) findViewById(R.id.recommend);
        tv_setp1 = (TextView) findViewById(R.id.recommend_setp1);
        tv_step2 = (TextView) findViewById(R.id.recommend_setp2);
        tv_step3 = (TextView) findViewById(R.id.recommend_setp3);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.recommend_pulltorefreshlistview);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.getRefreshableView().setFooterDividersEnabled(true);
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) {
                    iv_totop.setVisibility(View.VISIBLE);
                } else {
                    iv_totop.setVisibility(View.INVISIBLE);
                }
            }
        });
        iv_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullToRefreshListView.getRefreshableView().setSelection(0);
            }
        });
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyRecommend.this,FirstRecommendDetail.class);
                intent.putExtra("count_step1",count_step1);
                intent.putExtra("count_step2",count_step2);
                intent.putExtra("count_step3",count_step3);
                intent.putExtra("step2_money",tv_step2.getText().toString().trim());
                intent.putExtra("step3_money",tv_step3.getText().toString().trim());
                intent.putExtra("recommend_hguid",list.get(position-1).getHguid());
                startActivity(intent);
            }
        });
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                long lastts =0;
                if (list.size() != 0) {
                    lastts = list.get(list.size() - 1).getRegisterTS();
                }
                RecommendClient.directreferrals(MyRecommend.this, un, tooken, 10, lastts, new RecommendReferralsHandler() {
                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        FailMessage.showfail(MyRecommend.this, value);
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        FailMessage.showfail(MyRecommend.this, msg);
                    }

                    @Override
                    public void onLoginSuccess(RecommendReferralsResponse response) {
                        super.onLoginSuccess(response);
                        for (int i = 0; i < response.getDirectReferrals().length; i++) {
                            list.add(response.getDirectReferrals()[i]);
                        }
                        adapter.notifyDataSetChanged();
                        pullToRefreshListView.onRefreshComplete();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        toastOnly.toastShowShort("网络超时，请稍后重试");
                    }
                }, TestOrNot.isTest);
            }
        });

        linearLayout_back = (LinearLayout) findViewById(R.id.recommend_redback);
        linearLayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_godetail = new Intent(MyRecommend.this, RecommendDetail.class);
                intent_godetail.putExtra("total_money", tv_totle.getText().toString().trim());
                startActivity(intent_godetail);
            }
        });
    }

}
