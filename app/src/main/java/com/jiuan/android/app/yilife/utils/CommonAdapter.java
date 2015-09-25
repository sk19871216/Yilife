package com.jiuan.android.app.yilife.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;
    protected ImageView iv;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
        iv = new ImageView(context);
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
            final ViewHolder viewHolder = getViewHolder(position, convertView,
                    parent);
            convert(viewHolder, getItem(position));
            View view = viewHolder.getConvertView();
            if (position==0) {

                view.setClickable(false);

            }
//        }else{
//            ViewGroup.LayoutParams layoutParams =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            iv.setLayoutParams(layoutParams);
//            iv.setBackgroundResource(R.drawable.pictures_no);
//            return iv;
//        }
        return view;
	}

	public abstract void convert(ViewHolder helper, T item);

	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent)
	{
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

}
