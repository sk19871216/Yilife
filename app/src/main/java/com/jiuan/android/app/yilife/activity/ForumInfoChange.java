package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.SelectSexAdapter;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoClient;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoHandler;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoResponse;
import com.jiuan.android.app.yilife.bean.changeuserinfo.UserInfo;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForumInfoChange extends ParentActivity {
    private EditText et_name,et_realname,et_qq,et_email;
    private TextView tv_bir,tv_save,tv_title;
    private ListView lv;
    private LinearLayout layout_name,layout_realname,layout_bir,layout_qq,layout_email;
    private SelectSexAdapter adapter;
    private ArrayList<Integer> list;
    private String sexinlv,title,changeinfo,day,today,yy,nn,dd;
    private ImageView iv_back;
    private UserInfo userInfo;
    private UserInfo[] info = new UserInfo[1] ;
    private ProgressDialog dialog;
    private ToastOnly toastOnly;
    private CharSequence temp;
    private int editStart,editEnd;
    private TextWatcher textWatcher,textWatcher2;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_info_change);
        initialize();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(this);
        userInfo = new UserInfo();
        info[0] =userInfo;


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0;i<list.size();i++){
                    list.set(i,-1);
                    if (i==position){
                        list.set(position,position);
                    }
                }
                if (position==0){
                    sexinlv = "男";
                }else  if (position==1){
                    sexinlv="女";
                }else{
                    sexinlv="保密";
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    protected void initialize(){
        layout_name = (LinearLayout) findViewById(R.id.layout_bbsuserinfo_name);
        layout_realname = (LinearLayout) findViewById(R.id.layout_bbsuserinfo_realname);
        layout_bir = (LinearLayout) findViewById(R.id.layout_bbsuserinfo_bir);
        layout_qq = (LinearLayout) findViewById(R.id.layout_bbsuserinfo_qq);
        layout_email = (LinearLayout) findViewById(R.id.layout_bbsuserinfo_email);
        lv = (ListView) findViewById(R.id.lv_bbs_sex);
        et_name = (EditText) findViewById(R.id.edittext_bbs_changes_name);
        et_realname = (EditText) findViewById(R.id.edittext_bbs_changes_realname);
        et_qq = (EditText) findViewById(R.id.edittext_bbs_changes_qq);
        et_email = (EditText) findViewById(R.id.edittext_bbs_changes_email);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = et_name.getSelectionStart();
                editEnd = et_name.getSelectionEnd();
                if (temp.length() > 20) {
//                    Toast.makeText(Changes.this,
//                            "你输入的字数已经超过了20字！", Toast.LENGTH_SHORT)
//                            .show();
                    toastOnly.toastShowShort("您输入的字数已经超过了20字");
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    et_name.setText(s);
                    et_name.setSelection(tempSelection);
                }
            }
        };
        textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = et_realname.getSelectionStart();
                editEnd = et_realname.getSelectionEnd();
                if (temp.length() > 20) {
//                    Toast.makeText(Changes.this,
//                            "你输入的字数已经超过了20字！", Toast.LENGTH_SHORT)
//                            .show();
                    toastOnly.toastShowShort("您输入的字数已经超过了20字");
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    et_realname.setText(s);
                    et_realname.setSelection(tempSelection);
                }
            }
        };
        et_name.addTextChangedListener(textWatcher);
        et_realname.addTextChangedListener(textWatcher2);

        tv_bir = (TextView) findViewById(R.id.textview_forumchanges_bir);
        tv_bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                day = df.format(new Date()).replace("-", "/");
                today  = day;
                datePickerDialog = new DatePickerDialog(ForumInfoChange.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        if (i2 < 9) {
                            yy = "0" + (i2 + 1);
                        } else {
                            yy = "" + (i2 + 1);
                        }
                        if (i3 < 10) {
                            dd = "0" + i3;
                        } else {
                            dd = "" + i3;
                        }
                        day = i + "-" + yy + "-" + dd;
//                        day_up = ""+i+yy+dd;
                        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("day",i + "-" + yy + "-" + dd).commit();
                        tv_bir.setText(i + "-" + yy + "-" + dd);
                        info[0].setField("Birthday");
                        info[0].setValue(tv_bir.getText().toString());
                    }
                }, Integer.parseInt(day.substring(0, 4)), (Integer.parseInt(day.substring(5, 7)) - 1), Integer.parseInt(day.substring(8, 10)));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_save = (TextView) findViewById(R.id.blue_setting);
        tv_save.setText("保存");
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                switch (title){
                    case "更改昵称":
                        if (et_name.getText().toString().trim().equals("")){
                            toastOnly.toastShowShort("请输入昵称");
                            dialog.dismiss();
                        }else {
                            info[0].setField("NickName");
                            info[0].setValue(et_name.getText().toString().trim());
                            changeInfo();
                        }
                        break;
                    case "更改姓名":
                        if (et_realname.getText().toString().trim().equals("")){
                            toastOnly.toastShowShort("请输入姓名");
                            dialog.dismiss();
                        }else {
                            info[0].setField("RealName");
                            info[0].setValue(et_realname.getText().toString().trim());
                            changeInfo();
                        }
                        break;
                    case "更改性别":
                        info[0].setField("Sex");
                        switch (sexinlv){
                            case "男":
                                info[0].setValue("1");
                                break;
                            case "女":
                                info[0].setValue("2");
                                break;
                            case "保密":
                                info[0].setValue("0");
                                break;
                        }
//                        info[0].setValue(et_realname.getText().toString().trim());
                        changeInfo();
                        break;
                    case "出生年月":
                        if (tv_bir.getText().toString().trim().equals("")) {
                            toastOnly.toastShowShort("请选择出生年月");
                            dialog.dismiss();
                        }else {
                            info[0].setField("Birthday");
                            info[0].setValue(tv_bir.getText().toString().trim());
                            changeInfo();
                        }
                        break;
                    case "更改QQ":
                        if (et_qq.getText().toString().trim().equals("")){
                            toastOnly.toastShowShort("请输入QQ");
                            dialog.dismiss();
                        }else {
                            info[0].setField("QQ");
                            info[0].setValue(et_qq.getText().toString().trim());
                            changeInfo();
                        }
                        break;
                    case "更改邮箱":
                        String email = et_email.getText().toString().trim();
                        if (et_email.getText().toString().trim().equals("")){
                            toastOnly.toastShowShort("请输入邮箱");
                            dialog.dismiss();
                        }else {
                            if (isEmail(email)) {
                                info[0].setField("EmailAddress");
                                info[0].setValue(email);
                                changeInfo();
                            } else {
                                dialog.dismiss();
                                toastOnly.toastShowShort("邮箱格式错误");
                            }
                        }
                        break;
                }
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);

        list  = new ArrayList<Integer>();
        list.add(-1);
        list.add(-1);
        list.add(-1);
        adapter = new SelectSexAdapter(ForumInfoChange.this,list);
        Intent intent =getIntent();
        title = intent.getStringExtra("title");
        sexinlv =intent.getStringExtra("changeinfo");
        tv_title.setText(title);
        changeinfo = intent.getStringExtra("changeinfo");
        if (title.equals("更改昵称")){
            layout_name.setVisibility(View.VISIBLE);
            layout_realname.setVisibility(View.INVISIBLE);
            layout_bir.setVisibility(View.INVISIBLE);
            layout_qq.setVisibility(View.INVISIBLE);
            layout_email.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.INVISIBLE);
            et_name.setText(changeinfo);
        }else if (title.equals("更改姓名")){
            layout_name.setVisibility(View.INVISIBLE);
            layout_realname.setVisibility(View.VISIBLE);
            layout_bir.setVisibility(View.INVISIBLE);
            layout_qq.setVisibility(View.INVISIBLE);
            layout_email.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.INVISIBLE);
            et_realname.setText(changeinfo);
        }else if (title.equals("更改性别")){
            layout_name.setVisibility(View.INVISIBLE);
            layout_realname.setVisibility(View.INVISIBLE);
            layout_bir.setVisibility(View.INVISIBLE);
            layout_qq.setVisibility(View.INVISIBLE);
            layout_email.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);
            switch (changeinfo){
                case "男":
                    list.set(0,0);
                    break;
                case "女":
                    list.set(1,1);
                    break;
                case "保密":
                    list.set(2,2);
                    break;
            }
            adapter.notifyDataSetChanged();
//            et_.setText(changeinfo);
        }else if (title.equals("出生年月")){
            layout_name.setVisibility(View.INVISIBLE);
            layout_realname.setVisibility(View.INVISIBLE);
            layout_bir.setVisibility(View.VISIBLE);
            layout_qq.setVisibility(View.INVISIBLE);
            layout_email.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.INVISIBLE);
            if (changeinfo.equals("")){
                tv_bir.setText("");
            }else {
                tv_bir.setText(changeinfo);
            }
        }else if (title.equals("更改QQ")){
            layout_name.setVisibility(View.INVISIBLE);
            layout_realname.setVisibility(View.INVISIBLE);
            layout_bir.setVisibility(View.INVISIBLE);
            layout_qq.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.INVISIBLE);
            et_qq.setText(changeinfo);
        }else if (title.equals("更改邮箱")){
            layout_name.setVisibility(View.INVISIBLE);
            layout_realname.setVisibility(View.INVISIBLE);
            layout_bir.setVisibility(View.INVISIBLE);
            layout_qq.setVisibility(View.INVISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
            et_email.setText(changeinfo);
        }
    }
    protected void changeInfo(){
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        String phone = mySharedPreferences.getString("phone","");
        String tooken =mySharedPreferences.getString("AccessToken","");
        String hguid =mySharedPreferences.getString("HGUID","");
        ChangeuserinfoClient.changeforuminfo(ForumInfoChange.this,hguid,tooken,info,new ChangeuserinfoHandler(){
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
                FailMessage.showfail(ForumInfoChange.this,value);
            }

            @Override
            public void onLoginSuccess(ChangeuserinfoResponse response) {
                super.onLoginSuccess(response);
                dialog.dismiss();
                if(response.getIsSuccess()==0){
                    toastOnly.toastShowShort(response.getErrors()[0]);
                }else {
                    toastOnly.toastShowShort("修改成功");
                    ForumInfoChange.this.finish();
                }

            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                dialog.dismiss();
                FailMessage.showfail(ForumInfoChange.this,msg);
            }
        }, TestOrNot.isTest);
    }
    public boolean isEmail(String email) {
//        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//        Pattern p = Pattern.compile(str);
//        Matcher m = p.matcher(email);
        String format = "\\w{2,15}[@][a-z0-9]{2,}[.]\\p{Lower}{2,}";
//        String format = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
//        return m.matches();
        if (email.matches(format)){
            return true;
        }else{
            return false;
        }
    }
}
