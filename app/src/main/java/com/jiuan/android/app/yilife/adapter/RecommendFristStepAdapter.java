package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.recommend.RecommendReferralsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class RecommendFristStepAdapter extends BaseAdapter {
    private Context context;
    private RequestQueue queue;
    private ArrayList<RecommendReferralsBean> listbeans;
    private boolean istwoentry=false;
    public ArrayList<RecommendReferralsBean> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<RecommendReferralsBean> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public RecommendFristStepAdapter(Context context, ArrayList<RecommendReferralsBean> listbeans){
        this.context = context;
        this.listbeans = listbeans;
        queue = Volley.newRequestQueue(context);
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
                view = View.inflate(context, R.layout.item_recommendfirststep, null);
                holder.title = (TextView) view.findViewById(R.id.item_firststep_name);
                holder.phone = (TextView) view.findViewById(R.id.item_firststep_phone);
                holder.touxiang = (ImageView) view.findViewById(R.id.item_firststep_touxiang);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            if (!listbeans.get(position).getNickName().equals("")){
                holder.title.setText("昵称：" + listbeans.get(position).getNickName());
            }else{
                holder.title.setText("昵称："+"宜生活用户");
            }
            if (!listbeans.get(position).getMobile().equals("")) {
                String phone_xxx = listbeans.get(position).getMobile().substring(0, 3) + "****" + listbeans.get(position).getMobile().substring(7, 11);
                holder.phone.setText("用户名："+phone_xxx);
            }
            if (listbeans.get(position).getPhotoUrl().equals("")){
                holder.touxiang.setImageResource(R.drawable.touxiang);
            }else {
                final ViewHolder finalHolder = holder;
                ImageRequest imageRequest = new ImageRequest(listbeans.get(position).getPhotoUrl(), new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        finalHolder.touxiang.setImageBitmap(response);
                    }
                },
                        finalHolder.touxiang.getWidth(),
                        finalHolder.touxiang.getHeight(),
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                finalHolder.touxiang.setImageResource(R.drawable.touxiang);
                            }
                        });
                queue.add(imageRequest);
            }
            return view;
    }

    static class ViewHolder {
        TextView title,phone;
        ImageView touxiang;
    }
}
