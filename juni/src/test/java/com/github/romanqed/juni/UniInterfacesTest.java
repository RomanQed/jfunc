package com.github.romanqed.juni;

import com.github.romanqed.jfunc.*;
import com.github.romanqed.jsync.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public final class UniInterfacesTest {

    // UniFunction0

    @Test
    void uniFunction0OfSyncFunctionWorksCorrectly() throws Throwable {
        Function0<String> syncFunc = () -> "sync";
        var uni = UniFunction0.of(syncFunc);

        assertFalse(uni.isAsync());
        assertEquals("sync", uni.invoke());
        assertEquals("sync", uni.invokeAsync().get());
    }

    @Test
    void uniFunction0OfAsyncFunctionWorksCorrectly() throws Throwable {
        AsyncFunction0<String> asyncFunc = () -> CompletableFuture.completedFuture("async");
        var uni = UniFunction0.of(asyncFunc);

        assertTrue(uni.isAsync());
        assertEquals("async", uni.invoke());
        assertEquals("async", uni.invokeAsync().get());
    }

    // UniFunction1

    @Test
    void uniFunction1OfSyncFunctionWorksCorrectly() throws Throwable {
        Function1<Integer, String> syncFunc = Object::toString;
        var uni = UniFunction1.of(syncFunc);

        assertFalse(uni.isAsync());
        assertEquals("123", uni.invoke(123));
        assertEquals("123", uni.invokeAsync(123).get());
    }

    @Test
    void uniFunction1OfAsyncFunctionWorksCorrectly() throws Throwable {
        AsyncFunction1<Integer, String> asyncFunc = t -> CompletableFuture.completedFuture(t.toString());
        var uni = UniFunction1.of(asyncFunc);

        assertTrue(uni.isAsync());
        assertEquals("456", uni.invoke(456));
        assertEquals("456", uni.invokeAsync(456).get());
    }

    // UniFunction2

    @Test
    void uniFunction2OfSyncFunctionWorksCorrectly() throws Throwable {
        Function2<Integer, Integer, Integer> syncFunc = Integer::sum;
        var uni = UniFunction2.of(syncFunc);

        assertFalse(uni.isAsync());
        assertEquals(7, uni.invoke(3, 4));
        assertEquals(7, uni.invokeAsync(3, 4).get());
    }

    @Test
    void uniFunction2OfAsyncFunctionWorksCorrectly() throws Throwable {
        AsyncFunction2<Integer, Integer, Integer> asyncFunc = (a, b) -> CompletableFuture.completedFuture(a + b);
        var uni = UniFunction2.of(asyncFunc);

        assertTrue(uni.isAsync());
        assertEquals(10, uni.invoke(4, 6));
        assertEquals(10, uni.invokeAsync(4, 6).get());
    }

    // UniRunnable0

    @Test
    void uniRunnable0OfSyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        Runnable0 sync = () -> flag.set(true);

        var uni = UniRunnable0.of(sync);

        assertFalse(uni.isAsync());
        uni.run();
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync().get();
        assertTrue(flag.get());
    }

    @Test
    void uniRunnable0OfAsyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        AsyncRunnable0 async = () -> {
            flag.set(true);
            return CompletableFuture.completedFuture(null);
        };

        var uni = UniRunnable0.of(async);

        assertTrue(uni.isAsync());
        uni.run();
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync().get();
        assertTrue(flag.get());
    }

    // UniRunnable1

    @Test
    void uniRunnable1OfSyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        Runnable1<String> sync = s -> flag.set(true);

        var uni = UniRunnable1.of(sync);

        assertFalse(uni.isAsync());
        uni.run("test");
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync("test").get();
        assertTrue(flag.get());
    }

    @Test
    void uniRunnable1OfAsyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        AsyncRunnable1<String> async = s -> {
            flag.set(true);
            return CompletableFuture.completedFuture(null);
        };

        var uni = UniRunnable1.of(async);

        assertTrue(uni.isAsync());
        uni.run("test");
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync("test").get();
        assertTrue(flag.get());
    }

    // UniRunnable2

    @Test
    void uniRunnable2OfSyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        Runnable2<Integer, Integer> sync = (a, b) -> flag.set(true);

        var uni = UniRunnable2.of(sync);

        assertFalse(uni.isAsync());
        uni.run(1, 2);
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync(1, 2).get();
        assertTrue(flag.get());
    }

    @Test
    void uniRunnable2OfAsyncRunnableWorksCorrectly() throws Throwable {
        var flag = new AtomicBoolean(false);
        AsyncRunnable2<Integer, Integer> async = (a, b) -> {
            flag.set(true);
            return CompletableFuture.completedFuture(null);
        };

        var uni = UniRunnable2.of(async);

        assertTrue(uni.isAsync());
        uni.run(1, 2);
        assertTrue(flag.get());

        flag.set(false);
        uni.runAsync(1, 2).get();
        assertTrue(flag.get());
    }

    // Exception propagation tests

    @Test
    void uniRunnable0RunExceptionPropagation() {
        Runnable0 throwing = () -> {
            throw new RuntimeException("fail");
        };
        var uni = UniRunnable0.of(throwing);

        var ex = assertThrows(Throwable.class, uni::run);
        assertEquals("fail", ex.getMessage());

        var e = assertThrows(ExecutionException.class, () -> uni.runAsync().get());
        assertEquals("fail", e.getCause().getMessage());
    }

    @Test
    void uniFunction0InvokeExceptionPropagation() {
        Function0<String> throwing = () -> {
            throw new IllegalStateException("error");
        };
        var uni = UniFunction0.of(throwing);

        var ex = assertThrows(Throwable.class, uni::invoke);
        assertEquals("error", ex.getMessage());

        var e = assertThrows(ExecutionException.class, () -> uni.invokeAsync().get());
        assertEquals("error", e.getCause().getMessage());
    }
}
