package com.github.romanqed.jfut;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous operation that takes one argument and returns no result.
 *
 * @param <T> the input type
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncRunnable1<T> {

    /**
     * Returns a composed runnable that executes {@code func1}, then {@code func2}, with the same input.
     *
     * @param func1 the first operation
     * @param func2 the second operation
     * @param <T>   the input type
     * @return a composed asynchronous runnable
     */
    static <T> AsyncRunnable1<T> combine(AsyncRunnable1<T> func1, AsyncRunnable1<T> func2) {
        return func1.andThen(func2);
    }

    /**
     * Executes this operation asynchronously with the given input.
     *
     * @param t the input argument
     * @return a {@link CompletableFuture} representing the completion of the operation
     */
    CompletableFuture<Void> runAsync(T t);

    /**
     * Returns a composed runnable that performs this operation followed by the given one, using the same input.
     *
     * @param func the operation to perform after this one
     * @return a composed asynchronous runnable
     */
    default AsyncRunnable1<T> andThen(AsyncRunnable1<T> func) {
        Objects.requireNonNull(func);
        return t -> runAsync(t).thenCompose(v -> func.runAsync(t));
    }
}
