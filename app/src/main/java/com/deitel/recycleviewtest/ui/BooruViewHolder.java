package com.deitel.recycleviewtest.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseViewHolder;
import com.deitel.recycleviewtest.base.Contracts;
import com.deitel.recycleviewtest.glide.GlideApp;

import butterknife.BindView;

/**
 * Created by 44606 on 2018/3/17.
 */

public class BooruViewHolder extends BaseViewHolder<BooruBean> {
    @BindView(R.id.text_view_tag)
    TextView mTextViewTag;
    @BindView(R.id.image_view_rating)
    ImageView mImageViewRating;
    @BindView(R.id.image_view_image)
    ImageView mImageViewImage;

    public BooruViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(final BooruBean bean) {
        mImageViewImage.setAdjustViewBounds(true);
        mTextViewTag.setText(bean.getSize());
        String r = bean.getRating();
        if(r.equals("s")){
            GlideApp.with(mContext)
                    //.load(R.drawable.ic_loading)
                    .load(bean.getPreview())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_load_error)
                    .fallback(R.drawable.ic_load_error)
                    .into(mImageViewImage);
        }else{
            GlideApp.with(mContext)
                    //.load(R.drawable.ic_load_error)
                    .load(bean.getPreview())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_load_error)
                    .fallback(R.drawable.ic_load_error)
                    .into(mImageViewImage);
        }

        switch (r == null ? "o" : r) {
            case "s":
                mImageViewRating.setImageResource(R.drawable.ic_bookmark_green_24dp);
                break;
            case "q":
                mImageViewRating.setImageResource(R.drawable.ic_bookmark_yellow_24dp);
                break;
            case "e":
                mImageViewRating.setImageResource(R.drawable.ic_bookmark_red_24dp);
                break;
            default:
                mImageViewRating.setImageResource(R.drawable.ic_bookmark_blue_24dp);
                break;
        }
    }
}
