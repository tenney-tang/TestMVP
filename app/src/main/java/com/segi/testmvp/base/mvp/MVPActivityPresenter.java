package com.segi.testmvp.base.mvp;

import android.content.Context;

import com.segi.testmvp.base.app.BaseActivity;
import com.segi.testmvp.di.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * 拥有生命周期的 Presenter（使用 Activity）
 * <p/>
 */
public abstract class MVPActivityPresenter<V extends MVPView> extends RxFragment
        implements MVPPresenter {

    protected String TAG = this.getClass().getSimpleName();
    protected V mView;
    protected AppComponent appComponent;

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
        if (getParentView() instanceof BaseActivity) {
            appComponent = ((BaseActivity) getParentView()).getAppComponent();
        }
    }

    protected AppComponent getAppComponent() {
        return appComponent;
    }
}