package com.segi.testmvp.ui.test;

import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.segi.data.util.Logger;
import com.segi.testmvp.R;
import com.segi.testmvp.base.app.BaseRefreshRecyclerFragment;

/**
 * @author : tangjl
 * @date : 2018/11/8
 */
public class TestFragment extends BaseRefreshRecyclerFragment<TestContract.Presenter> implements TestContract.View {

    @Override
    protected BaseQuickAdapter onGetAdapter() {
        mAdapter =new TestAdapter(R.layout.item_test,mPresenter.getTestList());
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadTestData(0,0,0);
            }
        });
        //加载动画,默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        return mAdapter;
    }

    @Override
    protected TestContract.Presenter onBindPresenter() {
        return bindPresenter(TestPresenter.class);
    }

    @Override
    public void onFinishRefresh() {
        mAdapter.notifyDataSetChanged();
        //下来刷新，加载动画
        setRefreshing(false);
    }

    @Override
    public void onFinishError() {
        Logger.d(TAG,"tjl失败");
        setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFirstLoadingData() {
        setRefreshing(true);
        mPresenter.loadTestData(0,0,0);
    }
}
