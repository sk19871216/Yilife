package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.recommend.RecommendDetailBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class RecommendDetailAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RecommendDetailBean> listbeans;
    private boolean istwoentry=false;
    public ArrayList<RecommendDetailBean> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<RecommendDetailBean> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public RecommendDetailAdapter(Context context, ArrayList<RecommendDetailBean> listbeans){
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
            view = View.inflate(context, R.layout.item_recommend_detail, null);
            holder.tv_recommend_from = (TextView) view.findViewById(R.id.recommend_from);
            holder.tv_recommend_detail_ts = (TextView) view.findViewById(R.id.recommend_detail_ts);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        if (listbeans.get(position).isLower()) {
            if (!listbeans.get(position).getDisplayName().equals("")) {
                holder.tv_recommend_from.setText(listbeans.get(position).getDisplayName() + "的下级用户购买产品获得收益" + df.format(listbeans.get(position).getQuantity()) + "元");
            }else{
                String phone = listbeans.get(position).getMobile();
                if (!phone.equals("")) {
                    String phone_secret = phone.substring(0, 3) + "****" + phone.substring(7, 11);
                    holder.tv_recommend_from.setText(phone_secret + "的下级用户购买产品获得收益" + df.format(listbeans.get(position).getQuantity()) + "元");
                }
            }
        }else{
            if (!listbeans.get(position).getDisplayName().equals("")) {
                holder.tv_recommend_from.setText("用户"+listbeans.get(position).getDisplayName()+"购买产品获得收益"+df.format(listbeans.get(position).getQuantity())+"元");
            }else{
                String phone = listbeans.get(position).getMobile();
                if (!phone.equals("")) {
                    String phone_secret = phone.substring(0, 3) + "****" + phone.substring(7, 11);
                    holder.tv_recommend_from.setText("用户"+phone_secret+"购买产品获得收益"+df.format(listbeans.get(position).getQuantity())+"元");
                }
            }
        }
        String date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(listbeans.get(position).getOrderTS()));
        holder.tv_recommend_detail_ts.setText(date.substring(0,10));
        return view;
    }

    static class ViewHolder {
        TextView tv_recommend_from,tv_recommend_detail_ts;
    }
}
