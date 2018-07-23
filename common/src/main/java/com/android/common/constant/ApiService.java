package com.android.common.constant;

public class ApiService {

    public static final String ACCOUNT_SERVERCE_IP(){
        if(ConstantUtil.isRelease){
            return "https://mld-app.boyacx.com:8443";// z最新正式服务器地址
        }else{
            return "https://47.96.37.141:8443";//测试环境
        }
    }

    public static final String SERVERCE_IP(){
        if(ConstantUtil.isRelease){
            return "http://mld-app.boyacx.com:8080";// z最新正式服务器地址
        }else{
            return "http://47.96.37.141:8730";//测试环境
        }
    }


    //注册登录模块
    //String	reuslt	0000表示注册成功，9999表示操作失败
    public static final String REGISTER = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/cust/regist";//注册

    public static final String LOGIN_PWD = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/cust/login";//登录—用户名密码
    public static final String LOGIN_SMS = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/identify/code/login";//登录-验证码

    public static final String FIND_PSD = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/cust/fpwd";//找回密码
    public static final String IS_REGISTER = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/cust/isRegist";//是否注册
    public static final String UPDATE_PSD = ACCOUNT_SERVERCE_IP()+"/bycx-passport-service/aSysLogin/cust/mpwd";//修改密码
    public static final String VALIDATE_PHONE_VERIFY_CODE = ACCOUNT_SERVERCE_IP()+"/bycx-rece-service/aSysMsgCaptcha/examineVerifyCode";//验证验证码
    public static final String VALIDATE_PHONE_VERIFY_CODE_2 = ACCOUNT_SERVERCE_IP()+"/bycx-rece-service/aSysMsgCaptcha/examineVerifyCode";//验证验证码
    public static final String SEND_VERIFY_CODE = ACCOUNT_SERVERCE_IP()+"/bycx-rece-service/aSysMsgCaptcha/sendSms";//发送验证码


    public static final String GIRLS_API = "http://gank.io/api/data/福利/";//获取美女图片地址


    public static final String NEWS_API = "http://c.m.163.com/nc/article/headline/";//新闻地址




}
