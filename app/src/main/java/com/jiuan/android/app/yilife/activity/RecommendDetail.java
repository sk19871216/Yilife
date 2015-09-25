package com.jiuan.android.app.yilife.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.RecommendDetailAdapter;
import com.jiuan.android.app.yilife.adapter.RecommendFristStepAdapter;
import com.jiuan.android.app.yilife.bean.recommend.RecommendClient;
import com.jiuan.android.app.yilife.bean.recommend.RecommendDetailBean;
import com.jiuan.android.app.yilife.bean.recommend.RecommendDetailHandler;
import com.jiuan.android.app.yilife.bean.recommend.RecommendDetailResponse;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsBean;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsHandler;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

public class RecommendDetail extends ParentActivity {
    private PullToRefreshListView listView;
    private TextView tv_total_money,tv_title,tv_recommend_list;
    private SharedPreferences sharedPreferences;
    private ArrayList<RecommendDetailBean> list;
    private ProgressDialog progressDialog;
    private String un,tooken,money;
    private ToastOnly toastOnly;
    private RecommendDetailAdapter adapter;
    private ImageView iv_back,iv_totop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("收益明细");
        iv_totop = (ImageView) findViewById(R.id.recommendTotop);

        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_recommend_list = (TextView) findViewById(R.id.recommend_list);
        tv_total_money = (TextView) findViewById(R.id.recommend_detail);
        init();
        Intent intent = getIntent();
        money = intent.getStringExtra("total_money");

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        sharedPreferences = getSharedPreferences("self", 0);
        un = sharedPreferences.getString("HGUID", "");
        tooken = sharedPreferences.getString("AccessToken","");
        tv_total_money.setText(money);
        RecommendClient.tebatedetail(RecommendDetail.this, un, tooken, 10, 0, new RecommendDetailHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                progressDialog.dismiss();
                FailMessage.showfail(RecommendDetail.this, value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                progressDialog.dismiss();
                FailMessage.showfail(RecommendDetail.this, msg);
            }

            @Override
            public void onLoginSuccess(RecommendDetailResponse response) {
                super.onLoginSuccess(response);
                list.clear();
                for (int i = 0; i < response.getTebates().length; i++) {
                    list.add(response.getTebates()[i]);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
//                if (list.size()==0){
//                    tv_recommend_list.setCompoundDrawables(null,null,null,null);
//                }
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
        list = new ArrayList<RecommendDetailBean>();
        adapter = new RecommendDetailAdapter(this,list);

        toastOnly = new ToastOnly(this);
        progressDialog = new ProgressDialog(this);
        listView = (PullToRefreshListView) findViewById(R.id.recommendDetail_pulltorefreshlistview);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.getRefreshableView().setFooterDividersEnabled(true);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                listView.getRefreshableView().setSelection(0);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                long ts = list.get(list.size()-1).getOrderTS();
                RecommendClient.tebatedetail(RecommendDetail.this, un, tooken, 10, ts, new RecommendDetailHandler() {
                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        FailMessage.showfail(RecommendDetail.this, value);
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        FailMessage.showfail(RecommendDetail.this, msg);
                    }

                    @Override
                    public void onLoginSuccess(RecommendDetailResponse response) {
                        super.onLoginSuccess(response);
                        for (int i = 0; i < response.getTebates().length; i++) {
                            list.add(response.getTebates()[i]);
                        }
                        adapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        toastOnly.toastShowShort("网络超时，请稍后重试");
                    }
                }, TestOrNot.isTest);
            }
        });
    }

}
