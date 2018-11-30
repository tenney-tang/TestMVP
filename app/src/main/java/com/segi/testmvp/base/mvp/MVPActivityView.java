package com.segi.testmvp.base.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.segi.testmvp.base.app.BaseActivity;

/**
 * Activity 实现的 MVPView
 */
public abstract class MVPActivityView<P extends MVPPresenter> extends BaseActivity
        implements MVPView {

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mPresenter = onBindPresenter();
    }

    protected abstract P onBindPresenter();


    /**
     * 绑定 MVPFragmentPresenter
     *
     * @param presenterClass presenterClass
     * @param <T>            泛型
     * @return MVPFragmentPresenter
     */
    final protected <T extends MVPFragmentPresenter> T bindPresenter(Class<T> presenterClass) {
        return checkAndAddFragment(0, presenterClass.getCanonicalName(), presenterClass, null);
    }

    /**
     * 绑定 MVPFragmentPresenter
     *
     * @param presenterClass presenterClass
     * @param args           传输的数据
     * @param <T>            泛型
     * @return MVPFragmentPresenter
     */
    final protected <T extends MVPFragmentPresenter> T bindPresenter(Class<T> presenterClass,
                                                                     Bundle args) {
        return checkAndAddFragment(0, presenterClass.getCanonicalName(), presenterClass, args);
    }
}
