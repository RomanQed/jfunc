package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function1;
import com.github.romanqed.jsync.AsyncFunction1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface UniFunction1<T, R> extends Uni, Function1<T, R>, AsyncFunction1<T, R> {

    static <T, R> UniFunction1<T, R> of(Function1<T, R> func) {
        return new UniFunction1<>() {

            @Override
            public boolean isSync() {
                return true;
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

    static <T, R> UniFunction1<T, R> of(AsyncFunction1<T, R> func) {
        return new UniFunction1<>() {

            @Override
            public boolean isAsync() {
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
}
