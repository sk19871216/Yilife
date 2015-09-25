package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.geiallapps.AllappsBean;
import com.jiuan.android.app.yilife.config.FakeX509TrustManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class AppsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AllappsBean> listbeans;
    private  RequestQueue queue;

    public ArrayList<AllappsBean> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<AllappsBean> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public AppsAdapter(Context context, ArrayList<AllappsBean> listbeans){
        this.context = context;
        this.listbeans = listbeans;
        FakeX509TrustManager.allowAllSSL();
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
                view = View.inflate(context, R.layout.item_apps, null);
                holder.text_count = (TextView) view.findViewById(R.id.tv_apps_count);
                holder.text = (TextView) view.findViewById(R.id.text_apps_title);
                holder.imageview_icon = (NetworkImageView) view.findViewById(R.id.item_apps_icon);
                holder.ratingBar = (RatingBar) view.findViewById(R.id.room_ratingbar);

                holder.download = (TextView) view.findViewById(R.id.textview_donwload);
                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
//            holder.imageview_icon.setErrorImageResId(R.drawable.err_icon_iemylife);
//            holder.imageview_icon.setDefaultImageResId(R.drawable.err_icon_iemylife);
            holder.ratingBar.setRating((float)(listbeans.get(position).getRate()));
            holder.text.setText(listbeans.get(position).getAppName());
            holder.text_count.setText(""+(position+1));
//            holder.imageview_icon.setDefaultImageResId(R.drawable.icon_apps_yilife);
//            RequestQueue queue = Volley.newRequestQueue(context);
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
            holder.imageview_icon.setImageUrl(listbeans.get(position).getLogoPath(),loader);


//            holder.download.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
             holder.download.setText(listbeans.get(position).getDownstats());
            return view;
    }
    static class ViewHolder {
        TextView text_count,text,download;
        ImageView imageview_point;
        RatingBar ratingBar;
        NetworkImageView imageview_icon;
    }
}
