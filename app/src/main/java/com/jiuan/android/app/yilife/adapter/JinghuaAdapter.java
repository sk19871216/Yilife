package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.NoteList;
import com.jiuan.android.app.yilife.bean.JinghuaItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class JinghuaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<JinghuaItem> list_item;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<JinghuaItem> getList_item() {
        return list_item;
    }

    public void setList_item(ArrayList<JinghuaItem> list_item) {
        this.list_item = list_item;
    }

    public JinghuaAdapter(ArrayList<JinghuaItem> list_item, Context context) {
        this.list_item = list_item;
        this.context = context;
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
                view = View.inflate(context, R.layout.item_jinghua, null);

                holder.layout = (LinearLayout) view.findViewById(R.id.linearlayout_jinghua);
                holder.tv_titlt = (TextView) view.findViewById(R.id.tv_item_jinghua_title);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_jinghuaitem_time);
                holder.tv_count = (TextView) view.findViewById(R.id.tv_jinghuaitem_count);
                holder.tv_day = (TextView) view.findViewById(R.id.tv_jinghuaitem_day);
                holder.imageview = (NetworkImageView) view.findViewById(R.id.iv_jinghuaitem_icon);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.tv_titlt.setText(list_item.get(position).getTitle());
//            holder.tv_time.setText(list_item.get(position).getPosttime()+"");
//        Log.d("posttime",""+list_item.get(position).getPosttime());
//            holder.tv_day.setText(list_item.get(position).get
//        Long timestamp = Long.parseLong(list_item.get(position).getTime()+"");
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list_item.get(position).getTime()));
        holder.tv_day.setText(date.substring(0,10));
        holder.tv_time.setText(date.substring(11,16));
        holder.tv_count.setText(""+list_item.get(position).getReply());
//            holder.imageview.setDefaultImageResId(R.drawable.icon_aijiaren);

            holder.layout.setBackgroundColor(android.graphics.Color.parseColor(list_item.get(position).getColor()));
        RequestQueue queue = Volley.newRequestQueue(context);
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
        holder.imageview.setImageUrl(list_item.get(position).getIcon(), loader);
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NoteList.class);
                intent.putExtra("bankuai",list_item.get(position).getForumName());
                intent.putExtra("forums",list_item.get(position).getForumID());
                context.startActivity(intent);
            }
        });
            return view;

    }
    static class ViewHolder {
        TextView tv_titlt,tv_time,tv_count,tv_day;
        NetworkImageView imageview;
        LinearLayout layout;
    }
}
