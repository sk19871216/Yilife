package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.fragment.BanKuaiFragment;
import com.jiuan.android.app.yilife.fragment.JingHuaFragment;

import java.util.ArrayList;


public class BBSMain extends ParentActivity {
    private ViewPager vp;
    private ArrayList<Fragment> list;
    private ActionBar actionBar;
    private FragmentPagerAdapter adapter;
    private TextView tv_jinghua,tv_bankuai,tv_bar_title;
    private ImageView iv_jinghua,iv_bankuai,iv_back,iv_setting;
    private LinearLayout layout_jinghua,layout_bankuai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbsmain);
        list = new ArrayList<Fragment>();
        initialize();//初始化


        list.add(new JingHuaFragment());
        list.add(new BanKuaiFragment());
        iv_setting = (ImageView) findViewById(R.id.action_bar_setting);
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                if (mysharedPreferences.getInt("isLogin",-1)==1) {
                    Intent intent_wo = new Intent(BBSMain.this, MyBBsNote.class);
                    startActivity(intent_wo);
                }else{
                    Intent intent_login = new Intent(BBSMain.this,LoginNormal.class);
                    LoginFrom.setLoginfrom(1);
                    Toast.makeText(BBSMain.this,"请先登录",Toast.LENGTH_SHORT).show();
                    startActivity(intent_login);
                }
            }
        });
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                 if (position==0){
                     tv_jinghua.setTextColor(Color.parseColor("#209df3"));
                     tv_bankuai.setTextColor(Color.parseColor("#000000"));
                     iv_jinghua.setBackgroundColor(Color.parseColor("#209df3"));
                     iv_bankuai.setBackgroundColor(Color.parseColor("#00ffffff"));
                 }else if (position==1){
                     tv_jinghua.setTextColor(Color.parseColor("#000000"));
                     tv_bankuai.setTextColor(Color.parseColor("#209df3"));
                     iv_jinghua.setBackgroundColor(Color.parseColor("#00ffffff"));
                     iv_bankuai.setBackgroundColor(Color.parseColor("#209df3"));
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initialize(){
        vp = (ViewPager) findViewById(R.id.viewpager_bbsmain);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_jinghua = (ImageView) findViewById(R.id.iv_bbsmain_jinghua);
        iv_bankuai = (ImageView) findViewById(R.id.iv_bbsmain_bankuai);
        tv_jinghua = (TextView) findViewById(R.id.tv_bbsmain_jinghua);
        tv_bankuai = (TextView) findViewById(R.id.tv_bbsmain_bankuai);
        tv_jinghua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jinghua.setTextColor(Color.parseColor("#209df3"));
                tv_bankuai.setTextColor(Color.parseColor("#000000"));
                iv_jinghua.setBackgroundColor(Color.parseColor("#209df3"));
                iv_bankuai.setBackgroundColor(Color.parseColor("#00ffffff"));
                vp.setCurrentItem(0);
            }
        });
        tv_bankuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jinghua.setTextColor(Color.parseColor("#000000"));
                tv_bankuai.setTextColor(Color.parseColor("#209df3"));
                iv_jinghua.setBackgroundColor(Color.parseColor("#00ffffff"));
                iv_bankuai.setBackgroundColor(Color.parseColor("#209df3"));
                vp.setCurrentItem(1);
            }
        });
        layout_jinghua = (LinearLayout) findViewById(R.id.layout_bbsmain_jinghua);
        layout_bankuai = (LinearLayout) findViewById(R.id.layout_bbsmain_bankuai);
        layout_jinghua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jinghua.setTextColor(Color.parseColor("#209df3"));
                tv_bankuai.setTextColor(Color.parseColor("#000000"));
                iv_jinghua.setBackgroundColor(Color.parseColor("#209df3"));
                iv_bankuai.setBackgroundColor(Color.parseColor("#00ffffff"));
                vp.setCurrentItem(0);
            }
        });
        layout_bankuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jinghua.setTextColor(Color.parseColor("#000000"));
                tv_bankuai.setTextColor(Color.parseColor("#209df3"));
                iv_jinghua.setBackgroundColor(Color.parseColor("#00ffffff"));
                iv_bankuai.setBackgroundColor(Color.parseColor("#209df3"));
                vp.setCurrentItem(1);
            }
        });
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
    }

}
