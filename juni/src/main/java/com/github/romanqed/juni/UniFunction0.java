package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function0;
import com.github.romanqed.jsync.AsyncFunction0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface UniFunction0<T> extends Uni, Function0<T>, AsyncFunction0<T> {

    static <T> UniFunction0<T> of(Function0<T> func) {
        return new UniFunction0<>() {

            @Override
            public boolean isSync() {
                return true;
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

    static <T> UniFunction0<T> of(AsyncFunction0<T> func) {
        return new UniFunction0<>() {

            @Override
            public boolean isAsync() {
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
}
