package com.jiuan.android.app.yilife.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.utils.CommonAdapter;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.jiuan.android.app.yilife.utils.ViewHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyAdapter extends CommonAdapter<String>
{
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();
	public static List<String> mSelectedImage_count = new LinkedList<String>();

    public static ArrayList<String> linshilist = new ArrayList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;
    private TextView tv_select;
    public static ArrayList<Drawable> mDrawlist = new ArrayList<Drawable>();
	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath,TextView tv)
	{
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
        this.tv_select = tv;
        tv_select.setText("已选择"+mSelectedImage_count.size()+"/5张");
	}

	@Override
	public void convert(final ViewHolder helper, final String item)
	{
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
				helper.setImageResource(R.id.id_item_select,
						R.drawable.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
		if (item.equals("")){
            helper.getConvertView().setFocusable(false);
        }
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		mImageView.setColorFilter(null);
		//设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v)
			{

                    // 已经选择过该图片
                    if (mSelectedImage.contains(mDirPath + "/" + item)) {
                        mDrawlist.remove(mImageView.getDrawable());
                        mSelectedImage.remove(mDirPath + "/" + item);
                        mSelectedImage_count.remove(mDirPath + "/" + item);
                        linshilist.remove(mDirPath + "/" + item);
                        mSelect.setImageResource(R.drawable.picture_unselected);
                        mImageView.setColorFilter(null);
                    } else
                    // 未选择该图片
                    {
                        if (mSelectedImage_count.size() < 5) {
                            mDrawlist.add(mImageView.getDrawable());
                            mSelectedImage.add(mDirPath + "/" + item);
                            mSelectedImage_count.add(mDirPath + "/" + item);
                            linshilist.add(mDirPath + "/" + item);
                            mSelect.setImageResource(R.drawable.pictures_selected);
                            mImageView.setColorFilter(Color.parseColor("#77000000"));
                        } else {
//                       if (mSelectedImage_count.size()==5){
                            ToastOnly toastOnly = new ToastOnly(mContext);
                            toastOnly.toastShowShort("最多选择5张");
//                            Toast.makeText(mContext,"最多选择5张",Toast.LENGTH_SHORT).show();
                        }
                    }
//                tv_select = helper.getView(R.id.tv_showimage_select);
                    tv_select.setText("已选择" + mSelectedImage_count.size() + "/5张");
                }

		});
		
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item))
		{
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
}
