package com.example.module_account.ui.activity.register;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.android.common.mvp.BaseActivity;
import com.example.module_account.R;
@Route(path = "/account/register")
public class RegisterActivity extends BaseActivity<RegisterPresenter,RegisterModel> {

    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView(){

    }

}
