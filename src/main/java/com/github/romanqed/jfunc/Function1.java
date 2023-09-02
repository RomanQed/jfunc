package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * Represents a function that takes one parameter and returns a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #invoke(Object)}.
 *
 * @param <T> the type of the function parameter
 * @param <R> the type of the return value
 */
@FunctionalInterface
public interface Function1<T, R> {

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> Function1<T, T> identity() {
        return value -> value;
    }

    /**
     * Main functional method of interface, takes one parameter, performs assumed action and produce result.
     *
     * @param t function parameter
     * @return produced result
     * @throws Throwable if problems occur during execution
     */
    R invoke(T t) throws Throwable;

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(Function1)
     */
    default <V> Function1<T, V> andThen(Function1<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.invoke(invoke(t));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(Function1)
     */
    default <V> Function1<V, R> compose(Function1<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> invoke(before.invoke(v));
    }
}
