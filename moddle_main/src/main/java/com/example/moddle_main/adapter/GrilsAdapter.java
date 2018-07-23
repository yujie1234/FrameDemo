package com.example.moddle_main.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.moddle_main.R;
import com.example.moddle_main.bean.GirlsBean;

import java.util.List;

public class GrilsAdapter extends BaseQuickAdapter<GirlsBean.ResultsBean,BaseViewHolder>{


    public GrilsAdapter(int resourceId, List<GirlsBean.ResultsBean> mData){
        super(resourceId,mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlsBean.ResultsBean item) {
       ImageView imageView = (ImageView) helper.getView(R.id.iv_picture);

        Glide.with(mContext)
                .load(item.getUrl())
                .into(imageView);
}
}
