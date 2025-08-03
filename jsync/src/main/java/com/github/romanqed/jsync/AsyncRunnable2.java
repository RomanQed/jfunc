package com.github.romanqed.jsync;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous operation that takes two arguments and returns no result.
 *
 * @param <T1> the first input type
 * @param <T2> the second input type
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncRunnable2<T1, T2> {

    /**
     * Returns a composed runnable that executes {@code func1}, then {@code func2}, with the same inputs.
     *
     * @param func1 the first operation
     * @param func2 the second operation
     * @param <T1>  the first input type
     * @param <T2>  the second input type
     * @return a composed asynchronous runnable
     */
    static <T1, T2> AsyncRunnable2<T1, T2> combine(AsyncRunnable2<T1, T2> func1, AsyncRunnable2<T1, T2> func2) {
        return func1.andThen(func2);
    }

    /**
     * Executes this operation asynchronously with the given arguments.
     *
     * @param t1 the first input argument
     * @param t2 the second input argument
     * @return a {@link CompletableFuture} representing the completion of the operation
     */
    CompletableFuture<Void> runAsync(T1 t1, T2 t2);

    /**
     * Returns a composed runnable that performs this operation followed by the given one, using the same inputs.
     *
     * @param func the operation to perform after this one
     * @return a composed asynchronous runnable
     */
    default AsyncRunnable2<T1, T2> andThen(AsyncRunnable2<T1, T2> func) {
        Objects.requireNonNull(func);
        return (t1, t2) -> runAsync(t1, t2).thenCompose(v -> func.runAsync(t1, t2));
    }
}
