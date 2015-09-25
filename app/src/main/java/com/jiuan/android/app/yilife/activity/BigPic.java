package com.jiuan.android.app.yilife.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.imageloader.MyAdapter;

import java.io.File;
import java.io.IOException;

public class BigPic extends ParentActivity {
    private ImageView iv;
    private PopupWindow popupWindow;
    private ImageView iv_back;
    private String  path,from;
    private LinearLayout layout_sesseion,layout_timeline;
    private TextView tv_delete;
    private int position=-1;
    private Bitmap transformed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        Intent intent = getIntent();
        path = intent.getStringExtra("imagedetail");
        from = intent.getStringExtra("from");
        position = intent.getIntExtra("position",-1);
//        path = "/storage/emulated/0/DCIM/Camera/20150127_145249.jpg";
        iv = (ImageView) findViewById(R.id.iv_bigpic);
//        iv.setImageBitmap(getimage(path));
//        ImageLoaderNormal.getInstance(3, ImageLoaderNormal.Type.LIFO).loadImage(path, iv);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(pathName, options);
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        float hh = 480f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;//设置缩放比例
        bitmap = BitmapFactory.decodeFile(path, options);
        Matrix m =turnright(path);
        if (bitmap.getWidth()>bitmap.getHeight()) {
            transformed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getHeight(), bitmap.getHeight(), m, true);
        }else {
            transformed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getWidth(), m, true);
        }
        iv.setImageBitmap(transformed);
        Log.d("ssss", path);
        Log.d("ssssh", h+"");
        Log.d("ssssw", w+"");
        Log.d("ssss1", bitmap+"");
//        Log.d("ssss1", ""+ImageLoaderNormal.bitmapforBig);
//        iv.setImageBitmap(ImageLoaderNormal.bitmapforBig);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_delete = (TextView) findViewById(R.id.blue_setting);
        tv_delete.setText("删除");
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                        if (from.equals("fankui")){
//                            for (int i =0;i<Fankui.stringArrayList_fankui.size();i++) {
//                                if (Fankui.stringArrayList_fankui.get(i).equals(path)) {
//                                    Fankui.stringArrayList_fankui.remove(i);
//                                    MyAdapter.mSelectedImage_count.remove(i);
//                                    break;
//                                }
//                            }
                            if (position!=-1){
                                Fankui.stringArrayList_fankui.remove(position);
                                MyAdapter.mSelectedImage_count.remove(position);
                            }
                            BigPic.this.finish();
                        }else{
//                            for (int i =0;i<SendNote.stringArrayList_sendnote.size();i++) {
//                                if (SendNote.stringArrayList_sendnote.get(i).equals(path)) {
//                                    SendNote.stringArrayList_sendnote.remove(i);
//                                    MyAdapter.mSelectedImage_count.remove(i);
//                                }
//                            }
                            if (position!=-1){
                                Log.e("big1",""+position);
                                Log.e("big2",""+MyAdapter.mSelectedImage.size());
                                Log.e("big3",""+MyAdapter.mSelectedImage_count.size());
//                                SendNote.stringArrayList_sendnote.clear();
                                SendNote.stringArrayList_sendnote.remove(position);
                                MyAdapter.mSelectedImage_count.remove(position);
//                                MyAdapter.mSelectedImage.remove(position);
                            }
                            BigPic.this.finish();
                        }
                    }
        });
//        getneedimage(path);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAdapter.mSelectedImage.clear();
        if (transformed != null && !transformed.isRecycled()) {
            transformed.recycle();
            transformed = null;
        }
    }

    protected void getneedimage(String path) {
        File baseFile = new File(path);
        Uri uri = Uri.fromFile(baseFile);
        Intent intent = new Intent();
        String tt = Environment.getExternalStorageDirectory() + "/linshi/";
        File file = new File(tt);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            File tempFile = File.createTempFile("crop", ".png", file);
            Uri tempUri = Uri.fromFile(tempFile);
            intent.putExtra("output", tempUri);
//            intent.setDataAndType(tempUri, "image/*");// mUri是已经选择的图片Uri
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private Bitmap getimage(String srcPath) {

    Bitmap bitmap = BitmapFactory.decodeFile(srcPath);//此时返回bm为空


    return bitmap;//压缩好比例大小后再进行质量压缩
}
    private Matrix turnright(String path){
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
        return m;
    }
}
