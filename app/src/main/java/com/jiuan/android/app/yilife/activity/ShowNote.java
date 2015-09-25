package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.Util;
import com.jiuan.android.app.yilife.adapter.ShowNoteListAdapter2;
import com.jiuan.android.app.yilife.bean.ShownoteBean;
import com.jiuan.android.app.yilife.bean.reply.ReplyClient;
import com.jiuan.android.app.yilife.bean.reply.ReplyHandler;
import com.jiuan.android.app.yilife.bean.shownote.ShownoteClient;
import com.jiuan.android.app.yilife.bean.shownote.ShownoteHandler;
import com.jiuan.android.app.yilife.bean.shownote.ShownoteResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import java.util.ArrayList;

public class ShowNote extends ParentActivity {
    private PullToRefreshListView lv;
    private TextView textView,textView_title,tv_bar_title,tv_reply;
    private LinearLayout layout,layout_sesseion,layout_timeline,layout_qq,layout_sina,layout_other;
    private int topicid,forumid,count=1,recount=0,leftcount=1;
    private ShowNoteListAdapter2 adapter;
    private ImageView iv_back,iv_setting,iv_totop;
    private EditText editText;
    private String phone,tooken,title,sharelink,top_title,share_neirong,hguid;
    private ToastOnly toastOnly;
    private IWXAPI api;
    private CharSequence temp;
    private int editStart,editEnd;
    private ShownoteResponse shownoteResponse;
    private ArrayList<ShownoteBean> list_bean = new ArrayList<ShownoteBean>();
    private ProgressDialog dialog;
    private PopupWindow popupWindow;
    private Boolean isfirst= false;
    private int totalcount=0;
    public static  boolean hasAttach = false;
    private RelativeLayout relativeLayout_shownote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        relativeLayout_shownote = (RelativeLayout) findViewById(R.id.refreshlistview_shownote);
        textView = (TextView) findViewById(R.id.tv_all);
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = mySharedPreferences.getString("phone","");
        tooken = mySharedPreferences.getString("AccessToken","");
        hguid = mySharedPreferences.getString("HGUID","");
        dialog = new ProgressDialog(ShowNote.this);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        dialog.show();
        toastOnly = new ToastOnly(ShowNote.this);

//        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//        setSupportActionBar(toolbar);
        shownoteResponse = new ShownoteResponse();
        editText = (EditText) findViewById(R.id.edit_shownote_reply);
        editText.clearFocus();


//        adapter = new ShowNoteListAdapter(ShowNote.this,shownoteResponse,editText);
        iv_totop = (ImageView) findViewById(R.id.iv_totop);
        iv_totop.getBackground().setAlpha(100);
        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setImageResource(R.drawable.icon_share);
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                ShowNote.this.relativeLayout_shownote.getLocationOnScreen(location);// 获得指定控件的坐标
                View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_share_new, null,false);
                popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                layout_sesseion = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_session);
                layout_qq = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_qqfriend_share);
//                layout_qq.setOnClickListener(ShowNote.this);
                layout_sina = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_sina_share);
//                layout_sina.setOnClickListener(HTML5Activity.this);
                layout_other = (LinearLayout) popupWindow_view.findViewById(R.id.pop_share_other);

                layout_other.setVisibility(View.GONE);
                layout_qq.setVisibility(View.INVISIBLE);
                layout_sina.setVisibility(View.INVISIBLE);
                layout_sesseion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        ScAndSv.place = "SHOW";
                        ScAndSv.WX = "share";
                        api = WXAPIFactory.createWXAPI(ShowNote.this, Constants.APP_ID);
                        api.registerApp(Constants.APP_ID);
                        WXWebpageObject webpage = new WXWebpageObject();
//            webpage.webpageUrl = "http://int.iemylife.com/mobile/cam-gprsbp/camindex";
                        webpage.webpageUrl = sharelink;
                        WXMediaMessage msg = new WXMediaMessage(webpage);
//                        msg.title = title;
//            msg.description = "参与活动领红包";
//                        msg.description = list_bean.get(0).getNeirong();
//                        msg.description = share_neirong;
                        msg.description = title.trim();
                        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                        msg.thumbData = Util.bmpToByteArray(thumb, true);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("webpage");
                        req.message = msg;
                        api.sendReq(req);
                    }
                });
                layout_timeline = (LinearLayout) popupWindow_view.findViewById(R.id.linearlayout_timeline);
                layout_timeline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        ScAndSv.place = "SHOW";
                        ScAndSv.WX = "share";
                        api = WXAPIFactory.createWXAPI(ShowNote.this, Constants.APP_ID);
                        api.registerApp(Constants.APP_ID);
                        WXWebpageObject webpage = new WXWebpageObject();
                        webpage.webpageUrl = sharelink;
//            webpage.webpageUrl = "http://int.iemylife.com/mobile/cam-gprsbp/camindex";
                        WXMediaMessage msg = new WXMediaMessage(webpage);
//            msg.title = "免费体验血压计";
//            msg.description = "参与活动领红包";
                        msg.title = title.trim();
//            msg.description = "参与活动领红包";
                        msg.description = share_neirong.trim();
//                        msg.description = title.trim();
                        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
                        msg.thumbData = Util.bmpToByteArray(thumb, true);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("webpage");
                        req.message = msg;
                        req.scene =SendMessageToWX.Req.WXSceneTimeline;
                        api.sendReq(req);
                    }
                });
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new poponDismissListener());
                backgroundAlpha(0.5f);
                popupWindow.showAtLocation(relativeLayout_shownote, Gravity.NO_GRAVITY, location[0], location[1]+relativeLayout_shownote.getHeight() - popupWindow.getHeight());
            }
        });
        lv = (PullToRefreshListView) findViewById(R.id.lv_shownote);
//        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                  pullup();
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                closeSoftInput();
                adapter.setIndex(0);
                editText.setHint("回复精彩内容");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) {
                    iv_totop.setVisibility(View.VISIBLE);
                }else{
                    iv_totop.setVisibility(View.INVISIBLE);
                }
                if (list_bean.size()>0) {
                    if (list_bean.size() == (list_bean.get(0).getCount() + 1) && (firstVisibleItem + visibleItemCount) == totalItemCount && firstVisibleItem != 0) {
                        toastOnly.toastShowShort("暂无更多数据");
                        lv.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
                }
//                if (leftcount<=0 && (firstVisibleItem+visibleItemCount) == totalItemCount){
//                    textView.setVisibility(View.VISIBLE);
//                }else{
//                    textView.setVisibility(View.GONE);
//                }
//                if (leftcount<=0){
//                    textView.setVisibility(View.VISIBLE);
//                }else{
//                    textView.setVisibility(View.GONE);
//                }
            }
        });
        iv_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lv.smoothScrollTo(0,0);
                lv.getRefreshableView().setSelection(0);
                iv_totop.setVisibility(View.INVISIBLE);
            }
        });
        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_reply = (TextView) findViewById(R.id.tv_shownote_reply);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp= s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText.getSelectionStart();
                editEnd = editText.getSelectionEnd();
                if (temp.length() ==0) {
                    tv_reply.setClickable(false);
                }else{
                    tv_reply.setClickable(true);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        topicid = intent.getIntExtra("topicid",-1);
        top_title = intent.getStringExtra("title");
        adapter = new ShowNoteListAdapter2(ShowNote.this,list_bean,editText,title,recount);
//        adapter = new ShowNoteListAdapter(ShowNote.this,list_bean,editText,title,recount);
        lv.setAdapter(adapter);
        tv_reply.setFocusable(true);
        tv_reply.setClickable(false);
        tv_reply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                replyag();
            }
        });
//        tv_bar_title.setText(top_title);
//        iv_setting =
//        if (forumid==4){
//            .setText("宜生活");
//        }else if (forumid==2){
//            tv_bar_title.setText("爱家人");
//        }else if (forumid==11){
//            tv_bar_title.setText("版主会议室");
//        }else if (forumid==10){
//            tv_bar_title.setText("论坛事务");
//        }else if (forumid==6){
//            tv_bar_title.setText("产品反馈");
//        }else if (forumid==7){
//            tv_bar_title.setText("售后服务");
//        }else if (forumid==12){
//            tv_bar_title.setText("生活问答");
//        }
//        ThreadBangdingClient.request(ShowNote.this,topicid,count,10,new ThreadBangdingStuHandler(){
//            @Override
//            public void onLoginSuccess(ThreadBangdingStuResponse response) {
//                super.onLoginSuccess(response);
//                dialog.dismiss();
//                for (int i=0;i<response.getBean().length;i++) {
//                    list_bean.add(response.getBean()[i]);
//                }
////                adapter = new ShowNoteListAdapter(ShowNote.this,list_bean,response,editText);
////                lv.setAdapter(adapter);
////                shownoteResponse =response;
//                share_neirong = response.getOriginalContent();
//                tv_bar_title.setText(response.getForumName().trim());
//                sharelink = response.getShareLink();
//                title = response.getTitle();
//                recount = response.getReply();
//                adapter = new ShowNoteListAdapter(ShowNote.this,list_bean,editText,title,recount);
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
////                if (response.getReply() - count * 10 <= 0) {
////                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//////                    toastOnly.toastShowShort("暂无其他数据");
////                }
//                leftcount = response.getReply() - count * 10;
//                if ( leftcount<= 0) {
//                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
//            }
//
//            @Override
//            public void onInnovationFailure(String msg) {
//                super.onInnovationFailure(msg);
//                dialog.dismiss();
//                FailMessage.showfail(ShowNote.this,msg);
//            }
//
//            @Override
//            public void onInnovationError(String value) {
//                super.onInnovationError(value);
//                dialog.dismiss();
//                FailMessage.showfail(ShowNote.this,value);
//            }
//
//            @Override
//            public void onInnovationExceptionFinish() {
//                super.onInnovationExceptionFinish();
//                dialog.dismiss();
//                toastOnly.toastShowShort("网络超时");
//            }
//        }, TestOrNot.isTest);
        pullup1();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==5 && resultCode == RESULT_OK) {

        }
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        setover
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_show_note, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_session) {
//            api = WXAPIFactory.createWXAPI(ShowNote.this, Constants.APP_ID);
//            api.registerApp(Constants.APP_ID);
//            WXWebpageObject webpage = new WXWebpageObject();
////            webpage.webpageUrl = "http://int.iemylife.com/mobile/cam-gprsbp/camindex";
//            webpage.webpageUrl = sharelink;
//            WXMediaMessage msg = new WXMediaMessage(webpage);
//            msg.title = title;
////            msg.description = "参与活动领红包";
//            msg.description = list_bean.get(0).getNeirong();
//            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
//            msg.thumbData = Util.bmpToByteArray(thumb, true);
//            SendMessageToWX.Req req = new SendMessageToWX.Req();
//            req.transaction = buildTransaction("webpage");
//            req.message = msg;
//            api.sendReq(req);
//            return true;
//        }else if (id==R.id.action_timeline){
//            api = WXAPIFactory.createWXAPI(ShowNote.this, Constants.APP_ID);
//            api.registerApp(Constants.APP_ID);
//            WXWebpageObject webpage = new WXWebpageObject();
//            webpage.webpageUrl = sharelink;
////            webpage.webpageUrl = "http://int.iemylife.com/mobile/cam-gprsbp/camindex";
//            WXMediaMessage msg = new WXMediaMessage(webpage);
////            msg.title = "免费体验血压计";
////            msg.description = "参与活动领红包";
//            msg.title = title;
////            msg.description = "参与活动领红包";
//            msg.description = list_bean.get(0).getNeirong();
//            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apps_yilife);
//            msg.thumbData = Util.bmpToByteArray(thumb, true);
//            SendMessageToWX.Req req = new SendMessageToWX.Req();
//            req.transaction = buildTransaction("webpage");
//            req.message = msg;
//            req.scene =SendMessageToWX.Req.WXSceneTimeline;
//            api.sendReq(req);
//            return  true;
//        }else if(id==R.id.action_share){
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
//
//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        setOverflowIconVisible(featureId,menu);
//        return super.onMenuOpened(featureId, menu);
//    }
//    public static void setOverflowIconVisible(int featureId, Menu menu) {
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                }
//            }
//        }
//    }
protected  void pullupone(){
        ++count;

    ShownoteClient.request(ShowNote.this, topicid, 1, list_bean.size()+10, new ShownoteHandler() {
        @Override
        public void onLoginSuccess(ShownoteResponse response) {
            super.onLoginSuccess(response);
//                        adapter = new ShowNoteListAdapter(ShowNote.this,response,editText);
//                        lv.setAdapter(adapter);
//                        shownoteResponse = response;
            dialog.dismiss();
            share_neirong = response.getOriginalContent();
            tv_bar_title.setText(response.getForumName().trim());
            sharelink = response.getShareLink();
            title = response.getTitle();
            recount = response.getReply();
            list_bean.clear();
            for (int i=0;i<response.getBean().length;i++) {
                list_bean.add(response.getBean()[i]);
                Log.e("结果",""+list_bean.get(i).getNeirong());
                Log.e("结果re",""+list_bean.get(i).getNeirong().replace("\r",""));
            }
            list_bean.get(0).setTtitle(title);
            list_bean.get(0).setCount(response.getReply());
//                        shownoteResponse = response;
            adapter.notifyDataSetChanged();
            lv.onRefreshComplete();
//                count++;
//                leftcount = leftcount - (count+1) * 10;
            leftcount = response.getReply() - (count * 10)+1;
//            if ( leftcount<= 0) {
//                lv.setMode(PullToRefreshBase.Mode.DISABLED);
//            }
            if (totalcount==list_bean.size()){
                toastOnly.toastShowShort("暂无更多数据");
            }else{
                totalcount = list_bean.size();
            }
        }

        @Override
        public void onInnovationFailure(String msg) {
            super.onInnovationFailure(msg);
            FailMessage.showfail(ShowNote.this, msg);
            lv.onRefreshComplete();
        }

        @Override
        public void onInnovationError(String value) {
            super.onInnovationError(value);
            FailMessage.showfail(ShowNote.this, value);
            lv.onRefreshComplete();
        }

        @Override
        public void onInnovationExceptionFinish() {
            super.onInnovationExceptionFinish();
            toastOnly.toastShowShort("网络超时");
            lv.onRefreshComplete();
        }
    }, TestOrNot.isTest);

}
    protected  void pullup1(){
//        ++count;
        ShownoteClient.request(ShowNote.this, topicid, count,  10, new ShownoteHandler() {
            @Override
            public void onLoginSuccess(ShownoteResponse response) {
                super.onLoginSuccess(response);
//                        adapter = new ShowNoteListAdapter(ShowNote.this,response,editText);
//                        lv.setAdapter(adapter);
//                        shownoteResponse = response;
                dialog.dismiss();
                share_neirong = response.getOriginalContent();
                tv_bar_title.setText(response.getForumName().trim());
                sharelink = response.getShareLink();
                title = response.getTitle();
                recount = response.getReply();
                hasAttach = response.isHasAttach();
                for (int i=0;i<response.getBean().length;i++) {
                    list_bean.add(response.getBean()[i]);
                 }
                list_bean.get(0).setTtitle(title);
                list_bean.get(0).setCount(response.getReply());
//                        shownoteResponse = response;
                adapter.notifyDataSetChanged();
                lv.onRefreshComplete();
//                count++;
//                leftcount = leftcount - (count+1) * 10;
                leftcount = response.getReply() - count * 10+1;
                if (list_bean.size() == (list_bean.get(0).getCount()+1)){

                }else if(list_bean.size() < (list_bean.get(0).getCount()+1)){
                    lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                }
//                if ( leftcount<= 0) {
//                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                FailMessage.showfail(ShowNote.this, msg);
                lv.onRefreshComplete();
                dialog.dismiss();
            }

            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                FailMessage.showfail(ShowNote.this, value);
                lv.onRefreshComplete();
                dialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                toastOnly.toastShowShort("网络超时");
                lv.onRefreshComplete();
                dialog.dismiss();
            }
        }, TestOrNot.isTest);

    }
    protected  void pullup(){
//        if (isfirst) {
////            ++count;
//        }else{
            ++count;
//        }
        ShownoteClient.request(ShowNote.this, topicid, count,  10, new ShownoteHandler() {
            @Override
            public void onLoginSuccess(ShownoteResponse response) {
                super.onLoginSuccess(response);
//                        adapter = new ShowNoteListAdapter(ShowNote.this,response,editText);
//                        lv.setAdapter(adapter);
//                        shownoteResponse = response;

                    dialog.dismiss();
                    share_neirong = response.getOriginalContent();
                    tv_bar_title.setText(response.getForumName().trim());
                    sharelink = response.getShareLink();
                    title = response.getTitle();
                    recount = response.getReply();
                    for (int i = 0; i < response.getBean().length; i++) {
                        list_bean.add(response.getBean()[i]);
                    }
                    list_bean.get(0).setTtitle(title);
                    list_bean.get(0).setCount(response.getReply());
//                        shownoteResponse = response;
                    adapter.notifyDataSetChanged();
                    lv.onRefreshComplete();
                leftcount = response.getReply() - count * 10;
                if (totalcount==list_bean.size()){
                    toastOnly.toastShowShort("暂无更多数据");
                }else{
                    totalcount = list_bean.size();
                }
//                count++;
//                leftcount = leftcount - (count+1) * 10;
//                leftcount = response.getReply() - count * 10;
//                if ( leftcount<= 0) {
//                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                FailMessage.showfail(ShowNote.this, msg);
                lv.onRefreshComplete();
                dialog.dismiss();
            }

            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                FailMessage.showfail(ShowNote.this, value);
                lv.onRefreshComplete();
                dialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                toastOnly.toastShowShort("网络超时");
                lv.onRefreshComplete();
                dialog.dismiss();
            }
        }, TestOrNot.isTest);

    }

//    protected  void pullup(){
//        int size = list_bean.size()+10;
//            ThreadBangdingClient.request(ShowNote.this,topicid,count,size,new ThreadBangdingStuHandler(){
//            @Override
//            public void onLoginSuccess(ThreadBangdingStuResponse response) {
//                super.onLoginSuccess(response);
//                dialog.dismiss();
//                for (int i=0;i<response.getBean().length;i++) {
//                    list_bean.add(response.getBean()[i]);
//                }
////                adapter = new ShowNoteListAdapter(ShowNote.this,list_bean,response,editText);
////                lv.setAdapter(adapter);
////                shownoteResponse =response;
//                share_neirong = response.getOriginalContent();
//                tv_bar_title.setText(response.getForumName().trim());
//                sharelink = response.getShareLink();
//                title = response.getTitle();
//                recount = response.getReply();
//                adapter = new ShowNoteListAdapter(ShowNote.this,list_bean,editText,title,recount);
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
////                if (response.getReply() - count * 10 <= 0) {
////                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//////                    toastOnly.toastShowShort("暂无其他数据");
////                }
//                leftcount = response.getReply() - count * 10;
//                if ( leftcount<= 0) {
//                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
//            }
//
//            @Override
//            public void onInnovationFailure(String msg) {
//                super.onInnovationFailure(msg);
//                dialog.dismiss();
//                FailMessage.showfail(ShowNote.this,msg);
//            }
//
//            @Override
//            public void onInnovationError(String value) {
//                super.onInnovationError(value);
//                dialog.dismiss();
//                FailMessage.showfail(ShowNote.this,value);
//            }
//
//            @Override
//            public void onInnovationExceptionFinish() {
//                super.onInnovationExceptionFinish();
//                dialog.dismiss();
//                toastOnly.toastShowShort("网络超时");
//            }
//        }, TestOrNot.isTest);
//    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
        if (mysharedPreferences.getInt("isLogin",-1)==1 && !editText.getText().toString().equals("")){
            SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
            phone = mySharedPreferences.getString("phone","");
            tooken = mySharedPreferences.getString("AccessToken","");
            hguid = mySharedPreferences.getString("HGUID","");
            replyag();
            closeInput();
        }
        for (int i=0;i<ShowNoteListAdapter2.listdouble.size();i++) {
            Log.e("listdouble" + i, "" + ShowNoteListAdapter2.listdouble.get(i));
        }

    }
    protected  void closeSoftInput(){
        if (getCurrentFocus() !=null){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus()
                            .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    protected void replyag(){
        SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
        if (mysharedPreferences.getInt("isLogin",-1)!=1){
            Intent intent_login = new Intent(ShowNote.this,LoginNormal.class);
            Toast.makeText(ShowNote.this, "请先登录", Toast.LENGTH_SHORT).show();
            LoginFrom.setLoginfrom(3);
            startActivityForResult(intent_login, 5);
        }else{
            dialog.show();
            int nameid=0;
            int index = 0;
            if(adapter!=null){
                index = adapter.getIndex();
                nameid = adapter.getNameid();
            }
            if (editText.getText().toString().equals("")){
                dialog.dismiss();
                toastOnly.toastShowShort("发送内容不能为空");
            }else {
                Log.d("结果1",""+index);
                ReplyClient.request(ShowNote.this, hguid, tooken, topicid, editText.getText().toString().trim(), index, nameid, new ReplyHandler() {
                    @Override
                    public void onLoginSuccess(Integer response) {
                        super.onLoginSuccess(response);
                        toastOnly.toastShowShort("发送成功");
//                            ShownoteBean bean =new ShownoteBean();
//                            bean.setNeirong(editText.getText().toString().trim());
//                            bean.setPoster(phone);
//                            bean.setPosttime(new Date().getTime());
//                            if (adapter.getIndex()==0) {
//                                bean.setReplyTo("");
//                            }else{
//                                bean.setReplyTo("回复" + (adapter.getIndex())+"楼："+list_bean.get(adapter.getIndex()-1).getPoster().trim()+"的帖子");
//                            }
//                            bean.setFloor(list_bean.size()+1);
                        dialog.dismiss();
//                            list_bean.add(bean);
                        if (leftcount>0) {
                            Log.e("结果","11");
                            pullup();
                        }else{
                            Log.e("结果","12");
                            pullupone();
                        }
                        adapter.notifyDataSetChanged();
                        editText.setText("");
//                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            if (imm.isActive())
//                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                        closeSoftInput();
                        adapter.setIndex(0);
                        editText.setHint("回复精彩内容");
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        FailMessage.showfail(ShowNote.this, msg);
//                            toastOnly.toastShowShort("发送失败");
                        dialog.dismiss();
                    }

                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        FailMessage.showfail(ShowNote.this,value);
//                            toastOnly.toastShowShort("发送失败");
                        dialog.dismiss();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        dialog.dismiss();
                        toastOnly.toastShowShort("网络超时");
                    }
                }, TestOrNot.isTest);
            }
        }
        adapter.notifyDataSetChanged();

    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        handler.sendEmptyMessage(3);
        closeSoftInput();
    }
    public void closeInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//得到InputMethodManager的实例
        if (imm.isActive()) {
//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }
}
