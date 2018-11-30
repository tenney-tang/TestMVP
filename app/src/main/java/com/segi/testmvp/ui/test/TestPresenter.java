package com.segi.testmvp.ui.test;

import android.text.TextUtils;

import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;
import com.segi.data.cache.LocalCacheFactory;
import com.segi.testmvp.base.mvp.MVPFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 处理数据，返回数据
 *
 * @author : tangjl
 * @date : 2018/11/8
 */
public class TestPresenter extends MVPFragmentPresenter<TestContract.View> implements TestContract.Presenter {
    private List<TestBean.DataListBean> testBeans = new ArrayList<>();

    @Override
    public void loadTestData(int menuVersion, int menuSid, int settingsId) {
        //创建一个被观察者
        getAppComponent().getDataManeger().getDataStore().requestTestData(menuVersion, menuSid, settingsId)
                //请求数据 compose如果你用的是JDK 7以下版本，你得告诉编译器返回的类型
                .compose(this.<BaseBean<TestBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                //Subscriber 绑定双方，请求接口和请求回来的数据
                .subscribe(new Subscriber<BaseBean<TestBean>>() {
                    @Override
                    public void onCompleted() {
                        //成功的
                        mView.onFinishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        TestPresenter.this.getLocalCacheData();
                        //失败的
                        mView.onFinishError();
                    }

                    @Override
                    public void onNext(BaseBean<TestBean> testBeanBaseBean) {
                        //成功之后的数据
                        if (200 == testBeanBaseBean.code) {
                            testBeans.clear();
                            testBeans.addAll(testBeanBaseBean.data.dataList);
                        }
                    }
                });
    }

    @Override
    public List<TestBean.DataListBean> getTestList() {
        return testBeans;
    }

    /**
     * 缓存
     */
    private void getLocalCacheData() {
        if (getAppComponent().getDataManeger().getLocalCache().isExpired(LocalCacheFactory.TEST_LIST_INFO)) {
            TestBean testBean = getAppComponent().getDataManeger().getLocalCache().getSync(TestBean.class);
            if (testBean != null) {
                testBeans.clear();
                testBeans.addAll(testBean.dataList);
            }
        }
    }
}
