package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.sendpinglun.SendpinglunClient;
import com.jiuan.android.app.yilife.bean.sendpinglun.SendpinglunHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class PingLun extends ParentActivity {
    private EditText editText_title,editText_note;
    private CharSequence temp;
    private int editStart,editEnd,rate;
    private ToastOnly toastOnly = new ToastOnly(PingLun.this);
    private RatingBar ratingBar;
    private TextView textView,tv_title;
    private ImageView iv_back,iv_setting;
    private String phone,tooken,appname,title,neirong,hguid;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun);
        dialog= new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_title.setText("撰写评论");
        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (editText_title.getText().toString().trim().equals("")) {
                    toastOnly.toastShowShort("标题不能为空");
                    dialog.dismiss();
                }else if (editText_note.getText().toString().trim().length()<10) {
                    toastOnly.toastShowShort("内容不能少于10个字");
                    dialog.dismiss();
                }else if (editText_note.getText().toString().trim().equals("")) {
                    toastOnly.toastShowShort("内容不能为空");
                    dialog.dismiss();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                    appname = sharedPreferences.getString("AppUniqueName", "");
                    phone = sharedPreferences.getString("phone", "");
                    tooken = sharedPreferences.getString("AccessToken", "");
                    hguid = sharedPreferences.getString("HGUID", "");
                    title = editText_title.getText().toString().trim();
                    neirong = editText_note.getText().toString().trim();
                    rate = (int) ratingBar.getRating();
                    Log.d("appname",appname);
                    if (NetWorkInfo.isNetworkAvailable(PingLun.this)) {
                        SendpinglunClient.request(PingLun.this, hguid, tooken, appname, title, neirong, rate, new SendpinglunHandler() {
                            @Override
                            public void onLoginSuccess(String response) {
                                super.onLoginSuccess(response);
                                dialog.dismiss();
                                toastOnly.toastShowShort("发表成功");
                            }

                            @Override
                            public void onInnovationError(String value) {
                                super.onInnovationError(value);
                                dialog.dismiss();
                                FailMessage.showfail(PingLun.this, value);
                            }

                            @Override
                            public void onInnovationFailure(String msg) {
                                super.onInnovationFailure(msg);
                                dialog.dismiss();
                                FailMessage.showfail(PingLun.this, msg);
                            }

                            @Override
                            public void onInnovationExceptionFinish() {
                                super.onInnovationExceptionFinish();
                                dialog.dismiss();
                                toastOnly.toastShowShort("网络超时");
                            }
                        }, TestOrNot.isTest);
                    }else{
                        dialog.dismiss();
                        toastOnly.toastShowShort("请检查您的网络环境");
                    }
                }
            }
        });
        editText_title = (EditText) findViewById(R.id.edittext_pinglun_title);
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
                editStart = editText_title.getSelectionStart();
                editEnd = editText_title.getSelectionEnd();
                if (temp.length() > 20) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText_title.setText(s);
                    editText_title.setSelection(tempSelection);
                }else if (temp.length()>15 && temp.length()<20){
                    toastOnly.toastShowShort("您还可以输入"+(20-temp.length())+"字");

                }else if (temp.length()==20){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText_title.addTextChangedListener(textWatcher);
        editText_note = (EditText) findViewById(R.id.edittext_pinglun_note);
        TextWatcher textWatcher_note = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp= s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText_note.getSelectionStart();
                editEnd = editText_note.getSelectionEnd();
                if (temp.length() > 200) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText_note.setText(s);
                    editText_note.setSelection(tempSelection);
                }else if (temp.length()>180 && temp.length()<200){
                    toastOnly.toastShowShort("您还可以输入"+(200-temp.length())+"字");
                }else if (temp.length()==200){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText_note.addTextChangedListener(textWatcher_note);
        textView = (TextView) findViewById(R.id.level);
        ratingBar = (RatingBar) findViewById(R.id.room_ratingbar_detail);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating<=1.0){
                    textView.setText("不满意");
                }else if (rating==2.0){
                    textView.setText("一般");
                }else if (rating==3.0){
                    textView.setText("好用");
                }else if (rating==4.0){
                    textView.setText("满意");
                }else if (rating==5.0){
                    textView.setText("超赞");
                }
            }
        });

    }
}
