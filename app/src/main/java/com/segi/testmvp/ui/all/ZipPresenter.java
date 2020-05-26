package com.segi.testmvp.ui.all;

import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;
import com.segi.data.bean.ZipAndTestBean;
import com.segi.data.bean.ZipBean;
import com.segi.testmvp.base.mvp.MVPFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * @author : tangjl
 * @date : 2018/11/12
 */
public class ZipPresenter extends MVPFragmentPresenter<ZipContract.View> implements ZipContract.Presenter{
    private List<ZipBean.DataListBean> zipList=new ArrayList<>();
    private List<TestBean.DataListBean> testList=new ArrayList<>();
    @Override
    public void refresh(int menuVersion, int menuSid, int settingsId, int cityId, int provinceId) {
        //创建一个被观察者
        Observable<BaseBean<ZipBean>> zipObservable = getAppComponent().getDataManeger().getDataStore().getMsgV3(cityId,provinceId);
        Observable<BaseBean<TestBean>> testObservable = getAppComponent().getDataManeger().getDataStore().requestTestData( menuVersion, menuSid, settingsId);

        Observable<ZipAndTestBean>zipAndTestBeanObservable =zipObservable.zipWith(testObservable, new Func2<BaseBean<ZipBean>, BaseBean<TestBean>, ZipAndTestBean>() {
            @Override
            public ZipAndTestBean call(BaseBean<ZipBean> zipBeanBaseBean, BaseBean<TestBean> testBeanBaseBean) {
                ZipAndTestBean zipAndTestBean = new ZipAndTestBean();
                        zipAndTestBean.zipBean = zipBeanBaseBean.data;
                        zipAndTestBean.testBean = testBeanBaseBean.data;
                        return zipAndTestBean;
            }
        });
        zipAndTestBeanObservable.compose(this.<ZipAndTestBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZipAndTestBean>() {
                    @Override
                    public void onCompleted() {
                        mView.onFinishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFinishError();
                    }

                    @Override
                    public void onNext(ZipAndTestBean zipAndTestBean) {
                         zipList.clear();
                         testList.clear();
                         zipList.addAll(zipAndTestBean.zipBean.dataList);
                         testList.addAll(zipAndTestBean.testBean.dataList);

                    }
                });
    }

    @Override
    public List<ZipBean.DataListBean> getZipList() {
        return zipList;
    }

    @Override
    public List<TestBean.DataListBean> getTestList() {
        return testList;
    }
}
