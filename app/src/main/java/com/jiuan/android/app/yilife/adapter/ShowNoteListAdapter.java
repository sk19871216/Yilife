package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.ShownoteBean;
import com.jiuan.android.app.yilife.bean.shownote.ShownoteResponse;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ShowNoteListAdapter extends BaseAdapter {

    private static  final String TEMP = "<html>\n" +
            "\t<head>\n" +
            "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">\n" +
            "        <style type=\"text/css\">\n" +
            "            img {max-width: 100%}\n" +
            "        </style>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<br/>\n" +
            "        社会主义<br/><br/>\n" +
            "\t\t<a href=\"http://pica.nipic.com/2007-12-23/200712231523651_2.jpg\"><img src=\"http://pica.nipic.com/2007-12-23/200712231523651_2.jpg\" ></a>\n" +
            "            <br/><br/>\n" +
            "\t\t<a href=\"http://pic1a.nipic.com/2008-09-18/2008918151935897_2.jpg\"><img src=\"http://pic1a.nipic.com/2008-09-18/2008918151935897_2.jpg\"></a>\n" +
            "\t\t<br/><br/>\n" +
            "            哈哈\n" +
            "\t</body>\n" +
            "</html>";

    private Context context;
    private ShownoteResponse response;
    private ArrayList<ShownoteBean> list = new ArrayList<ShownoteBean>();
    private EditText editText;
    private boolean istwoentry=false;
    private int index,count=0;

    private String name,title;
    private int nameid;

    public int getNameid() {
        return nameid;
    }

    public void setNameid(int nameid) {
        this.nameid = nameid;
    }
    //    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

//    public ThreadBangdingStuResponse getResponse() {
//        return response;
//    }
//
//    public void setResponse(ThreadBangdingStuResponse response) {
//        this.response = response;
//    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ShownoteBean> getList() {
        return list;
    }

    public void setList(ArrayList<ShownoteBean> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public ShowNoteListAdapter(Context context, ArrayList<ShownoteBean> list,EditText editText,String title,int count) {
        this.context = context;
//        this.response = response;
        this.editText = editText;
        this.list = list;
        this.count = count;
        this.title = title;
//        if (!response.equals("")) {
//            for (int i = 0; i < response.getBean().length; i++) {
//                list.add(response.getBean()[i]);
//            }
//        }
    }


    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        if (position>0) {
            return list.get(position - 1);
        } else {
            return list.get(position+1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        View view1 = null;
        if (getItemViewType(position)==0){
//            for (int i =0;i<response.getBean().length;i++) {
//                list.add(response.getBean()[i]);
//            }
            FirstItemViewHolder firstItemViewHolder=null;
            if (convertView == null) {
                firstItemViewHolder = new FirstItemViewHolder();
                view=View.inflate(context, R.layout.item_shownote_title, null);
                firstItemViewHolder.tv_title = (TextView) view.findViewById(R.id.tv_shownote_title);
                firstItemViewHolder.tv_firname = (TextView) view.findViewById(R.id.tv_shownote_firname);
                firstItemViewHolder.tv_title_count = (TextView) view.findViewById(R.id.tv_shownote_title_count);
                firstItemViewHolder.tv_title_day = (TextView) view.findViewById(R.id.tv_shownote_title_day);
                firstItemViewHolder.tv_title_time = (TextView) view.findViewById(R.id.tv_shownote_title_time);
                firstItemViewHolder.tv_replycount = (TextView) view.findViewById(R.id.tv_shownote_replycount);
                firstItemViewHolder.webView = (WebView) view.findViewById(R.id.webview_showall);
                firstItemViewHolder.imageView = (ImageView) view.findViewById(R.id.iv_reply1f);
                view.setTag(firstItemViewHolder);
            }else{
                view = convertView;
                firstItemViewHolder = (FirstItemViewHolder) view.getTag();
            }
//            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(response.getTime()));
//            firstItemViewHolder.tv_title_day.setText(date.substring(0,10));
//            firstItemViewHolder.tv_title_time.setText(date.substring(11,16));
//            firstItemViewHolder.tv_title_count.setText(""+response.getReply());
//            firstItemViewHolder.tv_title.setText(response.getTitle());
//            firstItemViewHolder.tv_replycount.setText(""+response.getReply());
//            firstItemViewHolder.webView.loadData(response.getBean()[0].getNeirong(),"text/html; charset=UTF-8",null);
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(0).getPosttime()));
            firstItemViewHolder.tv_title_day.setText(date.substring(0,10));
            firstItemViewHolder.tv_title_time.setText(date.substring(11,16));
//            firstItemViewHolder.tv_title_count.setText(""+count);
            firstItemViewHolder.tv_title_count.setText(""+list.get(0).getCount());

//            firstItemViewHolder.tv_title.setText(title);
           // firstItemViewHolder.tv_title.setText(list.get(0).getTitle());
            firstItemViewHolder.tv_firname.setText(list.get(0).getPoster());
            firstItemViewHolder.tv_replycount.setText(""+list.get(0).getCount());
//            firstItemViewHolder.tv_replycount.setText(""+count);
            if (count==0) {
                firstItemViewHolder.webView.loadData(list.get(0).getNeirong(), "text/html; charset=UTF-8", null);
            }
            firstItemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
//                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
                        imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
                    index = position+1;

                    editText.setFocusable(true);
                    editText.setHint("回复"+(position+1)+"楼: "+list.get(position).getPoster());
//                    name = list.get(position-1).getPoster();
//                    name = list.get(position).getPoster();
                    nameid = list.get(position).getPosterID();
                }
            });
            view1= view;
            count++;
        }else {
//            for (int i =0;i<response.getBean().length;i++) {
//                list.add(response.getBean()[i]);
//            }
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                view = View.inflate(context, R.layout.item_shwonote, null);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_shownote_time);
                holder.tv_count = (TextView) view.findViewById(R.id.tv_shownote_floor);
                holder.tv_day = (TextView) view.findViewById(R.id.tv_shownote_day);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_shownote_name);
                holder.tv_reply = (TextView) view.findViewById(R.id.tv_show_reply);
                holder.tv_replyto = (TextView) view.findViewById(R.id.tv_shownote_replyto);
                holder.iv_down = (ImageView) view.findViewById(R.id.iv_showall);
                holder.iv_up = (ImageView) view.findViewById(R.id.iv_showa2line);
                holder.iv_reply = (ImageView) view.findViewById(R.id.iv_bigmsg);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
//            String date2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(position-1).getPosttime()));
            String date2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(position).getPosttime()));

//            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date((response.getBean()[position-1].getPosttime()*1000)));
            holder.tv_time.setText(date2.substring(11, 16));
//            holder.tv_count.setText(""+list.get(position-1).getFloor()+"#");
//            holder.tv_day.setText(date2.substring(0, 10));
//            holder.tv_name.setText(list.get(position-1).getPoster());
//            holder.tv_replyto.setText(list.get(position-1).getReplyTo());

            holder.tv_count.setText(""+list.get(position).getFloor()+"#");
            holder.tv_day.setText(date2.substring(0, 10));
            holder.tv_name.setText(list.get(position).getPoster());
            holder.tv_replyto.setText(list.get(position).getReplyTo());
            if (list.get(position).getReplyTo().equals("")){
                holder.tv_replyto.setVisibility(View.GONE);
            }else{
                holder.tv_replyto.setVisibility(View.VISIBLE);
            }
            String a = list.get(position).getNeirong().toString().replace("\r","");
            if (a.indexOf("\n")!=-1 || (a.indexOf("\r\n")!=-1)) {
                if (a.substring(a.indexOf("\n")+2).indexOf("\n") != -1 || a.substring(a.indexOf("\r\n")+2).indexOf("\r\n") != -1) {
                    istwoentry = true;
                } else {
                    istwoentry = false;
                }
            }else{
                istwoentry = false;
            }

            holder.tv_reply.setText(list.get(position).getNeirong().replace("\n","\r\n"));

            if (list.get(position).getNeirong().trim().length()>55 ||istwoentry){
                holder.tv_reply.setMaxLines(2);
                holder.iv_down.setVisibility(View.VISIBLE);
                holder.iv_up.setVisibility(View.GONE);
            }else{
                holder.tv_reply.setMaxLines(99);
                holder.iv_down.setVisibility(View.GONE);
                holder.iv_up.setVisibility(View.GONE);
            }
            final ViewHolder finalHolder = holder;
            holder.iv_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.tv_reply.setMaxLines(999);
                    finalHolder.iv_down.setVisibility(View.GONE);
                    finalHolder.iv_up.setVisibility(View.VISIBLE);
                }
            });
            holder.iv_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.tv_reply.setMaxLines(2);
                    finalHolder.iv_up.setVisibility(View.GONE);
                    finalHolder.iv_down.setVisibility(View.VISIBLE);
                }
            });
            holder.iv_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
//                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
                    imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
                    index = position+1;

                    editText.setFocusable(true);
                    editText.setHint("回复"+(position+1)+"楼: "+list.get(position).getPoster());
//                    name = list.get(position-1).getPoster();
//                    name = list.get(position).getPoster();
                    nameid = list.get(position).getPosterID();

                }
            });

            view1= view;
        }
        return  view1;
    }
    static class ViewHolder {
        TextView tv_replyto,tv_time,tv_count,tv_day,tv_name,tv_reply;
        ImageView iv_down,iv_reply,iv_up;
        boolean alllines;
    }
    static class FirstItemViewHolder {
        TextView tv_title,tv_title_time,tv_title_count,tv_title_day,tv_replycount,tv_firname;
        WebView webView;
        ImageView imageView;
    }
}
