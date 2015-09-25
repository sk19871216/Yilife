package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getredbag.GetRedBagListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class MyRedbagAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GetRedBagListBean> listbeans;

    public ArrayList<GetRedBagListBean> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<GetRedBagListBean> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public MyRedbagAdapter(Context context, ArrayList<GetRedBagListBean> listbeans){
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
                view = View.inflate(context, R.layout.item_myredbag, null);
                holder.text_count = (TextView) view.findViewById(R.id.tv_money_detail);
                holder.text = (TextView) view.findViewById(R.id.tv_redbag_useto);
                holder.tv_day = (TextView) view.findViewById(R.id.tv_redbag_day);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_redbag_time);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
//            if (listbeans.get(position).getType()==1){
//                holder.text.setText("抢红包");
//            }else if (listbeans.get(position).getType()==2){
//                holder.text.setText("兑换商品");
//            }
            holder.text.setText(listbeans.get(position).getCampaign());
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(listbeans.get(position).getCreateTS()));
        holder.tv_day.setText(date.substring(0,10));
        holder.tv_time.setText(date.substring(11,16));
        if (listbeans.get(position).getMoney()<0){
            holder.text_count.setText(listbeans.get(position).getMoney()+"");
        }else{
            holder.text_count.setText("\u2000"+listbeans.get(position).getMoney()+"");
        }
            return view;
    }
    static class ViewHolder {
        TextView text_count,text,tv_day,tv_time;
    }
}
