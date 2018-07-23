package com.android.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.android.common.R;
import com.android.common.util.MaterialDialogUtils;
import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by sky on 2017/03/08.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected ImageView ivBack;
    private TextView tvTitle;
    private FrameLayout viewContent;
    private RelativeLayout rlTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
        LayoutInflater.from(this).inflate(getContentView(), viewContent);
        initView();
        initData();
    }

    protected void init() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title);
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    /*引入布局*/
    public abstract int getContentView();

    /*初始化布局*/
    public abstract void initView();

    /*初始化数据*/
    public abstract void initData();


    /*设置左边返回键和名称*/
    public void setTitle(String title, int titleColor) {
        rlTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        ImmersionBar.with(this)
                .statusBarColor(titleColor)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarDarkFont(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式;
                .init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    private MaterialDialog dialog;

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
