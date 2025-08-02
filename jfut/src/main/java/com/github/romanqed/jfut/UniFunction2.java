package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface combining {@link com.github.romanqed.jfunc.Function2}
 * and {@link AsyncFunction2}, for functions with two parameters and a return value.
 */
public interface UniFunction2<T1, T2, R> extends Function2<T1, T2, R>, AsyncFunction2<T1, T2, R> {

    /**
     * Wraps a synchronous function into a {@link UniFunction2}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static <T1, T2, R> UniFunction2<T1, T2, R> of(Function2<T1, T2, R> func) {
        return new UniFunction2<>() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public R invoke(T1 t1, T2 t2) throws Throwable {
                return func.invoke(t1, t2);
            }

            @Override
            public CompletableFuture<R> invokeAsync(T1 t1, T2 t2) {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        return func.invoke(t1, t2);
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                        return null;
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous function into a {@link UniFunction2}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the async implementation
     */
    static <T1, T2, R> UniFunction2<T1, T2, R> of(AsyncFunction2<T1, T2, R> func) {
        return new UniFunction2<>() {

            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public R invoke(T1 t1, T2 t2) throws Throwable {
                try {
                    return func.invokeAsync(t1, t2).get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<R> invokeAsync(T1 t1, T2 t2) {
                return func.invokeAsync(t1, t2);
            }
        };
    }

    /**
     * Indicates whether this function is natively asynchronous.
     *
     * @return {@code true} if the underlying function is async, otherwise {@code false}
     */
    boolean isNativeAsync();
}
