package com.example.moddle_main.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.common.base.BaseFragment;
import com.android.common.constant.ApiService;
import com.android.common.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.moddle_main.R;
import com.example.moddle_main.adapter.NewListAdapter;
import com.example.moddle_main.bean.NewsItemBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import org.json.JSONObject;

import java.util.List;

/**
 * des:新闻fragment
 * Created by xsf
 * on 2016.09.17:30
 */
public class NewsFrament extends BaseFragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewListAdapter mAdapter;

    private int startPage = 20;
    private static final int PAGE_SIZE = 5;
    private String channleId = "T1348647909107";

    @Override
    public int getContentView() {
        return R.layout.framents_news;
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mRecyclerView = view.findViewById(R.id.rv_list);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initAdapter();
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            channleId = getArguments().getString("channleId");
        }
        refresh();
    }


    private void initAdapter() {
        mAdapter = new NewListAdapter(getActivity(), null);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        startPage = 0;//T1348647909107/60-20.html
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        OkGo.<String>get(ApiService.NEWS_API+channleId+"/"+startPage+"-20.html")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String resultStr = response.body();
                        try {
                            JSONObject object = new JSONObject(resultStr);
                            String str = object.optString(channleId);
                            List<NewsItemBean> newsItemBeanList = JSON.parseArray(str,NewsItemBean.class);
                            setData(true, newsItemBeanList);
                        }catch (Exception e){
                            ToastUtils.showShort(e.getMessage());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void loadMore() {

        OkGo.<String>get(ApiService.NEWS_API+channleId+"/"+startPage+"-20.html")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String resultStr = response.body();
                        try {
                            JSONObject object = new JSONObject(resultStr);
                            String str = object.optString(channleId);
                            List<NewsItemBean> newsItemBeanList = JSON.parseArray(str,NewsItemBean.class);
                            setData(false, newsItemBeanList);
                        }catch (Exception e){
                            ToastUtils.showShort(e.getMessage());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mAdapter.loadMoreFail();
                    }
                });
    }

    private void setData(boolean isRefresh, List data) {
        startPage += 20;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(getActivity(), "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }
}
