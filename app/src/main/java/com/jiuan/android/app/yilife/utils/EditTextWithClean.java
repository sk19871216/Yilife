package com.jiuan.android.app.yilife.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiuan.android.app.yilife.R;

/**
 * Created by Administrator on 2015/2/26.
 */
public class EditTextWithClean extends EditText {
    private CharSequence temp;
    private ImageView iv;
    public EditTextWithClean(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context,R.layout.item_faxian,null);
        iv = new ImageView(context);
        Drawable drawable = getResources().getDrawable(R.drawable.clean);
        setCompoundDrawables(null,null,drawable,null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (temp.length() != 0) {
                    iv.setImageResource(R.drawable.clean);
                    iv.setVisibility(VISIBLE);
                }else{
                    iv.setVisibility(GONE);
                }
            }
        });
    }

}
