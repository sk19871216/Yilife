package com.jiuan.android.app.yilife.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.forgetpasswordchecknumber.ForgetPasswordChecknumberClient;
import com.jiuan.android.app.yilife.bean.forgetpasswordchecknumber.ForgetPasswordChecknumberHandler;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneClient;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneHandler;
import com.jiuan.android.app.yilife.bean.registionchecknumber.RegistionChecknumberClient;
import com.jiuan.android.app.yilife.bean.registionchecknumber.RegistionChecknumberHandler;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneClient;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;


public class RegistrationFragment_checknumber extends Fragment {
    private Button buttonnext;
    private EditTextWithDel editText;
    private TextView textView,button_get;
    private ProgressDialog dialog;
    private String phone,request,freshtooken;
    private TextView tv_bar_title;
    private int second;
    private ToastOnly toastOnly;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle b = msg.getData();
            int i = b.getInt("time");
            if (i!=0) {
//                button_get.setFocusable(false);
                button_get.setClickable(false);
                button_get.setText(i + "秒");
            }else {
//                button_get.setFocusable(true);
                button_get.setClickable(true);
                button_get.setText("再次获取");
//                button_get.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
                button_get.setTextColor(android.graphics.Color.parseColor("#209df3"));
//                button_get.setBackgroundResource(R.drawable.button_white);
            }
            return false;
        }
    });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_registration2, container, false);
        tv_bar_title = (TextView) getActivity().findViewById(R.id.blue_title);

//        tv_title = (TextView) getActivity().findViewById(R.id.actionbar_title);
//        tv_title.setText("我的");
        button_get = (TextView)view.findViewById(R.id.registration2_button_getchecknumber);
        TimeDesc();

        button_get.setFocusable(false);


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        textView = (TextView) view.findViewById(R.id.registration2_textview_phone);
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        request = bundle.getString("request");
        textView.setText("验证码将发送至"+bundle.getString("phone"));
        editText= (EditTextWithDel) view.findViewById(R.id.registration2_edittext_checknumber);
        buttonnext = (Button)view.findViewById(R.id.registration2_button_checkphone);


        if (request.equals("woFragment")){
        tv_bar_title.setText("注册2/3");
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastOnly = new ToastOnly(getActivity());
                dialog.show();
                if (editText.getText().length()==0){
                    toastOnly.toastShowShort("请输入验证码");
                }else {
                    RegistionChecknumberClient.requestLogin(getActivity(), phone
                            , editText.getText().toString(),
                            new RegistionChecknumberHandler() {
                                @Override
                                public void onInnovationSuccess(JsonElement value) {
                                    super.onInnovationSuccess(value);
                                    dialog.dismiss();
                                    if (value.toString().equals("1")) {
                                        RegistrationFragment_done fragment = new RegistrationFragment_done();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("phone", phone);
                                        bundle.putString("code", editText.getText().toString());
                                        bundle.putString("request", request);
                                        fragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registration_linearlayout, fragment).addToBackStack(null).commit();
                                    } else {
                                        toastOnly.toastShowShort("验证码错误");
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
                            }, TestOrNot.isTest

                    );
                }
            }
        });
            button_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button_get.setText("60秒");
                    TimeDesc();
                    RegistionCheckphoneClient.requestLogin(getActivity(), phone
                            , new RegistionCheckphoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);

                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(getActivity(), msg);
                        }
                    }, TestOrNot.isTest);
                }
            });
        }else if (request.equals("forget")) {
            tv_bar_title.setText("忘记密码2/3");
            buttonnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toastOnly = new ToastOnly(getActivity());
                    dialog.show();
                    if (editText.getText().length()==0){
                        toastOnly.toastShowShort("请输入验证码");
                    }else {
                        ForgetPasswordChecknumberClient.requestLogin(getActivity(), phone, editText.getText().toString()
                                , new ForgetPasswordChecknumberHandler() {
                            @Override
                            public void onInnovationSuccess(JsonElement value) {
                                super.onInnovationSuccess(value);
                                dialog.dismiss();
                                if (value.toString().equals("1")) {
                                    RegistrationFragment_done fragment = new RegistrationFragment_done();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phone", phone);
                                    bundle.putString("code", editText.getText().toString());
                                    bundle.putString("request", request);
                                    fragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registration_linearlayout, fragment).addToBackStack(null).commit();
                                } else {
                                    toastOnly.toastShowShort("验证码错误");
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
            button_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button_get.setText("60秒");
                    TimeDesc();
                    ForgetPasswordPhoneClient.requestLogin(getActivity(), phone
                            , new ForgetPasswordPhoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);

                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(getActivity(), msg);
                        }
                    }, TestOrNot.isTest);
                }
            });
        }
        return view;
    }

        private void TimeDesc(){
//            button_get.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
            button_get.setTextColor(android.graphics.Color.parseColor("#A3A3A3"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=60;i>=0;i--){
                        try {
                            second = i;
                            Thread.sleep(1000);
                            Message msg = new Message();
                            Bundle b = new Bundle();
                            b.putInt("time",i);
                            msg.setData(b);
                            handler.sendMessage(msg);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                         }
                    }
                }
            }).start();

        }

}

