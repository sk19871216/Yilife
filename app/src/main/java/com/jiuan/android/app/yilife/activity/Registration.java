package com.jiuan.android.app.yilife.activity;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.fragment.RegistrationFragment_checkphone;

public class Registration extends ParentActivity {
    private RegistrationFragment_checkphone fragment;
    private EditText editText;
    private ActionBar actionBar;
    private TextView tv_bar_title,tv_setting;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_new);
//        fragment = (RegistrationFragment_checkphone) getSupportFragmentManager().findFragmentById(R.id.fragment_xx);

//        actionbar();//设置actionbar
        tv_bar_title = (TextView) findViewById(R.id.blue_title);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        Intent intent =getIntent();
        String request = intent.getStringExtra("request");
        if (request.equals("woFragment")){
            tv_bar_title.setText("注册");

        }else{
            tv_bar_title.setText("忘记密码");

        }
        RegistrationFragment_checkphone fragment = new RegistrationFragment_checkphone();
        Bundle bundle = new Bundle();
        bundle.putString("request",request);

        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.registration_linearlayout, fragment).commit();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    private void actionbar(){
        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.item_actionbar);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);
    }

}
