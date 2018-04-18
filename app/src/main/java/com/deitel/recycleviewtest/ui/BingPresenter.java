package com.deitel.recycleviewtest.ui;

import com.deitel.recycleviewtest.base.PVContract;
import com.deitel.recycleviewtest.base.StringPresenter;
import com.google.gson.Gson;

import java.util.List;

public class BingPresenter extends StringPresenter<List<BingBean.ImageBean>> {
    protected BingPresenter(PVContract.View view) {
        super(view);
    }
    @Override
    protected List<BingBean.ImageBean> dealResponse(String s) {

        BingBean bean = new Gson().fromJson(s, BingBean.class);
        return bean.getImages();
    }
}
