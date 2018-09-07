/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.common.okgo.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.common.util.MaterialDialogUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public abstract class StringDialogCallback extends StringCallback {

    private Context mContext;

    public StringDialogCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        showDialog("请稍后...");
    }

    @Override
    public void onFinish() {
        dismissDialog();
    }


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
