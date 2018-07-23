package com.example.moddle_main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.TabLayout;

import com.android.common.base.BaseFragment;
import com.android.common.util.ResourceUtils;
import com.example.moddle_main.MyUtils;
import com.example.moddle_main.R;
import com.example.moddle_main.adapter.BaseFragmentAdapter;
import com.example.moddle_main.bean.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabs;


    List<ChannelBean> newsChannelsMine;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);
    }

    @Override
    public void initData() {
        String mineString = ResourceUtils.readAssets2String("mine.json");
        newsChannelsMine = new Gson().fromJson(mineString, new TypeToken<List<ChannelBean>>() {
        }.getType());
        List<String> channelNames = new ArrayList<>();
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < newsChannelsMine.size(); i++) {
            channelNames.add(newsChannelsMine.get(i).getChannelName());
            mNewsFragmentList.add(createListFragments(newsChannelsMine.get(i)));
        }
        if (fragmentAdapter == null) {
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        } else {
            //刷新fragment
            fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList, channelNames);
        }
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
    }


    private Fragment createListFragments(ChannelBean newsChannel) {
        NewsFrament fragment = new NewsFrament();
        Bundle bundle = new Bundle();
        bundle.putString("channleId", newsChannel.getChannelId());
        fragment.setArguments(bundle);
        return fragment;
    }


}
