package com.android.common.okgo;

import android.text.TextUtils;

import com.android.common.constant.ConstantUtil;
import com.android.common.util.SPUtils;
import com.android.common.util.StringUtils;
import com.android.common.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class MyStringCallback extends StringCallback {


    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        String authToken = SPUtils.getInstance().getString(ConstantUtil.auth_token);
        if(!StringUtils.isEmpty(authToken)){
            request.getHeaders().put(ConstantUtil.auth_token, authToken);
        }

        String phoneNo = SPUtils.getInstance().getString(ConstantUtil.phoneNo);
        if(!StringUtils.isEmpty(phoneNo)){
            request.getHeaders().put(ConstantUtil.phoneNo, phoneNo);
        }
        request.getHeaders().put("sourceOsType", ConstantUtil.sourceOsType);
        request.getHeaders().put("sourceType", ConstantUtil.sourceType);
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
        String authToken = response.headers().get(ConstantUtil.auth_token);
        if(!StringUtils.isEmpty(authToken)){
            SPUtils.getInstance().put(ConstantUtil.auth_token,authToken);
        }
        String requestResult = response.body();
        if (!TextUtils.isEmpty(requestResult)) {
            try {
                JSONObject jsonObject = new JSONObject(requestResult);
                String code = jsonObject.optString("code");
                String msg = jsonObject.optString("msg");
                boolean success = jsonObject.optBoolean("success");
                String result = jsonObject.optString("result");

                if (code.equals("9999")) {
                    ToastUtils.showShort(msg);
                    return;
                }

                if (success) {
                    if (StringUtils.isEmpty(result) || result.equals("0000")) {
                        onSuccess(success, "", msg);
                    } else {
                        onSuccess(success, result, msg);
                    }

                } else {
                    if (code.equals("9997") || code.equals("9998")) {
                       //过期的情况
                    } else {
                        onSuccess(success, result, msg);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.showShort("服务器返回错误");
            }
        } else {

        }

    }


    public abstract void onSuccess(boolean isSuccess, String data, String msg);
}
