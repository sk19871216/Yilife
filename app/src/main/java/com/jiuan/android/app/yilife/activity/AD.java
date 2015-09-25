package com.jiuan.android.app.yilife.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.fragment.splash.Splash1Fragment;
import com.jiuan.android.app.yilife.fragment.splash.Splash2Fragment;
import com.jiuan.android.app.yilife.fragment.splash.Splash3Fragment;

import java.util.ArrayList;

public class AD extends ActionBarActivity {
    private TextView tv_skip;
    private ViewPager viewPager;
    private ArrayList<Fragment> list ;
    private FragmentPagerAdapter adapter;
    private int lastValue=-1,vp_state=0;
    private LinearLayout linearLayout;
    private ArrayList<ImageView> list_iv_point;
    private int ifyes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        tv_skip = (TextView) findViewById(R.id.ad_skip);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_ad);
        list_iv_point = new ArrayList<ImageView>();
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AD.this,MainActivity.class);
                startActivity(intent);
                AD.this.finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.vp_ad);
        list = new ArrayList<Fragment>();
//        SharedPreferences adSharedPreferences= getSharedPreferences("AD", Activity.MODE_PRIVATE);
        list.add(new Splash1Fragment());
        list.add(new Splash2Fragment());
        list.add(new Splash3Fragment());
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
        viewPager.setAdapter(adapter);

        for (int i=0;i<list.size();i++) {
            ImageView iv_point = new ImageView(AD.this);
            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(120, 120);
            iv_point.setPadding(7, 15, 7, 10);
            iv_point.setLayoutParams(params1);
            if (i == 0) {
//                iv_point.setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
                iv_point.setImageResource(R.drawable.ad_blue_point);
            } else {
                iv_point.setImageResource(R.drawable.ad_gray_point);
//                iv_point.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
            }
            linearLayout.addView(iv_point);
            list_iv_point.add(iv_point);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (vp_state == 1){
                    if (lastValue > positionOffsetPixels) {
                        // 递减，向右侧滑动

                       } else if (lastValue < positionOffsetPixels) {
                        // 递减，向右侧滑动
                        if (position == 2){
                            ifyes ++;

                            if (ifyes==1) {
                                Log.e("123123","123123");
                                Intent intent = new Intent(AD.this, MainActivity.class);
                                startActivity(intent);
                                AD.this.finish();
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int j=0;j<list.size();j++){
                    if (j==position){
                        list_iv_point.get(j).setImageResource(R.drawable.ad_blue_point);
//                        list_iv_point.get(j).setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
                    }else{
//                        list_iv_point.get(j).setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
                        list_iv_point.get(j).setImageResource(R.drawable.ad_gray_point);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                vp_state = state;
            }
        });
    }

}
