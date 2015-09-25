package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class LifeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private Boolean isNew;

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public LifeAdapter(Context context ,ArrayList<String> list,Boolean isNew){
        this.context = context;
        this.list = list;
        this.isNew = isNew;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
                view = View.inflate(context, R.layout.item_faxian, null);
                holder.text = (TextView) view.findViewById(R.id.item_faxian_text);
                holder.imageview = (ImageView) view.findViewById(R.id.item_faxian_imageview);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.text.setText(list.get(position));
            holder.imageview.setVisibility(View.INVISIBLE);
            return view;

    }
    static class ViewHolder {
        TextView text;
        ImageView imageview;
    }
}
