package com.deitel.recycleviewtest.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseViewHolder;
import com.deitel.recycleviewtest.base.Contracts;
import com.deitel.recycleviewtest.glide.GlideApp;

import butterknife.BindView;

public class BingViewHolder extends BaseViewHolder<BingBean.ImageBean>{

    @BindView(R.id.image_view_image)
    ImageView mImageViewImage;
    @BindView(R.id.text_view_description)
    TextView mTextViewDescription;
    @BindView(R.id.text_view_tag)
    TextView mTextViewTag;

    public BingViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(final BingBean.ImageBean bean) {
        mImageViewImage.setAdjustViewBounds(true);//当你需要在 ImageView调整边框时保持可绘制对象的比例时，将该值设为真。
        GlideApp.with(mContext)
                //.load(R.drawable.ic_loading)
                .load(bean.getUrl())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_load_error)
                .fallback(R.drawable.ic_load_error)// null url
                .into(mImageViewImage);
        mImageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(bean.getUrl(), bean.getUrl(), Contracts.Menu.MENU_BING,
                        "版权：" + bean.getCopyright() + "\n\n" +
                                "更新时间：" + bean.getEnddate() + "\n\n" +
                                "下载地址：" + bean.getUrl());
            }
        });
        mTextViewTag.setText(bean.getEnddate());
        mTextViewDescription.setText(bean.getCopyright());

    }
}
