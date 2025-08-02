package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface combining {@link com.github.romanqed.jfunc.Runnable0}
 * and {@link AsyncRunnable0}, representing a no-argument runnable action.
 */
public interface UniRunnable0 extends Runnable0, AsyncRunnable0 {

    /**
     * Wraps a synchronous runnable into a {@link UniRunnable0}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static UniRunnable0 of(Runnable0 func) {
        return new UniRunnable0() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public void run() throws Throwable {
                func.run();
            }

            @Override
            public CompletableFuture<Void> runAsync() {
                return CompletableFuture.runAsync(() -> {
                    try {
                        func.run();
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous runnable into a {@link UniRunnable0}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the async implementation
     */
    static UniRunnable0 of(AsyncRunnable0 func) {
        return new UniRunnable0() {

            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public void run() throws Throwable {
                try {
                    func.runAsync().get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<Void> runAsync() {
                return func.runAsync();
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
