package com.android.common.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.R;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by sky on 2017/03/08.
 */

public abstract class BaseBindActivity <V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity {

    protected V binding;
    protected VM viewModel;
    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout rlTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding();
        initTitleView();
        getDatabinding();
        viewModel.onCreate();
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
        viewModel = null;
        ImmersionBar.with(this).destroy();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView());
        binding.setVariable(initVariableId(), viewModel = initViewModel());
    }


    private void initTitleView(){
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView();

    /**
     * 初始化ViewModel的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public abstract VM initViewModel();

    public abstract V getDatabinding();

    public abstract void initView();


    public void setTitleParams(String title,int color){
        tvTitle.setText(title);
        rlTitle.setVisibility(View.VISIBLE);
        rlTitle.setBackgroundColor(getResources().getColor(color));
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarDarkFont(true)
//                .statusBarColorInt(getResources().getColor(R.color.white))
                .barColor(color)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式;
                .init();
    }
}
