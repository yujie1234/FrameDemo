package com.android.common.rx;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.common.util.MaterialDialogUtils;
import com.android.common.util.NetworkUtils;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************使用例子********************/
public abstract class RxObserver<T> implements Observer<T> {

    private Context mContext;

    public RxObserver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        showDialog("请稍后...");
    }

    @Override
    public void onComplete() {
        dismissDialog();
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        dismissDialog();
        //网络
        if (!NetworkUtils.isConnected()) {
            _onError("网络不可用");
        } else {
            String errorInfo;
            if(e instanceof SocketTimeoutException){
                errorInfo = "链接服务器失败";
            }else{
                errorInfo = "请求错误";
            }
            _onError(errorInfo);
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);


    private MaterialDialog dialog;

    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(mContext, title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
