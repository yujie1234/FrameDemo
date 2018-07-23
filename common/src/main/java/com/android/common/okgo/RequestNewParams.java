package com.android.common.okgo;

import com.android.common.util.LogUtils;
import com.google.gson.Gson;

/**
 * Created by Werwolf on 2017/4/26.
 */

public class RequestNewParams<T>{
    private T params;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    //这里可以传Map,List,实体类之类的，有特殊的还可以扩展
    public String buildJson(T t){
        setParams(t);
        String body = null;
        if(t != null){
           body = new Gson().toJson(this);
            LogUtils.e("OkGo","请求参数："+body);
        }
        return body;
    }

}
