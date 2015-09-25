package com.jiuan.android.app.yilife.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.jiuan.android.app.yilife.R;

public class Store extends ParentActivity {
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        iv = (ImageView) findViewById(R.id.round);
        Intent intent = getIntent();
        Bitmap bitmap=intent.getParcelableExtra("bitmap");
        iv.setImageBitmap(bitmap);

    }


}
