package com.jiuan.android.app.yilife.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class MyBBsNoteListFragment3 extends Fragment {
    private PullToRefreshListView lv;
    private MyBBsNoteListAdapter adapter;
    private String phone,tooken,hguid;
    private ArrayList<MyBBsNoteBean> list,list_total,list_fresh,list_save ;
    private int size = 0,lastsize=0,lastget=0,frelastsize=0,freshcount=0;
    private int more_count = 0;
    private long ts=0;
    private Boolean ismore=false;
    private ProgressDialog dialog;
    private ImageView iv_totop;
    private ToastOnly toastOnly;
    private RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mybbsnotelist, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        dialog.show();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.notelist_background1);
        toastOnly = new ToastOnly(getActivity());
        list = new ArrayList<MyBBsNoteBean>();
        list_total = new ArrayList<MyBBsNoteBean>();
        list_fresh = new ArrayList<MyBBsNoteBean>();
        list_save = new ArrayList<MyBBsNoteBean>();
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

//        MyBBSNoteClient.request(getActivity(), phone, tooken, 3, 1, size, new MyBBSNoteHandler() {
//            @Override
//            public void onLoginSuccess(MyBBSNoteResponse response) {
//                super.onLoginSuccess(response);
////                Log.d("listresponse",""+response.toString());
//                for (int i=0;i<response.getBean().length;i++){
//                    list.add(response.getBean()[i]);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onInnovationFailure(String msg) {
//                super.onInnovationFailure(msg);
//            }
//        }, TestOrNot.isTest);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowNote.class);
                intent.putExtra("topicid",list_total.get(position-1).getTopicID());
                intent.putExtra("ForumID",list_total.get(position-1).getForumID());
                startActivity(intent);
            }
        });
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        iv_totop = (ImageView) view.findViewById(R.id.iv_totop_mybbs);
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
//                freshcount++;
//                //第N次刷新 并且 未加载条数不为0
//                list_save.clear();
//                for (int i=0;i<list_total.size();i++){
//                    list_save.add(list_total.get(i));
//                }
//                if (freshcount>1){
//                    size =10;
//                    ts = 0;
//                    MyBBSNoteClient.request(getActivity(), phone, tooken, 3, 10, ts, new MyBBSNoteHandler() {
//                        @Override
//                        public void onLoginSuccess(MyBBSNoteResponse response) {
//                            super.onLoginSuccess(response);
//                            //fir_lastsize: 第一次刷新后 服务器还有多少条数据
//                            frelastsize = response.getLeftsize()-more_count;
//                            more_count = response.getLeftsize();
//                            list_total.clear();
//                            if (list_fresh.size() ==0) {
//                                list_fresh.clear();
//                            }
//
//                            if (frelastsize <= 10) {
//                                ismore = false;
//                                for (int i = 0; i < frelastsize; i++) {
//                                    list_fresh.add(response.getBean()[i]);
//                                }
//                                for (int x = 0; x < list_fresh.size(); x++) {
//                                    list_total.add(list_fresh.get(x));
//                                }
//                                for (int j = 0; j < list_save.size(); j++) {
//                                    list_total.add(list_save.get(j));
//                                }
//                                list_save.clear();
//
//                            }else if (frelastsize==0){
//                                for (int j = 0; j < list_save.size(); j++) {
//                                    list_total.add(list_save.get(j));
//                                }
//                            } else {
//                                ismore = true;
//                                for (int i = 0; i < response.getBean().length; i++) {
//                                    list_total.add(response.getBean()[i]);
//                                }
//                            }
//                            if (response.getLeftsize()==0){
//                                lv.onRefreshComplete();
//                                lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                            }else{
//                                lv.onRefreshComplete();
//                                lv.setMode(PullToRefreshBase.Mode.BOTH);
//                            }
//
////                            list_fresh.clear();
//
////                            adapter = new MyBBsNoteListAdapter(list_total,getActivity());
////                            lv.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
////                            new FinishRefresh().execute();
//                        }
//
//                        @Override
//                        public void onInnovationFailure(String msg) {
//                            super.onInnovationFailure(msg);
//                            FailMessage.showfail(getActivity(), msg);
////                            new FinishRefresh().execute();
//                            lv.onRefreshComplete();
//                        }
//                    }, TestOrNot.isTest);
//                }else if (freshcount==1){
//                    size =10;
//                    ts = 0;
//                    MyBBSNoteClient.request(getActivity(), phone, tooken, 3, size, ts, new MyBBSNoteHandler() {
//                        @Override
//                        public void onLoginSuccess(MyBBSNoteResponse response) {
//                            super.onLoginSuccess(response);
//                            Log.d(" responsegetLeftsize()",""+ response.getLeftsize());
//
//                            frelastsize = response.getLeftsize()-more_count;
//                            more_count = response.getLeftsize();
//                            list_total.clear();
//                            if (list_fresh.size() ==0) {
//                                list_fresh.clear();
//                            }
//                            if (frelastsize <= 10) {
//                                ismore = false;
//                                for (int i = 0; i < frelastsize; i++) {
//                                    list_fresh.add(response.getBean()[i]);
//                                }
//                                for (int x =0;x<list_fresh.size();x++){
//                                    list_total.add(list_fresh.get(x));
//                                }
//                                for (int j = 0; j < list_save.size(); j++) {
////                                    list_total = list_fresh;
//                                    list_total.add(list_save.get(j));
//                                }
//                                list_save.clear();
//
//                            }else if (frelastsize==0){
//                                for (int j = 0; j < list_save.size(); j++) {
//                                    list_total.add(list_save.get(j));
//                                }
//                            } else {
//                                ismore = true;
//                                for (int i = 0; i < response.getBean().length; i++) {
//                                    list_total.add(response.getBean()[i]);
//                                }
//                            }
//
//                            adapter.notifyDataSetChanged();
////                            new FinishRefresh().execute();
//                            if (response.getLeftsize()==0){
//                                lv.onRefreshComplete();
//                                lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                            }else{
//                                lv.onRefreshComplete();
//                                lv.setMode(PullToRefreshBase.Mode.BOTH);
//                            }
//                        }
//
//                        @Override
//                        public void onInnovationFailure(String msg) {
//                            super.onInnovationFailure(msg);
//                            FailMessage.showfail(getActivity(), msg);
////                            new FinishRefresh().execute();
//                            lv.onRefreshComplete();
//                        }
//                    }, TestOrNot.isTest);
//
//                }
//
                list_total.clear();
                size =10;
                ts = 0;
                MyBBSNoteClient.request(getActivity(), hguid, tooken, 3, 10, ts, new MyBBSNoteHandler() {
                    @Override
                    public void onLoginSuccess(MyBBSNoteResponse response) {
                        super.onLoginSuccess(response);
                        //fir_lastsize: 第一次刷新后 服务器还有多少条数据
                        if (response.getBean().length != 0) {
                            relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#f1f1f1"));
                            frelastsize = response.getLeftsize();
                            more_count = response.getLeftsize();
                            lastget = response.getLeftsize();


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
//                new FinishRefresh().execute();
                        }else{
                            dialog.dismiss();
                            lv.onRefreshComplete();
                            relativeLayout.setBackgroundResource(R.drawable.nojinghua);
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
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        dialog.dismiss();
                        FailMessage.showfail(getActivity(), value);
                        lv.onRefreshComplete();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        dialog.dismiss();
                        Toast.makeText(getActivity(),"网络超时",Toast.LENGTH_SHORT).show();
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
        size =10;
        ts = 0;
        MyBBSNoteClient.request(getActivity(), hguid, tooken, 3, 10, ts, new MyBBSNoteHandler() {
            @Override
            public void onLoginSuccess(MyBBSNoteResponse response) {
                super.onLoginSuccess(response);
                if (response.getBean().length != 0) {
                    relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#f1f1f1"));
                    dialog.dismiss();
                    //fir_lastsize: 第一次刷新后 服务器还有多少条数据
                    frelastsize=response.getLeftsize();
                    more_count = response.getLeftsize();
                    lastget = response.getLeftsize();


                    final int length = response.getBean().length;

                    for (int j=0;j<length;j++) {
                        list_total.add(response.getBean()[j]);
                    }

                    if (lastget==0){
                        lv.onRefreshComplete();
                        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    }else {
                        lv.onRefreshComplete();
                        lv.setMode(PullToRefreshBase.Mode.BOTH);
                    }
    //                list_total = list;
    //                list_save = list;
    //                adapter = new MyBBsNoteListAdapter(list_total,getActivity());
    //                lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                new FinishRefresh().execute();
                }else{
                    dialog.dismiss();
                    lv.onRefreshComplete();
                    relativeLayout.setBackgroundResource(R.drawable.nojinghua);
                }
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
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                dialog.dismiss();
                FailMessage.showfail(getActivity(), value);
                lv.onRefreshComplete();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                dialog.dismiss();
                Toast.makeText(getActivity(),"网络超时",Toast.LENGTH_SHORT).show();
                lv.onRefreshComplete();
            }
        }, TestOrNot.isTest);
    }
    private void secget(){

            ts = list_total.get(list_total.size() - 1).getPosttime();
            if (lastget>10){
                size = 10;
            }else{
                size = lastget;
            }
            MyBBSNoteClient.requestold(getActivity(), hguid, tooken, 3, size, ts, new MyBBSNoteHandler() {
                @Override
                public void onLoginSuccess(MyBBSNoteResponse response) {
                    super.onLoginSuccess(response);
//                    new FinishRefresh().execute();
//                    lv.onRefreshComplete();
                    for (int i = 0; i < response.getBean().length; i++) {
                        list_total.add(response.getBean()[i]);
                    }
                    for (int x =0;x<list_total.size();x++){
                        list_save.add(list_total.get(x));
                    }
                    lastget = response.getLeftsize();

//                    adapter = new MyBBsNoteListAdapter(list_total, getActivity());
//                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    lv.onRefreshComplete();
                    if (lastget==0){
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
            }, TestOrNot.isTest);
        }
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken","");
        phone = sharedPreferences.getString("phone","");
        hguid = sharedPreferences.getString("HGUID","");
        list_total.clear();
        firstget();
    }
}