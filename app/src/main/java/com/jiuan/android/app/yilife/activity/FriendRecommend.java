package com.jiuan.android.app.yilife.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.fragment.InvitationCodeFragment;
import com.jiuan.android.app.yilife.fragment.InvitationFriendFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendRecommend extends ParentActivity {
    private TextView tv_share_friend,tv_code,tv_title;
    private FrameLayout fl_share_friend,fl_code;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout linearLayout_friend,linearLayout_code;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_recommend);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("好友推荐");

        tv_share_friend = (TextView) findViewById(R.id.text_invitationfriend);
        fl_share_friend = (FrameLayout) findViewById(R.id.line_invitationfriend);
        tv_code = (TextView) findViewById(R.id.text_invitationcode);
        fl_code = (FrameLayout) findViewById(R.id.line_invitationcode);
        viewPager = (ViewPager) findViewById(R.id.friend_framelayout);
        linearLayout_friend = (LinearLayout) findViewById(R.id.invitation_friend);
        linearLayout_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        linearLayout_code = (LinearLayout) findViewById(R.id.invitation_code);
        linearLayout_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        final List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new InvitationFriendFragment());
        fragments.add(new InvitationCodeFragment());
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }


        };
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    tv_share_friend.setTextColor(android.graphics.Color.parseColor("#209df3"));
                    fl_share_friend.setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
                    tv_code.setTextColor(android.graphics.Color.parseColor("#7d7d80"));
                    fl_code.setBackgroundColor(android.graphics.Color.parseColor("#00ffffff"));
                }else if (position ==1){
                    tv_share_friend.setTextColor(android.graphics.Color.parseColor("#7d7d80"));
                    fl_share_friend.setBackgroundColor(android.graphics.Color.parseColor("#00ffffff"));
                    tv_code.setTextColor(android.graphics.Color.parseColor("#209df3"));
                    fl_code.setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
