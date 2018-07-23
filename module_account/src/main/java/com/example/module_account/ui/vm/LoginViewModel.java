package com.example.module_account.ui.vm;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.common.base.BaseViewModel;
import com.android.common.binding.command.BindingAction;
import com.android.common.binding.command.BindingCommand;
import com.android.common.binding.command.BindingConsumer;
import com.android.common.constant.ApiService;
import com.android.common.constant.ConstantUtil;
import com.android.common.okgo.MyStringCallback;
import com.android.common.okgo.RequestParams;
import com.android.common.util.AppUtils;
import com.android.common.util.LogUtils;
import com.android.common.util.PhoneUtils;
import com.android.common.util.RegexUtils;
import com.android.common.util.StringUtils;
import com.android.common.util.ToastUtils;
import com.example.module_account.ui.activity.RegisterActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);
    }

    public LoginViewModel(Context context) {
        super(context);
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,在View层的监听则会被调用
            uc.pSwitchObservable.set(!uc.pSwitchObservable.get());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
//        String phoneNo = userName.get();
//        if (StringUtils.isEmpty(phoneNo)) {
//            ToastUtils.showShort("请输入账号！");
//            return;
//        }else{
//            if(!RegexUtils.isMobileExact(phoneNo)){
//                ToastUtils.showShort("手机号格式有误！");
//            }
//        }
//
//        String pwd = password.get();
//        if (StringUtils.isEmpty(pwd)) {
//            ToastUtils.showShort("请输入密码！");
//            return;
//        }
//
//
//        RequestParams params = new RequestParams();
//        Map<String, String> map = new HashMap<>();
//        map.put("loginNo", phoneNo);
//        map.put("password", pwd);
//        map.put("applyType", ConstantUtil.applyType);
//        map.put("sourceOsType", ConstantUtil.sourceOsType);
////        String imei = PhoneUtils.getIMEI();
////        map.put("imei", imei);
//        map.put("loginType", ConstantUtil.loginTypeCL);
//        String json = params.buildToJson(map);
//
//             OkGo.<String>post(ApiService.LOGIN_PWD)
//                .tag(this)
//                .upJson(json)
//                .execute(new MyStringCallback() {
//                    @Override
//                    public void onSuccess(boolean isSuccess, String data, String msg) {
//                        LogUtils.d(msg);
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        LogUtils.e(response.getException().getMessage());
//                    }
//
//                    @Override
//                    public void onStart(Request<String, ? extends Request> request) {
//                        super.onStart(request);
//                        showDialog();
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        dismissDialog();
//                    }
//                });

        startActivity(RegisterActivity.class);
    }
}
