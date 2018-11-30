package com.segi.data.cache;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirClearCallback;
import com.anupcowkur.reservoir.ReservoirDeleteCallback;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.segi.data.bean.TestBean;
import com.segi.data.util.Logger;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * LocalCacheFactory 本地数据存储
 */
public class LocalCacheFactory implements LocalCache {

    /**
     * 本地缓存key
     */
    public static final String TEST_LIST_INFO = TestBean.class.getSimpleName();

    public LocalCacheFactory() {
    }

    @Override
    public void put(final String key, final Object object) {
        if (object == null) return;
        Reservoir.putAsync(key, object, new ReservoirPutCallback() {
            @Override
            public void onSuccess() {
                Logger.d("log", "Put success: key=" + key + " object=" + object.getClass());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(String key) {
        if (this.isExpired(key)) {
            try {
                Reservoir.delete(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refresh(final String key, final Object entity) {
        if (this.isExpired(key)) {
            Reservoir.deleteAsync(key, new ReservoirDeleteCallback() {
                @Override
                public void onSuccess() {
                    LocalCacheFactory.this.put(key, entity);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            LocalCacheFactory.this.put(key, entity);
        }
    }

    @Override
    public void clear() {
        try {
            Reservoir.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear(ReservoirClearCallback callback) {
        try {
            Reservoir.clearAsync(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<Boolean> clearSync() {
        return Reservoir.clearAsync();
    }

    @Override
    public <T> T getSync(Class<T> clazz) {
        try {
            String key = clazz.getSimpleName();
            if (isExpired(key))
                return Reservoir.get(key, clazz);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T getSync(String key, Class<T> clazz) {
        try {
            if (isExpired(key))
                return Reservoir.get(key, clazz);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isExpired(String key) {
        try {
            return Reservoir.contains(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T> Observable<T> get(String key, Class<T> clazz) {
        if (isExpired(key))
            return Reservoir.getAsync(key, clazz);
        return null;
    }

    @Override
    public <T> Observable<T> get(Class<T> clazz) {
        String key = clazz.getSimpleName();
        return get(key, clazz);
    }

    @Override
    public <T> void get(final String key, final Type typeOfT, final ReservoirGetCallback<T> callback) {
        Reservoir.getAsync(key, typeOfT, callback);
    }

    @Override
    public <T> void get(final String key, final Class<T> clazz,
                        final ReservoirGetCallback<T> callback) {
        Reservoir.getAsync(key, clazz, callback);
    }
}
