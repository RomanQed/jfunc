package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function2;
import com.github.romanqed.jsync.AsyncFunction2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A unified interface representing a binary function with return value,
 * supporting both synchronous and asynchronous execution.
 *
 * @param <T1> the type of the first argument
 * @param <T2> the type of the second argument
 * @param <R>  the result type
 */
public interface UniFunction2<T1, T2, R> extends Uni, Function2<T1, T2, R>, AsyncFunction2<T1, T2, R> {

    /**
     * Creates a synchronous unified function from the given {@link Function2}.
     *
     * @param func the function to wrap
     * @param <T1> the type of the first argument
     * @param <T2> the type of the second argument
     * @param <R>  the result type
     * @return a unified function that executes synchronously
     */
    static <T1, T2, R> UniFunction2<T1, T2, R> of(Function2<T1, T2, R> func) {
        return new UniFunction2<>() {

            @Override
            public boolean isSync() {
                return true;
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
     * Creates an asynchronous unified function from the given {@link AsyncFunction2}.
     *
     * @param func the function to wrap
     * @param <T1> the type of the first argument
     * @param <T2> the type of the second argument
     * @param <R>  the result type
     * @return a unified function that executes asynchronously
     */
    static <T1, T2, R> UniFunction2<T1, T2, R> of(AsyncFunction2<T1, T2, R> func) {
        return new UniFunction2<>() {

            @Override
            public boolean isAsync() {
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
}
