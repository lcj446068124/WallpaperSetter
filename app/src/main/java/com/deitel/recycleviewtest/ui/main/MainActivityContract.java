package com.deitel.recycleviewtest.ui.main;

public interface MainActivityContract {
    interface View {

        //void getSuccess(JuZiBean bean);

    }

    interface Presenter {
        void get();

        void unsubscribe();
    }
}
