package com.example.moddle_main.fragment.girls;

import android.app.Activity;

import com.android.common.rx.RxSchedulers;
import com.example.moddle_main.bean.GirlsBean;
import com.android.common.rx.RxObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GirlsPresenter extends GirlsContract.Presenter {


    @Override
    public void refresh(int page) {
        mModel.refresh(page)
                .compose(RxSchedulers.io_main())
                .compose(mView.bindLifecycle())
                .subscribe(new RxObserver<GirlsBean>(mContext) {
                    @Override
                    protected void _onNext(GirlsBean girlsBean) {
                        mView.refresh(girlsBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void loadMore(int page) {
        mModel.loadMore(page)
                .compose(RxSchedulers.io_main())
                .compose(mView.bindLifecycle())
                .subscribe(new RxObserver<GirlsBean>(mContext) {
                    @Override
                    protected void _onNext(GirlsBean girlsBean) {
                        mView.loadMore(girlsBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.loadFail(message);
                    }
                });
    }
}
