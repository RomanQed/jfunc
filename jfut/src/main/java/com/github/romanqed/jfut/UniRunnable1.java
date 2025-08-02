package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface combining {@link com.github.romanqed.jfunc.Runnable1}
 * and {@link AsyncRunnable1}, representing an action with one input argument.
 */
public interface UniRunnable1<T> extends Runnable1<T>, AsyncRunnable1<T> {

    /**
     * Wraps a synchronous runnable into a {@link UniRunnable1}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static <T> UniRunnable1<T> of(Runnable1<T> func) {
        return new UniRunnable1<>() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public void run(T t) throws Throwable {
                func.run(t);
            }

            @Override
            public CompletableFuture<Void> runAsync(T t) {
                return CompletableFuture.runAsync(() -> {
                    try {
                        func.run(t);
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous runnable into a {@link UniRunnable1}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the async implementation
     */
    static <T> UniRunnable1<T> of(AsyncRunnable1<T> func) {

        return new UniRunnable1<>() {
            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public void run(T t) throws Throwable {
                try {
                    func.runAsync(t).get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<Void> runAsync(T t) {
                return func.runAsync(t);
            }
        };
    }

    /**
     * Indicates whether this runnable is natively asynchronous.
     *
     * @return {@code true} if the underlying runnable is async, otherwise {@code false}
     */
    boolean isNativeAsync();
}
