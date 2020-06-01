package com.segi.testmvp.base.mvp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.segi.testmvp.base.app.BaseActivity;

/**
 * Activity 实现的 MVPView
 */
public abstract class MVPActivityView<P extends MVPPresenter> extends BaseActivity
        implements MVPView {

    protected P mPresenter;
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onBindPresenter();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFirstLoadingData();
            }
        });
    }

    protected abstract P onBindPresenter();


    /**
     * 绑定 MVPActivityPresenter
     *
     * @param presenterClass presenterClass
     * @param <T>            泛型
     * @return MVPFragmentPresenter
     */
    final protected <T extends MVPActivityPresenter> T bindPresenter(Class<T> presenterClass) {
        return checkAndAddFragment(0, presenterClass.getCanonicalName(), presenterClass, null);
    }

    /**
     * 绑定 MVPActivityPresenter
     *
     * @param presenterClass presenterClass
     * @param args           传输的数据
     * @param <T>            泛型
     * @return MVPFragmentPresenter
     */
    final protected <T extends MVPActivityPresenter> T bindPresenter(Class<T> presenterClass,
                                                                     Bundle args) {
        return checkAndAddFragment(0, presenterClass.getCanonicalName(), presenterClass, args);
    }


}
