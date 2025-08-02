package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface combining {@link com.github.romanqed.jfunc.Runnable2}
 * and {@link AsyncRunnable2}, representing an action with two input arguments.
 */
public interface UniRunnable2<T1, T2> extends Runnable2<T1, T2>, AsyncRunnable2<T1, T2> {

    /**
     * Wraps a synchronous runnable into a {@link UniRunnable2}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static <T1, T2> UniRunnable2<T1, T2> of(Runnable2<T1, T2> func) {
        return new UniRunnable2<>() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public void run(T1 t1, T2 t2) throws Throwable {
                func.run(t1, t2);
            }

            @Override
            public CompletableFuture<Void> runAsync(T1 t1, T2 t2) {
                return CompletableFuture.runAsync(() -> {
                    try {
                        func.run(t1, t2);
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous runnable into a {@link UniRunnable2}.
     *
     * @param func the runnable to wrap
     * @return a unified interface delegating to the async implementation
     */
    static <T1, T2> UniRunnable2<T1, T2> of(AsyncRunnable2<T1, T2> func) {
        return new UniRunnable2<>() {

            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public void run(T1 t1, T2 t2) throws Throwable {
                try {
                    func.runAsync(t1, t2).get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<Void> runAsync(T1 t1, T2 t2) {
                return func.runAsync(t1, t2);
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
