package com.segi.data.repository;


import com.segi.data.bean.ZipBean;
import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;

import rx.Observable;

/**
 * 数据仓库，给P层调用，传输请求参数
 * {@link DataStoreRepository}.
 *
 * @author : tangjl
 * @date : 2018/11/8
 */
public interface DataStore {
    //创建一个被观察者
    Observable<BaseBean<TestBean>> requestTestData(int menuVersion,
                                                   int menuSid,
                                                   int settingsId);

    Observable<BaseBean<ZipBean>> getMsgV3(int cityId, int provinceId);
}
