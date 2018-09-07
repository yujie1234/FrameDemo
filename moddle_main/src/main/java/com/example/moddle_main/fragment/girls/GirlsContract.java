package com.example.moddle_main.fragment.girls;

import android.view.Display;

import com.android.common.mvp.BaseModel;
import com.android.common.mvp.BasePresenter;
import com.android.common.mvp.BaseView;
import com.example.moddle_main.bean.GirlsBean;

import java.util.List;

import io.reactivex.Observable;

public interface GirlsContract {
    interface Model extends BaseModel{
        Observable<GirlsBean> refresh(int page);

        Observable<GirlsBean> loadMore(int page);
    }

    interface View extends BaseView{
        void refresh(GirlsBean girlsBean);

        void loadMore(GirlsBean girlsBean);

        void loadFail(String errorInfo);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void refresh(int page);

        public abstract void loadMore(int page);
    }
}
