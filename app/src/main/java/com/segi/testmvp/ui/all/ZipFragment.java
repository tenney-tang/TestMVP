package com.segi.testmvp.ui.all;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.segi.data.util.Logger;
import com.segi.testmvp.R;
import com.segi.testmvp.base.mvp.MVPFragmentView;
import com.segi.testmvp.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 多个请求合并成一个请求
 *
 * @author : tangjl
 * @date : 2018/11/12
 */
public class ZipFragment extends MVPFragmentView<ZipContract.Presenter> implements ZipContract.View {
    @BindView(R.id.toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.txt_all_title)
    TextView txtAllTitle;
    @BindView(R.id.txt_all_desc)
    TextView txtAllDesc;
    Unbinder unbinder;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zip;
    }

    @Override
    protected ZipContract.Presenter onBindPresenter() {
        return bindPresenter(ZipPresenter.class);
    }

    @Override
    public void onFinishRefresh() {
        txtAllTitle.setText(mPresenter.getZipList().get(0).title + " @第二个接口的@ " + mPresenter.getTestList().get(0).serviceDesc);

    }

    @Override
    public void onFinishError() {
        Logger.d(TAG, "tjl失败了？？");
    }

    @Override
    public void onFirstLoadingData() {
        mPresenter.refresh
                (0, 0, 0,
                        2, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
