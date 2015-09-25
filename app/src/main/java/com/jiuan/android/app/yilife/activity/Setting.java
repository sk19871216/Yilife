package com.jiuan.android.app.yilife.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.utils.DataCleanManager;

public class Setting extends ParentActivity {
    private TextView tv_title,tv_cachesize;
    private ImageView iv_back;
    private RelativeLayout layout_fankui,layout_us,layout_clean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title .setText("设置");
        tv_cachesize = (TextView) findViewById(R.id.setting_cachesize);
        try {
            String size = DataCleanManager.getTotalCacheSize(Setting.this);
            tv_cachesize.setText(size);
        }catch (Exception e){

        }
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layout_fankui = (RelativeLayout) findViewById(R.id.relativelayout_fankui_setting);
        layout_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this,Fankui.class);
                startActivity(intent);
            }
        });
        layout_us = (RelativeLayout) findViewById(R.id.relativelayout_aboutus_setting);
        layout_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this,AboutUs.class);
                startActivity(intent);
            }
        });
        layout_clean = (RelativeLayout) findViewById(R.id.relativelayout_clean_setting);
        layout_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog myDialog= null;
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

                int width = wm.getDefaultDisplay().getWidth();

                myDialog = new AlertDialog.Builder(Setting.this).create();
                myDialog.show();
                WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
                params.width = width * 4 / 5;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                myDialog.getWindow().setAttributes(params);
                myDialog.getWindow().setContentView(R.layout.alert_resg);
                myDialog.getWindow()
                        .findViewById(R.id.gologin)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Setting.this,"1222222",Toast.LENGTH_SHORT).show();
                            }
                        });*/
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setMessage("您确定要清除缓存吗？")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataCleanManager.clearAllCache(Setting.this);
                                try {
                                    String size = DataCleanManager.getTotalCacheSize(Setting.this);
                                    tv_cachesize.setText(size);
                                    Toast.makeText(Setting.this,"清除成功",Toast.LENGTH_SHORT).show();
                                }catch (Exception e){

                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setCancelable(false).show();
            }
        });
    }

}
