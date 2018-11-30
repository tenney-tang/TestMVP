package com.segi.testmvp.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 用于替代EventBus, 注意及时取消订阅
 */
@Singleton
public class RxBus {

    @Inject
    public RxBus() {
    }

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    /**
     * send the event
     */
    public void send(Object o) {
        _bus.onNext(o);
    }

    /**
     * subscribe to the event
     */
    public Observable<Object> toObservable() {
        return _bus;
    }

    public <T> Observable<T> toObservable(final Class<T> eventType) {
        return _bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }
        }).cast(eventType);
    }
}
