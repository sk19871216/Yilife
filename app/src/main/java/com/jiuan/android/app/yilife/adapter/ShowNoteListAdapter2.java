package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.ShowNote;
import com.jiuan.android.app.yilife.bean.ShownoteBean;
import com.jiuan.android.app.yilife.bean.shownote.ShownoteResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ShowNoteListAdapter2 extends BaseAdapter {

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
    private boolean istwoentry=false,huifu2lines=false,iscontain=false;
    private int index,count=0,counthlines=0;
    public static HashMap hashMap;
    private Handler handler;
    private HashSet<String> hashset;
    private String name,title;
    private int nameid;
    public static ArrayList<String> listdouble;
    private ArrayList<Integer> textlengthlist;

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



    public ShowNoteListAdapter2(Context context, ArrayList<ShownoteBean> list, EditText editText, String title, int count) {
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
        hashMap = new HashMap();
        hashset = new HashSet<String>();
        listdouble = new ArrayList<String>();
        textlengthlist = new ArrayList<Integer>();
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
                firstItemViewHolder.tv_isHasAttach = (TextView) view.findViewById(R.id.tv_iscontainsfujian);
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
            firstItemViewHolder.tv_title.setText(list.get(0).getTtitle());
            firstItemViewHolder.tv_firname.setText(list.get(0).getPoster());
            firstItemViewHolder.tv_replycount.setText(""+list.get(0).getCount());
//            firstItemViewHolder.tv_replycount.setText(""+count);
            if (ShowNote.hasAttach){
                firstItemViewHolder.tv_isHasAttach.setVisibility(View.VISIBLE);
            }else{
                firstItemViewHolder.tv_isHasAttach.setVisibility(View.INVISIBLE);
            }
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
            final String a =
//                    "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda\r\n"+
                            list.get(position).getNeirong().toString().replace("\r","");
//            final String huifu_title ="";
            final String huifu_title =list.get(position).getReplytitle().toString().replace("\r","")+"\r\n";
//                    "sssssssssssssssssssssssssssssssssssssssssssssssssssssss"+"\r\n";

//            textlengthlist.add(textlength);
            final ViewHolder finalHolder2 = holder;
            holder.tv_reply.setText(huifu_title+a);


//            holder.tv_reply.getMeasuredHeight()

                holder.tv_reply.post(new Runnable() {
                    @Override
                    public void run() {
                        int lineCount = finalHolder2.tv_reply.getLayout().getLineCount();
//                            hashMap.put("" + position, lineCount);
                        final float scale = context.getResources().getDisplayMetrics().density;
                        double h = (finalHolder2.tv_reply.getLayout().getHeight() / scale + 0.5f);
//                            double w = (finalHolder2.tv_reply.getLayout().getWidth() / scale + 0.5f);
                        double hh = h / (15 + 3 + 15);
//                            double ww = w/(15);
//                            double xx = list.get(position).getNeirong().toString().trim().length()/ww;

                        DisplayMetrics dm = new DisplayMetrics();

                        WindowManager manager = (WindowManager) context
                                .getSystemService(Context.WINDOW_SERVICE);
                        Display display = manager.getDefaultDisplay();
                        double w = display.getWidth() / scale + 0.5f;
                        double ww = (w - 20) / (15);
                        double xx = textlenth(a) / ww;
                        double xxx = textlenth(huifu_title) / ww;
//                        counthlines
                        if (!huifu_title.equals("")) {
                            if (huifu_title.substring(huifu_title.indexOf("\n") + 1).indexOf("\n") != -1
                                    || huifu_title.substring(huifu_title.indexOf("\r\n") + 2).indexOf("\r\n") != -1) {
                                istwoentry = true;
                            } else {
                                istwoentry = false;
                            }
                        }
                        if (a.substring(a.indexOf("\n")+1).indexOf("\n") != -1
                                || a.substring(a.indexOf("\r\n")+2).indexOf("\r\n") != -1) {
                            istwoentry = true;
                        } else {
                            istwoentry = false;
                        }
//                        if (lineCount>2 || istwoentry){
//                            listdouble.add("hh>2" + position + "--" + hh);
//                            listdouble.add("xx>2" + position + "--" + xx);
//                            listdouble.add("ww>2" + position + "--" + lineCount);
//                            listdouble.add("textlenth(a)>2" + position + "--" + textlenth(a));
//                            finalHolder2.tv_reply.setMaxLines(2);
//                            finalHolder2.iv_down.setVisibility(View.VISIBLE);
//                            finalHolder2.iv_up.setVisibility(View.GONE);
//                        }else{
//                            if (hh > 1 && xx > 2){
//                                listdouble.add("hh" + position + "--" + hh);
//                                listdouble.add("xx" + position + "--" + xx);
//                                listdouble.add("ww" + position + "--" + lineCount);
//                                listdouble.add("textlenth(a)" + position + "--" + textlenth(a));
//                                finalHolder2.tv_reply.setMaxLines(2);
//                                finalHolder2.iv_down.setVisibility(View.VISIBLE);
//                                finalHolder2.iv_up.setVisibility(View.GONE);
//                            }else{
//                                listdouble.add("hhelse" + position + "--" + hh);
//                                listdouble.add("xxelse" + position + "--" + xx);
//                                listdouble.add("wwelse" + position + "--" + lineCount);
//                                listdouble.add("textlenth(a)else" + position + "--" + textlenth(a));
//                                finalHolder2.tv_reply.setMaxLines(99);
//                                finalHolder2.iv_down.setVisibility(View.GONE);
//                                finalHolder2.iv_up.setVisibility(View.GONE);
//                            }
//                        }
                        if (huifu_title.equals("")){
                            if ((hh > 1 && xx > 2) || istwoentry ) {

                                if (!issp(a)) {
                                    listdouble.add("hh" + position + "--" + hh);
                                    listdouble.add("xx" + position + "--" + xx);
                                    listdouble.add("ww" + position + "--" + lineCount);
                                    listdouble.add("textlenth(a)" + position + "--" + textlenth(a));

                                    finalHolder2.tv_reply.setMaxLines(2);
                                    finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                }else{
                                    if (lineCount>2){
                                        listdouble.add("hh>2" + position + "--" + hh);
                                        listdouble.add("xx>2" + position + "--" + xx);
                                        listdouble.add("ww>2" + position + "--" + lineCount);
                                        listdouble.add("textlenth(a)>2" + position + "--" + textlenth(a));

                                        finalHolder2.tv_reply.setMaxLines(2);
                                        finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                        finalHolder2.iv_up.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                listdouble.add("" + position + "--" + hh);
                                listdouble.add("xx" + position + "--" + xx);
                                listdouble.add("ww" + position + "--" + lineCount);
                                listdouble.add("textlenth(a)" + position + "--" + textlenth(a));
//                                hashMap.put("" + position, 1);
                                finalHolder2.tv_reply.setMaxLines(99);
                                finalHolder2.iv_down.setVisibility(View.GONE);
                                finalHolder2.iv_up.setVisibility(View.GONE);
                            }
                        }else{
                            if (xxx>1 && xx!=0){
                                finalHolder2.tv_reply.setMaxLines(2);
                                finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                finalHolder2.iv_up.setVisibility(View.GONE);
                            }else if (xxx<1){
                                if (a.indexOf("\n") != -1 || a.indexOf("\r\n") != -1) {
                                    huifu2lines = true;
                                } else {
                                    huifu2lines = false;
                                }
                                if ((hh > 1 && xx > 1) || huifu2lines){
                                    if (!issp(a)) {
                                        finalHolder2.tv_reply.setMaxLines(2);
                                        finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                        finalHolder2.iv_up.setVisibility(View.GONE);
                                    }else{
                                        if (lineCount>2){
                                            finalHolder2.tv_reply.setMaxLines(2);
                                            finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                            finalHolder2.iv_up.setVisibility(View.GONE);
                                        }
                                    }

                                } else {
                                    finalHolder2.tv_reply.setMaxLines(99);
                                    finalHolder2.iv_down.setVisibility(View.GONE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
//                            if (!a.contains("�")) {
                        /*if (a.indexOf("\n")!=-1 || (a.indexOf("\r\n")!=-1)) {
                            if (a.substring(a.indexOf("\n")+2).indexOf("\n") != -1 || a.substring(a.indexOf("\r\n")+2).indexOf("\r\n") != -1) {
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
                                    listdouble.add("hh" + position + "--" + hh);
                                    listdouble.add("xx" + position + "--" + xx);
                                    listdouble.add("ww" + position + "--" + lineCount);
                                    listdouble.add("textlenth(a)" + position + "--" + textlenth(a));

                                    finalHolder2.tv_reply.setMaxLines(2);
                                    finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                }else{
                                    if (lineCount>2){
                                        listdouble.add("hh" + position + "--" + hh);
                                        listdouble.add("xx" + position + "--" + xx);
                                        listdouble.add("ww" + position + "--" + lineCount);
                                        listdouble.add("textlenth(a)" + position + "--" + textlenth(a));

                                        finalHolder2.tv_reply.setMaxLines(2);
                                        finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                        finalHolder2.iv_up.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                listdouble.add("" + position + "--" + hh);
                                listdouble.add("xx" + position + "--" + xx);
                                listdouble.add("ww" + position + "--" + lineCount);
                                listdouble.add("textlenth(a)" + position + "--" + textlenth(a));
                                hashMap.put("" + position, 1);
                                finalHolder2.tv_reply.setMaxLines(99);
                                finalHolder2.iv_down.setVisibility(View.GONE);
                                finalHolder2.iv_up.setVisibility(View.GONE);
                            }
                    }
                });*/




//            final ViewHolder finalHolder1 = holder;
//            holder.tv_reply.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (finalHolder1.tv_reply.getLineCount() > 2) {
//                                finalHolder1.tv_reply.setMaxLines(2);
//                                finalHolder1.iv_down.setVisibility(View.VISIBLE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            } else {
//                                finalHolder1.tv_reply.setMaxLines(99);
//                                finalHolder1.iv_down.setVisibility(View.GONE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            }
//                }
//            });
           /* holder.tv_reply.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
//                            if ( hashMap.get(""+position)==null){
                            if ( !hashset.contains(""+position)){
                                int lineCount = finalHolder2.tv_reply.getLayout().getLineCount();
                                hashMap.put("" + position, lineCount);
                                if (lineCount > 2) {
                                    hashset.add("" + position);

                                    finalHolder2.tv_reply.setMaxLines(2);
                                    finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                } else {
                                    hashset.add("" + position);
                                    finalHolder2.tv_reply.setMaxLines(99);
                                    finalHolder2.iv_down.setVisibility(View.GONE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                }
                            }else{
                                int maxline = (int) hashMap.get("" + position);
                                if (maxline > 2) {

                                    finalHolder2.tv_reply.setMaxLines(2);
                                    finalHolder2.iv_down.setVisibility(View.VISIBLE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                } else {
                                    finalHolder2.tv_reply.setMaxLines(99);
                                    finalHolder2.iv_down.setVisibility(View.GONE);
                                    finalHolder2.iv_up.setVisibility(View.GONE);
                                }
                            }
//                            if (finalHolder1.tv_reply.getLineCount() > 2) {
//                                finalHolder1.tv_reply.setMaxLines(2);
//                                finalHolder1.iv_down.setVisibility(View.VISIBLE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            } else {
//                                finalHolder1.tv_reply.setMaxLines(99);
//                                finalHolder1.iv_down.setVisibility(View.GONE);
//                                finalHolder1.iv_up.setVisibility(View.GONE);
//                            }
                            finalHolder2.tv_reply.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    });*/
//            handler = new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    if (msg.what==1){
//                        if (textlines>2){
//                            finalHolder1.tv_reply.setMaxLines(2);
//                            finalHolder1.iv_down.setVisibility(View.VISIBLE);
//                            finalHolder1.iv_up.setVisibility(View.GONE);
//                        }else{
//                            finalHolder1.tv_reply.setMaxLines(99);
//                            finalHolder1.iv_down.setVisibility(View.GONE);
//                            finalHolder1.iv_up.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            };
//            if (textlines>2){
//                holder.tv_reply.setMaxLines(2);
//                holder.iv_down.setVisibility(View.VISIBLE);
//                holder.iv_up.setVisibility(View.GONE);
//            }else{
//                holder.tv_reply.setMaxLines(99);
//                holder.iv_down.setVisibility(View.GONE);
//                holder.iv_up.setVisibility(View.GONE);
//            }
//            if (list.get(position).getNeirong().trim().length()>55 ||istwoentry){
//                holder.tv_reply.setMaxLines(2);
//                holder.iv_down.setVisibility(View.VISIBLE);
//                holder.iv_up.setVisibility(View.GONE);
//            }else{
//                holder.tv_reply.setMaxLines(99);
//                holder.iv_down.setVisibility(View.GONE);
//                holder.iv_up.setVisibility(View.GONE);
//            }
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
        TextView tv_title,tv_title_time,tv_title_count,tv_title_day,tv_replycount,tv_firname,tv_isHasAttach;
        WebView webView;
        ImageView imageView;
    }
    private int textlenth(String a){
        char[] array = a.toCharArray();
        int chineseCount = 0;
        int englishCount = 0;
        int textlength=0;
        int spcount = 0;
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
    private boolean issp(String a){
        char[] array = a.toCharArray();
            int sp =0;
        for (int i = 0; i < array.length; i++) {
            if((char)(byte)array[i]!=array[i]){
                if (isChinese(array[i])) {
                }else{
                    sp++;
                }
            }else{
            }
        }
        if (sp!=0){
            return true;
        }else{
            return false;
        }
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

