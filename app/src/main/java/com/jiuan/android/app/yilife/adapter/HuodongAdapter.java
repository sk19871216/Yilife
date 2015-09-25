package com.jiuan.android.app.yilife.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.HTML5Activity;
import com.jiuan.android.app.yilife.activity.NoteList;
import com.jiuan.android.app.yilife.bean.Huodong.HuodongBean;
import com.jiuan.android.app.yilife.bean.JinghuaItem;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessClient;
import com.jiuan.android.app.yilife.bean.ShareSuccess.ShareSuccessHandler;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class HuodongAdapter extends BaseAdapter {
    private Context context;
    private RequestQueue queue;
    private ToastOnly toastOnly;
    private ArrayList<HuodongBean> list_item;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<HuodongBean> getList_item() {
        return list_item;
    }

    public void setList_item(ArrayList<HuodongBean> list_item) {
        this.list_item = list_item;
    }

    public HuodongAdapter(ArrayList<HuodongBean> list_item, Context context) {
        this.list_item = list_item;
        this.context = context;
        queue = Volley.newRequestQueue(context);
        toastOnly = new ToastOnly(context);
    }

    public int getCount() {
        return list_item.size();
    }

    @Override
    public Object getItem(int position) {
        return list_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                view = View.inflate(context, R.layout.item_huodong, null);

                holder.tv_titlt = (TextView) view.findViewById(R.id.hd_title);
                holder.tv_time = (TextView) view.findViewById(R.id.hd_time);
                holder.iv_state = (ImageView) view.findViewById(R.id.hd_state);
                holder.iv_hd = (ImageView) view.findViewById(R.id.hd_hd);
                holder.iv_jiantou = (ImageView) view.findViewById(R.id.hd_jiantou);
                holder.iv_hideline = (TextView) view.findViewById(R.id.hd_hideline);
                holder.iv_hdshare = (TextView) view.findViewById(R.id.hd_share);
                holder.hd_item = (LinearLayout) view.findViewById(R.id.hd_item);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
        holder.hd_item.setClickable(false);
        if (position==0){
            holder.iv_jiantou.setImageResource(R.drawable.hd_left);
            holder.iv_hideline.setVisibility(View.INVISIBLE);
        }else{
            holder.iv_jiantou.setImageResource(R.drawable.hd_down);
            holder.iv_hideline.setVisibility(View.VISIBLE);
        }
        holder.tv_titlt.setText(list_item.get(position).getDisplayName());
        holder.iv_hdshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_item.get(position).getState()==1) {
                    SharedPreferences mysharedPreferences = context.getSharedPreferences("huodong", 0);
                    SharedPreferences.Editor editor = mysharedPreferences.edit();
                    editor.putInt("huodongid", list_item.get(position).getCampaignID()).commit();
                    Intent intent = new Intent("" + 23);
                    intent.putExtra("hd_position", position);
                    context.sendBroadcast(intent);
                }else if (list_item.get(position).getState()==2){
                    toastOnly.toastShowShort("活动已结束");
                }
            }
        });
        String datestart = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list_item.get(position).getStartTime()));
        String dateend = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list_item.get(position).getEndTime()));
        holder.tv_time.setText(datestart.substring(0,10));
        if (list_item.get(position).getState()==1) {
            holder.iv_state.setImageResource(R.drawable.hd_counting);
        }else{
            holder.iv_state.setImageResource(R.drawable.hd_closing);
        }

        final ViewHolder finalHolder = holder;
        ImageRequest imageRequest = new ImageRequest(list_item.get(position).getBannerForApp(),new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                finalHolder.iv_hd.setImageBitmap(response);

            }
        },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        finalHolder.iv_hd.setImageResource(R.drawable.loadingerr);

                    }
                });
        queue.add(imageRequest);
        holder.iv_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_item.get(position).getState()==1) {
                    ShareSuccessClient.request(context, list_item.get(position).getCampaignID(), 1, new ShareSuccessHandler() {
                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                        }

                        @Override
                        public void onLoginSuccess(String response) {
                            super.onLoginSuccess(response);
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                        }
                    }, TestOrNot.isTest);
                    Intent intenth5 = new Intent(context, HTML5Activity.class);
                    intenth5.putExtra("path", list_item.get(position).getCamH5Url());
                    context.startActivity(intenth5);
                }else if (list_item.get(position).getState()==2){
                    toastOnly.toastShowShort("活动已结束");
                }
            }
        });
            return view;

    }
    static class ViewHolder {
        TextView tv_titlt,tv_time,iv_hideline,iv_hdshare;
        ImageView iv_hd,iv_state,iv_jiantou;
        LinearLayout hd_item;
    }
}
