package com.segi.testmvp.di.component;

import android.app.Application;

import com.segi.data.di.DataManager;
import com.segi.testmvp.App;
import com.segi.testmvp.di.module.AppModule;
import com.segi.testmvp.utils.RxBus;
import com.segi.testmvp.utils.ToastUtils;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void injectApp(App app);

    /**
     * 获取全局上下文
     */
    Application getApplication();

    DataManager getDataManeger();

    ToastUtils getToast();

    RxBus rxBus();

}
