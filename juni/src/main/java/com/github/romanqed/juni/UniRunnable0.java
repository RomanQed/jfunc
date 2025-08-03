package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;
import com.github.romanqed.jsync.AsyncRunnable0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface representing a zero-argument runnable,
 * supporting both synchronous and asynchronous execution.
 */
public interface UniRunnable0 extends Uni, Runnable0, AsyncRunnable0 {

    /**
     * Wraps a synchronous runnable into a {@link UniRunnable0}.
     *
     * @param func the runnable to wrap
     * @return a unified runnable that executes synchronously
     */
    static UniRunnable0 of(Runnable0 func) {
        return new UniRunnable0() {

            @Override
            public boolean isSync() {
                return true;
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
     * @return a unified runnable that executes asynchronously
     */
    static UniRunnable0 of(AsyncRunnable0 func) {
        return new UniRunnable0() {

            @Override
            public boolean isAsync() {
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
}
