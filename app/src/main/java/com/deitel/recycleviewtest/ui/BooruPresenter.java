package com.deitel.recycleviewtest.ui;

import com.deitel.recycleviewtest.base.PVContract;
import com.deitel.recycleviewtest.base.StringPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BooruPresenter extends StringPresenter<List <BooruBean>> {

    protected BooruPresenter(PVContract.View view) {
        super(view);
    }

    @Override
    protected List<BooruBean> dealResponse(String s) {

        return new Gson().fromJson(s,new TypeToken<List<BooruBean>>() {}.getType());
    }

}
