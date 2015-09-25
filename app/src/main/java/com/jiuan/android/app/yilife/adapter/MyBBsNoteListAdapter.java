package com.jiuan.android.app.yilife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.MyBBsNoteBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2014/12/22.
 */
public class MyBBsNoteListAdapter extends BaseAdapter {
    private Context context;
    private LruCache<String, Bitmap> mLruCache;
    private HashMap<String, SoftReference<Bitmap>> mHashMap;
    private ArrayList<MyBBsNoteBean> list_item;
    private  RequestQueue queue;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<MyBBsNoteBean> getList_item() {
        return list_item;
    }

    public void setList_item(ArrayList<MyBBsNoteBean> list_item) {
        this.list_item = list_item;
    }

    public MyBBsNoteListAdapter(ArrayList<MyBBsNoteBean> list_item, Context context) {
        this.list_item = list_item;
        this.context = context;
        queue = Volley.newRequestQueue(context);
        initCache();
//        mHashMap = new HashMap<String, SoftReference<Bitmap>>();
//        int maxMemorysize = (int) (Runtime.getRuntime().maxMemory()/1024);
//        mLruCache = new LruCache<String, Bitmap>(maxMemorysize/8){
//            protected int sizeOf(String string,Bitmap bitmap){
//                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
//            }
//            protected void isGone(boolean evicted,String key,Bitmap oldBm,Bitmap newBm){
//
//                Log.d("asdasd","asdasdasd");
//
//                if(evicted){
////                    mHashMap.put(key, new SoftReference<Bitmap>(oldBm));
//                }
//            }
//        };
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
            view = View.inflate(context, R.layout.item_mybbsnotelist, null);
            holder.tv_titlt = (TextView) view.findViewById(R.id.tv_item_mybbsnotelist_title);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_mybbsnotelist_time);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_mybbsnotelist_count);
            holder.tv_day = (TextView) view.findViewById(R.id.tv_mybbsnotelist_day);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_mybbsnotelist_name);
            holder.imageview = (NetworkImageView) view.findViewById(R.id.iv_mybbsnotelist_icon);
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
//        try{
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
        /*    ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache(
            ) {
                @Override
                public Bitmap getBitmap(String url) {
*//*                    String path = Environment.getExternalStorageDirectory()+"/download_test/"+url.substring(url.lastIndexOf("/"));

                    Bitmap bitmap=null;

                    if (new File(path).exists()){
                        bitmap =  BitmapFactory.decodeFile(path);
                    }else{
                        bitmap = mLruCache.get(url);
                    }
                    return bitmap;*//*

                }
                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    mLruCache.put(url, bitmap);

                    saveImageView(bitmap, url.substring(url.lastIndexOf("/")));
                }
            });*/
            holder.imageview.setImageUrl(list_item.get(position).getIcon(), loader);
//        }catch(OutOfMemoryError oom){
//        }
        return view;

    }
    static class ViewHolder {
        TextView tv_titlt,tv_time,tv_count,tv_day,tv_name;
        NetworkImageView imageview;
    }
    public Bitmap readBitMap(String  srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        //获取资源图片
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }
    public void initCache(){
        mHashMap = new HashMap<String, SoftReference<Bitmap>>();
//        int maxMemorysize = (int) (Runtime.getRuntime().maxMemory()/1024);
        mLruCache = new LruCache<String, Bitmap>(1024){
            protected int sizeOf(String string,Bitmap bitmap){
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
            protected void isGone(boolean evicted,String key,Bitmap oldBm,Bitmap newBm){
                if(evicted){
                    mHashMap.put(key, new SoftReference<Bitmap>(oldBm));
                }
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if(evicted){
                    mHashMap.put(key, new SoftReference<Bitmap>(oldValue));
                }
            }
        };

    }
    public void saveImageView(Bitmap bm,String imageName){
        String PATH = Environment.getExternalStorageDirectory()+"/download_test/";
        File dirFile = new File(PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(PATH+imageName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
