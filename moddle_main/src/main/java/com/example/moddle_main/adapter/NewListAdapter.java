package com.example.moddle_main.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.moddle_main.R;
import com.example.moddle_main.bean.NewsItemBean;

import java.util.List;

/**
 * des:新闻列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class NewListAdapter extends BaseMultiItemQuickAdapter<NewsItemBean, BaseViewHolder> {

    public NewListAdapter(Context context, List data) {
        super(data);
        addItemType(NewsItemBean.PICTURE_1, R.layout.item_news);
        addItemType(NewsItemBean.PICTURE_2, R.layout.item_news_photo);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItemBean item) {
        switch (helper.getItemViewType()) {
            case NewsItemBean.PICTURE_1:
                ImageView imageView = helper.getView(R.id.news_summary_photo_iv);
                Glide.with(mContext)
                        .load(item.getImgsrc())
                        .into(imageView);
                helper.setText(R.id.news_summary_title_tv, item.getTitle())
                        .setText(R.id.news_summary_digest_tv, item.getDigest())
                        .setText(R.id.news_summary_ptime_tv,item.getPtime());
                break;
            case NewsItemBean.PICTURE_2:
                helper.setText(R.id.news_summary_title_tv,item.getTitle())
                .setText(R.id.news_summary_ptime_tv,item.getPtime());
                ImageView img_left = helper.getView(R.id.news_summary_photo_iv_left);
                ImageView img_middle = helper.getView(R.id.news_summary_photo_iv_middle);
                ImageView img_right = helper.getView(R.id.news_summary_photo_iv_right);

                Glide.with(mContext).load(item.getImgsrc()).into(img_left);
                Glide.with(mContext).load(item.getImgsrc()).into(img_middle);
                Glide.with(mContext).load(item.getImgsrc()).into(img_right);
                break;
        }
    }

}