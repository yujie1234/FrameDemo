package com.android.common.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
public interface BaseView {

    <T> LifecycleTransformer<T> bindLifecycle();

//    void showLoading();
//
//    void stopLoading();
}
