package com.example.moddle_main.fragment.girls;

import com.android.common.constant.ApiService;
import com.example.moddle_main.bean.GirlsBean;
import com.android.common.okgo.RxUtils;
import com.lzy.okgo.model.HttpMethod;
import io.reactivex.Observable;

public class GirlsModel implements GirlsContract.Model{
    private static final int PAGE_SIZE = 5;

    @Override
    public Observable<GirlsBean> refresh(int page) {
        String url = ApiService.GIRLS_API + PAGE_SIZE + "/" + page;
        return RxUtils.request(HttpMethod.GET,url,GirlsBean.class);
    }

    @Override
    public Observable<GirlsBean> loadMore(int page) {
       String url = ApiService.GIRLS_API + PAGE_SIZE + "/" + page;
        return RxUtils.request(HttpMethod.GET,url,GirlsBean.class);
    }
}
