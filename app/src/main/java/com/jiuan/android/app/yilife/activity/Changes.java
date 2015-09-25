package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.adapter.SelectSexAdapter;
import com.jiuan.android.app.yilife.bean.changepasswordnornal.ChangepasswordnornalClient;
import com.jiuan.android.app.yilife.bean.changepasswordnornal.ChangepasswordnornalHandler;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoClient;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoHandler;
import com.jiuan.android.app.yilife.bean.changeuserinfo.ChangeuserinfoResponse;
import com.jiuan.android.app.yilife.bean.changeuserinfo.UserInfo;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Changes extends ParentActivity {
    private EditTextWithDel editText_name,editText_phone;
    private RadioGroup radioGroup;
    private TextView textView_bir,tv_bar_title,tv_bar_menu;
    private String nn,yy,dd,day,tooken,phone,title,today,day_up,hguid;
    private DatePickerDialog datePickerDialog;
    private ActionBar actionBar;
    private EditTextWithDel editText_first,editText_second,editText_old;
    private ToastOnly toastOnly = new ToastOnly(this);
    private LinearLayout linearLayout;
    private RadioButton radioButton_man,radioButton_woman,radioButton_other;
    private ProgressDialog dialog;
    private UserInfo[] info = new UserInfo[1] ;
    private Intent intent;
    private ImageView iv_back;
    private TextWatcher textWatcher;
    private CharSequence temp;
    private int editStart,editEnd;
    private String name,sex1,birday,sexinlv="男";
    private ListView lv,lv_sex;
    private ArrayList<Integer> list;
    private SelectSexAdapter adapter;
    private String const_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes);
        dialog = new ProgressDialog(Changes.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");


        UserInfo userInfo = new UserInfo();
        info[0] = userInfo;
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        String sexfirst = mySharedPreferences.getString("sex","");

        list = new ArrayList<Integer>();
        list.add(-1);
        list.add(-1);
        list.add(-1);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_changes);
        editText_name = (EditTextWithDel) findViewById(R.id.edittext_changes_name);
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
                editStart = editText_name.getSelectionStart();
                editEnd = editText_name.getSelectionEnd();
                if (temp.length() > 10) {
//                    Toast.makeText(Changes.this,
//                            "你输入的字数已经超过了20字！", Toast.LENGTH_SHORT)
//                            .show();
                    toastOnly.toastShowShort("您输入的字数已经超过了10字");
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    editText_name.setText(s);
                    editText_name.setSelection(tempSelection);
                }
            }
        };
        editText_name.addTextChangedListener(textWatcher);

        editText_phone = (EditTextWithDel) findViewById(R.id.edittext_changes_phone);
        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp= s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText_phone.getSelectionStart();
                editEnd = editText_phone.getSelectionEnd();
                if (temp.length() > 200) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText_phone.setText(s);
                    editText_phone.setSelection(tempSelection);
                }else if (temp.length()>150 && temp.length()<200){
                    toastOnly.toastShowShort("您还可以输入"+(200-temp.length())+"字");
                }else if (temp.length()==200){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText_phone.addTextChangedListener(textWatcher1);
        editText_first = (EditTextWithDel) findViewById(R.id.changes_edittext_firstpassword);
        editText_second = (EditTextWithDel) findViewById(R.id.changes_edittext_secondpassword);
        editText_old = (EditTextWithDel) findViewById(R.id.changes_edittext_oldpassword);
        lv_sex = (ListView) findViewById(R.id.lv_sex);
//        radioGroup = (RadioGroup) findViewById(R.id.radio_changes_sex);
//        radioButton_man = (RadioButton) findViewById(R.id.radio_man);
//        radioButton_woman = (RadioButton) findViewById(R.id.radio_woman);
//        radioButton_other = (RadioButton) findViewById(R.id.radio_other);
//        if (sexfirst.equals("男")){
//            radioButton_man.setChecked(true);
//        }else if (sexfirst.equals("女")){
//            radioButton_woman.setChecked(true);
//        }else{
//            radioButton_other.setChecked(true);
//        }
        lv = (ListView) findViewById(R.id.lv_sex);
        adapter = new SelectSexAdapter(Changes.this,list);
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
        textView_bir = (TextView) findViewById(R.id.textview_changes_bir);
        Intent intent_witch = getIntent();
        title = intent_witch.getStringExtra("title");
        if (title.equals("更改昵称")){
            name = intent_witch.getStringExtra("name");

            editText_name.setText(name);
            editText_phone.setVisibility(View.GONE);
            lv_sex.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
            textView_bir.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }else if (title.equals("我的地址")){
            editText_name.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
            String addr = intent_witch.getStringExtra("addr");
            editText_phone.setText(addr);
            lv_sex.setVisibility(View.GONE);
            textView_bir.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);

        }else if (title.equals("更改性别")){
            sex1 = intent_witch.getStringExtra("sex");
//            if (sex1.equals("男")){
//                radioButton_man.setChecked(true);
//            }else if(sex1.equals("女")){
//                radioButton_woman.setChecked(true);
//            }else{
//                radioButton_other.setChecked(true);
//            }
            sexinlv =sex1;
            if (sexinlv.equals("男")){
                list.set(0,0);
                adapter.notifyDataSetChanged();
                sexinlv = "男";
            }else if(sexinlv.equals("女")){
                lv.setSelection(1);
                list.set(1,1);
                adapter.notifyDataSetChanged();
                sexinlv = "女";
            }else{
                lv.setSelection(2);
                list.set(2,2);
                adapter.notifyDataSetChanged();
                sexinlv = "保密";
            }
            editText_name.setVisibility(View.GONE);
            editText_phone.setVisibility(View.GONE);
            textView_bir.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }else if (title.equals("出生年月")){
            birday = intent_witch.getStringExtra("bir");
            if (birday!=null) {
                textView_bir.setText(birday);
            }else{
                textView_bir.setText("请选择您的出生年月日");
            }
            editText_name.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
            lv_sex.setVisibility(View.GONE);
            editText_phone.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }else if (title.equals("密码管理")){
            editText_name.setVisibility(View.GONE);
//            radioGroup.setVisibility(View.GONE);
            lv_sex.setVisibility(View.GONE);
            textView_bir.setVisibility(View.GONE);
            editText_phone.setVisibility(View.GONE);
        }

        tv_bar_title = (TextView) findViewById(R.id.blue_title);
        tv_bar_title.setText(title);
        tv_bar_menu = (TextView) findViewById(R.id.blue_setting);
        tv_bar_menu.setText("保存");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_bar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                phone = mySharedPreferences.getString("phone","");
                tooken =mySharedPreferences.getString("AccessToken","");
                hguid =mySharedPreferences.getString("HGUID","");
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                intent = new Intent();
                if (title.equals("更改昵称")) {
                    if (!editText_name.getText().toString().equals("")) {
                        editor.putString("name", editText_name.getText().toString());
                        info[0].setField("UserName");
                        info[0].setValue(editText_name.getText().toString());
                        editor.commit();
                        changename();
                    }else if(editText_name.getText().toString().equals("")){
                        dialog.dismiss();
                        toastOnly.toastShowShort("请输入昵称");
                    }
                }else if (title.equals("更改性别")){
                    if (sexinlv.equals("男")){
                        editor.putString("sex", "男");
                        info[0].setField("Sex");
                        info[0].setValue("1");
                        editor.commit();
                        changename();
                    }else if (sexinlv.equals("女")){
                        editor.putString("sex", "女");
                        info[0].setField("Sex");
                        info[0].setValue("2");
                        editor.commit();
                        changename();
                    }else{
                        editor.putString("sex", "保密");
                        info[0].setField("Sex");
                        info[0].setValue("0");
                        editor.commit();
                        changename();
                    }
//                    if (radioGroup.getCheckedRadioButtonId() == R.id.radio_man) {
//                        editor.putString("sex", "男");
//                        info[0].setField("Sex");
//                        info[0].setValue("1");
//                        editor.commit();
//                        changename();
//                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_woman) {
//                        editor.putString("sex", "女");
//                        info[0].setField("Sex");
//                        info[0].setValue("2");
//                        editor.commit();
//                        changename();
//                    } else {
//                        editor.putString("sex", "保密");
//                        info[0].setField("Sex");
//                        info[0].setValue("-1");
//                        editor.commit();
//                        changename();
//                    }
                }else if (title.equals("密码管理")) {
                    Log.d("1",editText_first.getText().toString());
                    Log.d("2",editText_second.getText().toString());
                    if (!editText_first.getText().toString().equals(editText_second.getText().toString())) {
                        toastOnly.toastShowShort("两次输入的密码不一致");
                        dialog.dismiss();
                        editText_first.requestFocus();
//                        editText_old.requestFocus();
//                        editText_old.setText("");
//                        editText_first.setText("");
//                        editText_second.setText("");
                    } else {
                        Boolean isSpace =false;
                        for (int i=0;i<editText_first.getText().length();i++){
                            if (editText_first.getText().toString().substring(i,i+1).equals(" ")){
                                isSpace = true;
                            }
                        }
                        if((0<editText_first.getText().toString().length() && editText_first.getText().toString().length()<6) || editText_first.getText().toString().length()>16){
                            toastOnly.toastShowShort("密码应该为6~16位");
                            editText_first.requestFocus();
                            dialog.dismiss();
                        }else if(isSpace) {
                            toastOnly.toastShowShort("密码中不能出现空格");
                            editText_first.requestFocus();
                            dialog.dismiss();
                        }else if (editText_old.getText().toString().equals("")){
                            toastOnly.toastShowShort("请输入旧密码");
                            editText_old.requestFocus();
                            dialog.dismiss();
                        }else if (editText_first.getText().toString().equals("")){
                            toastOnly.toastShowShort("请输入密码");
                            editText_first.requestFocus();
                            dialog.dismiss();
                        }else if (editText_second.getText().toString().equals("")){
                            toastOnly.toastShowShort("请输入确认密码");
                            editText_second.requestFocus();
                            dialog.dismiss();
                        }else if (editText_old.getText().toString().equals(editText_first.getText().toString())){
                            toastOnly.toastShowShort("设置密码不能与当前密码一致");
                            editText_first.requestFocus();
                            dialog.dismiss();
                        }else {
                            if (NetWorkInfo.isNetworkAvailable(Changes.this)) {
                                if (TestOrNot.isTest){
                                    const_s = ScAndSv.const_salt_test;
                                }else{
                                    const_s = ScAndSv.const_salt;
                                }

                                ChangepasswordnornalClient.requestLogin(Changes.this, hguid, tooken,
                                        InnovationAlgorithm.SHA1(const_s, editText_old.getText().toString()),
                                        InnovationAlgorithm.SHA1(const_s, editText_first.getText().toString()),
                                        new ChangepasswordnornalHandler() {
                                            @Override
                                            public void onInnovationSuccess(JsonElement value) {
                                                super.onInnovationSuccess(value);
                                                dialog.dismiss();
                                                toastOnly.toastShowShort("修改成功，请重新登录");
                                                Intent broad = new Intent("" + 100);
                                                sendBroadcast(broad);
                                                SharedPreferences  mySharedPreferences= getSharedPreferences("login", Activity.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                                editor.putInt("isLogin",0).commit();
                                                Changes.this.setResult(RESULT_OK, intent);
                                                Changes.this.finish();
                                            }

                                            @Override
                                            public void onInnovationFailure(String msg) {
                                                super.onInnovationFailure(msg);
                                                if (msg.equals("2007")){
                                                    editText_old.setFocusable(true);
                                                    toastOnly.toastShowShort("原密码错误");
                                                    editText_old.requestFocus();
                                                }else{
                                                    FailMessage.showfail(Changes.this, msg);
                                                }
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
                                                dialog.dismiss();
                                                if (value.equals("2007")){
                                                    editText_old.setFocusable(true);
                                                    toastOnly.toastShowShort("原密码错误");
                                                    editText_old.requestFocus();
                                                }else{
                                                    FailMessage.showfail(Changes.this, value);
                                                }
                                            }
                                        }, TestOrNot.isTest);
                            }else{
                                dialog.dismiss();
                                toastOnly.toastShowShort("请检查您的网络环境");
                            }
                        }
                    }
                }else if(title.equals("出生年月")){
//                    Log.d("today",today.replace("/",""));
//                    Log.d("todaytoday",textView_bir.getText().toString().replace("-",""));
//                    if (Long.parseLong(today.replace("/",""))<Long.parseLong(textView_bir.getText().toString().replace("-",""))){
//                        toastOnly.toastShowShort("生日选择错误");
//                        dialog.dismiss();
//                    }else {
                    if (!textView_bir.getText().toString().trim().equals("")) {
                        info[0].setField("Birthday");
                        info[0].setValue(textView_bir.getText().toString());
                        changename();
                    }else if(editText_name.getText().toString().equals("")){
                            dialog.dismiss();
                            toastOnly.toastShowShort("请选择出生年月");
                        }
//                    }
                }else if (title.equals("我的地址")){
                    if (!editText_phone.getText().toString().trim().equals("")) {
                        info[0].setField("Address");
                        info[0].setValue(editText_phone.getText().toString());
                        changename();
                    }else if(editText_phone.getText().toString().equals("")){
                        dialog.dismiss();
                        toastOnly.toastShowShort("请填写您的地址");
                    }
                }
            }
        });
        textView_bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                day = df.format(new Date()).replace("-", "/");
                today  = day;
                datePickerDialog = new DatePickerDialog(Changes.this, new DatePickerDialog.OnDateSetListener() {
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
                        day_up = ""+i+yy+dd;
                        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("day",i + "-" + yy + "-" + dd).commit();
                        textView_bir.setText(i + "-" + yy + "-" + dd);
                        info[0].setField("Birthday");
                        info[0].setValue(textView_bir.getText().toString());
                    }
                }, Integer.parseInt(day.substring(0, 4)), (Integer.parseInt(day.substring(5, 7)) - 1), Integer.parseInt(day.substring(8, 10)));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMaxDate(new Date().getTime());
                datePickerDialog.show();


            }
        });
    }



        private void changename(){
            if (info[0].getField().equals("UserName") && info[0].getValue().contains(" ")) {
                toastOnly.toastShowShort("昵称不能包含空格");
                dialog.dismiss();
            }else if (info[0].getField().equals("UserName") && info[0].getValue().length()<2){
                toastOnly.toastShowShort("昵称不能小于2个字");
                dialog.dismiss();
            }else {
                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                tooken = sharedPreferences.getString("AccessToken", "");
                hguid = sharedPreferences.getString("HGUID", "");
                if (NetWorkInfo.isNetworkAvailable(Changes.this)) {
                    ChangeuserinfoClient.requestLogin(Changes.this, hguid, tooken, info,
                            new ChangeuserinfoHandler() {
                                @Override
                                public void onInnovationFailure(String msg) {
                                    super.onInnovationFailure(msg);
                                    dialog.dismiss();
                                    FailMessage.showfail(Changes.this, msg);
                                }

                                @Override
                                public void onLoginSuccess(ChangeuserinfoResponse response) {
                                    super.onLoginSuccess(response);
                                    dialog.dismiss();
                                    if(response.getIsSuccess()==0){
                                        toastOnly.toastShowShort(response.getErrors()[0]);
                                    }else {
                                        toastOnly.toastShowShort("修改成功");
                                        Changes.this.setResult(RESULT_OK, intent);
                                        Changes.this.finish();
                                    }
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
                                    FailMessage.showfail(Changes.this, value);
                                }
                            }, TestOrNot.isTest);
                }else{
                    dialog.dismiss();
                    toastOnly.toastShowShort("请检查您的网络环境");
                }
            }
        }


}

