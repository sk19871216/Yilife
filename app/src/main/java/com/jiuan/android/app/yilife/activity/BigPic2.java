package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.changetouxiangpic.ChangetouxiangPicClient;
import com.jiuan.android.app.yilife.bean.changetouxiangpic.ChangetouxiangPicHandler;
import com.jiuan.android.app.yilife.utils.TestOrNot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import Decoder.BASE64Encoder;

public class BigPic2 extends ParentActivity {
    private ImageView iv;
    private PopupWindow popupWindow;
    private ImageView iv_back;
    private String  path,from,phone,tooken,hguid;
    private LinearLayout layout_sesseion,layout_timeline;
    private TextView tv_delete;
    private Bitmap bitmapneed;
    public static Bitmap bitmaplast=null;
    private ProgressDialog dialog;
    private boolean isforreslt=false;
    private ChangetouxiangPicClient client;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                ChangetouxiangPicClient.cancelRequests(BigPic2.this,true);
            }
        }
    };
//
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isforreslt", isforreslt);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        isforreslt=false;
        Log.e("azx","creat");
        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", "");
        tooken = sharedPreferences.getString("AccessToken", "");
        hguid = sharedPreferences.getString("HGUID", "");

        Intent intent = getIntent();
        path = intent.getStringExtra("imagedetail");
        from = intent.getStringExtra("from");
//        path = "/storage/emulated/0/DCIM/Camera/20150127_145249.jpg";
        Log.d("ssss", path);
        iv = (ImageView) findViewById(R.id.iv_bigpic);
//        iv.setImageBitmap(getimage(path));
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_delete = (TextView) findViewById(R.id.blue_setting);
        tv_delete.setText("");
        if (savedInstanceState !=null) {
            if (isforreslt)
                getneedimage(path);
        }else{
            getneedimage(path);
        }
//        tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//                public void onClick(View v) {
//                    BigPic2.this.finish();
//                    bitmaplast =toRoundCorner(bitmapneed,0.0f);
//                ChangetouxiangPicClient.requestLogin(BigPic2.this,phone,tooken,"png",ChangeTO64(bitmaplast),new ChangetouxiangPicHandler(){
//                    @Override
//                    public void onInnovationError(String value) {
//                        super.onInnovationError(value);
//                        FailMessage.showfail(BigPic2.this, value);
//                    }
//
//                    @Override
//                    public void onInnovationFailure(String msg) {
//                        super.onInnovationFailure(msg);
//                        FailMessage.showfail(BigPic2.this,msg);
//                    }
//
//                    @Override
//                    public void onLoginSuccess(String response) {
//                        super.onLoginSuccess(response);
//                    }
//
//                    @Override
//                    public void onInnovationExceptionFinish() {
//                        super.onInnovationExceptionFinish();
//                        Toast.makeText(BigPic2.this,"网络超时",Toast.LENGTH_SHORT).show();
//                    }
//                }, TestOrNot.isTest);
////                    Intent intent1 = new Intent(BigPic2.this,Store.class);
////                    intent1.putExtra("bitmap",bitmaplast);
////                    startActivity(intent1);
//                }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isforreslt = true;
        Log.d("ssssasdata", ""+data);
        Log.d("ssssasdrequestCode", ""+requestCode);
        Log.d("ssssasdresultCode", ""+resultCode);
        if (requestCode==1 && resultCode == RESULT_OK){
            dialog = new ProgressDialog(BigPic2.this);
            dialog.setMessage("上传中...");
//            dialog.show();
            Intent broad = new Intent("" + 200);
            sendBroadcast(broad);
            bitmapneed = data.getParcelableExtra("data");
            SharedPreferences sharedPreferences = getSharedPreferences("self",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("bitmap",bitmapToBase64(bitmapneed)).commit();
            client = new ChangetouxiangPicClient();
            client.requestLogin(BigPic2.this,hguid,tooken,"png",ChangeTO64(bitmapneed),new ChangetouxiangPicHandler(){
                @Override
                public void onStart() {
                    super.onStart();
                    Toast.makeText(BigPic2.this,"头像上传中...",Toast.LENGTH_SHORT).show();
                    BigPic2.this.finish();
                }

                @Override
                public void onInnovationError(String value) {
                    super.onInnovationError(value);
//                    FailMessage.showfail(BigPic2.this,value);
//                    BigPic2.this.finish();
//                    dialog.dismiss();
//                    Toast.makeText(BigPic2.this,"上传失败",Toast.LENGTH_SHORT).show();
//                    FailMessage.showfail(BigPic2.this, value);
//                    BigPic2.this.finish();
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
//                    Toast.makeText(BigPic2.this,"上传失败",Toast.LENGTH_SHORT).show();
//                    BigPic2.this.finish();
//                    dialog.dismiss();
                }

                @Override
                public void onLoginSuccess(String response) {
                    super.onLoginSuccess(response);
//                    SharedPreferences sharedPreferences =getSharedPreferences("self", Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor editor_self = sharedPreferences.edit();
//                    editor_self.putString("bitmap", ChangeTO64(bitmapneed)).commit();
//                    BigPic2.this.finish();
//                    dialog.dismiss();
//                    dialog.dismiss();
//                    BigPic2.this.finish();
//                    Toast.makeText(BigPic2.this,"上传成功",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInnovationExceptionFinish() {
                    super.onInnovationExceptionFinish();
                    dialog.dismiss();
                    Toast.makeText(BigPic2.this,"网络超时",Toast.LENGTH_SHORT).show();
//                    client.cancelRequests(BigPic2.this,true);
//                    client.shutdown();
//                    client.cancelAllRequests(true);
//                    handler.sendEmptyMessage(1);
                    BigPic2.this.finish();

                }
            }, TestOrNot.isTest);
        }else{
            BigPic2.this.finish();
        }
    }

    private Bitmap getimage(String srcPath) {

        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,null);//此时返回bm为空


        return bitmap;//压缩好比例大小后再进行质量压缩
    }
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        System.out.println("图片是否变成圆角模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        System.out.println("pixels+++++++"+pixels);
        return output;
    }
    protected void getneedimage(String path){
        Log.d("ssssasdpath", "path");
        File baseFile = new File(path);
        Uri uri =  Uri.fromFile(baseFile) ;
        Intent intent = new Intent();
        String tt = Environment.getExternalStorageDirectory()+"/linshi/";
        File file = new File(tt);
        if (!file.exists()) {
            file.mkdirs();
        }
        try{
            File tempFile = File.createTempFile("crop", ".png",file);
            Uri tempUri = Uri.fromFile(tempFile);
            intent.putExtra("output", tempUri);
//            intent.setDataAndType(tempUri, "image/*");// mUri是已经选择的图片Uri
        } catch (IOException e) {
            e.printStackTrace();
        }


        intent.putExtra("outputFormat", "PNG");
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);// 输出图片大小
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);

    }
    public  String ChangeTO64(Bitmap bp)
    {
//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        String imgFile = path;//待处理的图片
//        Bitmap bp = getimage(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int options = 100;
//        bp.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        while ( baos.toByteArray().length / 1024>1024) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            options -= 10;//每次都减少10
//            bp.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//
//        }
//        InputStream in = null;
//        byte[] data = null; //读取图片字节数组
//        try
//        {
//            in = new FileInputStream(imgFile);
////            data = new byte[in.available()];
////            in.read(data);
//            in.read(baos.toByteArray());
//            in.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace(); }
            //对字节数组Base64编码
        bp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(baos.toByteArray());//返回Base64编码过的字节数组字符串
    }
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
