package com.example.module_account.ui.activity.register;

import com.android.common.mvp.BaseModel;
import com.android.common.mvp.BasePresenter;
import com.android.common.mvp.BaseView;

public interface RegisterContract {

    interface Model extends BaseModel{

    }

    interface View extends BaseView{

    }

    abstract class Presenter extends BasePresenter<View,Model>{

    }

}
