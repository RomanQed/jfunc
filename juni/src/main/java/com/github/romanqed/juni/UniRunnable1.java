package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jsync.AsyncRunnable1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface UniRunnable1<T> extends Uni, Runnable1<T>, AsyncRunnable1<T> {

    static <T> UniRunnable1<T> of(Runnable1<T> func) {
        return new UniRunnable1<>() {

            @Override
            public boolean isSync() {
                return true;
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

    static <T> UniRunnable1<T> of(AsyncRunnable1<T> func) {

        return new UniRunnable1<>() {

            @Override
            public boolean isAsync() {
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
}
