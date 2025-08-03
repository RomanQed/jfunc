package com.github.romanqed.jsync;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Function0;
import com.github.romanqed.jfunc.Runnable0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class Futures {
    private Futures() {
    }

    public static CompletableFuture<Void> run(Runnable0 func, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            try {
                func.run();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        }, executor);
    }

    public static CompletableFuture<Void> run(Runnable0 func) {
        return CompletableFuture.runAsync(() -> {
            try {
                func.run();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        });
    }

    public static <T> CompletableFuture<T> provide(Function0<T> func, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return func.invoke();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
                return null;
            }
        }, executor);
    }

    public static <T> CompletableFuture<T> provide(Function0<T> func) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return func.invoke();
            } catch (Throwable e) {
                Exceptions.throwAny(e);
                return null;
            }
        });
    }
}
