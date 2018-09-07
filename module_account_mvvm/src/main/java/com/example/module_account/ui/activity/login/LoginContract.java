package com.example.module_account.ui.activity.login;

import com.android.common.mvp.BaseModel;
import com.android.common.mvp.BasePresenter;
import com.android.common.mvp.BaseView;
import com.example.module_account.ui.activity.bean.ProductItemModel;

import java.util.List;

import io.reactivex.Observable;

public interface LoginContract {

    interface Model extends BaseModel{
        Observable<String> login(String userName,String pwd);

        Observable<String> getProductList();
    }

    interface View extends BaseView{
        void loadSecess();
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void login(String userName,String pwd);
    }
}
