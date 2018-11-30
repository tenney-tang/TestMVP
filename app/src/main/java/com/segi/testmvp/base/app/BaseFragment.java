package com.segi.testmvp.base.app;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.segi.testmvp.di.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment的基类
 */

public abstract class BaseFragment extends RxFragment {

    /**
     * AttachTo 的 Activity
     */
    protected Activity mActivity;
    /**
     * 全局上下文
     */
    protected Context mContext;

    protected String TAG = "NotInitFragment";
    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getAppComponent().getApplication();
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取布局Id,onCreateView方法会填充显示这个Layout
     *
     * @return 布局Id
     */
    protected abstract int getLayoutResId();

    /**
     * 检测和添加 Fragment
     *
     * @param containerId   containerId
     * @param tag           tag
     * @param fragmentClass fragment Class
     * @param args          args
     * @param <T>           T
     * @return Fragment
     */
    @SuppressWarnings("unchecked")
    final protected <T extends Fragment> T checkAndAddFragment(@IdRes int containerId, String tag,
                                                               Class<T> fragmentClass, Bundle args) {

        FragmentManager fragmentManager = getChildFragmentManager();
        String className = fragmentClass.getCanonicalName();

        T fragment = (T) fragmentManager.findFragmentByTag(tag);
        if (fragment == null || fragment.isDetached()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragment = (T) Fragment.instantiate(getActivity(), className, args);
            transaction.add(containerId, fragment, tag);
            transaction.commitAllowingStateLoss();
        }

        return fragment;
    }

    /**
     * 获取全局实例对象
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return ((BaseActivity) getActivity()).getAppComponent();
    }

    /**
     * 以下Toast不用担心会导致内存泄露问题
     */
    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showToast(int resId) {
        showToast(mContext.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    protected void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            getAppComponent().getToast().makeText(msg, duration);
        } else {
            getAppComponent().getToast().makeText(msg);
        }
    }

}
