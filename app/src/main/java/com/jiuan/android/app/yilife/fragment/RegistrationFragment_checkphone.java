package com.jiuan.android.app.yilife.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneClient;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneHandler;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneClient;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;


public class RegistrationFragment_checkphone extends Fragment {
    private Button buttonnext;
    private EditTextWithDel editText;
    private ProgressDialog dialog;
    private String request;
    private TextView tv_bar_title;
    private ActionBar actionBar;
    private ToastOnly toastOnly;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_registration, container, false);
        tv_bar_title = (TextView) getActivity().findViewById(R.id.blue_title);
//        tv_title = (TextView) getActivity().findViewById(R.id.actionbar_title);
//        tv_title.setText("我的");
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(getActivity());
        Bundle bundle = getArguments();
        request = bundle.getString("request");
        editText= (EditTextWithDel) view.findViewById(R.id.registration_edittext_phone);
        buttonnext = (Button)view.findViewById(R.id.login_button_checkphone);
        if (request.equals("woFragment")){
            tv_bar_title.setText("注册1/3");
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (NetWorkInfo.isNetworkAvailable(getActivity())) {
                dialog.show();
                if (editText.getText().length()!=11){
                    toastOnly.toastShowShort("手机号应为11位数字");
                    dialog.dismiss();
                }else {
                    RegistionCheckphoneClient.requestLogin(getActivity(), editText.getText().toString()
                            , new RegistionCheckphoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);
                            dialog.dismiss();
                            RegistrationFragment_checknumber fragment = new RegistrationFragment_checknumber();
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", editText.getText().toString());
                            bundle.putString("request", request);
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registration_linearlayout, fragment).addToBackStack(null).commit();
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
                }else{
                    dialog.dismiss();
                    toastOnly.toastShowShort("请检查您的网络环境");
                }
            }
        });
        }else if (request.equals("forget")) {
                tv_bar_title.setText("忘记密码1/3");
                buttonnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (NetWorkInfo.isNetworkAvailable(getActivity())) {
                        dialog.show();
                        if (editText.getText().length() != 11) {
                            toastOnly.toastShowShort("手机号应为11位数字");
                            dialog.dismiss();
                        } else {
                            ForgetPasswordPhoneClient.requestLogin(getActivity(), editText.getText().toString()
                                    , new ForgetPasswordPhoneHandler() {
                                @Override
                                public void onInnovationSuccess(JsonElement value) {
                                    super.onInnovationSuccess(value);
                                    dialog.dismiss();
                                    Log.d("value", "" + value);
                                    RegistrationFragment_checknumber fragment = new RegistrationFragment_checknumber();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phone", editText.getText().toString());
                                    bundle.putString("request", request);
                                    fragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registration_linearlayout, fragment).addToBackStack(null).commit();

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
                        }else{
                            dialog.dismiss();
                            toastOnly.toastShowShort("请检查您的网络环境");
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
