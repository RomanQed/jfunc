package com.github.romanqed.jfut;

import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous function that takes two arguments and produces a result.
 *
 * @param <T1> the first input type
 * @param <T2> the second input type
 * @param <R>  the result type
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncFunction2<T1, T2, R> {

    /**
     * Invokes this function asynchronously with the given arguments.
     *
     * @param t1 the first input argument
     * @param t2 the second input argument
     * @return a {@link CompletableFuture} representing the asynchronous result
     */
    CompletableFuture<R> invokeAsync(T1 t1, T2 t2);
}
