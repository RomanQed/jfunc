package com.github.romanqed.jfut;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AsyncInterfacesTest {

    // AsyncFunction0

    @Test
    void asyncFunction0InvokeAsyncReturnsValue() throws Exception {
        AsyncFunction0<String> f = () -> CompletableFuture.completedFuture("test");
        assertEquals("test", f.invokeAsync().get());
    }

    // AsyncFunction1

    @Test
    void asyncFunction1AndThenComposesSequentially() throws Exception {
        AsyncFunction1<Integer, Integer> f1 = t -> CompletableFuture.completedFuture(t + 2);
        AsyncFunction1<Integer, Integer> f2 = t -> CompletableFuture.completedFuture(t * 3);
        AsyncFunction1<Integer, Integer> combined = f1.andThen(f2);

        assertEquals(15, combined.invokeAsync(3).get().intValue()); // (3 + 2) * 3
    }

    @Test
    void asyncFunction1ComposeComposesSequentially() throws Exception {
        AsyncFunction1<Integer, Integer> f1 = t -> CompletableFuture.completedFuture(t + 2);
        AsyncFunction1<Integer, Integer> f2 = t -> CompletableFuture.completedFuture(t * 3);
        AsyncFunction1<Integer, Integer> composed = f1.compose(f2);

        assertEquals(11, composed.invokeAsync(3).get().intValue()); // (3 * 3) + 2
    }

    @Test
    void asyncFunction1IdentityReturnsInput() throws Exception {
        AsyncFunction1<String, String> identity = AsyncFunction1.identity();
        assertEquals("hello", identity.invokeAsync("hello").get());
    }

    // AsyncFunction2

    @Test
    void asyncFunction2InvokeAsyncReturnsValue() throws Exception {
        AsyncFunction2<Integer, Integer, Integer> f = (a, b) -> CompletableFuture.completedFuture(a + b);
        assertEquals(7, f.invokeAsync(3, 4).get().intValue());
    }

    // AsyncRunnable0

    @Test
    void asyncRunnable0AndThenCallsBoth() throws Exception {
        AtomicBoolean called1 = new AtomicBoolean(false);
        AtomicBoolean called2 = new AtomicBoolean(false);

        AsyncRunnable0 r1 = () -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable0 r2 = () -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable0 combined = r1.andThen(r2);
        combined.runAsync().get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }

    @Test
    void asyncRunnable0CombineStaticMethod() throws Exception {
        AtomicBoolean called1 = new AtomicBoolean(false);
        AtomicBoolean called2 = new AtomicBoolean(false);

        AsyncRunnable0 r1 = () -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable0 r2 = () -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable0 combined = AsyncRunnable0.combine(r1, r2);
        combined.runAsync().get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }

    // AsyncRunnable1

    @Test
    void asyncRunnable1AndThenCallsBothWithSameArg() throws Exception {
        AtomicBoolean called1 = new AtomicBoolean(false);
        AtomicBoolean called2 = new AtomicBoolean(false);

        AsyncRunnable1<String> r1 = s -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable1<String> r2 = s -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable1<String> combined = r1.andThen(r2);
        combined.runAsync("arg").get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }

    @Test
    void asyncRunnable1CombineStaticMethod() throws Exception {
        AtomicBoolean called1 = new AtomicBoolean(false);
        AtomicBoolean called2 = new AtomicBoolean(false);

        AsyncRunnable1<String> r1 = s -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable1<String> r2 = s -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable1<String> combined = AsyncRunnable1.combine(r1, r2);
        combined.runAsync("arg").get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }

    // AsyncRunnable2

    @Test
    void asyncRunnable2AndThenCallsBothWithSameArgs() throws Exception {
        AtomicBoolean called1 = new AtomicBoolean(false);
        AtomicBoolean called2 = new AtomicBoolean(false);

        AsyncRunnable2<Integer, Integer> r1 = (a, b) -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable2<Integer, Integer> r2 = (a, b) -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable2<Integer, Integer> combined = r1.andThen(r2);
        combined.runAsync(1, 2).get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }

    @Test
    void asyncRunnable2CombineStaticMethod() throws Exception {
        var called1 = new AtomicBoolean(false);
        var called2 = new AtomicBoolean(false);

        AsyncRunnable2<Integer, Integer> r1 = (a, b) -> {
            called1.set(true);
            return CompletableFuture.completedFuture(null);
        };
        AsyncRunnable2<Integer, Integer> r2 = (a, b) -> {
            called2.set(true);
            return CompletableFuture.completedFuture(null);
        };

        AsyncRunnable2<Integer, Integer> combined = AsyncRunnable2.combine(r1, r2);
        combined.runAsync(1, 2).get();

        assertTrue(called1.get());
        assertTrue(called2.get());
    }
}
