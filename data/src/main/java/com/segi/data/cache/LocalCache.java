package com.segi.data.cache;

import com.anupcowkur.reservoir.ReservoirClearCallback;
import com.anupcowkur.reservoir.ReservoirGetCallback;

import java.lang.reflect.Type;

import rx.Observable;


public interface LocalCache {

    void put(String key, Object entity);

    boolean isExpired(String key);

    void delete(String key);

    void refresh(String key, Object entity);

    void clear();

    void clear(ReservoirClearCallback callback);

    Observable<Boolean> clearSync();

    <T> T getSync(Class<T> clazz);

    <T> T getSync(String key, Class<T> clazz);

    <T> Observable<T> get(String key, Class<T> clazz);

    <T> Observable<T> get(Class<T> clazz);

    <T> void get(final String key, final Type typeOfT, final ReservoirGetCallback<T> callback);

    <T> void get(final String key, final Class<T> clazz, final ReservoirGetCallback<T> callback);
}
