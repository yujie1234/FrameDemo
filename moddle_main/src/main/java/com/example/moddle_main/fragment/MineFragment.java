package com.example.moddle_main.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;

import com.android.common.base.BaseFragment;
import com.android.common.constant.PermissionConstants;
import com.android.common.util.MaterialDialogUtils;
import com.android.common.util.PermissionUtils;
import com.android.common.util.ScreenUtils;
import com.android.common.util.ToastUtils;
import com.example.moddle_main.R;

import java.util.List;

public class MineFragment extends BaseFragment{


    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_permition){
            PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE)
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(final ShouldRequest shouldRequest) {
//                            MaterialDialogUtils.
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
                                MaterialDialogUtils.showOpenAppSettingDialog(getActivity());
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

}