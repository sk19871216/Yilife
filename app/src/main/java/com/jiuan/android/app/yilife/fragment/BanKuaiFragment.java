package com.jiuan.android.app.yilife.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.NoteList;
import com.jiuan.android.app.yilife.bean.ForumsItem;
import com.jiuan.android.app.yilife.bean.getForums.GetForumsClient;
import com.jiuan.android.app.yilife.bean.getForums.GetForumsHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/1/8.
 */
public class BanKuaiFragment extends Fragment {
    private GridLayout layout;
    private NetworkImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    private View.OnClickListener listener;
    private TextView tv_bar_title,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    private ImageView iv_back;
    private ArrayList<String> list_title;
    private ArrayList<Integer> list_forumsid;
    private ProgressDialog dialog;
    private LinearLayout layout_yilife,layout_aijiaren,layout_meeting,layout_forum,layout_fankui,layout_shouhou,layout_wenda,layout_other;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bankuai, container, false);
        list_forumsid = new ArrayList<Integer>();
        list_title = new ArrayList<String>();
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.show();
        iv1= (NetworkImageView) view.findViewById(R.id.iv_1);
//        iv1.setDefaultImageResId(R.drawable.icon_iemylife2);
        iv2= (NetworkImageView) view.findViewById(R.id.iv_2);
//        iv2.setDefaultImageResId(R.drawable.icon_aijiaren);
        iv3= (NetworkImageView) view.findViewById(R.id.iv_3);
//        iv3.setDefaultImageResId(R.drawable.icon_meeting);
        iv4= (NetworkImageView) view.findViewById(R.id.iv_4);
//        iv4.setDefaultImageResId(R.drawable.icon_forum);
        iv5= (NetworkImageView) view.findViewById(R.id.iv_5);
//        iv5.setDefaultImageResId(R.drawable.icon_chanpinfankui);
        iv6= (NetworkImageView) view.findViewById(R.id.iv_6);
//        iv6.setDefaultImageResId(R.drawable.icon_shouhoufuwu);
        iv7= (NetworkImageView) view.findViewById(R.id.iv_7);
//        iv7.setDefaultImageResId(R.drawable.icon_wenda);
        iv8= (NetworkImageView) view.findViewById(R.id.iv_8);
        iv8.setDefaultImageResId(R.drawable.icon_qidai);

        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        tv4 = (TextView) view.findViewById(R.id.tv_4);
        tv5 = (TextView) view.findViewById(R.id.tv_5);
        tv6 = (TextView) view.findViewById(R.id.tv_6);
        tv7 = (TextView) view.findViewById(R.id.tv_7);
        if (NetWorkInfo.isNetworkAvailable(getActivity())) {
            GetForumsClient.request(getActivity(), new GetForumsHandler() {
                @Override
                public void onLoginSuccess(ForumsItem[] response) {
                    super.onLoginSuccess(response);
                    dialog.dismiss();
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache(

                    ) {
                        @Override
                        public Bitmap getBitmap(String url) {
                            return null;
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {

                        }
                    });
                    iv1.setImageUrl(response[0].getIcon(), loader);
                    iv2.setImageUrl(response[1].getIcon(), loader);
                    iv3.setImageUrl(response[2].getIcon(), loader);
                    iv4.setImageUrl(response[3].getIcon(), loader);
                    iv5.setImageUrl(response[4].getIcon(), loader);
                    iv6.setImageUrl(response[5].getIcon(), loader);
                    iv7.setImageUrl(response[6].getIcon(), loader);

                    tv1.setText(response[0].getTitle().trim());
                    tv2.setText(response[1].getTitle().trim());
                    tv3.setText(response[2].getTitle().trim());
                    tv4.setText(response[3].getTitle().trim());
                    tv5.setText(response[4].getTitle().trim());
                    tv6.setText(response[5].getTitle().trim());
                    tv7.setText(response[6].getTitle().trim());

                    for (int i = 0; i < response.length; i++) {
                        list_forumsid.add(response[i].getForumID());
                        list_title.add(response[i].getTitle().trim());
                    }

                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(getActivity(), msg);
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
                    dialog.dismiss();
                    FailMessage.showfail(getActivity(), value);
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();
                }
            }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            Toast.makeText(getActivity(),"请检查您的网络环境",Toast.LENGTH_SHORT).show();
        }
        layout_yilife = (LinearLayout) view.findViewById(R.id.layout_bankuai_yilife);
        layout_aijiaren = (LinearLayout) view.findViewById(R.id.layout_bankuai_aijiaren);
        layout_meeting = (LinearLayout) view.findViewById(R.id.layout_bankuai_banzhuhuiyishi);
        layout_forum = (LinearLayout) view.findViewById(R.id.layout_bankuai_luntanshiwu);
        layout_fankui = (LinearLayout) view.findViewById(R.id.layout_bankuai_chanpingfankui);
        layout_shouhou = (LinearLayout) view.findViewById(R.id.layout_bankuai_shouhoufuwu);
        layout_wenda = (LinearLayout) view.findViewById(R.id.layout_bankuai_wenda);
        layout_other = (LinearLayout) view.findViewById(R.id.layout_bankuai_other);
        layout_yilife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(0));
                    intent.putExtra("forums", list_forumsid.get(0));
                    startActivity(intent);
                }
            }
        });
        layout_aijiaren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(1));
                    intent.putExtra("forums", list_forumsid.get(1));
                    startActivity(intent);
                }
            }
        });
        layout_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(2));
                    intent.putExtra("forums", list_forumsid.get(2));
                    startActivity(intent);
                }
            }
        });
        layout_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(3));
                    intent.putExtra("forums", list_forumsid.get(3));
                    startActivity(intent);
                }
            }
        });
        layout_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(4));
                    intent.putExtra("forums", list_forumsid.get(4));
                    startActivity(intent);
                }
            }
        });
        layout_shouhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(5));
                    intent.putExtra("forums", list_forumsid.get(5));
                    startActivity(intent);
                }
            }
        });
        layout_wenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_forumsid.size()!=0) {
                    Intent intent = new Intent(getActivity(), NoteList.class);
                    intent.putExtra("bankuai", list_title.get(6));
                    intent.putExtra("forums", list_forumsid.get(6));
                    startActivity(intent);
                }
            }
        });
//        layout_other.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),NoteList.class);
//                intent.putExtra("bankuai",list_title.get(7));
//                intent.putExtra("forums",list_forumsid.get(7));
//                startActivity(intent);
//            }
//        });
        return view;
    }
}
