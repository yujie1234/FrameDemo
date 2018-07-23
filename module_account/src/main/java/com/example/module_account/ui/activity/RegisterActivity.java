package com.example.module_account.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.base.BaseBindActivity;
import com.example.module_account.BR;
import com.example.module_account.R;
import com.example.module_account.databinding.ActivityRegisterBinding;
import com.example.module_account.ui.vm.RegisterViewModel;

@Route(path = "/account/register")
public class RegisterActivity extends BaseBindActivity<ActivityRegisterBinding,RegisterViewModel> {

    @Override
    public int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RegisterViewModel initViewModel() {
        return new RegisterViewModel(this);
    }

    @Override
    public void initData() {
        super.initData();
        setTitleParams("注册",R.color.white);
    }
}
