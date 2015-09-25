package com.jiuan.android.app.yilife.activity;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.fankuiup.FankuiClient;
import com.jiuan.android.app.yilife.bean.fankuiup.FankuiHandler;
import com.jiuan.android.app.yilife.bean.fankuiup.FankuiImageHandler;
import com.jiuan.android.app.yilife.imageloader.MyAdapter;
import com.jiuan.android.app.yilife.imageloader.SelectImageBBSActivity;
import com.jiuan.android.app.yilife.utils.ImageLoaderFankui;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.jiuan.oa.android.library.util.Installation;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Decoder.BASE64Encoder;

public class Fankui extends ParentActivity implements View.OnClickListener{
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    private TextView tv_bar_title,tv_count;
    private ImageView iv_back,tv_setting;
    private EditText editText;
    private CharSequence temp;
    private int editStart,editEnd;
    private ToastOnly toastOnly;
    private String phone,tooken,feedbackid,content,hguid;
//    private ArrayList<String> stringArrayList_fankui ;
    public static ArrayList<String> stringArrayList_fankui;
    private LinearLayout layout;
    private ProgressDialog dialog;
    private RelativeLayout layout1,layout2;
    private int add_potsition=0;
    private Handler handler;
    private ArrayList<Bitmap> listbit;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imageview_fankui_add1:
                Log.d("imageview_fankui_add1",""+stringArrayList_fankui.size());
                if (stringArrayList_fankui.size()==0){
                    MyAdapter.mSelectedImage_count.clear();
                    int fankuisize = stringArrayList_fankui.size();
                    for (int j = 0; j < fankuisize; j++) {
                        MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(j));
                    }
                    Intent intent = new Intent(Fankui.this, SelectImageBBSActivity.class);
                    intent.putStringArrayListExtra("photoPaths", stringArrayList_fankui);
                    startActivityForResult(intent,3);
                }else{
                    Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
                    intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(0));
                    intent_bigpic.putExtra("from","fankui");
                    intent_bigpic.putExtra("position",0);
                    startActivity(intent_bigpic);
                }
                break;
            case R.id.imageview_fankui_add2:
                Log.d("imageview_fankui_add2",""+stringArrayList_fankui.size());
                if (stringArrayList_fankui.size()==1){
                    MyAdapter.mSelectedImage_count.clear();
                    int fankuisize = stringArrayList_fankui.size();
                    for (int j = 0; j < fankuisize; j++) {
                        MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(j));
                    }
                    Intent intent = new Intent(Fankui.this, SelectImageBBSActivity.class);
                    intent.putStringArrayListExtra("photoPaths", stringArrayList_fankui);
                    startActivityForResult(intent,3);
                }else if (stringArrayList_fankui.size()>1){
                    Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
                    intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(1));
                    intent_bigpic.putExtra("from","fankui");
                    intent_bigpic.putExtra("position",1);
                    startActivity(intent_bigpic);
                }
                break;
            case R.id.imageview_fankui_add3:
                if (stringArrayList_fankui.size()==2){
                    MyAdapter.mSelectedImage_count.clear();
                    int fankuisize = stringArrayList_fankui.size();
                    for (int j = 0; j < fankuisize; j++) {
                        MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(j));
                    }
                    Intent intent = new Intent(Fankui.this, SelectImageBBSActivity.class);
                    intent.putStringArrayListExtra("photoPaths", stringArrayList_fankui);
                    startActivityForResult(intent,3);
                }else if (stringArrayList_fankui.size()>2){
                    Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
                    intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(2));
                    intent_bigpic.putExtra("from","fankui");
                    intent_bigpic.putExtra("position",2);
                    startActivity(intent_bigpic);
                }

                break;
            case R.id.imageview_fankui_add4:
                if (stringArrayList_fankui.size()==3){
                    MyAdapter.mSelectedImage_count.clear();
                    int fankuisize = stringArrayList_fankui.size();
                    for (int j = 0; j < fankuisize; j++) {
                        MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(j));
                    }
                    Intent intent = new Intent(Fankui.this, SelectImageBBSActivity.class);
                    intent.putStringArrayListExtra("photoPaths", stringArrayList_fankui);
                    startActivityForResult(intent,3);
                }else if (stringArrayList_fankui.size()>3){
                    Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
                    intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(3));
                    intent_bigpic.putExtra("from","fankui");
                    intent_bigpic.putExtra("position",3);
                    startActivity(intent_bigpic);
                }
                break;
            case R.id.imageview_fankui_add5:
                if (stringArrayList_fankui.size()==4){
                    MyAdapter.mSelectedImage_count.clear();
                    int fankuisize = stringArrayList_fankui.size();
                    for (int j = 0; j < fankuisize; j++) {
                        MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(j));
                    }
                    Intent intent = new Intent(Fankui.this, SelectImageBBSActivity.class);
                    intent.putStringArrayListExtra("photoPaths", stringArrayList_fankui);
                    startActivityForResult(intent,3);
                }else if (stringArrayList_fankui.size()>4){
                    Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
                    intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(4));
                    intent_bigpic.putExtra("from","fankui");
                    intent_bigpic.putExtra("position",4);
                    startActivity(intent_bigpic);
                }
                break;
//                for (int i=0;i<stringArrayList_fankui.size();i++){
//                    MyAdapter.mSelectedImage_count.add(stringArrayList_fankui.get(i));
//                }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
//        actionbar();//设置actionbar
//        stringArrayList_fankui = new ArrayList<String>();
        listbit = new ArrayList<Bitmap>();
        stringArrayList_fankui = new ArrayList<String>();
        tv_count = (TextView) findViewById(R.id.textview_fankui_count);
        tv_count.setText("已选择"+stringArrayList_fankui.size()+"/5 张");
        toastOnly = new ToastOnly(Fankui.this);
        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_bar_title.setText("反馈");
        tv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        tv_setting.setImageResource(R.drawable.icon_commit);
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    upload();
            }
        });
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageView1 = (ImageView) findViewById(R.id.imageview_fankui_add1);
        imageView1.setOnClickListener(this);
//        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
//                intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(0));
//                intent_bigpic.putExtra("from","fankui");
//                startActivity(intent_bigpic);
//                return false;
//            }
//        });
        imageView2 = (ImageView) findViewById(R.id.imageview_fankui_add2);
        imageView2.setOnClickListener(this);
//        imageView2.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
////                for (int i=0;i<stringArrayList_fankui.size();i++){
////                    MyAdapter.mSelectedImage.add(stringArrayList_fankui.get(i));
////                }
//                Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
//                intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(1));
//                intent_bigpic.putExtra("from","fankui");
//                startActivity(intent_bigpic);
//                return false;
//            }
//        });
        imageView3 = (ImageView) findViewById(R.id.imageview_fankui_add3);
        imageView3.setOnClickListener(this);
//        imageView3.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
//                intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(2));
//                intent_bigpic.putExtra("from","fankui");
//                startActivity(intent_bigpic);
//                return false;
//            }
//        });
        imageView4 = (ImageView) findViewById(R.id.imageview_fankui_add4);
        imageView4.setOnClickListener(this);
//        imageView4.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
//                intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(3));
//                intent_bigpic.putExtra("from","fankui");
//                startActivity(intent_bigpic);
//                return false;
//            }
//        });
        imageView5 = (ImageView) findViewById(R.id.imageview_fankui_add5);
        imageView5.setOnClickListener(this);
//        imageView5.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent_bigpic = new Intent(Fankui.this,BigPic.class);
//                intent_bigpic.putExtra("imagedetail",stringArrayList_fankui.get(4));
//                intent_bigpic.putExtra("from","fankui");
//                startActivity(intent_bigpic);
//                return false;
//            }
//        });
        layout1 = (RelativeLayout) findViewById(R.id.layout_addimageview);
        layout2 = (RelativeLayout) findViewById(R.id.layout_addimageview2);

//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Fankui.this, SelectImageActivity.class);
//                startActivityForResult(intent,3);
//            }
//        });

        editText = (EditText) findViewById(R.id.edittext_fankui);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp= s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText.getSelectionStart();
                editEnd = editText.getSelectionEnd();
                if (temp.length() > 500) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText.setText(s);
                    editText.setSelection(tempSelection);
                }else if (temp.length()>450 && temp.length()<500){
                    toastOnly.toastShowShort("您还可以输入"+(500-temp.length())+"字");
                }else if (temp.length()==500){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
//        new Thread(){
//            public void run() {
//                changetobitmap();
//                if(listbit.size() == 0){
//                    Log.e("single imageview", "single imageview of drawable is null");
//                }else{
//
//                }
//            };
//        }.start();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == RESULT_OK) {
//            dialog.show();
//            stringArrayList_fankui.clear();
//            if (data.getStringArrayListExtra("imagepaths").size() !=0) {
//                for (int i = 0; i < data.getStringArrayListExtra("imagepaths").size(); i++) {
//                    stringArrayList_fankui.add(data.getStringArrayListExtra("imagepaths").get(i));
////                    MyAdapter.mSelectedImage.add(data.getStringArrayListExtra("imagepaths").get(i));
//                }
//
//            }
            for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
                  stringArrayList_fankui.add(MyAdapter.mSelectedImage.get(i));

////                MyAdapter.mSelectedImage.add(data.getStringArrayListExtra("imagepaths").get(i));
            }
            tv_count.setText("已选择"+stringArrayList_fankui.size()+"/5 张");
            Log.d("123","223");

//
        }
    }
    public Bitmap picnormal(String path){

//        return getimage(path);

        Bitmap b = getimage(path);
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(path);
        } catch (IOException ex) {
            // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
        }
        int degree=0;
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }

        Matrix m = new Matrix();
        m.setRotate(degree);
//        Bitmap transformed = Bitmap.createBitmap(b, 0, 0,b.getWidth(), b.getHeight(), m, true);
        if (b.getWidth()>b.getHeight()) {
            Bitmap transformed = Bitmap.createBitmap(b, 0, 0, b.getHeight(), b.getHeight(), m, true);
//            return compressImage(transformed);
            return transformed;

        }else{
            Bitmap transformed = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getWidth(), m, true);
//            return compressImage(transformed);
            return transformed;

        }

//        Bitmap transformed = Bitmap.createBitmap(b, 0, 0,600, 600, m, true);
//        return compressImage(transformed);
    }
    public void upload() {
        dialog = new ProgressDialog(Fankui.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", "");
        tooken = sharedPreferences.getString("AccessToken", "");
        hguid = sharedPreferences.getString("HGUID", "");
        feedbackid = Installation.id(Fankui.this).replace("-", "");
        content = editText.getText().toString();
        Log.d("phone", phone);
        Log.d("tooken", tooken);
        Log.d("feedbackid", feedbackid);
        Log.d("content", content);

        if (stringArrayList_fankui.size() != 0) {
            dialog.show();
            for (int i = 0; i < stringArrayList_fankui.size(); i++) {
                Log.d("pp",""+stringArrayList_fankui.get(i));

                String ext;
                if (stringArrayList_fankui.get(i).contains("png")){
                    ext = "png";
                }else {
                    ext = "jpg";
                }
                Log.d("ext",""+ext);
                String imagedata = GetImageStr(stringArrayList_fankui.get(i));
                FankuiClient.requestImage(Fankui.this, hguid, tooken, ext, feedbackid, imagedata,
                        new FankuiImageHandler() {
                            @Override
                            public void onRequestSuccess(String response) {
                                super.onRequestSuccess(response);
                                dialog.dismiss();
                                Log.e("结果s", "sssS");
//                                toastOnly.toastShowShort("反馈成功");
                                feedbackTest();
                            }
                            @Override
                            public void onInnovationFailure(String msg) {
                                super.onInnovationFailure(msg);
                                dialog.dismiss();
                                Log.e("结果s","sssF");
                                toastOnly.toastShowShort("反馈成功");
                            }

                            @Override
                            public void onInnovationExceptionFinish() {
                                super.onInnovationExceptionFinish();
                                dialog.dismiss();
                                toastOnly.toastShowShort("网络超时");
                            }

                            @Override
                            public void onInnovationError(String value) {
                                super.onInnovationError(value);
                                dialog.dismiss();
                                Log.e("结果s","sssE");
                                toastOnly.toastShowShort("反馈成功");
                            }
                        }, TestOrNot.isTest);
            }
        }else{
            if (!content.equals("")) {
                dialog.setCancelable(false);
                dialog.show();
                feedbackTest();
            }else{
                toastOnly.toastShowShort("请填写反馈内容");
            }
        }
    }
    public  String GetImageStr(String path)
    {
//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        Bitmap bp = getimage(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bp.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while ( baos.toByteArray().length / 1024>1024) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bp.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        InputStream in = null;
        byte[] data = null; //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
//            data = new byte[in.available()];
//            in.read(data);
            in.read(baos.toByteArray());
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace(); } //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(baos.toByteArray());//返回Base64编码过的字节数组字符串
    }
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
//        while ( baos.toByteArray().length / 1024>1024) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while ( baos.toByteArray().length / 1024>512) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
        float hh = 480f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAdapter.mSelectedImage.clear();
        MyAdapter.mSelectedImage_count.clear();
    }
    private long getAvailMemory(Context context)
    {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        //return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        return mi.availMem/(1024*1024);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("getAvailMemory",""+getAvailMemory(Fankui.this));
//        handler= new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what==3){
//                    setimage();
//                }
//            }
//        };
        closeSoftInput();
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
//        dialog.show();
//        final ArrayList<Bitmap> listbit = new ArrayList<Bitmap>();
        for (int i=0;i<MyAdapter.mSelectedImage.size();i++) {
//            listbit.add(compressImage(picnormal(MyAdapter.mSelectedImage.get(i))));
            stringArrayList_fankui.add(MyAdapter.mSelectedImage.get(i));
            Log.d("sssF",""+stringArrayList_fankui.get(i));
        }
//        tv_count.setText("已选择"+stringArrayList_fankui.size()+"/5 张");
        tv_count.setText("已选择"+stringArrayList_fankui.size()+"/5 张");
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what==1)
//                    dialog.dismiss();
//                setimage();
//            }
//        };
//        getbit();
//         setimage();

//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what ==2){
                    setimage1();
                    dialog.dismiss();
//                }
//            }
//        };
//        for (int i =0;i<MyAdapter.mSelectedImage.size()+1;i++) {
//            final int finalI = i;
//            if (i==MyAdapter.mSelectedImage.size()){
//                handler.sendEmptyMessage(2);
//            }else {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        changetobitmap();
////                handler.sendEmptyMessage(2);
//                    }
//                }).start();
//            }
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                    Looper.prepare();
//
//                    handler = new Handler() {
//                        public void handleMessage(Message msg) {
//                            // process incoming messages here
//                        }
//                    };
//
//                    Looper.loop();
//                }
//
//
//        }).start();
//        while (listbit.size()< MyAdapter.mSelectedImage.size()){
//
//        }

//        changetobitmap();
        clear(Fankui.this);
    }
    protected void changetobitmap(){
        for (int i=0;i<MyAdapter.mSelectedImage.size();i++){
//            Log.e("changetobitmap",""+i);
            listbit.add(compressImage(picnormal(MyAdapter.mSelectedImage.get(i))));
        }
//        if (listbit.size()==5) {
//            handler.sendEmptyMessage(2);
//        }
    }
    protected void setimage1(){
        Log.e("changetobitmap","ssssss");
        int testsize = MyAdapter.mSelectedImage.size();
        if (stringArrayList_fankui.size()==0){
            add_potsition=1;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(false);
            imageView2.setLongClickable(false);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);
            imageView1.setImageResource(R.drawable.icon_photo);
            imageView2.setImageBitmap(null);
            imageView3.setImageBitmap(null);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==1) {
            add_potsition=2;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(false);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

            // imageView1.setImageResource(R.drawable.home);
//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
//            imageView1.setImageBitmap(listbit.get(0));
            imageView2.setImageResource(R.drawable.icon_photo);
            imageView3.setImageBitmap(null);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==2) {
            add_potsition=3;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

            //imageView1.setImageResource(R.drawable.home);
            //imageView2.setImageResource(R.drawable.home);

//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_close));
//            imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));


//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(1), imageView2);
//            imageView1.setImageBitmap(listbit.get(0));
//            imageView1.setImageBitmap(listbit.get(1));
            imageView3.setImageResource(R.drawable.icon_photo);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==3) {
            add_potsition=4;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

//            imageView1.setImageResource(R.drawable.home);
//            imageView2.setImageResource(R.drawable.home);
//            imageView3.setImageResource(R.drawable.home);

//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
//            imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
//            imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));

//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(1), imageView2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(2), imageView3);
//            imageView1.setImageBitmap(listbit.get(0));
//            imageView1.setImageBitmap(listbit.get(1));
//            imageView1.setImageBitmap(listbit.get(2));
            imageView4.setImageResource(R.drawable.icon_photo);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==4) {
            add_potsition=5;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(true);
            imageView5.setLongClickable(false);


//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));
//            imageView4.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(3))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(1), imageView2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(2), imageView3);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(3), imageView4);
            imageView5.setImageResource(R.drawable.icon_photo);
        }else if (stringArrayList_fankui.size()==5) {
            add_potsition=6;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(true);
            imageView5.setLongClickable(true);


//
//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));
//            imageView4.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(3))));
//            imageView5.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(4))));

//            imageView1.setImageBitmap(listbit.get(0));
//            imageView2.setImageBitmap(listbit.get(1));
//            imageView3.setImageBitmap(listbit.get(2));
//            imageView4.setImageBitmap(listbit.get(3));
//            imageView5.setImageBitmap(listbit.get(4));
//            ImageLoader.getInstance().loadImage();

            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(1), imageView2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(2), imageView3);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(3), imageView4);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_fankui.get(4), imageView5);

//            BitmapDrawable bd1 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView1.setBackgroundDrawable(bd1);
//
//            BitmapDrawable bd2 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView2.setBackgroundDrawable(bd2);
//            BitmapDrawable bd3 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(2))));
//            imageView3.setBackgroundDrawable(bd3);
//            BitmapDrawable bd4 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(3))));
//            imageView4.setBackgroundDrawable(bd4);
//            BitmapDrawable bd5 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView5.setBackgroundDrawable(bd5);



        }
//        handler.sendEmptyMessage(2);
        Log.d("123","123");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        handler.sendEmptyMessage(3);
    }
    protected void setimage(){
        int testsize = MyAdapter.mSelectedImage.size();
        if (stringArrayList_fankui.size()==0){
            add_potsition=1;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(false);
            imageView2.setLongClickable(false);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);
            imageView1.setImageResource(R.drawable.icon_photo);
            imageView2.setImageBitmap(null);
            imageView3.setImageBitmap(null);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==1) {
            add_potsition=2;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(false);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

            // imageView1.setImageResource(R.drawable.home);
//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));

//            imageView1.setImageBitmap(listbit.get(0));
            imageView2.setImageResource(R.drawable.icon_photo);
            imageView3.setImageBitmap(null);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==2) {
            add_potsition=3;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(false);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

            //imageView1.setImageResource(R.drawable.home);
            //imageView2.setImageResource(R.drawable.home);

//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_close));
//            imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));


            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));

//            imageView1.setImageBitmap(listbit.get(0));
//            imageView1.setImageBitmap(listbit.get(1));
            imageView3.setImageResource(R.drawable.icon_photo);
            imageView4.setImageBitmap(null);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==3) {
            add_potsition=4;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(false);
            imageView5.setLongClickable(false);

//            imageView1.setImageResource(R.drawable.home);
//            imageView2.setImageResource(R.drawable.home);
//            imageView3.setImageResource(R.drawable.home);

//            imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
//            imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));
//            imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_close));

            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));

//            imageView1.setImageBitmap(listbit.get(0));
//            imageView1.setImageBitmap(listbit.get(1));
//            imageView1.setImageBitmap(listbit.get(2));
            imageView4.setImageResource(R.drawable.icon_photo);
            imageView5.setImageBitmap(null);
        }else if (stringArrayList_fankui.size()==4) {
            add_potsition=5;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(true);
            imageView5.setLongClickable(false);

//            imageView1.setImageResource(R.drawable.home);
//            imageView2.setImageResource(R.drawable.home);
//            imageView3.setImageResource(R.drawable.home);
//            imageView4.setImageResource(R.drawable.home);

//              imageView1.setImageDrawable(MyAdapter.mDrawlist.get(0));
//              imageView2.setImageDrawable(MyAdapter.mDrawlist.get(1));
//              imageView3.setImageDrawable(MyAdapter.mDrawlist.get(2));
//              imageView4.setImageDrawable(MyAdapter.mDrawlist.get(3));

            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));
            imageView4.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(3))));
//            imageView4.setImageBitmap(listbit.get(0));
//            imageView2.setImageBitmap(listbit.get(1));
//            imageView3.setImageBitmap(listbit.get(2));
//            imageView4.setImageBitmap(listbit.get(3));
            imageView5.setImageResource(R.drawable.icon_photo);
        }else if (stringArrayList_fankui.size()==5) {
            add_potsition=6;
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView1.setLongClickable(true);
            imageView2.setLongClickable(true);
            imageView3.setLongClickable(true);
            imageView4.setLongClickable(true);
            imageView5.setLongClickable(true);


//
//            imageView1.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView2.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView3.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(2))));
//            imageView4.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(3))));
//            imageView5.setImageBitmap(compressImage(picnormal(stringArrayList_fankui.get(4))));


//            ImageLoader.getInstance(5, ImageLoader.Type.LIFO).loadImage(stringArrayList_fankui.get(0), imageView1);
//            ImageLoader.getInstance(5, ImageLoader.Type.LIFO).loadImage(stringArrayList_fankui.get(1), imageView2);
//            ImageLoader.getInstance(5, ImageLoader.Type.LIFO).loadImage(stringArrayList_fankui.get(2), imageView3);
//            ImageLoader.getInstance(5, ImageLoader.Type.LIFO).loadImage(stringArrayList_fankui.get(3), imageView4);
//            ImageLoader.getInstance(5, ImageLoader.Type.LIFO).loadImage(stringArrayList_fankui.get(4), imageView5);

//            BitmapDrawable bd1 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView1.setBackgroundDrawable(bd1);
//
//            BitmapDrawable bd2 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(1))));
//            imageView2.setBackgroundDrawable(bd2);
//            BitmapDrawable bd3 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(2))));
//            imageView3.setBackgroundDrawable(bd3);
//            BitmapDrawable bd4 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(3))));
//            imageView4.setBackgroundDrawable(bd4);
//            BitmapDrawable bd5 = new BitmapDrawable(compressImage(picnormal(stringArrayList_fankui.get(0))));
//            imageView5.setBackgroundDrawable(bd5);



        }
//        handler.sendEmptyMessage(2);
        Log.d("123","123");
    }
    protected void feedbackTest(){

        FankuiClient.requestMessage(Fankui.this, hguid, tooken, feedbackid, content, new FankuiHandler() {
            @Override
            public void onRequestSuccess(String response) {
                super.onRequestSuccess(response);
                dialog.dismiss();
                Log.e("结果s","sssS1");
                toastOnly.toastShowShort("反馈成功");
            }
            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                dialog.dismiss();
                Log.e("结果s","sssSf");
                toastOnly.toastShowShort("反馈成功");
            }
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                dialog.dismiss();
                Log.e("结果s","sssSE");
                toastOnly.toastShowShort("反馈成功");
            }
            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                dialog.dismiss();
                toastOnly.toastShowShort("网络超时");
            }

        }, TestOrNot.isTest);
    }
    protected void getGUID(){
        UUID uuid = UUID.randomUUID();
        Log.e("结果uuid",uuid.toString());
    }
//    protected void getbit(){
//        for (int i=0;i<MyAdapter.mSelectedImage.size();i++){
//            listbit.add(compressImage(picnormal(MyAdapter.mSelectedImage.get(i))));
//        }
//        handler.sendEmptyMessage(1);
//    }
    protected  void closeSoftInput(){
        if (getCurrentFocus() !=null){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus()
                            .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    private void clear(Context context){
        ActivityManager activityManger=(ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list=activityManger.getRunningAppProcesses();
        if(list!=null)
            for(int i=0;i<list.size();i++)
            {
                ActivityManager.RunningAppProcessInfo apinfo=list.get(i);

                Log.e("pid---->>>>>>>",""+apinfo.pid);
                Log.e("processName->> ",""+apinfo.processName);
                Log.e("importance-->>",""+apinfo.importance);
                String[] pkgList=apinfo.pkgList;

                if(apinfo.importance>ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE)
                {
                    // Process.killProcess(apinfo.pid);
                    for(int j=0;j<pkgList.length;j++)
                    {
                        //2.2以上是过时的,请用killBackgroundProcesses代替
                        /**清理不可用的内容空间**/
                        //activityManger.restartPackage(pkgList[j]);
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
    }
}
