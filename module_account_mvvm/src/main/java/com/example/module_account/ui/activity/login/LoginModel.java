package com.example.module_account.ui.activity.login;

import com.android.common.constant.ApiService;
import com.android.common.constant.ConstantUtil;
import com.android.common.okgo.RxUtils;
import com.android.common.okgo.callback.request.PostRequestUtil;
import com.example.module_account.ui.activity.bean.ProductItemModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<String> login(String phoneNo, String password) {
        Map params = new HashMap();
        params.put("phoneNo", phoneNo);
        params.put("password", password);
        return PostRequestUtil.request(ApiService.LOGIN_PWD, params);
    }


    @Override
    public Observable<String> getProductList() {
        Map map = new HashMap<>();
        map.put("pageNo", Integer.valueOf(1));
        map.put("pageSize", Integer.valueOf(10));
        map.put("chanNo","44000013");
        map.put("appType", "30500149");
        map.put("hotRecommend","13900001");
        return PostRequestUtil.request(ApiService.PRODUCT_LIST,map);
    }
}
