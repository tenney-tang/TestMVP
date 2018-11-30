package com.segi.testmvp.di.module;

import android.app.Application;

import com.segi.data.di.DataManager;
import com.segi.testmvp.utils.RxBus;
import com.segi.testmvp.utils.ToastUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application mApplication;
    private String mBaseUrl;

    public AppModule(Application application, String baseUrl) {
        mApplication = application;
        mBaseUrl = baseUrl;
    }

    /**
     * 提供单例Application
     * @return
     */
    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    /**
     * 提供单例DataManager
     * @return
     */
    @Singleton
    @Provides
    public DataManager provideDataManager() {
        return new DataManager(mApplication, mBaseUrl);
    }


    @Provides
    @Singleton
    public ToastUtils provideToastUtils() {
        return new ToastUtils(mApplication);
    }

    @Provides
    @Singleton
    public RxBus provideRxBus() {
        return new RxBus();
    }
}
