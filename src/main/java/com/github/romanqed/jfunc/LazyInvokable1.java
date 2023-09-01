package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * @param <T>
 * @param <R>
 */
public final class LazyInvokable1<T, R> implements Invokable1<T, R> {
    private final Object lock;
    private final Invokable1<T, R> body;
    private volatile R value;

    public LazyInvokable1(Invokable1<T, R> body) {
        this.body = Objects.requireNonNull(body);
        this.lock = new Object();
    }

    /**
     * @param t
     * @return
     * @throws Throwable
     */
    @Override
    public R invoke(T t) throws Throwable {
        if (value == null) {
            synchronized (lock) {
                if (value == null) {
                    value = body.invoke(t);
                }
            }
        }
        return value;
    }
}
