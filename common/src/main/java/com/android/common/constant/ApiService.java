package com.android.common.constant;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.Observable;

public class ApiService {

    public static final String ACCOUNT_SERVERCE_IP(){
        if(ConstantUtil.isRelease){
            return "http://mld-app.boyacx.com:8080";// z最新正式服务器地址
        }else{
            return "http://47.96.37.141:8730";//测试环境
        }
    }


    public static final String LOGIN_PWD = ACCOUNT_SERVERCE_IP()+"/bycx-drainage-service/bMarketUserInfo/cbLogin";//登录—用户名密码


    //产品列表获取接口
    public static String PRODUCT_LIST = ACCOUNT_SERVERCE_IP()+ "/bycx-drainage-service/bMarketProdDetails/getProductList";

    public static final String GIRLS_API = "http://gank.io/api/data/福利/";//获取美女图片地址


    public static final String NEWS_API = "http://c.m.163.com/nc/article/headline/";//新闻地址

}
