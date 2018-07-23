package com.bayacx.standbycredit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.common.util.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnQQLogin = findViewById(R.id.btn_qq_login);
        btnQQLogin.setOnClickListener(this);
        Button btnWeiXinLogin = findViewById(R.id.btn_weixin_login);
        btnWeiXinLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_qq_login:
                qqLogin();
                break;
            case R.id.btn_weixin_login:
                weiXinLogin();
                break;
        }
    }


    private void qqLogin() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(this);//授权回调监听，监听oncomplete，onerror，oncancel三种状态
        if (plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if (plat.isAuthValid()) {
            //判断是否已经存在授权状态，可以根据自己的登录逻辑设置
            ToastUtils.showShort("已经授权过了");
            return;
        }
        plat.authorize();    //要功能，不要数据
//        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

    private void weiXinLogin() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.authorize();
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtils.showShort("onComplete");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShort("onError");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort("onCancel");
    }
}
