package com.jiuan.android.app.yilife.config;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;


import java.io.File;

/**
 * Created by Administrator on 2015/3/17.
 */
public class MyBroad extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String action = intent.getAction();
        if (action.equals("android.intent.action.DOWNLOAD_COMPLETE")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("self", Activity.MODE_PRIVATE);
            long id = sharedPreferences.getLong("downloadId",0);
//            DownloadManager mgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

            Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(id));
            if (c.moveToFirst()) {

                String file = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                Uri uri = Uri.fromFile(new File(file)); //这里是APK路径
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent1);
            }



//            Log.e("downloadid",id+"");
//            String  s = DownloadManager.COLUMN_LOCAL_URI;
//            Log.e("downloadid11",s);
//            if (id != 0){
//
//                try {
//                    downloadManager.openDownloadedFile(id);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
        }
//        Toast.makeText(context,"2234",Toast.LENGTH_SHORT).show();

    }
}
