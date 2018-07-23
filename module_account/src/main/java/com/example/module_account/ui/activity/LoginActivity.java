package com.example.module_account.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.base.BaseActivity;
import com.android.common.base.BaseBindActivity;
import com.example.module_account.R;
import com.example.module_account.BR;
import com.example.module_account.databinding.ActivityLoginBinding;
import com.example.module_account.ui.vm.LoginViewModel;


@Route(path = "/account/login")
public class LoginActivity extends BaseBindActivity<ActivityLoginBinding,LoginViewModel>{

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
