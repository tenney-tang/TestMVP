package com.segi.testmvp.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.segi.testmvp.base.app.BaseFragment;
import com.segi.testmvp.di.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * 拥有生命周期的 Presenter（使用 Fragment）
 * <p/>
 */
public abstract class MVPFragmentPresenter<V extends MVPView> extends RxFragment
        implements MVPPresenter {

    protected String TAG = this.getClass().getSimpleName();
    protected V mView;
    protected AppComponent appComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取 attachTo 的 View(MVP 中的 V)
     *
     * @return View
     */
    protected V getParentView() {
        return (V) (getParentFragment() != null ? getParentFragment() : getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mView = getParentView();
        Fragment parentFragment = getParentFragment();

        if (getParentView() instanceof BaseFragment) {
            appComponent = ((BaseFragment) parentFragment).getAppComponent();
        }
    }

    protected AppComponent getAppComponent() {
        return appComponent;
    }
}