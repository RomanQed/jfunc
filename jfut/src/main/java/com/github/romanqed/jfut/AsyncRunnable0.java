package com.github.romanqed.jfut;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous operation that takes no arguments and returns no result.
 *
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncRunnable0 {

    /**
     * Returns a composed runnable that executes {@code func1}, then {@code func2}.
     *
     * @param func1 the first operation
     * @param func2 the second operation
     * @return a composed asynchronous runnable
     */
    static AsyncRunnable0 combine(AsyncRunnable0 func1, AsyncRunnable0 func2) {
        return func1.andThen(func2);
    }

    /**
     * Executes this operation asynchronously.
     *
     * @return a {@link CompletableFuture} representing the completion of the operation
     */
    CompletableFuture<Void> runAsync();

    /**
     * Returns a composed runnable that performs this operation followed by the given one.
     *
     * @param func the operation to perform after this one
     * @return a composed asynchronous runnable
     */
    default AsyncRunnable0 andThen(AsyncRunnable0 func) {
        Objects.requireNonNull(func);
        return () -> runAsync().thenCompose(v -> func.runAsync());
    }
}
