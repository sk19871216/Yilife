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
public class SelectSexAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> listbeans;
    private ArrayList<String> list_string;
    public ArrayList<Integer> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<Integer> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public SelectSexAdapter(Context context, ArrayList<Integer> listbeans){
        this.context = context;
        this.listbeans = listbeans;
        list_string  = new ArrayList<>();
        list_string.add("男");
        list_string.add("女");
        list_string.add("保密");
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
                view = View.inflate(context, R.layout.item_sex, null);
                holder.iv_selected = (ImageView) view.findViewById(R.id.iv_blue_selected);
                holder.tv_sex = (TextView) view.findViewById(R.id.tv_sex);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
                   if (listbeans.get(position)==position) {
                       holder.iv_selected.setVisibility(View.VISIBLE);
                   }else{
                       holder.iv_selected.setVisibility(View.GONE);
                   }
                 holder.tv_sex.setText(list_string.get(position));


        return view;
    }

    static class ViewHolder {
        ImageView iv_selected;
        TextView tv_sex;
    }
}
