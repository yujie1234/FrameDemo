package com.android.common.okgo.callback.request;

import com.android.common.okgo.ParamsUtil;
import com.android.common.okgo.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;

public class PostRequestUtil {

    public static <T> Observable<T> request(String url, Map map) {
        return request(url,null,null,map);
    }

    public static <T> Observable<T> request(String url, Class<T> clazz,Map map) {
        return request(url,null,clazz,map);
    }

    public static <T> Observable<T> request(String url, Type type, Class<T> clazz, Map map) {
        PostRequest<T> request = OkGo.post(url);
        request.upJson(ParamsUtil.getParam(map));
//        request.headers(headers);
//        request.params(params);
        if (type != null) {
            request.converter(new JsonConvert<T>(type));
        } else if (clazz != null) {
            request.converter(new JsonConvert<T>(clazz));
        } else {
            request.converter(new JsonConvert<T>(String.class));
        }
        return request.adapt(new ObservableBody<T>());
    }

}
