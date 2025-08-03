package com.github.romanqed.juni;

/**
 * A base interface for unified sync/async functional abstractions.
 * <p>
 * Implementations of this interface may expose both synchronous and asynchronous APIs,
 * but the nature of the original implementation can differ.
 * These methods indicate whether the underlying implementation was originally
 * designed as synchronous, asynchronous, or both.
 * <p>
 * This is useful for optimizing invocation logic: for example, there is little benefit
 * in calling {@code invokeAsync()} if it simply wraps a synchronous function via
 * {@code CompletableFuture.supplyAsync}.
 */
public interface Uni {

    /**
     * Indicates that the underlying implementation is natively synchronous,
     * i.e., the synchronous path is the original and possibly more efficient one.
     *
     * @return true if the implementation is primarily synchronous
     */
    default boolean isSync() {
        return false;
    }

    /**
     * Indicates that the underlying implementation is natively asynchronous,
     * i.e., the asynchronous path is the original and possibly more efficient one.
     *
     * @return true if the implementation is primarily asynchronous
     */
    default boolean isAsync() {
        return false;
    }

    /**
     * Indicates that both sync and async paths are equally valid and implemented,
     * and the choice can be made freely depending on context.
     *
     * @return true if both execution modes are fully implemented
     */
    default boolean isUni() {
        return isSync() && isAsync();
    }
}
