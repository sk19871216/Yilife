package com.jiuan.android.app.yilife.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastOnly {
    private Toast toast;
    private Context mContext;
    public ToastOnly(Context context) {
        mContext = context;
    }
    public void toastShowShort(String text) {
        toastShowLong(text, Toast.LENGTH_SHORT);
    }
    public void toastShowLong(String text) {
        toastShowLong(text, Toast.LENGTH_LONG);
    }
    private void toastShowLong(String text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(mContext, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}