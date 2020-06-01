package com.segi.testmvp.ui.test2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.segi.data.bean.TestBean;
import com.segi.data.util.Logger;
import com.segi.testmvp.R;
import com.segi.testmvp.base.mvp.MVPActivityView;

import java.util.List;

public class OtherActivity extends MVPActivityView<OtherContract.Presenter> implements OtherContract.View {

    private TextView mTextView;

    @Override
    protected OtherContract.Presenter onBindPresenter() {
        return bindPresenter(OtherPresenter.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_other;
    }

    @Override
    protected void addFragment(Bundle savedInstanceState) {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = (TextView) findViewById(R.id.tv_other_result);
    }


    @Override
    public void onFirstLoadingData() {
        Logger.d(TAG, "onFirstLoadingData");
        mPresenter.loadData(0, 0, 0);
    }


    @Override
    public void onFinishRefresh() {
        List<TestBean.DataListBean> dataList = mPresenter.getDataList();
        Logger.d(TAG, "onFinishRefresh, dataListSize:" + dataList.size());
        mTextView.setText("onFinishRefresh, dataListSize:" + dataList.size());
    }

    @Override
    public void onFinishError() {
        Logger.d(TAG, "onFinishErrorxx");
        mTextView.setText("onFinishErrorxx");
    }
}
