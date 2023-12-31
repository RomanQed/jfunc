package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * A container that provides lazy initialization, implements {@link Function0}.
 *
 * @param <T> the type of results supplied by this invokable
 */
public final class LazyFunction0<T> implements Function0<T> {
    private final Object lock;
    private final Function0<T> body;
    private volatile T value;

    public LazyFunction0(Function0<T> body) {
        this.body = Objects.requireNonNull(body);
        this.lock = new Object();
    }

    /**
     * Gets the result stored in the buffer, or, if the buffer is empty, calls the wrapped lambda interface.
     *
     * @return a result
     * @throws Throwable if wrapped lambda throws exception
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
