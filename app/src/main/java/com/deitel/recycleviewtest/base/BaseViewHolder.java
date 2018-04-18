package com.deitel.recycleviewtest.base;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.deitel.recycleviewtest.ui.watch.WatchActivity;

import java.io.Serializable;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by 44606 on 2018/3/17.
 */

public abstract class BaseViewHolder<B extends BaseBean> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected Context aContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        aContext = mContext.getApplicationContext();
        ButterKnife.bind(this, itemView);
    }
    public abstract void bindView(B beans);


    protected void sendData(String url, String preview, String source, String description) {
        Intent intent = new Intent(mContext, WatchActivity.class);
        intent.putExtra("image_url", url);
        intent.putExtra("image_preview", preview);
        intent.putExtra("image_source", source);
        intent.putExtra("image_description", description);
        mContext.startActivity(intent);
    }
}
