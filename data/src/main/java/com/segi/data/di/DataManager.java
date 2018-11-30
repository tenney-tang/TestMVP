package com.segi.data.di;

import android.content.Context;

import com.segi.data.cache.LocalCache;
import com.segi.data.di.component.DaggerDataComponent;
import com.segi.data.di.component.DataComponent;
import com.segi.data.di.module.DataModule;
import com.segi.data.repository.DataStore;

public class DataManager {

    private final DataComponent mDataComponent;

    public DataManager(Context context, String baseUrl) {
        mDataComponent = DaggerDataComponent.builder().dataModule(new DataModule(context, baseUrl)).build();
        mDataComponent.injectDataManager(this);
    }

    public DataStore getDataStore() {
        return mDataComponent.getDataStore();
    }

    public LocalCache getLocalCache() {
        return mDataComponent.getLocalCache();
    }
}
