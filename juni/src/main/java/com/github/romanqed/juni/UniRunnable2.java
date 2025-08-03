package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable2;
import com.github.romanqed.jsync.AsyncRunnable2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface UniRunnable2<T1, T2> extends Uni, Runnable2<T1, T2>, AsyncRunnable2<T1, T2> {

    static <T1, T2> UniRunnable2<T1, T2> of(Runnable2<T1, T2> func) {
        return new UniRunnable2<>() {

            @Override
            public boolean isSync() {
                return true;
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

    static <T1, T2> UniRunnable2<T1, T2> of(AsyncRunnable2<T1, T2> func) {
        return new UniRunnable2<>() {

            @Override
            public boolean isAsync() {
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
}
