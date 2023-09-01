package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface Invokable1<T, R> {

    /**
     * @param <T>
     * @return
     */
    static <T> Invokable1<T, T> identity() {
        return value -> value;
    }

    /**
     * @param t
     * @return
     * @throws Throwable
     */
    R invoke(T t) throws Throwable;

    /**
     * @param after
     * @param <V>
     * @return
     */
    default <V> Invokable1<T, V> andThen(Invokable1<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.invoke(invoke(t));
    }

    /**
     * @param before
     * @param <V>
     * @return
     */
    default <V> Invokable1<V, R> compose(Invokable1<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> invoke(before.invoke(v));
    }
}
