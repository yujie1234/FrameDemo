package com.example.module_account.ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.common.mvp.BaseActivity;
import com.android.common.util.ToastUtils;
import com.android.common.view.CustomEditText;
import com.example.module_account.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import butterknife.BindView;
import butterknife.OnClick;


@Route(path = "/account/login")
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {


    CustomEditText etName;
    CustomEditText etPwd;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return bindToLifecycle();
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String userName = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            if (!TextUtils.isEmpty(pwd)) {
                mPresenter.login(userName,pwd);
            } else {
                ToastUtils.showShort("密码不能为空");
            }
        } else {
            ToastUtils.showShort("用户名不能为空");
        }
    }


    @Override
    public void loadSecess() {
        ARouter.getInstance().build("/main/mainpage").navigation();
    }
}
