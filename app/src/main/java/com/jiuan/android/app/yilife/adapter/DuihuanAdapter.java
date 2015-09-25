package com.jiuan.android.app.yilife.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.DuihuanDone;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanBean1;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class DuihuanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DuihuanBean1> listbeans;
    private ListView lv;
    private double money;
    private Boolean isSelect=false;
    public static int a=-1;

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
    }

    public ArrayList<DuihuanBean1> getListbeans() {
        return listbeans;
    }

    public void setListbeans(ArrayList<DuihuanBean1> listbeans) {
        this.listbeans = listbeans;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public DuihuanAdapter(Context context, ArrayList<DuihuanBean1> listbeans,ListView lv,double money){
        this.context = context;
        this.listbeans = listbeans;
        this.lv = lv;
        this.money = money;
    }

    public int getCount() {
        return listbeans.size()+1;
    }

//    @Override
//    public Object getItem(int position) {
//        return listbeans.get(position);
//    }
//
    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        if (position==listbeans.size()) {
            return 0;
        } else {
            return 1;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    public Object getItem(int position) {

//        if (position<listbeans.size()-1) {
//            return listbeans.get(position - 1);
//        } else {
//            return listbeans.get(position);
//        }
        return listbeans.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        View view1 = null;
//        ViewHolder holder = null;
        if (getItemViewType(position)!=0) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                view = View.inflate(context, R.layout.item_duihuan, null);
                holder.text_money = (TextView) view.findViewById(R.id.tv_duihuanlist_money);
                holder.layout = (RelativeLayout) view.findViewById(R.id.linearlayout_duihuan);

                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.text_money.setText(listbeans.get(position).getPrice() + "");
            holder.layout.setBackgroundResource(R.drawable.icon_unclick);
            if (listbeans.get(position).getP() == position) {
                a=position;
                holder.layout.setBackgroundResource(R.drawable.icon_click);
            } else {
                holder.layout.setBackgroundResource(R.drawable.icon_unclick);
            }
//        final ViewHolder finalHolder = holder;
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!isSelect){
//                        finalHolder.layout.setBackgroundResource(R.drawable.icon_click);
//                        isSelect = true;
//
//                    }else{
//                        finalHolder.layout.setBackgroundResource(R.drawable.icon_unclick);
//                        isSelect =false;
//                    }
//                }
//            });

            view1 =view;
//            return view;
        }else{
            LastViewHolder holder =null;
            if (convertView == null) {
                holder = new LastViewHolder();
                view = View.inflate(context, R.layout.item_duihuanbutton, null);
                holder.button = (Button) view.findViewById(R.id.bt_duihuan);

                view.setTag(holder);
            } else {
                view = convertView;
                holder = (LastViewHolder) view.getTag();
            }
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (a==-1){
                        ToastOnly toastOnly = new ToastOnly(context);
                        toastOnly.toastShowShort("请选择兑换券");
                    }else{
                           if (listbeans.get(a).getPrice()<=listbeans.get(0).getMymoney()){
                               new AlertDialog.Builder(context)
                                       .setMessage("将从红包中扣除相应金额，确定兑换吗？")
                                       .setNegativeButton("取消",null)
                                       .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent intent =new Intent(context, DuihuanDone.class);
                                               intent.putExtra("productid",listbeans.get(a).getProductID());
                                               a=-1;
                                               context.startActivity(intent);
                                           }
                                       })
                                       .show();

                            }else{
                                ToastOnly toastOnly = new ToastOnly(context);
                                toastOnly.toastShowShort("金额不足");
                            }
                    }

                }
            });
//            return  view1;
            view1 = view;
        }
        return  view1;
    }

    static class ViewHolder {
        TextView text_money;
        RelativeLayout layout;
    }
    static class LastViewHolder {
        Button button;
    }
}
