package com.example.moddle_main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.base.BaseActivity;
import com.android.common.constant.PermissionConstants;
import com.android.common.util.LogUtils;
import com.android.common.util.MaterialDialogUtils;
import com.android.common.util.PermissionUtils;
import com.android.common.util.ScreenUtils;
import com.android.common.util.StringUtils;
import com.android.common.util.ToastUtils;
import com.example.moddle_main.R;
import com.example.moddle_main.fragment.GirlsFragment;
import com.example.moddle_main.fragment.HomeFragment;
import com.example.moddle_main.fragment.MineFragment;

import java.util.List;


@Route(path = "/main/mainpage")
public class MainActivity extends BaseActivity {

    private GirlsFragment girlsFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private Fragment curfragment;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {
        Bundle extrsa = getIntent().getExtras();
        if (extrsa != null) {
            String fragmentType = extrsa.getString("fragmentType");
            if (StringUtils.equals(fragmentType, "girlsFragment")) {
                skipGirls();
            }
        } else {
           skipHome();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.rb_news){
            skipHome();
        }else if(id == R.id.rb_girls){
            skipGirls();
        }else if(id == R.id.rb_mine){
            skipMine();
        }else if(id == R.id.btn_permition){

                //TODO   ASDsf
                PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE)
                        .rationale(new PermissionUtils.OnRationaleListener() {
                            @Override
                            public void rationale(final ShouldRequest shouldRequest) {
                            MaterialDialogUtils.showRationaleDialog(MainActivity.this,shouldRequest);
                                LogUtils.e(""+shouldRequest);
                            }
                        })
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {

                                ToastUtils.showShort("授权成功");
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever,
                                                 List<String> permissionsDenied) {
                                if (!permissionsDeniedForever.isEmpty()) {
                                    MaterialDialogUtils.showOpenAppSettingDialog(MainActivity.this);
                                }
                            }
                        })
                        .theme(new PermissionUtils.ThemeCallback() {
                            @Override
                            public void onActivityCreate(Activity activity) {
                                ScreenUtils.setFullScreen(activity);
                            }
                        })
                        .request();
            }

    }


    private void skipGirls() {
        if (girlsFragment == null) {
            girlsFragment = new GirlsFragment();
        }
        if (!(curfragment instanceof GirlsFragment)) {
            jumpFrm(curfragment, girlsFragment, R.id.fragment_main, "美女");
            curfragment = girlsFragment;
        }

    }


    private void skipHome() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (!(curfragment instanceof HomeFragment)) {
            jumpFrm(curfragment, homeFragment, R.id.fragment_main, "首页");
            curfragment = homeFragment;
        }
    }


    private void skipMine() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        if (!(curfragment instanceof MineFragment)) {
            jumpFrm(curfragment, mineFragment, R.id.fragment_main, "我的");
            curfragment = mineFragment;
        }
    }


    protected void jumpFrm(Fragment from, Fragment to, int id, String tag) {
        if (to == null) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (from == null) {
            transaction.add(id, to, tag);
        } else {
            transaction.hide(from);
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(id, to, tag);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
