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
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetForumuserinfoHandler;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetForumuserinfoResponse;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoClient;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class BBSinfoActivity extends ParentActivity implements View.OnClickListener{
    private RelativeLayout layout_name,layout_real_name,layout_sex,layout_bir,layout_qq,layout_mail,layout_phone;
    private TextView tv_name,tv_real_name,tv_sex,tv_bir,tv_qq,tv_mail,tv_phone,tv_title;
    private ToastOnly toastOnly;
    private ProgressDialog dialog;
    private ImageView iv_back,networkImageView;
    private RequestQueue queue;
    private String phone_noxx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbsinfo);
        queue = Volley.newRequestQueue(BBSinfoActivity.this);
        toastOnly = new ToastOnly(this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        initialize();
    }
    protected void initialize(){
        //layout布局
        layout_name = (RelativeLayout) findViewById(R.id.relativelayout_bbs_nicheng);
        layout_name.setOnClickListener(this);
        layout_phone = (RelativeLayout) findViewById(R.id.relativelayout_bbs_phone);
        layout_phone.setOnClickListener(this);
        layout_real_name = (RelativeLayout) findViewById(R.id.relativelayout_real_name);
        layout_real_name.setOnClickListener(this);
        layout_sex = (RelativeLayout) findViewById(R.id.relativelayout_bbs_xingbie);
        layout_sex.setOnClickListener(this);
        layout_bir = (RelativeLayout) findViewById(R.id.relativelayout_bbs_birday);
        layout_bir.setOnClickListener(this);
        layout_qq = (RelativeLayout) findViewById(R.id.relativelayout_bbs_qq);
        layout_qq.setOnClickListener(this);
        layout_mail = (RelativeLayout) findViewById(R.id.relativelayout_bbs_mail);
        layout_mail.setOnClickListener(this);

        //textview
        tv_name = (TextView) findViewById(R.id.tv_bbs_nicheng);
        tv_real_name = (TextView) findViewById(R.id.tv_bbs_realname);
        tv_sex = (TextView) findViewById(R.id.tv_bbs_sex);
        tv_bir = (TextView) findViewById(R.id.tv_bbs_bir);
        tv_qq = (TextView) findViewById(R.id.tv_bbs_qq);
        tv_mail = (TextView) findViewById(R.id.tv_bbsmail);
        tv_phone = (TextView) findViewById(R.id.tv_bbs_phone);
        networkImageView = (ImageView) findViewById(R.id.tv_bbs_touxiangg);

        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("社区资料");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        if (!sharedPreferences.getString("bitmap","").equals("")) {
            networkImageView.setImageBitmap(base64ToBitmap(sharedPreferences.getString("bitmap","")));
        }
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BBSinfoActivity.this,ForumInfoChange.class);
        int id = v.getId();
        switch (id){
            case  R.id.relativelayout_bbs_nicheng:
                intent.putExtra("title", "更改昵称");
                intent.putExtra("changeinfo",tv_name.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_real_name:
                intent.putExtra("title", "更改姓名");
                intent.putExtra("changeinfo",tv_real_name.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_bbs_xingbie:
                intent.putExtra("title", "更改性别");
                intent.putExtra("changeinfo",tv_sex.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_bbs_birday:
                intent.putExtra("title", "出生年月");
                intent.putExtra("changeinfo",tv_bir.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_bbs_qq:
                intent.putExtra("title", "更改QQ");
                intent.putExtra("changeinfo",tv_qq.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_bbs_mail:
                intent.putExtra("title", "更改邮箱");
                intent.putExtra("changeinfo",tv_mail.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.relativelayout_bbs_phone:
                if (!tv_phone.getText().equals("")) {
                    Intent intent_phone_old  = new Intent(BBSinfoActivity.this,CheckOldPhone.class);
                    intent_phone_old.putExtra("oldphone",tv_phone.getText().toString().trim());
                    startActivity(intent_phone_old);
                }else{
                    Intent intent_phone_new  = new Intent(BBSinfoActivity.this,CheckNewPhone.class);
                    intent_phone_new.putExtra("hasnophone",true);
                    startActivity(intent_phone_new);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        String tooken = sharedPreferences.getString("AccessToken","");
        String phone = sharedPreferences.getString("phone","");
        String hguid = sharedPreferences.getString("HGUID","");
        if (NetWorkInfo.isNetworkAvailable(BBSinfoActivity.this)) {
            GetuserinfoClient.getforumsuserinfo(BBSinfoActivity.this, hguid, tooken, new GetForumuserinfoHandler() {
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
                    FailMessage.showfail(BBSinfoActivity.this, value);
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(BBSinfoActivity.this, msg);
                }

                @Override
                public void onLoginSuccess(GetForumuserinfoResponse response) {
                    super.onLoginSuccess(response);
                    dialog.dismiss();
                    tv_name.setText(response.getNickName().trim());
                    tv_real_name.setText(response.getRealName().trim());
                    if (response.getSex() == 1) {
                        tv_sex.setText("男");
                    } else if (response.getSex() == 2) {
                        tv_sex.setText("女");
                    }else if (response.getSex() == 0){
                        tv_sex.setText("保密");
                    }else{
                        tv_sex.setText("");
                    }

                tv_bir.setText(response.getBirthday().

                trim()

                );
                if(!response.getPhone().

                trim()

                .

                equals("")

                )

                {
                    phone_noxx = response.getPhone().trim().substring(0, 3) + "****" + response.getPhone().trim().substring(7, 11);
                }

                else

                {
                    phone_noxx = response.getPhone().trim();
                }

                tv_phone.setText(phone_noxx);
                tv_qq.setText(response.getQq().

                trim()

                );
                tv_mail.setText(response.getEmailAddress().

                trim()

                );
                String sss = response.getAvatars().substring(response.getAvatars().lastIndexOf(".") + 1);
//                    Log.e("123", sss);
//                    boolean ispic = false;
//                    if (sss.equals("png") || sss.equals("jpg") || sss.equals("jpeg")){
//                        ispic =true;
//                    }
                if(!response.getAvatars().

                equals("")

                )

                {
                    ImageRequest imageRequest = new ImageRequest(
                            response.getAvatars().replace("https", "http"),
//                                    "http://developer.android.com/images/home/aw_dac.png",
                            new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    dialog.dismiss();
                                    networkImageView.setImageBitmap(toRoundCorner(response, 90.0f));
                                }
                            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            networkImageView.setImageResource(R.drawable.touxiang);
                        }
                    });
                    queue.add(imageRequest);
                }

                else

                {
                    networkImageView.setImageResource(R.drawable.touxiang);
                    dialog.dismiss();
                }
            }
        }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
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
