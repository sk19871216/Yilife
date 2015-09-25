package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.BBsNoteListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ForumsNoteListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BBsNoteListBean> list_item;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<BBsNoteListBean> getList_item() {
        return list_item;
    }

    public void setList_item(ArrayList<BBsNoteListBean> list_item) {
        this.list_item = list_item;
    }

    public ForumsNoteListAdapter(ArrayList<BBsNoteListBean> list_item, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;

        if(convertView == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_forumsnotelist, null);
            holder.tv_titlt = (TextView) view.findViewById(R.id.tv_item_forumsnote_title);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_forumsnote_time);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_forumsnote_count);
            holder.tv_day = (TextView) view.findViewById(R.id.tv_forumsnote_day);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_forumsnote_name);
            holder.imageview = (ImageView) view.findViewById(R.id.iv_forumsnote_icon);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date((list_item.get(position).getPosttime())));
        holder.tv_day.setText(date.substring(0, 10));
        holder.tv_time.setText(date.substring(11, 16));
        holder.tv_titlt.setText(list_item.get(position).getTitle());
        holder.tv_count.setText(""+list_item.get(position).getReply());
        holder.tv_name.setText(list_item.get(position).getPoster());
        if (list_item.get(position).getAttributes().length !=0) {
            for (int i = 0; i < list_item.get(position).getAttributes().length; i++) {
                if (list_item.get(position).getAttributes()[i] == 100) {
                    holder.imageview.setVisibility(View.VISIBLE);
                    holder.imageview.setImageResource(R.drawable.icon_flag_jing);
                    break;
                } else if (list_item.get(position).getAttributes()[i] == 101) {
                    holder.imageview.setVisibility(View.VISIBLE);
                    holder.imageview.setImageResource(R.drawable.icon_flag_jing);
                    break;
                } else if (list_item.get(position).getAttributes()[i] == 102){
                    holder.imageview.setVisibility(View.VISIBLE);
                    holder.imageview.setImageResource(R.drawable.icon_flag_new);
                }else{
                    holder.imageview.setVisibility(View.INVISIBLE);
                }
            }
        }else{
            holder.imageview.setVisibility(View.INVISIBLE);
        }

        return view;

    }
    static class ViewHolder {
        TextView tv_titlt,tv_time,tv_count,tv_day,tv_name;
        ImageView imageview;
    }
}
