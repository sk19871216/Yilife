package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoClient;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoHandler;
import com.jiuan.android.app.yilife.bean.getuserinfo.GetuserinfoResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.config.RoundImageView;
import com.jiuan.android.app.yilife.imageloader.SelectImageTouxiangActivity;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDetail extends ParentActivity implements View.OnClickListener{
    private EditText editText,editText_ag,editText_phone,editText_first;
    private RelativeLayout layout_nicheng,layout_addr,layout_phone,layout_bir,layout_sex,layout_password,layout_touxiang,layout_sanfang,layout_erweima,layout_invitationcode;
    private ImageView imageView;
    private TextView tv_nicheng,tv_phone,tv_bir,tv_sex,tv_touxiang,tv_addr,tv_invitationcode;
    private DatePickerDialog datePickerDialog;
    private String day,nn,yy,dd,name,phone,sex1,birday,path,date,nowdate;
    private ToastOnly toastOnly = new ToastOnly(this);
    private Button button_exit ;
    private LinearLayout linearLayout;
    private ActionBar actionBar;
    private TextView tv_bar_title,tv_setting;
    private ImageView iv_back;
    private int sex;
    private int haspassword = 1;
    private ImageView networkImageView;
    private RequestQueue queue;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private RoundImageView roundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState){
            date = savedInstanceState.getString("date");
        }
        setContentView(R.layout.activity_my_detail);
        dialog = new ProgressDialog(MyDetail.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        initialize();//初始化
        queue = Volley.newRequestQueue(MyDetail.this);
        tv_bar_title = (TextView) findViewById(R.id.blue_title);
        tv_bar_title.setText("个人信息");
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setVisibility(View.GONE);
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        registerBoradcastReceiver();

//        spget();
//        getUserInfo();
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", date);

    }

    @Override
    public void onClick(View v) {
        Intent intent_change  = new Intent(MyDetail.this,Changes.class);
        int id =v.getId();
        switch (id) {
            case R.id.relativelayout_mydetail_touxiang:
                String tt = Environment.getExternalStorageDirectory()+"/linshi/";
                File file = new File(tt);
                delete(file);
                alertDialog  = new AlertDialog.Builder(MyDetail.this).create();
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                alertDialog.getWindow().setContentView(R.layout.mydialog);
                alertDialog.getWindow()
                        .findViewById(R.id.mydialog_cancle)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.getWindow()
                        .findViewById(R.id.mydialog_local)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MyDetail.this, SelectImageTouxiangActivity.class);
                                startActivity(intent);
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.getWindow()
                        .findViewById(R.id.mydialog_takepic)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                date = df.format(new Date());
                                date = date.substring(0, 4)+date.substring(5,7)+date.substring(8,10)+"_"+date.substring(11,13)+date.substring(14,16)+date.substring(17,19);
                                nowdate = Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg";

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/", date+".jpg")));
                                startActivityForResult(intent, 2);
                                alertDialog.dismiss();
                            }
                        });
//                Intent intent = new Intent(MyDetail.this, SelectImageTouxiangActivity.class);
//                startActivity(intent);
                break;
            case R.id.relativelayout_mydetail_nicheng:
                intent_change.putExtra("title", "更改昵称");
                intent_change.putExtra("name",tv_nicheng.getText().toString().trim());
                startActivityForResult(intent_change,1);
                break;
            case R.id.relativelayout_mydetail_phone:
                if (!tv_phone.getText().equals("")){
                    Intent intent_phone_old  = new Intent(MyDetail.this,CheckOldPhone.class);
                    intent_phone_old.putExtra("oldphone",tv_phone.getText().toString().trim());
                    startActivity(intent_phone_old);
                }else{
                    Intent intent_phone_new  = new Intent(MyDetail.this,CheckNewPhone.class);
                    intent_phone_new.putExtra("hasnophone",true);
                    startActivity(intent_phone_new);
                }

                break;
            case R.id.relativelayout_mydetail_birday:
                intent_change.putExtra("title", "出生年月");
                intent_change.putExtra("bir",tv_bir.getText().toString().trim());
                startActivityForResult(intent_change,1);
                break;
            case R.id.relativelayout_mydetail_xingbie:
                intent_change.putExtra("title", "更改性别");
                intent_change.putExtra("sex",tv_sex.getText().toString().trim());
                startActivityForResult(intent_change,1);
                break;
            case R.id.relativelayout_mydetail_changepassword:
                if (haspassword==1) {
                    intent_change.putExtra("title", "密码管理");
                    startActivityForResult(intent_change, 1);
                }else if(haspassword==0){
                    Intent intent_password_new  = new Intent(MyDetail.this,CreatNewPassword.class);
                    startActivity(intent_password_new);
                }
                break;
            case R.id.relativelayout_mydetail_addr:
                intent_change.putExtra("title", "我的地址");
                intent_change.putExtra("addr",tv_addr.getText().toString().trim());
                startActivityForResult(intent_change,1);
                break;
//            case R.id.relativelayout_mydetail_erweima:
//                Intent intent_erweima  =  new Intent(MyDetail.this,MyErweima.class);
//                intent_erweima.putExtra("nikename",tv_nicheng.getText().toString().trim());
//                startActivity(intent_erweima);
//                break;
            case R.id.relativelayout_mydetail_bangding:
                Intent intent_bangding  =  new Intent(MyDetail.this,ThreadBangding.class);
                startActivity(intent_bangding);
                break;
//            case R.id.relativelayout_mydetail_invitationcode:
//                Intent intent_invitationcode  =  new Intent(MyDetail.this,FriendRecommend.class);
//                intent_invitationcode.putExtra("invitationcode",tv_invitationcode.getText().toString().trim());
//                startActivity(intent_invitationcode);
//                break;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            spget();
            getUserInfo();
        }else if (requestCode == 2 && resultCode == RESULT_OK){
            String picpath = Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg";
            File file = new File(picpath);
            if (file.exists()){
                Intent intent = new Intent(MyDetail.this,BigPic2.class);
                intent.putExtra("imagedetail",picpath);
                startActivity(intent);
            }

        }

    }
    private void spget() {
        SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        name = mySharedPreferences.getString("name", "");
        phone = mySharedPreferences.getString("phone", "");
        sex1 = mySharedPreferences.getString("sex", "");
        birday = mySharedPreferences.getString("day", "");
        path = mySharedPreferences.getString("imagepath", "");

        tv_nicheng.setText(name);
        if (!phone.equals("")) {
            tv_phone.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
        }else{
            tv_phone.setText(phone);
        }
        tv_sex.setText(sex1);
        if (birday == "" || birday == "-2208988800" ||birday.equals("0001-01-01")) {
            tv_bir.setText("");
        }else {
            tv_bir.setText(birday);
        }
//        if (name.length() != 0) {
//            tv_touxiang.setBackgroundResource(R.drawable.icon_tx);
//            tv_touxiang.setText(name.substring(name.length() - 1, name.length()));
//        }else{
//            tv_touxiang.setText("");
//            tv_touxiang.setBackgroundResource(R.drawable.touxiang);
//        }
//        if (!path.equals("")){
//            imageView.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeFile(path)));
//        }
    }
    private void initialize(){
//        imageView = (ImageView) findViewById(R.id.imageview_mydetail_touxiang);
//        imageView.setOnClickListener(this);
//        tv_touxiang = (TextView) findViewById(R.id.tv_mydetail_touxiangg);
        networkImageView = (ImageView) findViewById(R.id.tv_mydetail_touxiangg);
        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        if (!sharedPreferences.getString("bitmap","").equals("")) {
            networkImageView.setImageBitmap(base64ToBitmap(sharedPreferences.getString("bitmap","")));
        }
        layout_nicheng = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_nicheng);
        layout_nicheng.setOnClickListener(this);
//        layout_erweima = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_erweima);
//        layout_erweima.setOnClickListener(this);
        layout_phone = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_phone);
        layout_phone.setOnClickListener(this);
        layout_addr = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_addr);
        layout_addr.setOnClickListener(this);
        layout_bir= (RelativeLayout) findViewById(R.id.relativelayout_mydetail_birday);
        layout_bir.setOnClickListener(this);
        layout_sex = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_xingbie);
        layout_sex.setOnClickListener(this);
        layout_password = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_changepassword);
        layout_password.setOnClickListener(this);
        layout_touxiang = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_touxiang);
        layout_touxiang.setOnClickListener(this);
        layout_sanfang = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_bangding);
        layout_sanfang.setOnClickListener(this);
//        layout_invitationcode = (RelativeLayout) findViewById(R.id.relativelayout_mydetail_invitationcode);
//        layout_invitationcode.setOnClickListener(this);
        tv_nicheng = (TextView) findViewById(R.id.tv_mydetail_nicheng);
        tv_phone = (TextView) findViewById(R.id.tv_mydetail_phone);
        tv_sex = (TextView) findViewById(R.id.tv_mydetail_sex);
        tv_bir = (TextView) findViewById(R.id.tv_mydetail_bir);
        tv_addr = (TextView) findViewById(R.id.tv_mydetail_addr);
//        tv_invitationcode = (TextView) findViewById(R.id.tv_mydetail_invitationcode);


        button_exit = (Button) findViewById(R.id.mydetail_logout);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyDetail.this);
                builder.setMessage("您是否继续退出")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences  mySharedPreferences= getSharedPreferences("login", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                editor.putInt("isLogin",0).commit();
                                MyDetail.this.finish();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                AlertDialog alert = builder.create();

            }
        });
    }
    public void getUserInfo(){
        dialog.show();
        final SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        String tooken = sharedPreferences.getString("AccessToken", "");
        String phone = sharedPreferences.getString("phone","");
        String hguid = sharedPreferences.getString("HGUID","");
        if (NetWorkInfo.isNetworkAvailable(MyDetail.this)) {
            GetuserinfoClient.requestLogin(MyDetail.this, hguid, tooken,
                    new GetuserinfoHandler() {
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
//                        toastOnly.toastShowShort("更新数据失败");
                            FailMessage.showfail(MyDetail.this, msg);
                            dialog.dismiss();
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            Toast.makeText(MyDetail.this, "网络超时", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(MyDetail.this, value);
                            dialog.dismiss();
                        }

                        @Override
                        public void onLoginSuccess(GetuserinfoResponse response) {
                            super.onLoginSuccess(response);
                            SharedPreferences sharedPreferences1 = getSharedPreferences("self",0);
                            sharedPreferences1.edit().putString("InvitationCode",response.getInvitationCode());
                            sharedPreferences1.edit().putString("phone", response.getPhone().trim()).commit();
                            haspassword = response.getHasPassword();
                            tv_nicheng.setText(response.getUserName().trim());
                            if (!response.getPhone().trim().equals("")) {
                                String xxPhone = response.getPhone().trim().substring(0, 3) + "****" + response.getPhone().trim().substring(7, 11);
                                tv_phone.setText(xxPhone);
                            }else{
                                tv_phone.setText(response.getPhone().trim());
                            }
                            tv_addr.setText(response.getAddr().trim());
//                            tv_invitationcode.setText(response.getInvitationCode());
                            if (response.getSex().equals("1")) {
                                tv_sex.setText("男");
                            } else if (response.getSex().equals("2")) {
                                tv_sex.setText("女");
                            } else if (response.getSex().equals("0")){
                                tv_sex.setText("保密");
                            }else{
                                tv_sex.setText("");
                            }
                            if (response.getBirthday() == "" || response.getBirthday() == "-2208988800" || response.getBirthday().equals("0001-01-01")) {
                                tv_bir.setText("");
                            } else {;
                                tv_bir.setText(response.getBirthday());
                            }

                            //得到网络图片
                            String sss =response.getLogo().substring(response.getLogo().lastIndexOf(".")+1);
                            if (!response.getLogo().equals("")) {
                                String logopath_local = sharedPreferences1.getString("logopath","");
                                if (!logopath_local.equals(response.getLogo())) {
                                    ImageRequest imageRequest = new ImageRequest(
                                            response.getLogo().replace("https", "http"),
                                            new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap response) {
                                                    networkImageView.setImageBitmap(response);
                                                     SharedPreferences sharedPreferences = MyDetail.this.getSharedPreferences("self", Activity.MODE_PRIVATE);
                                                     SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                                     editor_self.putString("bitmap", bitmapToBase64(response)).commit();
                                                }
                                            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            networkImageView.setImageResource(R.drawable.touxiang);

                                        }
                                    });
                                    queue.add(imageRequest);
                                }else{
                                    String bitmap64 = sharedPreferences1.getString("bitmap","");
                                    networkImageView.setImageBitmap(base64ToBitmap(bitmap64));
                                }

                            }else{
                                networkImageView.setImageResource(R.drawable.touxiang);
                                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("bitmap", "").commit();
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", response.getUserName());
                            editor.putString("phone", response.getPhone());
                            editor.putString("day", response.getBirthday());
                            editor.putString("sex", tv_sex.getText().toString());
                            editor.commit();
                            dialog.dismiss();

                        }
                    }, TestOrNot.isTest);
        }else{
            dialog.dismiss();
            toastOnly.toastShowShort("请检查您的网络环境");
        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+100)){
                MyDetail.this.finish();
            }
        }
    };
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(""+100);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
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

    @Override
    protected void onResume() {
        super.onResume();
        spget();
        getUserInfo();
    }
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
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
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}