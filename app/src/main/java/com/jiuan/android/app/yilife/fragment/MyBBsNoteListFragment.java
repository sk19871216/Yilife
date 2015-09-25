package com.jiuan.android.app.yilife.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.ShowNote;
import com.jiuan.android.app.yilife.adapter.MyBBsNoteListAdapter;
import com.jiuan.android.app.yilife.bean.MyBBsNoteBean;
import com.jiuan.android.app.yilife.bean.mybbsnote.MyBBSNoteClient;
import com.jiuan.android.app.yilife.bean.mybbsnote.MyBBSNoteHandler;
import com.jiuan.android.app.yilife.bean.mybbsnote.MyBBSNoteResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;


public class MyBBsNoteListFragment extends Fragment {
    private PullToRefreshListView lv;
    private MyBBsNoteListAdapter adapter;
    private ArrayList<MyBBsNoteBean>[] lists;
    private String phone,tooken,hguid;
    private ArrayList<MyBBsNoteBean> list,list_total,list_fresh,list_save ;
    private int size = 0,lastsize=0,lastget=0,frelastsize=0,freshcount=0;
    private int more_count = 0;
    private long ts=0;
    private Boolean ismore=false;
    private ProgressDialog dialog;
    private ToastOnly toastOnly;
    private ImageView iv_totop;
    private RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mybbsnotelist, container, false);
        toastOnly = new ToastOnly(getActivity());
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        dialog.show();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.notelist_background1);
        list = new ArrayList<MyBBsNoteBean>();
        list_total = new ArrayList<MyBBsNoteBean>();
        list_fresh= new ArrayList<MyBBsNoteBean>();
        list_save= new ArrayList<MyBBsNoteBean>();
        lv = (PullToRefreshListView) view.findViewById(R.id.lv_mybbsnotelist);

        ILoadingLayout startLabels = lv
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉加载...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
        adapter = new MyBBsNoteListAdapter(list_total,getActivity());
        lv.setAdapter(adapter);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken","");
        phone = sharedPreferences.getString("phone","");
        hguid = sharedPreferences.getString("HGUID","");

//        firstget();
//        list_total = list;
//        list_save = list;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowNote.class);
                intent.putExtra("topicid",list_total.get(position-1).getTopicID());
                intent.putExtra("title",list_total.get(position-1).getForumID());
                startActivity(intent);
            }
        });
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        iv_totop = (ImageView) view.findViewById(R.id.iv_totop_mybbs);
        iv_totop.getBackground().setAlpha(100);
        iv_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.getRefreshableView().setSelection(0);
                iv_totop.setVisibility(View.INVISIBLE);
            }
        });
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
                if (lastget==0 && firstVisibleItem!=0 && (firstVisibleItem+visibleItemCount) == totalItemCount){
                    toastOnly.toastShowShort("暂无更多数据");
                }else{
                }
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    size = 10;
                    ts = 0;
                    MyBBSNoteClient.request(getActivity(), hguid, tooken, 1, 10, ts, new MyBBSNoteHandler() {
                        @Override
                        public void onLoginSuccess(MyBBSNoteResponse response) {
                            super.onLoginSuccess(response);
                            if (response.getBean().length != 0) {
                                relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#f1f1f1"));
                                lastget = response.getLeftsize();
                                list_total.clear();
                                final int length = response.getBean().length;
                                for (int j = 0; j < length; j++) {
                                    list_total.add(response.getBean()[j]);
                                }
                                if (lastget == 0) {
                                    lv.onRefreshComplete();
                                    lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    lv.onRefreshComplete();
                                    lv.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                adapter.notifyDataSetChanged();
                            }else{
                                lv.onRefreshComplete();
                                dialog.dismiss();
                                relativeLayout.setBackgroundResource(R.drawable.nozhuti);
                            }
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(getActivity(), msg);
                            lv.onRefreshComplete();
//                new FinishRefresh().execute();
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            lv.onRefreshComplete();
                            Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(getActivity(), value);
                            lv.onRefreshComplete();
                        }
                    }, TestOrNot.isTest);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                secget();


            }
        });

        return view;
    }
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        protected void onPostExecute(Void result){
            lv.onRefreshComplete();
        }
    }

    private void firstget() {

            size = 10;
            ts = 0;
            MyBBSNoteClient.request(getActivity(), hguid, tooken, 1, 10, ts, new MyBBSNoteHandler() {
                @Override
                public void onLoginSuccess(MyBBSNoteResponse response) {
                    super.onLoginSuccess(response);
                    if (response.getBean().length != 0) {
                        relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#f1f1f1"));
                        list_total.clear();
                        lastget = response.getLeftsize();
                        dialog.dismiss();
                        final int length = response.getBean().length;
                        for (int j = 0; j < length; j++) {
                            list_total.add(response.getBean()[j]);
                        }
                        if (lastget == 0) {
                            lv.onRefreshComplete();
                            lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            lv.onRefreshComplete();
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
//                list_total = list;
//                list_save = list;
//                adapter = new MyBBsNoteListAdapter(list_total,getActivity());
//                lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        lv.onRefreshComplete();
                        dialog.dismiss();
                        relativeLayout.setBackgroundResource(R.drawable.nozhuti);
                    }
//                new FinishRefresh().execute();
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(getActivity(), msg);
                    lv.onRefreshComplete();
//                new FinishRefresh().execute();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    lv.onRefreshComplete();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(getActivity(), value);
                    lv.onRefreshComplete();
                }
            }, TestOrNot.isTest);

    }
    private void secget(){
            Log.d("结果ts",""+ts);

            if (lastget > 10) {
                size = 10;
            } else {
                size = lastget;
            }
            ts = list_total.get(list_total.size() - 1).getSortTS();
            MyBBSNoteClient.requestold(getActivity(), hguid, tooken, 1, size, ts, new MyBBSNoteHandler() {
                @Override
                public void onLoginSuccess(MyBBSNoteResponse response) {
                    super.onLoginSuccess(response);
//                    new FinishRefresh().execute();
//                    lv.onRefreshComplete();
                    for (int i = 0; i < response.getBean().length; i++) {
                        list_total.add(response.getBean()[i]);
                    }
                    lastget = response.getLeftsize();
                    adapter.notifyDataSetChanged();
                    lv.onRefreshComplete();
                    if (lastget == 0) {
                        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    }
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(getActivity(), msg);
                    lv.onRefreshComplete();
//                    new FinishRefresh().execute();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    lv.onRefreshComplete();
                    Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(getActivity(), value);
                    lv.onRefreshComplete();
                }
            }, TestOrNot.isTest);

        }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken","");
        phone = sharedPreferences.getString("phone","");
        hguid = sharedPreferences.getString("HGUID","");
//        list_total.clear();
        firstget();
    }
}