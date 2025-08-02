package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface combining both {@link com.github.romanqed.jfunc.Function1}
 * and {@link AsyncFunction1}, for functions with one parameter and a return value.
 */
public interface UniFunction1<T, R> extends Function1<T, R>, AsyncFunction1<T, R> {

    /**
     * Wraps a synchronous function into a {@link UniFunction1}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static <T, R> UniFunction1<T, R> of(Function1<T, R> func) {
        return new UniFunction1<>() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public R invoke(T t) throws Throwable {
                return func.invoke(t);
            }

            @Override
            public CompletableFuture<R> invokeAsync(T t) {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        return func.invoke(t);
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                        return null;
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous function into a {@link UniFunction1}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the async implementation
     */
    static <T, R> UniFunction1<T, R> of(AsyncFunction1<T, R> func) {
        return new UniFunction1<>() {

            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public R invoke(T t) throws Throwable {
                try {
                    return func.invokeAsync(t).get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<R> invokeAsync(T t) {
                return func.invokeAsync(t);
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
