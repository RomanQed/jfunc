package com.github.romanqed.jsync;

import java.util.concurrent.CompletableFuture;

/**
 * Represents an asynchronous function that takes no arguments and returns a result.
 *
 * @param <T> the result type
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncFunction0<T> {

    /**
     * Invokes this function asynchronously.
     *
     * @return a {@link CompletableFuture} representing the asynchronous result
     */
    CompletableFuture<T> invokeAsync();
}
