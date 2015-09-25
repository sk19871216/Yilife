package com.jiuan.android.app.yilife.activity;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaClient;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaHandler;
import com.jiuan.android.app.yilife.bean.erweima.MyErWeiMaResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.RoundImageView;
import com.jiuan.android.app.yilife.utils.TestOrNot;

public class MyErweima extends ParentActivity {
    private TextView textView,tv_title;
    private ImageView iv_back,imageView_erweima;
    private RoundImageView iv_touxiang;
    private RequestQueue queue;
    private ProgressDialog dialog;
    private int width=0;
    private Handler handler;
    private String un,phone,bitmap64,tooken,name;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_erweima);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.show();
        queue = Volley.newRequestQueue(this);
        textView = (TextView) findViewById(R.id.erweima_name);
        iv_touxiang = (RoundImageView) findViewById(R.id.erweima_touxiang);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        tv_title = (TextView) findViewById(R.id.blue_title);
        imageView_erweima = (ImageView) findViewById(R.id.myErweima);

//        int width = getWindowManager().getDefaultDisplay().getWidth()-240;

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title.setText("我的二维码");


    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent =getIntent();
        name = intent.getStringExtra("nikename");
        sharedPreferences = getSharedPreferences("self", 0);
        un = sharedPreferences.getString("HGUID", "");
        tooken = sharedPreferences.getString("AccessToken","");
        bitmap64 = sharedPreferences.getString("bitmap","");
        phone = sharedPreferences.getString("phone","");
        if (name.equals("")){
            if (phone.length()!=11){
                textView.setText("");
            }else{
                textView.setText(phone.substring(0,3)+"****"+phone.substring(7,11));
            }
        }else{
            textView.setText(name);
        }
        Log.e("结果结果",bitmap64);
        if (bitmap64.equals("")){
            Log.e("结果结果",bitmap64);
            iv_touxiang.setImageResource(R.drawable.touxiang);
        }else {
            Log.e("结果结果",bitmap64);
            iv_touxiang.setImageBitmap(base64ToBitmap(bitmap64));
        }
        MyErWeiMaClient.request(MyErweima.this,un,tooken,new MyErWeiMaHandler(){
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                dialog.dismiss();
                FailMessage.showfail(MyErweima.this,value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                dialog.dismiss();
                FailMessage.showfail(MyErweima.this,msg);
            }

            @Override
            public void onLoginSuccess(MyErWeiMaResponse response) {
                super.onLoginSuccess(response);
                ImageRequest imageRequest = new ImageRequest(
                        response.getqRCodeUrl(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                dialog.dismiss();
                                Drawable drawable =new BitmapDrawable(response);
                                imageView_erweima.setBackgroundDrawable(drawable);
//                                imageView_erweima.setImageBitmap(response);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                    }
                });
                queue.add(imageRequest);
//                ImageRequest imageRequest1 = new ImageRequest(
//                        response.getPhotoUrl(),
//                        new Response.Listener<Bitmap>() {
//                            @Override
//                            public void onResponse(Bitmap response) {
//                                dialog.dismiss();
//                                imageView_erweima.setImageBitmap(response);
//                            }
//                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        dialog.dismiss();
//                        iv_touxiang.setImageResource(R.drawable.touxiang);
//                    }
//                });
//                queue.add(imageRequest);
//                if (response.getNickname().equals("")){
//                    if (response.getMobilePhone().length() == 11){
//                        String pp = response.getMobilePhone().substring(0,3)+"****"+response.getMobilePhone().substring(7,11);
//                        textView.setText(pp);
//                    }else{
//                        textView.setText("");
//                    }
//                }else{
//                    textView.setText(response.getNickname());
//                }
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                dialog.dismiss();
                Toast.makeText(MyErweima.this,"网络超时",Toast.LENGTH_SHORT).show();
            }
        }, TestOrNot.isTest);
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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

}
