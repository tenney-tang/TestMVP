package com.segi.testmvp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.segi.data.util.Logger;
import com.segi.testmvp.di.component.AppComponent;
import com.segi.testmvp.di.component.DaggerAppComponent;
import com.segi.testmvp.di.module.AppModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application
 * @author : tangjl
 * @date : 2018/11/7
 */
public class App extends Application {

    private AppComponent mAppComponent;

    private static App mApp;

    public static App getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this, BuildConfig.HTTP_HOST_URL)).build();
        mAppComponent.injectApp(this);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
        }

        //初始化klog
        Logger.init(BuildConfig.LOG_ENABLE, "Logger");

        //初始化LeakCanary
        LeakCanary.install(this);

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
