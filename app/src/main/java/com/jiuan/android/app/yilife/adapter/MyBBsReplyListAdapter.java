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
import com.jiuan.android.app.yilife.bean.splashimage.MyReplyBean;

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
public class MyBBsReplyListAdapter extends BaseAdapter {
    private Context context;
    private LruCache<String, Bitmap> mLruCache;
    private HashMap<String, SoftReference<Bitmap>> mHashMap;
    private ArrayList<MyReplyBean> list_item;
    private  RequestQueue queue;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<MyReplyBean> getList_item() {
        return list_item;
    }

    public void setList_item(ArrayList<MyReplyBean> list_item) {
        this.list_item = list_item;
    }

    public MyBBsReplyListAdapter(ArrayList<MyReplyBean> list_item, Context context) {
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
            view = View.inflate(context, R.layout.item_mybbsreplylist, null);
            holder.tv_titlt = (TextView) view.findViewById(R.id.tv_myreply_title);
            holder.tv_neirong = (TextView) view.findViewById(R.id.tv_myreply_reply);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_myreply_time);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_myreply_count);
            holder.tv_day = (TextView) view.findViewById(R.id.tv_myreply_day);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_myreply_nicheng);
            holder.imageview = (NetworkImageView) view.findViewById(R.id.iv_myreply_icon);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date((list_item.get(position).getPosttime())));
        holder.tv_day.setText(date.substring(0, 10));
        holder.tv_time.setText(date.substring(11, 16));
        holder.tv_titlt.setText("标题："+list_item.get(position).getTitle().trim());
        holder.tv_count.setText(""+list_item.get(position).getReply());
        holder.tv_neirong.setText("回复："+list_item.get(position).getPostMessage().trim()+"");

        holder.tv_name.setText(list_item.get(position).getPoster().trim());
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
                    String path = Environment.getExternalStorageDirectory()+"/download_test/"+url.substring(url.lastIndexOf("/"));

                    Bitmap bitmap=null;
//                            = mLruCache.get(url);
//                    if(bitmap == null){
//                        SoftReference<Bitmap> mReference = mHashMap.get(url);
//                        if (mReference!=null) {
//                            bitmap = mReference.get();
//                            mLruCache.put(url, bitmap);
//                            mHashMap.remove(url);
//                        }
//                        else{
//                            File myCaptureFile = new File(path);
//                            if (myCaptureFile.exists()) {//若该文件存在
//                                bitmap = BitmapFactory.decodeFile(path);
//                                return bitmap;
//                            }
//                        }
//                    }
                    if (new File(path).exists()){
                        bitmap =  BitmapFactory.decodeFile(path);
                    }else{
                        bitmap = mLruCache.get(url);
                    }
                    return bitmap;
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
        TextView tv_titlt,tv_time,tv_count,tv_day,tv_name,tv_neirong;
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
