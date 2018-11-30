package com.segi.data.di.component;

import com.segi.data.cache.LocalCache;
import com.segi.data.di.DataManager;
import com.segi.data.di.module.DataModule;
import com.segi.data.repository.DataStore;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : tangjl
 * @date : 2018/11/6
 */
@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {

    void injectDataManager(DataManager dataManager);

    DataStore getDataStore();

    LocalCache getLocalCache();
}
