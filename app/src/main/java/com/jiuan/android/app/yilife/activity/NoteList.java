package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.ForumsNoteListAdapter;
import com.jiuan.android.app.yilife.bean.BBsNoteListBean;
import com.jiuan.android.app.yilife.bean.getadnote.GetadnotelistClient;
import com.jiuan.android.app.yilife.bean.getadnote.GetadnotelistHandler;
import com.jiuan.android.app.yilife.bean.getadnote.GetadnotelistResponse;
import com.jiuan.android.app.yilife.bean.getnotelist.GetnotelistClient;
import com.jiuan.android.app.yilife.bean.getnotelist.GetnotelistHandler;
import com.jiuan.android.app.yilife.bean.getnotelist.GetnotelistResponse;
import com.jiuan.android.app.yilife.bean.gettopnote.GetTopnotelistClient;
import com.jiuan.android.app.yilife.bean.gettopnote.GetTopnotelistHandler;
import com.jiuan.android.app.yilife.bean.gettopnote.GetTopnotelistResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

public class NoteList extends ParentActivity {
    private PullToRefreshListView refreshListView;
    private TextView tv_bar_title;
    private ImageView iv_back,tv_setting;
    private int forums=0,pagesize=10,frelastsize=0,lastsize=0,lastget=0, more_count = 0,freshcount=0;
    private long ts=0;
    private String title;
    private ArrayList<BBsNoteListBean> list,list_total,list_ad,list_fresh,list_remove,list_save,list_top;
    private ForumsNoteListAdapter adapter;
    private Handler handler;
    private Boolean ismore=false,isfirsttime=false;
    private ToastOnly toastOnly;
    private ImageView iv_totop;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        isfirsttime =true;
        toastOnly = new ToastOnly(NoteList.this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        list = new ArrayList<BBsNoteListBean>();
        list_total = new ArrayList<BBsNoteListBean>();
        list_ad = new ArrayList<BBsNoteListBean>();
        list_fresh = new ArrayList<BBsNoteListBean>();
        list_remove = new ArrayList<BBsNoteListBean>();
        list_save = new ArrayList<BBsNoteListBean>();
        list_top = new ArrayList<BBsNoteListBean>();

        Intent intent = getIntent();

        forums = intent.getIntExtra("forums",-1);
        Log.d("forums",""+forums);
        title = intent.getStringExtra("bankuai").trim();
        refreshListView = (PullToRefreshListView) findViewById(R.id.refreshlistview_notelist);
        refreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        adapter = new ForumsNoteListAdapter(list_total, NoteList.this);
        refreshListView.setAdapter(adapter);
        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_bar_title.setText(title);

        tv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        tv_setting.setImageResource(R.drawable.icon_edit);
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                if (mysharedPreferences.getInt("isLogin",-1)==1){
                    Intent intent_send = new Intent(NoteList.this,SendNote.class);
                    intent_send.putExtra("forums",forums);
                    startActivity(intent_send);
                }else{
                    Intent intent_login = new Intent(NoteList.this,LoginNormal.class);
                    Toast.makeText(NoteList.this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginFrom.setLoginfrom(2);
                    startActivity(intent_login);
                }
            }
        });
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_totop = (ImageView) findViewById(R.id.iv_totop_notelist);
        iv_totop.getBackground().setAlpha(100);
        iv_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshListView.getRefreshableView().setSelection(0);
                iv_totop.setVisibility(View.INVISIBLE);
            }
        });
//        adget();
//        handler =new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what==1){
//                    firstget();
//                }
//            }
//        };
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int topicid = list_total.get(position-1).getTopicID();
                int forums = list_total.get(position-1).getForumID();
                Intent intent_show = new Intent(NoteList.this,ShowNote.class);
                intent_show.putExtra("topicid",topicid);
                intent_show.putExtra("title",title);
                startActivity(intent_show);
            }
        });
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {


            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pulldown();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                if (lastget!=0) {
                    secget();
//                    new FinishRefresh().execute();
//                }else{
//                    refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                    toastOnly.toastShowShort("没有更多数据");
//                }
            }
        });
    }
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        protected void onPostExecute(Void result){
            refreshListView.onRefreshComplete();
        }
    }
    private void firstget() {
        if (NetWorkInfo.isNetworkAvailable(NoteList.this)) {
            GetnotelistClient.request(NoteList.this, forums, 10, ts, new GetnotelistHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(NoteList.this, msg);
                    refreshListView.onRefreshComplete();
                    dialog.dismiss();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    toastOnly.toastShowShort("网络超时");
                    dialog.dismiss();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    FailMessage.showfail(NoteList.this, value);
                    refreshListView.onRefreshComplete();
                    dialog.dismiss();
                }

                @Override
                public void onLoginSuccess(GetnotelistResponse response) {
                    super.onLoginSuccess(response);
//                Log.d("response111",""+response.getBean()[0].getTopicID());
//                Log.d("response112",""+response.getTs());
//                for (int i = 0; i < response.getBean().length; i++) {
//                    list_total.add(response.getBean()[i]);
//
//                }

                    dialog.dismiss();
                    //asdasd
                    frelastsize = response.getLeftsize();
                    more_count = response.getLeftsize();
                    lastget = response.getLeftsize();
                    final int length = response.getBean().length;

                    for (int j = 0; j < length; j++) {
                        list_total.add(response.getBean()[j]);
                    }
                    if (lastget == 0) {
                        refreshListView.onRefreshComplete();
                        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        refreshListView.onRefreshComplete();
                        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                    }

                    adapter.notifyDataSetChanged();
                }
            }, TestOrNot.isTest);
        }else{
            toastOnly.toastShowShort("请检查您的网络环境");
            dialog.dismiss();
        }
    }
    private void secget(){
        if (NetWorkInfo.isNetworkAvailable(NoteList.this)) {
            if (ismore) {
                if (frelastsize > 20) {
                    frelastsize = frelastsize - 10;
                    pagesize = 10;
                    ismore = true;
                    ts = list_total.get(list_total.size() - 1).getPosttime();
                    GetnotelistClient.requestold(NoteList.this, forums, pagesize, ts, new GetnotelistHandler() {
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(NoteList.this, msg);
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            toastOnly.toastShowShort("网络超时");
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(NoteList.this, value);
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onLoginSuccess(GetnotelistResponse response) {
                            super.onLoginSuccess(response);
//                Log.d("response111",""+response.getBean()[0].getTopicID());
//                Log.d("response112",""+response.getTs());
//                for (int i = 0; i < response.getBean().length; i++) {
//                    list.add(response.getBean()[i]);
//                }
                            for (int i = 0; i < response.getBean().length; i++) {
                                list_total.add(response.getBean()[i]);
                            }
                            list_save.clear();
                            for (int x = 0; x < list_total.size(); x++) {

                                list_save.add(list_total.get(x));
                            }
//                        adapter = new ForumsNoteListAdapter(list_total, NoteList.this);
//                        refreshListView.setAdapter(adapter);
                            refreshListView.onRefreshComplete();
                            adapter.notifyDataSetChanged();
                        }
                    }, TestOrNot.isTest);
                } else {
                    pagesize = frelastsize - 10;
                    ismore = false;
                    ts = list_total.get(list_total.size() - 1).getPosttime();
                    Log.d("tss", "" + ts);
                    GetnotelistClient.requestold(NoteList.this, forums, pagesize, ts, new GetnotelistHandler() {
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            refreshListView.onRefreshComplete();
                            FailMessage.showfail(NoteList.this, msg);
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            toastOnly.toastShowShort("网络超时");
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            refreshListView.onRefreshComplete();
                            FailMessage.showfail(NoteList.this, value);
                        }

                        @Override
                        public void onLoginSuccess(GetnotelistResponse response) {
                            super.onLoginSuccess(response);
//                Log.d("response111",""+response.getBean()[0].getTopicID());
//                Log.d("response112",""+response.getTs());
//                for (int i = 0; i < response.getBean().length; i++) {
//                    list.add(response.getBean()[i]);
//                }
                            for (int i = 0; i < response.getBean().length; i++) {
                                list_total.add(response.getBean()[i]);
                            }
                            for (int j = 0; j < list_save.size(); j++) {
                                list_total.add(list_save.get(j));
                            }
                            list_save.clear();
                            for (int x = 0; x < list_total.size(); x++) {
                                list_save.add(list_total.get(x));
                            }
//                        adapter = new ForumsNoteListAdapter(list_total, NoteList.this);
//                        refreshListView.setAdapter(adapter);
                            refreshListView.onRefreshComplete();
                            adapter.notifyDataSetChanged();
                        }
                    }, TestOrNot.isTest);
                }
            } else {
                ts = list_total.get(list_total.size() - 1).getPosttime();
                if (lastget > 10) {
                    pagesize = 10;
                } else {
                    pagesize = lastget;
                }
                GetnotelistClient.requestold(NoteList.this, forums, pagesize, ts, new GetnotelistHandler() {
                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        refreshListView.onRefreshComplete();
                        FailMessage.showfail(NoteList.this, msg);
                    }

                    @Override
                    public void onLoginSuccess(GetnotelistResponse response) {
                        super.onLoginSuccess(response);

                        list_save.clear();
                        for (int i = 0; i < response.getBean().length; i++) {
                            list_total.add(response.getBean()[i]);
                        }
                        for (int x = 0; x < list_total.size(); x++) {

                            list_save.add(list_total.get(x));
                        }
                        lastget = response.getLeftsize();
                        Log.d("lastgetnolist", "" + lastget);

//                        adapter = new ForumsNoteListAdapter(list_total, NoteList.this);
//                        refreshListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        refreshListView.onRefreshComplete();
                        if (lastget == 0) {
                            refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        refreshListView.onRefreshComplete();
                        toastOnly.toastShowShort("网络超时");
                    }

                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        refreshListView.onRefreshComplete();
                        FailMessage.showfail(NoteList.this, value);
                    }
                }, TestOrNot.isTest);
//
            }
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
    private void adget(){
        if (NetWorkInfo.isNetworkAvailable(NoteList.this)) {
            GetadnotelistClient.request(NoteList.this, forums, new GetadnotelistHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(NoteList.this, msg);
                    dialog.dismiss();
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
                    FailMessage.showfail(NoteList.this, value);
                }

                @Override
                public void onLoginSuccess(GetadnotelistResponse response) {
                    super.onLoginSuccess(response);
                    for (int i = 0; i < response.getBean().length; i++) {
                        list_total.add(response.getBean()[i]);
                        list_ad.add(response.getBean()[i]);
                    }
                    gettop();

                }
            }, TestOrNot.isTest);
        }else{
            toastOnly.toastShowShort("请检查您的网络环境");
            dialog.dismiss();
        }
    }
    private void gettop(){
        if (NetWorkInfo.isNetworkAvailable(NoteList.this)) {
            GetTopnotelistClient.request(NoteList.this, forums, new GetTopnotelistHandler() {
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(NoteList.this, msg);
                    dialog.dismiss();
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
                    FailMessage.showfail(NoteList.this, value);
                }

                @Override
                public void onLoginSuccess(GetTopnotelistResponse response) {
                    super.onLoginSuccess(response);
                    for (int i = 0; i < response.getBean().length; i++) {
                        list_total.add(response.getBean()[i]);
                        list_ad.add(response.getBean()[i]);
                    }
                    handler.sendEmptyMessage(1);
                }
            }, TestOrNot.isTest);
        }else{
            toastOnly.toastShowShort("请检查您的网络环境");
            dialog.dismiss();
        }
    }
    public void pulldown(){
        if (NetWorkInfo.isNetworkAvailable(NoteList.this)) {
            freshcount++;
            list_save.clear();
            for (int i = 0; i < list_total.size(); i++) {
                list_save.add(list_total.get(i));
            }
            for (int j = 0; j < list_ad.size(); j++) {
                list_save.remove(0);
            }
            //第N次刷新 并且 未加载条数不为0
            if (freshcount > 1) {

                pagesize = 10;
                ts = 0;
                GetnotelistClient.request(NoteList.this, forums, pagesize, ts, new GetnotelistHandler() {
                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        FailMessage.showfail(NoteList.this, msg);
                        refreshListView.onRefreshComplete();
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
                        FailMessage.showfail(NoteList.this, value);
                        refreshListView.onRefreshComplete();
                    }

                    @Override
                    public void onLoginSuccess(GetnotelistResponse response) {
                        super.onLoginSuccess(response);
                        frelastsize = response.getLeftsize() - more_count;
                        more_count = response.getLeftsize();
                        list_total.clear();
                        int getsize = response.getBean().length;
                        if (list_fresh.size() != 0) {
                            list_fresh.clear();
                        }

                        if (frelastsize <= 10 && frelastsize != 0) {
                            ismore = false;
                            for (int x = 0; x < list_ad.size(); x++) {
                                list_total.add(list_ad.get(x));
                            }
                            for (int i = 0; i < frelastsize; i++) {
                                list_fresh.add(response.getBean()[i]);
                            }
                            for (int x = 0; x < list_fresh.size(); x++) {
                                list_total.add(list_fresh.get(x));
                            }
                            for (int j = 0; j < list_save.size(); j++) {
//                                    list_total = list_fresh;
                                list_total.add(list_save.get(j));
                            }
                        } else if (frelastsize == 0) {
                            Log.e("结果size",""+list_save.size());
                            Log.e("结果size1",""+getsize);
                            if (list_save.size()<getsize) {
                                int a = response.getBean().length - list_save.size();
                                for (int x = 0; x < list_ad.size(); x++) {
                                    list_total.add(list_ad.get(x));
                                }
                                for (int k = 0; k < a; k++) {
                                    list_total.add(response.getBean()[k]);
                                }
                                for (int j = 0; j < list_save.size(); j++) {
                                    list_total.add(list_save.get(j));
                                }
                            }else {
                                for (int x = 0; x < list_ad.size(); x++) {
                                    list_total.add(list_ad.get(x));
                                }
                                Log.d("list_save", "" + list_save.size());
                                for (int j = 0; j < list_save.size(); j++) {
                                    list_total.add(list_save.get(j));
                                }
                            }
                        } else {
                            for (int x = 0; x < list_ad.size(); x++) {
                                list_total.add(list_ad.get(x));
                            }
                            ismore = true;
                            for (int i = 0; i < response.getBean().length; i++) {
                                list_total.add(response.getBean()[i]);
                            }
                        }
                        if (response.getLeftsize() == 0) {
                            refreshListView.onRefreshComplete();
                            refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            refreshListView.onRefreshComplete();
                            refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        adapter.notifyDataSetChanged();

                    }
                }, TestOrNot.isTest);
            } else if (freshcount == 1) {
                pagesize = 10;
                ts = 0;
                GetnotelistClient.request(NoteList.this, forums, pagesize, ts, new GetnotelistHandler() {
                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        FailMessage.showfail(NoteList.this, msg);
                        refreshListView.onRefreshComplete();
                    }

                    @Override
                    public void onLoginSuccess(GetnotelistResponse response) {
                        super.onLoginSuccess(response);
                        //                Log.d("response111",""+response.getBean()[0].getTopicID());
                        //                Log.d("response112",""+response.getTs());
                        Log.e("结果more_count",""+more_count);

                        frelastsize = response.getLeftsize() - more_count;
                        more_count = response.getLeftsize();
                        list_total.clear();
                        int getsize = response.getBean().length;

                        if (list_fresh.size() != 0) {
                            list_fresh.clear();
                        }
                        if (frelastsize <= 10 && frelastsize != 0) {
                            ismore = false;
                            for (int i = 0; i < frelastsize; i++) {
                                list_fresh.add(response.getBean()[i]);
                            }
                            for (int x = 0; x < list_ad.size(); x++) {
                                list_total.add(list_ad.get(x));
                            }
                            for (int x = 0; x < list_fresh.size(); x++) {
                                list_total.add(list_fresh.get(x));
                            }
                            for (int j = 0; j < list_save.size(); j++) {
//                                    list_total = list_fresh;
                                list_total.add(list_save.get(j));
                            }
                        } else if (frelastsize == 0) {
                            Log.e("结果size",""+list_save.size());
                            Log.e("结果size1",""+getsize);
                            if (list_save.size()<getsize){
                                int a = response.getBean().length-list_save.size();
                                for (int x = 0; x < list_ad.size(); x++) {
                                    list_total.add(list_ad.get(x));
                                }
                                for (int k =0;k<a;k++){
                                    list_total.add(response.getBean()[k]);
                                }
                                for (int j = 0; j < list_save.size(); j++) {
                                    list_total.add(list_save.get(j));
                                }
                            }else {
                                for (int x = 0; x < list_ad.size(); x++) {
                                    list_total.add(list_ad.get(x));
                                }
                                Log.d("list_save", "" + list_save.size());
                                for (int j = 0; j < list_save.size(); j++) {
                                    list_total.add(list_save.get(j));
                                }
                            }
                        } else {

                            ismore = true;
                            for (int x = 0; x < list_ad.size(); x++) {
                                list_total.add(list_ad.get(x));
                            }
                            for (int i = 0; i < response.getBean().length; i++) {
                                list_total.add(response.getBean()[i]);
                            }
                        }
                        if (response.getLeftsize() == 0) {
                            refreshListView.onRefreshComplete();
                            refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            refreshListView.onRefreshComplete();
                            refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, TestOrNot.isTest);
            }
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("结果count",""+freshcount);
////        freshcount=0;
//        if (!isfirsttime) {
//            pulldown();
//        }
//        isfirsttime=false;
        list_total.clear();
        list_save.clear();
        list_ad.clear();
        dialog.show();
        adget();
        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                    firstget();
                }
            }
        };
    }
}
