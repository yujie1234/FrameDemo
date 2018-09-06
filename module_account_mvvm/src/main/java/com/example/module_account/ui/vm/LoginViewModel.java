package com.example.module_account.ui.vm;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.android.common.binding.BaseViewModel;
import com.android.common.binding.command.BindingAction;
import com.android.common.binding.command.BindingCommand;
import com.android.common.binding.command.BindingConsumer;
import com.android.common.constant.ApiService;
import com.android.common.constant.ConstantUtil;
import com.android.common.okgo.MyStringCallback;
import com.android.common.okgo.RequestParams;
import com.android.common.util.LogUtils;
import com.android.common.util.ToastUtils;
import com.example.module_account.R;
import com.example.module_account.databinding.ActivityLoginBinding;
import com.example.module_account.ui.activity.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.HashMap;
import java.util.Map;
import com.example.module_account.BR;
/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {
    private  ActivityLoginBinding binding;
    public String userName;
    public String password;

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    //密码开关观察者
    public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);

    public LoginViewModel(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binding = ((LoginActivity)context).getDatabinding();
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setUserName("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,在View层的监听则会被调用
            pSwitchObservable.set(!pSwitchObservable.get());
            if (pSwitchObservable.get()) {
                //密码可见
                //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press);
                binding.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //密码不可见
                binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw);
                binding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
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
        if(!TextUtils.isEmpty(userName)){
            if(!TextUtils.isEmpty(password)){
                RequestParams params = new RequestParams();
                Map<String, String> map = new HashMap<>();
                map.put("loginNo", userName);
                map.put("password", password);
                map.put("applyType", ConstantUtil.applyType);
                map.put("sourceOsType", ConstantUtil.sourceOsType);
                map.put("loginType", ConstantUtil.loginTypeCL);
                String json = params.buildToJson(map);

                OkGo.<String>post(ApiService.LOGIN_PWD)
                        .tag(this)
                        .upJson(json)
                        .execute(new MyStringCallback() {
                            @Override
                            public void onSuccess(boolean isSuccess, String data, String msg) {
                                LogUtils.d(msg);
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                LogUtils.e(response.getException().getMessage());
                            }

                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                super.onStart(request);
                                showDialog();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                dismissDialog();
                            }
                        });

            }else{
                ToastUtils.showShort("密码不能为空");
            }
        }else{
            ToastUtils.showShort("用户名不能为空");
        }
    }
}
