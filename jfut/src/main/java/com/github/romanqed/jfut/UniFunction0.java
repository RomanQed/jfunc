package com.github.romanqed.jfut;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface representing both synchronous ({@link com.github.romanqed.jfunc.Function0})
 * and asynchronous ({@link AsyncFunction0}) functions with no parameters and a return value.
 *
 * <p>This interface allows seamless integration of both sync and async implementations.
 */
public interface UniFunction0<T> extends Function0<T>, AsyncFunction0<T> {

    /**
     * Wraps a synchronous function into a {@link UniFunction0}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the sync implementation
     */
    static <T> UniFunction0<T> of(Function0<T> func) {
        return new UniFunction0<>() {

            @Override
            public boolean isNativeAsync() {
                return false;
            }

            @Override
            public T invoke() throws Throwable {
                return func.invoke();
            }

            @Override
            public CompletableFuture<T> invokeAsync() {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        return func.invoke();
                    } catch (Throwable e) {
                        Exceptions.throwAny(e);
                        return null;
                    }
                });
            }
        };
    }

    /**
     * Wraps an asynchronous function into a {@link UniFunction0}.
     *
     * @param func the function to wrap
     * @return a unified interface delegating to the async implementation
     */
    static <T> UniFunction0<T> of(AsyncFunction0<T> func) {
        return new UniFunction0<>() {

            @Override
            public boolean isNativeAsync() {
                return true;
            }

            @Override
            public T invoke() throws Throwable {
                try {
                    return func.invokeAsync().get();
                } catch (ExecutionException e) {
                    throw e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            public CompletableFuture<T> invokeAsync() {
                return func.invokeAsync();
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
