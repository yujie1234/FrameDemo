package com.example.module_account.ui.activity.login;

import com.alibaba.fastjson.JSON;
import com.android.common.rx.RxObserver;
import com.android.common.rx.RxSchedulers;
import com.android.common.util.LogUtils;
import com.android.common.util.ToastUtils;
import com.example.module_account.ui.activity.bean.ProductItemModel;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String userName, String pwd) {

        mModel.login(userName, pwd)
                .compose(RxSchedulers.io_main())
                .compose(mView.bindLifecycle())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        LogUtils.e("OkGo  "+s);
                        return mModel.getProductList();
                    }
                })
                .map(new Function<String, List<ProductItemModel>>() {
                    @Override
                    public List<ProductItemModel> apply(String s) throws Exception {
                        LogUtils.e("OkGo  "+s);
                        JSONObject jsonObject = new JSONObject(s);
                        String result = jsonObject.optString("result");
                        List<ProductItemModel> productItemModels = JSON.parseArray(result, ProductItemModel.class);
                        return productItemModels;
                    }
                })
                .subscribe(new RxObserver<List<ProductItemModel>>(mContext) {
                               @Override
                               protected void _onNext(List<ProductItemModel> productItemModels) {
                                   LogUtils.e("OkGo  "+productItemModels.toString());

                               }

                               @Override
                               protected void _onError(String message) {
                                   ToastUtils.showShort(message);
                               }
                           }
                );
    }

}
