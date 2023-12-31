package com.github.romanqed.jfunc;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A container that provides lazy initialization, implements {@link Supplier}.
 *
 * @param <T> the type of results supplied by this supplier
 */
public final class LazySupplier<T> implements Supplier<T> {
    private final Object lock;
    private final Supplier<T> body;
    private volatile T value;

    public LazySupplier(Supplier<T> body) {
        this.body = Objects.requireNonNull(body);
        this.lock = new Object();
    }

    /**
     * Gets the result stored in the buffer, or, if the buffer is empty, calls the wrapped lambda interface.
     *
     * @return a result
     */
    @Override
    public T get() {
        if (value == null) {
            synchronized (lock) {
                if (value == null) {
                    value = body.get();
                }
            }
        }
        return value;
    }
}
