package com.jiuan.android.app.yilife.fragment.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;


public class Splash2Fragment extends Fragment {
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash2, container, false);
//        textView = (TextView) view.findViewById(R.id.skip_splash);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;

    }
}
