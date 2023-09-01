package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * @param <T>
 */
public final class LazyInvokable0<T> implements Invokable0<T> {
    private final Object lock;
    private final Invokable0<T> body;
    private volatile T value;

    public LazyInvokable0(Invokable0<T> body) {
        this.body = Objects.requireNonNull(body);
        this.lock = new Object();
    }

    /**
     * @return
     * @throws Throwable
     */
    @Override
    public T invoke() throws Throwable {
        if (value == null) {
            synchronized (lock) {
                if (value == null) {
                    value = body.invoke();
                }
            }
        }
        return value;
    }
}
