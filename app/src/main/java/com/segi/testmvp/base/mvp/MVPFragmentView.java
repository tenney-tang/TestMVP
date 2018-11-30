package com.segi.testmvp.base.mvp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.segi.testmvp.base.app.BaseFragment;

/**
 * Fragment 实现的 MVPView
 */
public abstract class MVPFragmentView<P extends MVPPresenter> extends BaseFragment implements MVPView {

    private boolean isFirstLoad = true;

    protected P mPresenter;

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    protected void setFirstLoad(boolean b) {
        isFirstLoad = b;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = onBindPresenter();
        view.post(new Runnable() {
            @Override
            public void run() {
                onFirstLoadingData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    lazyInit();
                    isFirstLoad = false;
                }
            });
        }
    }

    /**
     * 懒加载方法
     */
    protected void lazyInit() {
    }

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
    final protected <T extends MVPFragmentPresenter> T bindPresenter(Class<T> presenterClass, Bundle args) {
        return checkAndAddFragment(0, presenterClass.getCanonicalName(), presenterClass, args);
    }

    protected abstract P onBindPresenter();

}
