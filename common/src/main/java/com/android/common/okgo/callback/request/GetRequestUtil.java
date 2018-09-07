package com.android.common.okgo.callback.request;

import com.android.common.okgo.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;

import io.reactivex.Observable;

public class GetRequestUtil {


    public static <T> Observable<T> request(String url, Type type, Class<T> clazz, HttpParams params, HttpHeaders headers) {
        GetRequest<T> request = OkGo.get(url);
        request.headers(headers);
        request.params(params);
        if (type != null) {
            request.converter(new JsonConvert<T>(type));
        } else if (clazz != null) {
            request.converter(new JsonConvert<T>(clazz));
        } else {
            request.converter(new JsonConvert<T>());
        }
        return request.adapt(new ObservableBody<T>());
    }

}
