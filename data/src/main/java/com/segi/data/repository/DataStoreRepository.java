package com.segi.data.repository;

import com.segi.data.api.ApiService;
import com.segi.data.bean.ZipBean;
import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;
import com.segi.data.cache.LocalCache;
import com.segi.data.cache.LocalCacheFactory;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Factory that creates different implementations of {@link DataStore}.
 */
public class DataStoreRepository implements DataStore {

    private final ApiService mApiService;

    private final LocalCache mLocalCache;

    public DataStoreRepository(ApiService restApi, LocalCache localCache) {
        mApiService = restApi;
        mLocalCache = localCache;
    }

    /**
     * 拿到请求参数后，去请求接口
     *
     * @param menuVersion
     * @param menuSid
     * @param settingsId
     * @return
     */
    @Override
    public Observable<BaseBean<TestBean>> requestTestData(int menuVersion, int menuSid, int settingsId) {
        return mApiService.requestTestData(menuVersion, menuSid, settingsId)
                //缓存
                .doOnNext(saveDataToCache)
                //设置Observable被观察者在什么线程运行
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean<ZipBean>> getMsgV3(int cityId, int provinceId) {
        return mApiService.getMsgV3(cityId, provinceId)
                .subscribeOn(Schedulers.io());
    }

    /**
     * 本地缓存
     */
    private final Action1<Object> saveDataToCache = new Action1<Object>() {
        @Override
        public void call(Object o) {
            if (o != null) {
                if (o instanceof BaseBean) {
                    BaseBean baseBean = (BaseBean) o;
                    mLocalCache.refresh(LocalCacheFactory.TEST_LIST_INFO, baseBean.data);
                }
            }
        }
    };

}
