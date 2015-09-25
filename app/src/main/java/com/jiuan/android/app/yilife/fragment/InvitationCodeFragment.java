package com.jiuan.android.app.yilife.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.BangDingOrNot;
import com.jiuan.android.app.yilife.bean.InvivationCode.InvitationCodeClient;
import com.jiuan.android.app.yilife.bean.InvivationCode.InvitationCodeHandler;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaClient;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaHandler;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaResponse;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;


/**
 * Created by Administrator on 2015/1/8.
 */
public class InvitationCodeFragment extends Fragment {
    private EditText editText;
    private TextView textView;
    private SharedPreferences mysharedPreferences;
    private ProgressDialog progressDialog;
    private String un,tooken,code;
    private ToastOnly toastOnly;
    private LinearLayout layout_true,layout_false;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitationcode, container, false);
        editText = (EditText) view.findViewById(R.id.et_invitationcode);
        textView = (TextView) view.findViewById(R.id.tv_commit_invitationcode);
        mysharedPreferences = getActivity().getSharedPreferences("self", 0);
        un = mysharedPreferences.getString("HGUID", "");
        tooken= mysharedPreferences.getString("AccessToken","");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(getActivity());
        layout_false = (LinearLayout) view.findViewById(R.id.linearlayout_isinvited_false);
        layout_true = (LinearLayout) view.findViewById(R.id.linearlayout_isinvited_true);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().equals("")) {
                    progressDialog.show();
                    InvitationCodeClient.request(getActivity(), un, tooken, editText.getText().toString().trim(), new InvitationCodeHandler() {
                        @Override
                        public void onLoginSuccess(String response) {
                            super.onLoginSuccess(response);
                            progressDialog.dismiss();
                            toastOnly.toastShowShort("温馨提示：已成功建立关系，谢谢您的支持");
                            layout_true.setVisibility(View.VISIBLE);
                            layout_false.setVisibility(View.GONE);
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            progressDialog.dismiss();
                            FailMessage.showfail(getActivity(), msg);
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            progressDialog.dismiss();
                            FailMessage.showfail(getActivity(), value);
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "网络不好，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }, TestOrNot.isTest);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyErWeiMaClient.request(getActivity(), un, tooken, new MyErWeiMaHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);

            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);

            }

            @Override
            public void onLoginSuccess(MyErWeiMaResponse response) {
                super.onLoginSuccess(response);
                if (response.isInvited()){
                    layout_true.setVisibility(View.VISIBLE);
                    layout_false.setVisibility(View.GONE);
                }else{
                    layout_true.setVisibility(View.GONE);
                    layout_false.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
            }
        }, TestOrNot.isTest);
    }
}
