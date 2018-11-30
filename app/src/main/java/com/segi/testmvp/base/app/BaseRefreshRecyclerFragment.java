package com.segi.testmvp.base.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.segi.testmvp.R;
import com.segi.testmvp.base.mvp.MVPFragmentView;
import com.segi.testmvp.base.mvp.MVPPresenter;
import com.segi.testmvp.widget.RecycleViewDivider;

import javax.annotation.Nullable;

import butterknife.BindView;

/**
 * 可下拉刷新，内容是 RecyclerView 的 Fragment
 *
 * @author : tangjl
 * @date : 2018/11/8
 */
public abstract class BaseRefreshRecyclerFragment<P extends MVPPresenter> extends MVPFragmentView<P> {

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private View mEmptyView;
    private View mFooterView;

    public BaseQuickAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    /**
     * 是否显示分割线，默认显示
     */
    protected boolean isShowDivider = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_refresh_recycler_base;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEmptyView = inflater.inflate(R.layout.widget_recycler_empty, container, false);
        mFooterView = inflater.inflate(R.layout.widget_recycler_footer, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mAdapter = onGetAdapter();
        mLayoutManager = onGetLayoutManager();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setEmptyView(mEmptyView);
        mAdapter.addFooterView(mFooterView);

        if (isShowDivider) {
            Drawable color = ContextCompat.getDrawable(getContext(), R.color.colorDivider);
            RecycleViewDivider divider = new RecycleViewDivider(mActivity, RecycleViewDivider.VERTICAL_LIST, color, 0);
            divider.setHeight(1);
            mRecyclerView.addItemDecoration(divider);
        }
    }

    abstract protected BaseQuickAdapter onGetAdapter();

    protected RecyclerView.LayoutManager onGetLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public void setRefreshing(final boolean b) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(b);
            }
        });
    }

    /**
     * 判断footView是否为空
     */
    public void removeFooterView() {
        if (null != mAdapter && null != mAdapter.getFooterLayout()) {
            mAdapter.removeAllFooterView();
        }
    }
}
