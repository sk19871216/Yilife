package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getpinglunlist.PinglunlistBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class PinglunAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PinglunlistBean> listbeans;
    private boolean istwoentry=false;
    public ArrayList<PinglunlistBean> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<PinglunlistBean> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public PinglunAdapter(Context context, ArrayList<PinglunlistBean> listbeans){
        this.context = context;
        this.listbeans = listbeans;
    }

    public int getCount() {
        return listbeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listbeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                view = View.inflate(context, R.layout.item_pinglun_detail, null);
                holder.title = (TextView) view.findViewById(R.id.tv_pinglun_title);
                holder.count = (TextView) view.findViewById(R.id.tv_pinglun_index);
                holder.day = (TextView) view.findViewById(R.id.tv_pinglun_day);
                holder.time = (TextView) view.findViewById(R.id.tv_pinglun_time);
                holder.user = (TextView) view.findViewById(R.id.tv_pinglun_user);
                holder.neirong = (TextView) view.findViewById(R.id.tv_pinglun_neirong);
                holder.ratingBar = (RatingBar) view.findViewById(R.id.room_ratingbar_detail);
                holder.iv = (ImageView) view.findViewById(R.id.iv_pinglun_showall);
                holder.iv_up = (ImageView) view.findViewById(R.id.iv_pinglun_up);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            float a = 0;
            if(listbeans.get(position)!=null){
                 a = (float) listbeans.get(position).getRate();
            }else{
                a = 5.0f;
            }

            holder.ratingBar.setRating(a);
            holder.title.setText(listbeans.get(position).getTitle());
            holder.count.setText((position+1)+".");
            String date =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(listbeans.get(position).getCreateTime()));

            holder.day.setText(date.substring(0,10));
            holder.time.setText(date.substring(11,16));
            holder.user.setText(listbeans.get(position).getFromUser());
            holder.neirong.setText(listbeans.get(position).getContent());
            String b = listbeans.get(position).getContent().toString().trim();
            if (b.indexOf("\n")!=-1) {
                if (b.substring(b.indexOf("\n")).indexOf("\n") != -1) {
                    istwoentry = true;
                } else {
                    istwoentry = false;
                }
            }else{
                istwoentry = false;
            }
            if (listbeans.get(position).getContent().trim().length()>45){
                holder.neirong.setMaxLines(2);
                holder.iv.setVisibility(View.VISIBLE);
                holder.iv_up.setVisibility(View.GONE);
            }else{
                holder.neirong.setMaxLines(99);
                holder.iv.setVisibility(View.GONE);
                holder.iv_up.setVisibility(View.GONE);
            }
            final ViewHolder finalHolder = holder;
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.neirong.setMaxLines(99);
                    finalHolder.iv.setVisibility(View.GONE);
                    finalHolder.iv_up.setVisibility(View.VISIBLE);
                }
            });
        holder.iv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.neirong.setMaxLines(2);
                finalHolder.iv_up.setVisibility(View.GONE);
                finalHolder.iv.setVisibility(View.VISIBLE);
            }
        });
            return view;
    }
    static class ViewHolder {
        TextView title,neirong,time,user,count,day;
        RatingBar ratingBar;
        ImageView iv,iv_up;
    }
}
