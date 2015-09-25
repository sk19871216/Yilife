package com.jiuan.android.app.yilife.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.ShowNote;
import com.jiuan.android.app.yilife.adapter.JinghuaAdapter;
import com.jiuan.android.app.yilife.bean.JinghuaItem;
import com.jiuan.android.app.yilife.bean.jinghualist.JinghualistClient;
import com.jiuan.android.app.yilife.bean.jinghualist.JinghualistHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/8.
 */
public class JingHuaFragment extends Fragment {
    private ListView listView;
    private ProgressDialog dialog;
    private JinghuaAdapter adapter;
    private ArrayList<JinghuaItem> list;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jinghua, container, false);
//        tv_title = (TextView) getActivity().findViewById(R.id.actionbar_title);
//        tv_title.setText("宜生活");
        list = new ArrayList<JinghuaItem>();
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.show();
        if (NetWorkInfo.isNetworkAvailable(getActivity())) {
            JinghualistClient.request(getActivity(), new JinghualistHandler() {
                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(getActivity(), "获取失败");
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
                    Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoginSuccess(JinghuaItem[] response) {
                    super.onLoginSuccess(response);
                    Log.d("responsesiz111e", "" + response.length);
                    dialog.dismiss();
                    if (response.length != 0) {
                        for (int i = 0; i < response.length; i++) {
                            list.add(response[i]);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            Toast.makeText(getActivity(),"请检查您的网络环境",Toast.LENGTH_SHORT).show();
        }
        adapter = new JinghuaAdapter(list,getActivity());
        listView = (ListView) view.findViewById(R.id.listview_bbs_jinghua);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int topicid = list.get(position).getTopicID();
                Intent intent = new Intent(getActivity(),ShowNote.class);
                intent.putExtra("title",list.get(position).getForumName().trim());
                intent.putExtra("topicid",topicid);
                startActivity(intent);
            }
        });
        return view;
    }
}
