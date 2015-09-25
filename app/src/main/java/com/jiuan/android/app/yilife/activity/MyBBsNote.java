package com.jiuan.android.app.yilife.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.mybbsnote.MyBBSNoteClient;
import com.jiuan.android.app.yilife.fragment.MyBBsNoteListFragment;
import com.jiuan.android.app.yilife.fragment.MyBBsNoteListFragment3;
import com.jiuan.android.app.yilife.fragment.MyBBsNoteListFragment4;

import java.util.ArrayList;

public class MyBBsNote extends ParentActivity implements View.OnClickListener{
    private TextView tv_list1,tv_list2,tv_list3,iv_title,iv_setting;
    private ViewPager vp;
    private FragmentPagerAdapter adapter;
    private ArrayList<Fragment> list;
    private ImageView iv1,iv2,iv3,iv_back;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_mybbsnotelist1:
                setposition1();
                break;
            case R.id.tv_mybbsnotelist2:
                setposition2();
                break;
            case R.id.tv_mybbsnotelist3:
                setposition3();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bbs_note);


        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBBSNoteClient.cancelRequests(MyBBsNote.this,true);
                onBackPressed();
            }
        });
        iv_title = (TextView) findViewById(R.id.blue_title);
        iv_title.setText("我的发帖");
        iv_setting = (TextView) findViewById(R.id.blue_setting);
        iv_setting.setVisibility(View.GONE);

        list = new ArrayList<Fragment>();
        list.add(new MyBBsNoteListFragment());
        list.add(new MyBBsNoteListFragment4());
//        list.add(new MyBBsNoteListFragment2());
        list.add(new MyBBsNoteListFragment3());
        vp = (ViewPager) findViewById(R.id.mybbsnotelist);
        vp.setOffscreenPageLimit(2);

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
        vp.setAdapter(adapter );
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    setposition1();
                }else if (position==1){
                    setposition2();
                }else if (position==2){
                    setposition3();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tv_list1 = (TextView) findViewById(R.id.tv_mybbsnotelist1);
        tv_list1.setOnClickListener(this);
        tv_list2 = (TextView) findViewById(R.id.tv_mybbsnotelist2);
        tv_list2.setOnClickListener(this);
        tv_list3 = (TextView) findViewById(R.id.tv_mybbsnotelist3);
        tv_list3.setOnClickListener(this);
        iv1 = (ImageView) findViewById(R.id.iv_mybbsnotelist1);
        iv2 = (ImageView) findViewById(R.id.iv_mybbsnotelist2);
        iv3 = (ImageView) findViewById(R.id.iv_mybbsnotelist3);


    }

      public void setposition1(){
            tv_list1.setTextColor(android.graphics.Color.parseColor("#209df3"));
            iv1.setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
          tv_list2.setTextColor(android.graphics.Color.parseColor("#000000"));
          iv2.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
          tv_list3.setTextColor(android.graphics.Color.parseColor("#000000"));
          iv3.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
          vp.setCurrentItem(0);
      }
    public void setposition2(){
        tv_list1.setTextColor(android.graphics.Color.parseColor("#000000"));
        iv1.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
        tv_list2.setTextColor(android.graphics.Color.parseColor("#209df3"));
        iv2.setBackgroundColor( android.graphics.Color.parseColor("#209df3"));
        tv_list3.setTextColor(android.graphics.Color.parseColor("#000000"));
        iv3.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
        vp.setCurrentItem(1);
    }
    public void setposition3(){
        tv_list1.setTextColor(android.graphics.Color.parseColor("#000000"));
        iv1.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
        tv_list2.setTextColor(android.graphics.Color.parseColor("#000000"));
        iv2.setBackgroundColor( android.graphics.Color.parseColor("#00ffffff"));
        tv_list3.setTextColor(android.graphics.Color.parseColor("#209df3"));
        iv3.setBackgroundColor( android.graphics.Color.parseColor("#209df3"));
        vp.setCurrentItem(2);
    }

}
