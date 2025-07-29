package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * A container that provides lazy initialization, implements {@link LazyFunction1}.
 *
 * @param <T> the type of the function parameter
 * @param <R> the type of the result of the function
 */
public final class LazyFunction1<T, R> implements Function1<T, R> {
    private final Object lock;
    private final Function1<T, R> body;
    private volatile R value;

    /**
     * Constructs a new {@link LazyFunction1} with the given computation body.
     * The result will be computed once for the first input, and reused for all subsequent calls.
     * Note: the input parameter is ignored on subsequent invocations.
     *
     * @param body the function to be evaluated lazily
     * @throws NullPointerException if {@code body} is null
     */
    public LazyFunction1(Function1<T, R> body) {
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
