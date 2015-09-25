package com.jiuan.android.app.yilife.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.Apps;
import com.jiuan.android.app.yilife.activity.BBSMain;
import com.jiuan.android.app.yilife.activity.HTML5Activity;
import com.jiuan.android.app.yilife.activity.Huodong;


public class FaxianNewFragment extends Fragment {
    private ImageView app,stroe,bbs,other,iv_setting,huodong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faxiannew, container, false);

        app = (ImageView) view.findViewById(R.id.icon_appnew);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent();
                intent.setClass(getActivity(),Apps.class);
                startActivity(intent);
            }
        });
//        stroe = (ImageView) view.findViewById(R.id.icon_store);

//        stroe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  =  new Intent();
//                intent.setClass(getActivity(),Store.class);
//                startActivity(intent);
//            }
//        });
        huodong = (ImageView) view.findViewById(R.id.icon_hdnew);
        huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent();
                intent.setClass(getActivity(),Huodong.class);
                startActivity(intent);
            }
        });
        bbs = (ImageView) view.findViewById(R.id.icon_bbsnew);
        bbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent();
                intent.setClass(getActivity(),BBSMain.class);
                startActivity(intent);
            }
        });
//        other = (ImageView) view.findViewById(R.id.icon_other);
//        other.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return view;
    }


}

