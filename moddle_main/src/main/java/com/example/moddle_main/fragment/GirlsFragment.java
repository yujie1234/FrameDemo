package com.example.moddle_main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.base.BaseFragment;
import com.android.common.constant.ApiService;
import com.android.common.okgo.JsonCallBack;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.moddle_main.R;
import com.example.moddle_main.adapter.GrilsAdapter;
import com.example.moddle_main.bean.GirlsBean;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GirlsFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GrilsAdapter mAdapter;

    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 5;

    @Override
    public int getContentView() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initData() {
        refresh();
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mRecyclerView = view.findViewById(R.id.rv_list);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new GrilsAdapter(R.layout.item_girls_layout, null);
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
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        OkGo.<GirlsBean>get(ApiService.GIRLS_API + PAGE_SIZE + "/" + mNextRequestPage)
                .tag(this)
                .execute(new JsonCallBack<GirlsBean>(GirlsBean.class) {
                    @Override
                    public void onSuccess(Response<GirlsBean> response) {
                        GirlsBean girlsBean = response.body();
                        setData(true, girlsBean.getResults());
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
        OkGo.<GirlsBean>get(ApiService.GIRLS_API + PAGE_SIZE + "/" + mNextRequestPage)
                .tag(this)
                .execute(new JsonCallBack<GirlsBean>(GirlsBean.class) {
                    @Override
                    public void onSuccess(Response<GirlsBean> response) {
                        GirlsBean girlsBean = response.body();
                        setData(false, girlsBean.getResults());
                    }

                    @Override
                    public void onError(Response<GirlsBean> response) {
                        super.onError(response);
                        mAdapter.loadMoreFail();
                    }

                });
    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
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
