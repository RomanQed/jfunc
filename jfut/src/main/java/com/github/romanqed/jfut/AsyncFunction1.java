package com.github.romanqed.jfut;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous function that takes one argument and produces a result.
 *
 * @param <T> the input type
 * @param <R> the result type
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncFunction1<T, R> {

    /**
     * Returns an identity function that completes with its input.
     *
     * @param <T> the input and result type
     * @return an identity asynchronous function
     */
    static <T> AsyncFunction1<T, T> identity() {
        return CompletableFuture::completedFuture;
    }

    /**
     * Invokes this function asynchronously with the given input.
     *
     * @param t the input argument
     * @return a {@link CompletableFuture} representing the asynchronous result
     */
    CompletableFuture<R> invokeAsync(T t);

    /**
     * Returns a composed function that first applies this function, then applies the {@code after} function to its result.
     *
     * @param after the function to apply after this one
     * @param <V>   the output type of the {@code after} function
     * @return a composed asynchronous function
     */
    @SuppressWarnings("unchecked")
    default <V> AsyncFunction1<T, V> andThen(AsyncFunction1<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> (CompletableFuture<V>) invokeAsync(t).thenCompose(after::invokeAsync);
    }

    /**
     * Returns a composed function that first applies the {@code before} function, then applies this function to its result.
     *
     * @param before the function to apply before this one
     * @param <V>    the input type of the {@code before} function
     * @return a composed asynchronous function
     */
    default <V> AsyncFunction1<V, R> compose(AsyncFunction1<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> before.invokeAsync(v).thenCompose(this::invokeAsync);
    }
}
