package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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
import com.jiuan.android.app.yilife.bean.sendnote.SendNoteClient;
import com.jiuan.android.app.yilife.bean.sendnote.SendNoteHandler;
import com.jiuan.android.app.yilife.bean.sendnote.SendNoteResponse;
import com.jiuan.android.app.yilife.bean.sendnote.SendNoteTextHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.imageloader.MyAdapter;
import com.jiuan.android.app.yilife.imageloader.SelectImageBBSActivity;
import com.jiuan.android.app.yilife.utils.ImageLoaderFankui;
import com.jiuan.android.app.yilife.utils.KeyBoadlayout;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Decoder.BASE64Encoder;

public class SendNote extends ParentActivity implements View.OnClickListener{
    private EditText editText_title,editText_note;
    private CharSequence temp;
    private int editStart,editEnd;
    private TextView tv_count,tv_title;
    private ToastOnly toastOnly;
    private LinearLayout layout_add,layout_select,layout_showphoto;
    private int piccount=0;
    public static ArrayList<String> stringArrayList_sendnote;
    private ArrayList<String> list_imagepaths,images64;
    private ImageView iv_back,iv_setting,iv1,iv2,iv3,iv4,iv5,iv6;
    private String phone,tooken,nowdate,date,hguid;
    private int forums;
    private RelativeLayout layout1,layout2,layout_out;
    private int[] aids = null;
    private ProgressDialog dialog;
    private KeyBoadlayout keyBoadlayout;
    private int count,countforsamsung=0,countforsamsung1=0;
    private ArrayList<String> listforsamsung ;
    private boolean isflase = false;
    private boolean istaken=false,iscreat = false,issaved=false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                count++;
                if (count == stringArrayList_sendnote.size()) {
                    SendNoteClient.sendtext(SendNote.this, hguid, tooken, forums, editText_title.getText().toString().trim()
                            , editText_note.getText().toString().trim().replace("\n", "<br>"), aids, new SendNoteTextHandler() {
                        @Override
                        public void onLoginSuccess(int[] response) {
                            super.onLoginSuccess(response);
                            dialog.dismiss();
                            toastOnly.toastShowShort("发表成功");
                            SendNote.this.finish();
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            dialog.dismiss();
                            FailMessage.showfail(SendNote.this, msg);
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
                            FailMessage.showfail(SendNote.this, value);
                        }
                    }, TestOrNot.isTest);
                }

            } else if (msg.what == 2) {
                    layout_showphoto.setVisibility(View.VISIBLE);
                } else if (msg.what == 3) {
                    layout_showphoto.setVisibility(View.GONE);
                }
        }
    };
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", date);
        outState.putStringArrayList("samsung", stringArrayList_sendnote);
        outState.putBoolean("istaken", istaken);
    }

    @Override
    protected void onPause() {
        super.onPause();
        iscreat = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("activity","onCreate");
        listforsamsung = new ArrayList<String>();
        if (null != savedInstanceState){
            issaved = true;
            Log.e("activitycountforsamsung",""+countforsamsung);
//            countforsamsung = savedInstanceState.getInt("countforsamsung");
//            countforsamsung++;
            istaken = savedInstanceState.getBoolean("istaken");
            date = savedInstanceState.getString("date");
//            listforsamsung.clear();
            listforsamsung = savedInstanceState.getStringArrayList("samsung");
//            listforsamsung.remove(listforsamsung.size()-1);
            if (istaken) {
                listforsamsung.add(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + date + ".jpg");
            }
            Log.e("activitylistforsamsung2",""+listforsamsung.size());

        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(R.layout.activity_send_note);
        picint();

        keyBoadlayout = (KeyBoadlayout) findViewById(R.id.layout_isout);
        keyBoadlayout.setOnResizeListener(new KeyBoadlayout.OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {
                if (h>oldh){
                    //软键盘隐藏
                    handler.sendEmptyMessage(2);
                }else{
                    //软键盘显示
                    handler.sendEmptyMessage(3);
                }
            }
        });
//        layout_out.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int heightDiff = layout_out.getRootView().getHeight() - layout_out.getHeight();
//                if (heightDiff > 100) { // 如果高度差超过100像素，就很有可能是有软键盘...
//                    layout_showphoto.setVisibility(View.GONE);
//                }else{
//                    layout_showphoto.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        iscreat = true;
        dialog = new ProgressDialog(SendNote.this);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        images64 = new ArrayList<String>();
        Intent intent = getIntent();
        forums = intent.getIntExtra("forums",-1);
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_title.setText("发帖");
        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setImageResource(R.drawable.icon_commit);
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                dialog.show();
                dialog.setCancelable(false);
                if (editText_title.getText().toString().equals("") ) {
                    dialog.dismiss();
                    toastOnly.toastShowShort("标题不能为空");
                }else if (editText_note.getText().toString().trim().length()<10){
                    dialog.dismiss();
                    toastOnly.toastShowShort("内容字数不能小于10个字");
                }else{
                    SharedPreferences mySharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                    phone = mySharedPreferences.getString("phone","");
                    tooken = mySharedPreferences.getString("AccessToken","");
                    hguid = mySharedPreferences.getString("HGUID","");
                    if (NetWorkInfo.isNetworkAvailable(SendNote.this)) {
                        if (stringArrayList_sendnote.size()!=0) {
                          aids = new int[stringArrayList_sendnote.size()];
//                            Log.e("aids",""+aids[4]);
                          for (int i = 0; i < stringArrayList_sendnote.size(); i++) {
                              final int position = i;
//                              String to64pic =  GetImageStr(stringArrayList_sendnote.get(i));
//                              Log.e("to64pic",to64pic);
                              if (i == stringArrayList_sendnote.size() - 1) {

                                      SendNoteClient.sendimage(SendNote.this, hguid, tooken, "png",GetImageStr(stringArrayList_sendnote.get(i)), new SendNoteHandler() {
                                          @Override
                                          public void onLoginSuccess(SendNoteResponse response) {
                                              super.onLoginSuccess(response);
                                              Log.d("res", response + "");
                                              aids[position] = response.getAid();
                                              if (response.getAid()!=-1) {
                                                  handler.sendEmptyMessage(1);
                                              }

                                          }

                                          @Override
                                          public void onInnovationFailure(String msg) {
                                              super.onInnovationFailure(msg);
                                              dialog.dismiss();
                                              FailMessage.showfail(SendNote.this, msg);
                                              isflase =true;
                                          }

                                          @Override
                                          public void onInnovationExceptionFinish() {
                                              super.onInnovationExceptionFinish();
                                              dialog.dismiss();
                                              toastOnly.toastShowShort("网络超时");
                                              isflase =true;
                                          }

                                          @Override
                                          public void onInnovationError(String value) {
                                              super.onInnovationError(value);
                                              dialog.dismiss();
                                              FailMessage.showfail(SendNote.this, value);
                                              isflase =true;
                                          }
                                      }, TestOrNot.isTest);
                                      if (isflase){
                                          break;
                                      }
                                  } else {
                                      SendNoteClient.sendimage(SendNote.this, hguid, tooken, "png", GetImageStr(stringArrayList_sendnote.get(i)), new SendNoteHandler() {
                                          @Override
                                          public void onLoginSuccess(SendNoteResponse response) {
                                              super.onLoginSuccess(response);
                                              Log.d("res", response + "");
                                              aids[position] = response.getAid();
                                              handler.sendEmptyMessage(1);
                                          }

                                          @Override
                                          public void onInnovationFailure(String msg) {
                                              super.onInnovationFailure(msg);
                                              dialog.dismiss();
                                              FailMessage.showfail(SendNote.this, msg);
                                              isflase =true;
                                          }

                                          @Override
                                          public void onInnovationExceptionFinish() {
                                              super.onInnovationExceptionFinish();
                                              dialog.dismiss();
                                              toastOnly.toastShowShort("网络超时");
                                              isflase =true;
                                          }

                                          @Override
                                          public void onInnovationError(String value) {
                                              super.onInnovationError(value);
                                              dialog.dismiss();
                                              FailMessage.showfail(SendNote.this, value);
                                              isflase =true;
                                          }
                                      }, TestOrNot.isTest);
                                      if (isflase){
                                          break;
                                      }
                                  }
                              }

                          }else{
//                          if (editText_note.getText().toString().equals("")){
//                              toastOnly.toastShowShort("文字，图片不能均为空");
//                          }else {
                              SendNoteClient.sendtext(SendNote.this, hguid, tooken, forums, editText_title.getText().toString().trim()
                                      , editText_note.getText().toString().trim().replace("\n", "<br>"), aids, new SendNoteTextHandler() {
                                  @Override
                                  public void onLoginSuccess(int[] response) {
                                      super.onLoginSuccess(response);
                                      dialog.dismiss();
                                      toastOnly.toastShowShort("发表成功");
                                      SendNote.this.finish();
                                  }

                                  @Override
                                  public void onInnovationFailure(String msg) {
                                      super.onInnovationFailure(msg);
                                      dialog.dismiss();
                                      FailMessage.showfail(SendNote.this, msg);
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
                                      FailMessage.showfail(SendNote.this, value);
                                  }
                              }, TestOrNot.isTest);
//                          }
                          }
                      }else{
                        dialog.dismiss();
                          toastOnly.toastShowShort("请检查您的网络环境");
                      }
                  }
            }
        });
        toastOnly = new ToastOnly(SendNote.this);
        stringArrayList_sendnote = new ArrayList<String>();
        layout_showphoto = (LinearLayout) findViewById(R.id.linearlayout_sendnote_pic);
        tv_count = (TextView) findViewById(R.id.textview_sendnote_count);
        tv_count.setText("已选择"+stringArrayList_sendnote.size()+"/5 张");
        layout_add = (LinearLayout) findViewById(R.id.linearlayout_addphoto);
            layout_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stringArrayList_sendnote.size()<5) {
                        istaken=false;
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = df.format(new Date());
                        date = date.substring(0, 4)+date.substring(5,7)+date.substring(8,10)+"_"+date.substring(11,13)+date.substring(14,16)+date.substring(17,19);
                        nowdate =Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg";

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/", date+".jpg")));
                        startActivityForResult(intent, 1);
                    }else{
                        toastOnly.toastShowShort("最多只能选择5张照片");
                    }
            }
            });
            layout_select = (LinearLayout) findViewById(R.id.linearlayout_seletphoto);
            layout_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    for (int i=0;i<stringArrayList_sendnote.size();i++){
//                        MyAdapter.mSelectedImage.add(stringArrayList_sendnote.get(i));
//                    }
                    if (stringArrayList_sendnote.size()<5) {
                        istaken = false;
                        MyAdapter.mSelectedImage_count.clear();
                        int fankuisize = stringArrayList_sendnote.size();
                        for (int j = 0; j < fankuisize; j++) {
                            MyAdapter.mSelectedImage_count.add(stringArrayList_sendnote.get(j));
                        }
                        Intent intent = new Intent(SendNote.this, SelectImageBBSActivity.class);
                        intent.putStringArrayListExtra("photoPaths", stringArrayList_sendnote);
                        startActivityForResult(intent, 3);
                    }else{
                        toastOnly.toastShowShort("最多只能选择5张照片");
                    }
                }
            });
        editText_title = (EditText) findViewById(R.id.edittext_sendnote_title);
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
                editStart = editText_title.getSelectionStart();
                editEnd = editText_title.getSelectionEnd();
                if (temp.length() > 20) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText_title.setText(s);
                    editText_title.setSelection(tempSelection);
                }else if (temp.length()>15 && temp.length()<20){
                    toastOnly.toastShowShort("您还可以输入"+(20-temp.length())+"字");

                }else if (temp.length()==20){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText_title.addTextChangedListener(textWatcher);
        editText_note = (EditText) findViewById(R.id.edittext_sendnote_note);
        TextWatcher textWatcher_note = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp= s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText_note.getSelectionStart();
                editEnd = editText_note.getSelectionEnd();
                if (temp.length() > 200) {
                    toastOnly.toastShowShort("您输入的字数已经超过了限制");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    editText_note.setText(s);
                    editText_note.setSelection(tempSelection);
                }else if (temp.length()>180 && temp.length()<200){
                    toastOnly.toastShowShort("您还可以输入"+(200-temp.length())+"字");
                }else if (temp.length()==200){
                    toastOnly.toastShowShort("您输入的字数已达到最大限制");
                }
            }
        };
        editText_note.addTextChangedListener(textWatcher_note);

    }
    protected  void closeSoftInput(){
        if (getCurrentFocus() !=null){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus()
                            .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("activity","onActivityResult");

        if (requestCode==1 && resultCode == RESULT_OK){
            istaken=true;
            if (!issaved){
                String aaa = Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg";
                File file = new File(aaa);
                Log.e("sendnote",aaa);
                if (file.exists()){
                    Log.e("sendnote","exists");
//                    stringArrayList_sendnote.add(Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg");
                    MyAdapter.mSelectedImage_count.add(Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg");
                    stringArrayList_sendnote.add(Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+date+".jpg");
                }
            }
//            piccount++;
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String date = df.format(new Date());
//            date = date.substring(0,4)+date.substring(5,7)+date.substring(8,10)+"_"+date.substring(11,13)+date.substring(14,16)+date.substring(17,19);
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            file.mkdirs();// 创建文件夹
//            String fileName = Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+piccount+".jpg";
//            String fileName = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/"+date+".jpg";
//            MyAdapter.mSelectedImage.add(fileName);
//            FileOutputStream b = null;
//            try {
//                b = new FileOutputStream(nowdate);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    b.flush();
//                    b.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (data!=null){
//            }
//            for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
//                stringArrayList_sendnote.add(MyAdapter.mSelectedImage.get(i));
////                    stringArrayList_sendnote.add(data.getStringArrayListExtra("imagepaths").get(i));
////                    MyAdapter.mSelectedImage.add(data.getStringArrayListExtra("imagepaths").get(i));
//            }

//
//            Log.e("sendnotesize",""+stringArrayList_sendnote.size());
//            tv_count.setText("已选择"+stringArrayList_sendnote.size()+"/5 张");
//            if (MyAdapter.mSelectedImage_count.size()==1) {
//                layout_showphoto.setVisibility(View.VISIBLE);
//                layout1.setVisibility(View.VISIBLE);
//                iv1.setVisibility(View.VISIBLE);
//                ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
////                iv1.setImageBitmap(compressImage(picnormal(nowdate)));
////                ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
//            }else if (MyAdapter.mSelectedImage_count.size()==2) {
//                layout_showphoto.setVisibility(View.VISIBLE);
//                layout1.setVisibility(View.VISIBLE);
//                iv2.setVisibility(View.VISIBLE);
//                iv2.setImageBitmap(compressImage(picnormal(nowdate)));
//            }else if (MyAdapter.mSelectedImage_count.size()==3) {
//                layout_showphoto.setVisibility(View.VISIBLE);
//                layout1.setVisibility(View.VISIBLE);
//                iv3.setVisibility(View.VISIBLE);
//                iv3.setImageBitmap(compressImage(picnormal(nowdate)));
//            }else if (MyAdapter.mSelectedImage_count.size()==4) {
//                layout_showphoto.setVisibility(View.VISIBLE);
//                layout2.setVisibility(View.VISIBLE);
//                iv4.setVisibility(View.VISIBLE);
//                iv4.setImageBitmap(compressImage(picnormal(nowdate)));
//            }else if (MyAdapter.mSelectedImage_count.size()==5) {
//                layout_showphoto.setVisibility(View.VISIBLE);
//                layout2.setVisibility(View.VISIBLE);
//                iv5.setVisibility(View.VISIBLE);
//                iv5.setImageBitmap(compressImage(picnormal(nowdate)));
//            }
        }else if (requestCode==3 && resultCode == RESULT_OK){
//            ArrayList<String> selectpaths = new ArrayList<String>();
//            selectpaths = data.getStringArrayListExtra("imagepaths");
//            stringArrayList_sendnote.clear();
//            if (MyAdapter.mSelectedImage.size() !=0) {
//                for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
//                    stringArrayList_sendnote.add(MyAdapter.mSelectedImage.get(i));
////                    stringArrayList_sendnote.add(data.getStringArrayListExtra("imagepaths").get(i));
////                    MyAdapter.mSelectedImage.add(data.getStringArrayListExtra("imagepaths").get(i));
//                }
//
//            }


        }
    }
    protected  Bitmap getimagebit(String path){
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
        Bitmap transformed;
        Matrix m =turnright(path);
        if (bitmap.getWidth()>bitmap.getHeight()) {
            transformed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getHeight(), bitmap.getHeight(), m, true);
        }else {
            transformed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getWidth(), m, true);
        }
        return  transformed;
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
    public  String GetImageStr(String path)
    {
//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        Bitmap bp = getimagebit(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bp.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while ( baos.toByteArray().length / 1024>512) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
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
        while ( baos.toByteArray().length / 1024>300) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
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
    public void picint(){
        iv1 = (ImageView) findViewById(R.id.imageview_sendnote_add1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView) findViewById(R.id.imageview_sendnote_add2);
        iv2.setOnClickListener(this);
        iv3 = (ImageView) findViewById(R.id.imageview_sendnote_add3);
        iv3.setOnClickListener(this);
        iv4 = (ImageView) findViewById(R.id.imageview_sendnote_add4);
        iv4.setOnClickListener(this);
        iv5 = (ImageView) findViewById(R.id.imageview_sendnote_add5);
        iv5.setOnClickListener(this);

        layout1 = (RelativeLayout) findViewById(R.id.layout_addimageview);
        layout2 = (RelativeLayout) findViewById(R.id.layout_addimageview2);
    }
    public Bitmap picnormal(String path){
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
//        return compressImage(transformed);
        if (b.getWidth()>b.getHeight()) {
            Bitmap transformed = Bitmap.createBitmap(b, 0, 0, b.getHeight(), b.getHeight(), m, true);
            return compressImage(transformed);
        }else{
            Bitmap transformed = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getWidth(), m, true);
            return compressImage(transformed);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        issaved = false;
        Intent intent_bigpic = new Intent(SendNote.this,BigPic.class);
        switch (id) {
            case R.id.imageview_sendnote_add1:
                issaved = false;
                intent_bigpic.putExtra("imagedetail",stringArrayList_sendnote.get(0));
                intent_bigpic.putExtra("position",0);
                    break;
            case R.id.imageview_sendnote_add2:
                issaved = false;
                intent_bigpic.putExtra("imagedetail",stringArrayList_sendnote.get(1));
                intent_bigpic.putExtra("position",1);
                    break;
            case R.id.imageview_sendnote_add3:
                issaved = false;
                intent_bigpic.putExtra("imagedetail",stringArrayList_sendnote.get(2));
                intent_bigpic.putExtra("position",2);
                    break;
            case R.id.imageview_sendnote_add4:
                issaved = false;
                intent_bigpic.putExtra("imagedetail",stringArrayList_sendnote.get(3));
                intent_bigpic.putExtra("position",3);
                    break;
            case R.id.imageview_sendnote_add5:
                issaved = false;
                intent_bigpic.putExtra("imagedetail", stringArrayList_sendnote.get(4));
                intent_bigpic.putExtra("position",4);
                    break;
            }
        intent_bigpic.putExtra("from","sendnote");
        startActivity(intent_bigpic);
    }
    @Override
    protected void onDestroy() {
        Log.e("activity","onDestroy");
        super.onDestroy();
        MyAdapter.mSelectedImage.clear();
        MyAdapter.mSelectedImage_count.clear();
        clear(SendNote.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("activity", "onResume");
        Log.e("activitylistforsamsung",""+listforsamsung.size());
        closeSoftInput();
        if (issaved) {
            if (!istaken && !iscreat) {
                Log.e("onResume","1");
                tv_count.setText("已选择" + stringArrayList_sendnote.size() + "/5 张");
                if (MyAdapter.mSelectedImage.size() != 0) {
                    for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
                        stringArrayList_sendnote.add(MyAdapter.mSelectedImage.get(i));
                    }
                }
                setimage();
            } else if (istaken && iscreat) {
                Log.e("onResume","2");
                tv_count.setText("已选择" + listforsamsung.size() + "/5 张");
                for (int i = 0; i < listforsamsung.size(); i++) {
                    stringArrayList_sendnote.add(listforsamsung.get(i));
//                    MyAdapter.mSelectedImage.add(listforsamsung.get(listforsamsung.size()-1));
                    MyAdapter.mSelectedImage_count.add(listforsamsung.get(listforsamsung.size()-1));
//                    MyAdapter.mSelectedImage_count.add(listforsamsung.get(i));
                }
//            Log.e("activitylistsendnote0",""+stringArrayList_sendnote.get(0));
//            Log.e("activitylistsendnote01",""+getimage(stringArrayList_sendnote.get(0)));

                setimage();
//            }
//            else if (!istaken && ){

            }
        }else{
            Log.e("onResume","3");
            tv_count.setText("已选择" + MyAdapter.mSelectedImage_count.size() + "/5 张");
            if (MyAdapter.mSelectedImage_count.size() != 0) {
                for (int i = 0; i < MyAdapter.mSelectedImage.size(); i++) {
                    stringArrayList_sendnote.add(MyAdapter.mSelectedImage.get(i));
                }
            }
            setimage();
        }
//        MyAdapter.mSelectedImage_count.clear();
//        for (int j=0;j<MyAdapter.mSelectedImage.size();j++){
//            MyAdapter.mSelectedImage_count.add(MyAdapter.mSelectedImage.get(j));
//        }
    }
    protected void setimage(){
        Log.e("activity","setimage");
        Log.e("activity",""+stringArrayList_sendnote.size());
        tv_count.setText("已选择"+stringArrayList_sendnote.size()+"/5 张");
        if (stringArrayList_sendnote.size()==0){
            layout_showphoto.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            iv1.setImageBitmap(null);
            iv2.setImageBitmap(null);
            iv3.setImageBitmap(null);
            iv4.setImageBitmap(null);
            iv5.setImageBitmap(null);
        }else if (stringArrayList_sendnote.size()==1) {
            layout_showphoto.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.INVISIBLE);
            iv3.setVisibility(View.INVISIBLE);
//            iv1.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(0))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(1), iv2);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(2), iv3);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(3), iv4);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(4), iv5);
            iv2.setImageBitmap(null);
            iv3.setImageBitmap(null);
            iv4.setImageBitmap(null);
            iv5.setImageBitmap(null);
        }else if (stringArrayList_sendnote.size()==2) {
            layout_showphoto.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.INVISIBLE);
//            iv1.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(0))));
//            iv2.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(1))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(1), iv2);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(2), iv3);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(3), iv4);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(4), iv5);
            iv3.setImageBitmap(null);
            iv4.setImageBitmap(null);
            iv5.setImageBitmap(null);
        }else if (stringArrayList_sendnote.size()==3) {
            layout_showphoto.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
//            iv1.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(0))));
//            iv2.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(1))));
//            iv3.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(2))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(1), iv2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(2), iv3);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(3), iv4);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(4), iv5);
            iv4.setImageBitmap(null);
            iv5.setImageBitmap(null);
        }else if (stringArrayList_sendnote.size()==4) {
            layout_showphoto.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            iv4.setVisibility(View.VISIBLE);
            iv5.setVisibility(View.INVISIBLE);
//            iv1.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(0))));
//            iv2.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(1))));
//            iv3.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(2))));
//            iv4.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(3))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(1), iv2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(2), iv3);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(3), iv4);
//            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(4), iv5);
            iv5.setImageBitmap(null);
        }else if (stringArrayList_sendnote.size()==5) {
            layout_showphoto.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            iv4.setVisibility(View.VISIBLE);
            iv5.setVisibility(View.VISIBLE);
//            iv1.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(0))));
//            iv2.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(1))));
//            iv3.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(2))));
//            iv4.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(3))));
//            iv5.setImageBitmap(compressImage(picnormal(stringArrayList_sendnote.get(4))));
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(0), iv1);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(1), iv2);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(2), iv3);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(3), iv4);
            ImageLoaderFankui.getInstance(5, ImageLoaderFankui.Type.LIFO).loadImage(stringArrayList_sendnote.get(4), iv5);
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
