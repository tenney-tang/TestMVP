package com.segi.testmvp.ui.test2;

import android.util.Log;

import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;
import com.segi.data.util.Logger;
import com.segi.testmvp.base.mvp.MVPActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class OtherPresenter extends MVPActivityPresenter<OtherContract.View> implements OtherContract.Presenter {

    private List<TestBean.DataListBean> mDataList = new ArrayList<>();


    @Override
    public void loadData(int menuVersion, int menuSid, int settingsId) {
        Logger.d(TAG, "loadData");
        getAppComponent().getDataManeger().getDataStore().requestTestData(menuVersion, menuSid, settingsId)
                //请求数据 compose如果你用的是JDK 7以下版本，你得告诉编译器返回的类型
                .compose(this.<BaseBean<TestBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                //Subscriber 绑定双方，请求接口和请求回来的数据
                .subscribe(new Subscriber<BaseBean<TestBean>>() {
                    @Override
                    public void onCompleted() {
//                        //成功的
                        mView.onFinishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError, message:" + e.getMessage());
                        //失败的
                        mView.onFinishError();
                    }

                    @Override
                    public void onNext(BaseBean<TestBean> testBeanBaseBean) {
                        //成功之后的数据
                        if (200 == testBeanBaseBean.code) {
                            mDataList.clear();
                            mDataList.addAll(testBeanBaseBean.data.dataList);
                        }
                    }
                });
    }

    @Override
    public List<TestBean.DataListBean> getDataList() {
        Log.d(TAG, "getDataList");
        return mDataList;
    }

}
