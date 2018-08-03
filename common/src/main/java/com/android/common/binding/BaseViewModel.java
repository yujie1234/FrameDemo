package com.android.common.binding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.common.binding.IBaseViewModel;
import com.android.common.util.MaterialDialogUtils;

/**
 * Created by goldze on 2017/6/15.
 */
public class BaseViewModel extends BaseObservable implements IBaseViewModel {
    protected Context context;
    protected Fragment fragment;
    protected Activity activity;

    public BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        this.context = context;
    }

    public BaseViewModel(Fragment fragment) {
        this(fragment.getContext());
        this.fragment = fragment;
    }

    public BaseViewModel(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    private MaterialDialog dialog;

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(context, title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
