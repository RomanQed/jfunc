package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * A container that provides lazy initialization, implements {@link LazyInvokable1}.
 *
 * @param <T> the type of the function parameter
 * @param <R> the type of the result of the function
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
     * Gets the result stored in the buffer, or, if the buffer is empty, calls the wrapped lambda interface.
     *
     * @param t the function parameter
     * @return a result
     * @throws Throwable if wrapped lambda throws exception
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
