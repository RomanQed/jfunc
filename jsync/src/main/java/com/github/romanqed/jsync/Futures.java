package com.github.romanqed.jsync;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function0;
import com.github.romanqed.jfunc.Runnable0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Utility class for bridging synchronous functional code ({@link Runnable0}, {@link Function0})
 * with the asynchronous {@link CompletableFuture} API.
 * <p>
 * Provides convenience methods to run or supply values asynchronously from functional interfaces
 * that may throw checked exceptions.
 * <p>
 * All exceptions thrown by user-provided functions are rethrown as unchecked via {@link Exceptions#throwAny(Throwable)}.
 */
public final class Futures {
    private Futures() {
    }

    /**
     * Runs the provided {@link Runnable0} asynchronously using the given executor.
     *
     * @param func     the runnable to execute
     * @param executor the executor to run the task
     * @return a {@link CompletableFuture} representing the task
     */
    public static CompletableFuture<Void> run(Runnable0 func, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            try {
                func.run();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        }, executor);
    }

    /**
     * Runs the provided {@link Runnable0} asynchronously using the default executor.
     *
     * @param func the runnable to execute
     * @return a {@link CompletableFuture} representing the task
     */
    public static CompletableFuture<Void> run(Runnable0 func) {
        return CompletableFuture.runAsync(() -> {
            try {
                func.run();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        });
    }

    /**
     * Executes the provided {@link Function0} asynchronously using the given executor,
     * and returns its result via {@link CompletableFuture}.
     *
     * @param func     the function to execute
     * @param executor the executor to run the task
     * @param <T>      the type of result
     * @return a {@link CompletableFuture} supplying the result
     */
    public static <T> CompletableFuture<T> provide(Function0<T> func, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return func.invoke();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
                return null;
            }
        }, executor);
    }

    /**
     * Executes the provided {@link Function0} asynchronously using the default executor,
     * and returns its result via {@link CompletableFuture}.
     *
     * @param func the function to execute
     * @param <T>  the type of result
     * @return a {@link CompletableFuture} supplying the result
     */
    public static <T> CompletableFuture<T> provide(Function0<T> func) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return func.invoke();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
                return null;
            }
        });
    }
}
