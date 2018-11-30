package com.segi.testmvp.base.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.segi.testmvp.App;
import com.segi.testmvp.di.component.AppComponent;
import com.segi.testmvp.utils.FragmentUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    /**
     * 全局上下文
     */
    protected Context mContext;
    protected String TAG = "NotInitActivity";
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mBind = ButterKnife.bind(this);
        mContext = getAppComponent().getApplication();
        TAG = this.getClass().getSimpleName();
        addFragment(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if (null != mBind) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    /**
     * 获取布局资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 添加Fragment
     */
    protected abstract void addFragment(Bundle savedInstanceState);

    /**
     * Replace Fragment
     *
     * @param containerId    容器Id
     * @param fragment       fragment
     * @param addToBackStack 是否加入返回栈
     */
    protected void replaceFragment(@IdRes int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), containerId, fragment, addToBackStack);
    }

    /**
     * 默认不加入返回栈 Replace ragment
     *
     * @param containerId 容器Id
     * @param fragment    fragment
     */
    protected void replaceFragment(@IdRes int containerId, Fragment fragment) {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), containerId, fragment);
    }

    /**
     * 添加 Fragment
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        String className = fragmentClass.getCanonicalName();

        T fragment = (T) fragmentManager.findFragmentByTag(tag);
        if (fragment == null || fragment.isDetached()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragment = (T) Fragment.instantiate(mContext, className, args);
            transaction.add(containerId, fragment, tag);
            transaction.commitAllowingStateLoss();
        }
        return fragment;
    }

    /**
     * Get the Main Application component for dependency injection.
     * 获取全局实例对象
     *
     * @return ApplicationComponent
     */
    public AppComponent getAppComponent() {
        return App.getInstance().getAppComponent();
    }

    /**
     * 以下Toast不用担心会导致内存泄露问题
     */
    protected void showToast(String msg) {
        getAppComponent().getToast().makeText(msg);
    }

    protected void showToast(int resId) {
        getAppComponent().getToast().makeText(resId);
    }

    protected void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            getAppComponent().getToast().makeText(msg, duration);
        } else {
            showToast(msg);
        }
    }
}
