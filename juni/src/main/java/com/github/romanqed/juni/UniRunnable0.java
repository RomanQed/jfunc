package com.github.romanqed.juni;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;
import com.github.romanqed.jsync.AsyncRunnable0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface UniRunnable0 extends Uni, Runnable0, AsyncRunnable0 {

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
