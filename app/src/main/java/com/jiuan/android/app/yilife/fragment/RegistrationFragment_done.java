package com.jiuan.android.app.yilife.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.MyBBsNote;
import com.jiuan.android.app.yilife.activity.MyRedbag;
import com.jiuan.android.app.yilife.activity.PingLun;
import com.jiuan.android.app.yilife.activity.SendNote;
import com.jiuan.android.app.yilife.bean.forgetdone.ForgetDoneClient;
import com.jiuan.android.app.yilife.bean.forgetdone.ForgetDoneHandler;
import com.jiuan.android.app.yilife.bean.registiondone.RegistionDoneClient;
import com.jiuan.android.app.yilife.bean.registiondone.RegistionDoneHandler;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;


public class RegistrationFragment_done extends Fragment {
    private Button buttonnext;
    private EditTextWithDel editText,editText1;
    private String phone,code,request;
    private ToastOnly toastOnly ;
    private TextView tv_bar_title;
    private ProgressDialog dialog;
    private String const_s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_registration3, container, false);
        tv_bar_title = (TextView) getActivity().findViewById(R.id.blue_title);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
//        tv_title = (TextView) getActivity().findViewById(R.id.actionbar_title);
//        tv_title.setText("我的");
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        code = bundle.getString("code");
        request = bundle.getString("request");
        editText= (EditTextWithDel) view.findViewById(R.id.registration3_edittext_firstpassword);
        editText1= (EditTextWithDel) view.findViewById(R.id.registration3_edittext_secondpassword);
        buttonnext = (Button)view.findViewById(R.id.registration3_button_checkphone);
        if (request.equals("woFragment")){
            tv_bar_title.setText("注册3/3");
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastOnly = new ToastOnly(getActivity());
                dialog.show();
                Boolean isSpace =false;
                for (int i=0;i<editText.getText().length();i++){
                    if (editText.getText().toString().substring(i,i+1).equals(" ")){
                        isSpace = true;
                    }
                }
                if (!editText.getText().toString().equals(editText1.getText().toString())) {
                    toastOnly.toastShowShort("两次密码输入不一致");
                    dialog.dismiss();
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    editText.setText("");
                    editText1.setText("");

                }else if (editText.getText().length()==0) {
                    toastOnly.toastShowShort("请输入密码");
                    dialog.dismiss();
                } else if (editText1.getText().length()==0) {
                    toastOnly.toastShowShort("请输入确认密码");
                    dialog.dismiss();
                } else if((0<editText.getText().toString().length() && editText.getText().toString().length()<6) || editText.getText().toString().length()>16){
                    toastOnly.toastShowShort("密码应该为6~16位");
                    dialog.dismiss();
                }else if(isSpace) {
                    toastOnly.toastShowShort("密码中不能出现空格");
                    dialog.dismiss();
                }else {
                    if (TestOrNot.isTest){
                        const_s = ScAndSv.const_salt_test;
                    }else{
                        const_s = ScAndSv.const_salt;
                    }
                    RegistionDoneClient.requestLogin(getActivity(), phone, code,
//                            editText.getText().toString()
                            InnovationAlgorithm.SHA1(const_s,editText.getText().toString())
                            ,""
                            , new RegistionDoneHandler() {
                        @Override
                        public void onLoginSuccess(LoginResponse response) {
                            super.onLoginSuccess(response);
                            SharedPreferences mysharedPreferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mysharedPreferences.edit();
                            editor.putInt("isLogin", 1).commit();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor_self = sharedPreferences.edit();
                            editor_self.putString("AccessToken", response.getToken().getAccessToken());
                            editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                            editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                            editor_self.putString("phone", phone);
                            if (!sharedPreferences.getString("phone", "").equals("")) {
                                if (!sharedPreferences.getString("phone", "").equals(phone)) {
                                    editor_self.putString("name", "");
                                    editor_self.putString("day", "");
                                    editor_self.putString("sex", "");
                                }
                            }
                            editor_self.commit();
//                            toastOnly.toastShowShort("设置成功");
                            dialog.dismiss();
                            if (LoginFrom.getLoginfrom() == 0) {
//                                Intent intent_finsh = new Intent(getActivity(), MainActivity.class);
//                                intent_finsh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent_finsh.putExtra("position", 2);
//                                startActivity(intent_finsh);
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
//                                Intent gointent = new Intent(getActivity(),MyBBsNote.class);
//                                startActivity(gointent);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==1){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                Intent gointent = new Intent(getActivity(),MyBBsNote.class);
                                startActivity(gointent);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==2){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                Intent gointent = new Intent(getActivity(),SendNote.class);
                                startActivity(gointent);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==3){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==4){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                Intent gointent = new Intent(getActivity(),PingLun.class);
                                startActivity(gointent);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==5){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==6){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                Intent gointent = new Intent(getActivity(),MyRedbag.class);
                                startActivity(gointent);
                                getActivity().finish();
                            }else if(LoginFrom.getLoginfrom()==7){
                                Intent broad = new Intent(""+100);
                                getActivity().sendBroadcast(broad);
                                getActivity().finish();
                            }
                        }


                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            dialog.dismiss();
                            FailMessage.showfail(getActivity(),msg);
                        }

                         @Override
                         public void onInnovationExceptionFinish() {
                             dialog.dismiss();
                             toastOnly.toastShowShort("网络超时");
                             super.onInnovationExceptionFinish();
                         }

                         @Override
                         public void onInnovationError(String value) {
                             super.onInnovationError(value);
                             dialog.dismiss();
                             FailMessage.showfail(getActivity(),value);
                          }

                     }, TestOrNot.isTest);

                }

            }
        });
        }else if (request.equals("forget")){
            tv_bar_title.setText("忘记密码3/3");
            buttonnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    Boolean isSpace =false;
                    for (int i=0;i<editText.getText().length();i++){
                        if (editText.getText().toString().substring(i,i+1).equals(" ")){
                            isSpace = true;
                        }
                    }
                    toastOnly = new ToastOnly(getActivity());
                    if (!editText.getText().toString().equals(editText1.getText().toString())) {
                        toastOnly.toastShowShort("两次密码输入不一致");
                        dialog.dismiss();
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        editText.setText("");
                        editText1.setText("");
                    }else if (editText.getText().length()==0) {
                        toastOnly.toastShowShort("请输入密码");
                        dialog.dismiss();
                    } else if (editText1.getText().length()==0) {
                        toastOnly.toastShowShort("请输入确认密码");
                        dialog.dismiss();
                    } else if((0<editText.getText().toString().length() && editText.getText().toString().length()<6) || editText.getText().toString().length()>16){
                        toastOnly.toastShowShort("密码应该为6~16位");
                        dialog.dismiss();
                    }else if(isSpace) {
                        toastOnly.toastShowShort("密码中不能出现空格");
                        dialog.dismiss();

                    }else {
                        if (TestOrNot.isTest){
                            const_s = ScAndSv.const_salt_test;
                        }else{
                            const_s = ScAndSv.const_salt;
                        }
                            ForgetDoneClient.requestLogin(getActivity(), phone, code,
                                    InnovationAlgorithm.SHA1(const_s, editText.getText().toString())
//                                editText.getText().toString()
                                    , new ForgetDoneHandler() {
                                        @Override
                                        public void onLoginSuccess(LoginResponse response) {
                                            super.onLoginSuccess(response);
                                            SharedPreferences mysharedPreferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = mysharedPreferences.edit();
                                            editor.putInt("isLogin", 1).commit();
                                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
                                            SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                            editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                            editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                            editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                                            editor_self.putString("phone", phone);
                                            if (!sharedPreferences.getString("phone", "").equals("")) {
                                                if (!sharedPreferences.getString("phone", "").equals(phone)) {
                                                    editor_self.putString("name", "");
                                                    editor_self.putString("day", "");
                                                    editor_self.putString("sex", "");
                                                }
                                            }
                                            editor_self.commit();
                                            toastOnly.toastShowShort("设置成功");
                                            dialog.dismiss();
                                            if (LoginFrom.getLoginfrom() == 0) {
//                                                Intent intent_finsh = new Intent(getActivity(), MainActivity.class);
//                                                intent_finsh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                intent_finsh.putExtra("position", 2);
//                                                startActivity(intent_finsh);
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
//                                                Intent gointent = new Intent(getActivity(),MyBBsNote.class);
//                                                startActivity(gointent);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==1){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                Intent gointent = new Intent(getActivity(),MyBBsNote.class);
                                                startActivity(gointent);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==2){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                Intent gointent = new Intent(getActivity(),SendNote.class);
                                                startActivity(gointent);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==3){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==4){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                Intent gointent = new Intent(getActivity(),PingLun.class);
                                                startActivity(gointent);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==5){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==6){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                Intent gointent = new Intent(getActivity(),MyRedbag.class);
                                                startActivity(gointent);
                                                getActivity().finish();
                                            }else if(LoginFrom.getLoginfrom()==7){
                                                Intent broad = new Intent(""+100);
                                                getActivity().sendBroadcast(broad);
                                                getActivity().finish();
                                            }
                                        }


                                        @Override
                                        public void onInnovationFailure(String msg) {
                                            super.onInnovationFailure(msg);
                                            dialog.dismiss();
                                            FailMessage.showfail(getActivity(), msg);
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
                                            FailMessage.showfail(getActivity(), value);
                                        }
                                    }, TestOrNot.isTest);

                        }
                }
            });
        }

        return view;
    }
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        View view =((ActionBarActivity)getActivity()).getSupportActionBar().getCustomView();
//        tv_title = (TextView) view.findViewById(R.id.actionbar_title);
//        tv_title.setText("我");
//    }

}
