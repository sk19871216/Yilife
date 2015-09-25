package com.jiuan.android.app.yilife.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/4/15.
 */
public class MyWebView extends WebView{

    public interface CompleteListener {

        void onPageComplete();

    }

    private CompleteListener mCompleteListener;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (getContentHeight() > 0) {
            // WebView has displayed some content and is scrollable.
            if(mCompleteListener!=null){
                mCompleteListener.onPageComplete();
            }
        }
    }

    public void setCompleteListener(CompleteListener listener) {
        mCompleteListener = listener;
    }
}
