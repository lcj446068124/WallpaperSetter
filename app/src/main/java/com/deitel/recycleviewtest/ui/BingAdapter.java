package com.deitel.recycleviewtest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseAdapter;

public class BingAdapter extends BaseAdapter<BingViewHolder, BingBean.ImageBean> {
    @Override
    public BingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_description_with_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BingViewHolder) holder).bindView(mBeans.get(position));
    }
}
