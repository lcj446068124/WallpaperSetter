package com.deitel.recycleviewtest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseAdapter;

/**
 * Created by 44606 on 2018/3/22.
 */

public class BooruAdapter extends BaseAdapter<BooruViewHolder,BooruBean> {
    @Override
    public BooruViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooruViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booru, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BooruViewHolder) holder).bindView(mBeans.get(position));
    }
}
