package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getpinglunlist.PinglunlistBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2014/12/22.
 */
public class PinglunAdapter2 extends BaseAdapter {
    private Context context;
    private int spcount=0;
    private ArrayList<PinglunlistBean> listbeans;
    private boolean istwoentry=false;
    private HashMap hashMap;
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



    public PinglunAdapter2(Context context, ArrayList<PinglunlistBean> listbeans){
        this.context = context;
        this.listbeans = listbeans;
        hashMap = new HashMap();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            String date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(listbeans.get(position).getCreateTime()));

            holder.day.setText(date.substring(0,10));
            holder.time.setText(date.substring(11,16));
            holder.user.setText(listbeans.get(position).getFromUser());
        final ViewHolder finalHolder1 = holder;
//            holder.neirong.getViewTreeObserver().addOnGlobalLayoutListener(
//                    new ViewTreeObserver.OnGlobalLayoutListener() {
//                        @Override
//                        public void onGlobalLayout() {
//
//                            if (finalHolder1.neirong.getLineCount() > 2) {
//                                finalHolder1.neirong.setMaxLines(2);
//                                finalHolder1.iv.setVisibility(View.VISIBLE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            } else {
//                                finalHolder1.neirong.setMaxLines(99);
//                                finalHolder1.iv.setVisibility(View.GONE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            }
//                            finalHolder1.neirong.getViewTreeObserver()
//                                    .removeGlobalOnLayoutListener(this);
//                        }
//                    });
        holder.neirong.setText(listbeans.get(position).getContent().trim());
        final String getneirong = listbeans.get(position).getContent().trim();
        final ViewHolder finalHolder2 = holder;
        holder.neirong.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = finalHolder2.neirong.getLayout().getLineCount();
//                            hashMap.put("" + position, lineCount);
                final float scale = context.getResources().getDisplayMetrics().density;
                double h = (finalHolder2.neirong.getLayout().getHeight() / scale + 0.5f);
//                            double w = (finalHolder2.tv_reply.getLayout().getWidth() / scale + 0.5f);
                double hh = h / (15 + 3 + 15);
//                            double ww = w/(15);
//                            double xx = list.get(position).getNeirong().toString().trim().length()/ww;

                DisplayMetrics dm = new DisplayMetrics();

                WindowManager manager = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                double w = display.getWidth() / scale + 0.5f;
                double ww = (w - 20-50) / (15);
                double xx = textlenth(getneirong) / ww;
//                            if (!a.contains("�")) {
                if (getneirong.indexOf("\n")!=-1 || (getneirong.indexOf("\r\n")!=-1)) {
                    if (getneirong.substring(getneirong.indexOf("\n")+2).indexOf("\n") != -1 || getneirong.substring(getneirong.indexOf("\r\n")+2).indexOf("\r\n") != -1) {
                        istwoentry = true;
                    } else {
                        istwoentry = false;
                    }
                }else{
                    istwoentry = false;
                }
                if ((hh > 1 && xx > 2) || istwoentry ) {
//                            if (lineCount > 2) {
                    if (spcount==0) {


                        finalHolder2.neirong.setMaxLines(2);
                        finalHolder2.iv.setVisibility(View.VISIBLE);
                        finalHolder2.iv_up.setVisibility(View.GONE);
                    }else{
                        if (lineCount>2){


                            finalHolder2.neirong.setMaxLines(2);
                            finalHolder2.iv.setVisibility(View.VISIBLE);
                            finalHolder2.iv_up.setVisibility(View.GONE);
                        }
                    }

                } else {

                    hashMap.put("" + position, 1);
                    finalHolder2.neirong.setMaxLines(99);
                    finalHolder2.iv.setVisibility(View.GONE);
                    finalHolder2.iv_up.setVisibility(View.GONE);
                }
            }
        });


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
    private int textlenth(String a){
        char[] array = a.toCharArray();
        int chineseCount = 0;
        int englishCount = 0;
        int textlength=0;
        spcount = 0;
        for (int i = 0; i < array.length; i++) {
            if((char)(byte)array[i]!=array[i]){
                if (isChinese(array[i])) {
                    chineseCount++;
                }else{
                    spcount++;
                }
            }else{
                englishCount++;
            }
        }
        textlength = chineseCount+englishCount/2+spcount;
        return  textlength;
    }
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
