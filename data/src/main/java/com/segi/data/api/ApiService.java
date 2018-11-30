package com.segi.data.api;


import com.segi.data.bean.ZipBean;
import com.segi.data.bean.BaseBean;
import com.segi.data.bean.TestBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 接口类，所有网络请求接口统一在此
 *
 * @author : tangjl
 * @date : 2018/11/6
 */
public interface ApiService {
    @GET("xxx/xxx-api/xxx")
    Observable<BaseBean<TestBean>> requestTestData(@Query("menuVersion") int menuVersion,
                                                   @Query("menuSid") int menuSid,
                                                   @Query("settingsId") int settingsId);

    /**
     * 消息
     *
     * @param cityId
     * @param provinceId
     * @return
     */
    // @GET("xx-sso/v1/xx/xxx")这里面记得替换成自己的api接口
    @GET("xx-sso/v1/xx/xxx")
    Observable<BaseBean<ZipBean>> getMsgV3(@Query("cityId") int cityId,
                                           @Query("provinceId") int provinceId);
}
