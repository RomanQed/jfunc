package com.github.romanqed.jfunc;

import java.util.Objects;
import java.util.function.Function;

/**
 * A container that provides lazy initialization, implements {@link Function}.
 *
 * @param <T> the type of the function parameter
 * @param <R> the type of the result of the function
 */
public final class LazyFunction<T, R> implements Function<T, R> {
    private final Object lock;
    private final Function<T, R> body;
    private volatile R value;

    public LazyFunction(Function<T, R> body) {
        this.body = Objects.requireNonNull(body);
        this.lock = new Object();
    }

    /**
     * Gets the result stored in the buffer, or, if the buffer is empty, calls the wrapped lambda interface.
     *
     * @param t the function parameter
     * @return a result
     */
    @Override
    public R apply(T t) {
        if (value == null) {
            synchronized (lock) {
                if (value == null) {
                    value = body.apply(t);
                }
            }
        }
        return value;
    }
}
