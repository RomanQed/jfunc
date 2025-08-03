package com.github.romanqed.jfunc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public final class ExceptionsTest {

    @Test
    void testThrowAny() {
        IOException ex = new IOException("test");
        IOException thrown = assertThrows(IOException.class, () -> Exceptions.throwAny(ex));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentRunnable0() {
        AtomicBoolean called = new AtomicBoolean(false);
        Exceptions.silent(() -> called.set(true));
        assertTrue(called.get());

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () -> Exceptions.silent(() -> {
            throw ex;
        }));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentRunnable1() {
        AtomicReference<String> value = new AtomicReference<>();
        Exceptions.silent(value::set, "test");
        assertEquals("test", value.get());

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () ->
                Exceptions.silent((String s) -> {
                    throw ex;
                }, "test"));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentRunnable2() {
        AtomicReference<String> result = new AtomicReference<>();
        Exceptions.silent((String a, String b) -> result.set(a + b), "a", "b");
        assertEquals("ab", result.get());

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () ->
                Exceptions.silent((Runnable2<String, String>) (a, b) -> {
                    throw ex;
                }, "x", "y"));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentFunction0() {
        assertEquals("42", Exceptions.silent(() -> "42"));

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () ->
                Exceptions.silent(() -> {
                    throw ex;
                }));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentFunction1() {
        assertEquals("TEST", Exceptions.silent((String s) -> s.toUpperCase(), "test"));

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () ->
                Exceptions.silent((String s) -> {
                    throw ex;
                }, "test"));
        assertSame(ex, thrown);
    }

    @Test
    void testSilentFunction2() {
        assertEquals(4, Exceptions.silent((String s, String o) -> s.indexOf(o), "hello", "o"));

        IOException ex = new IOException("fail");
        IOException thrown = assertThrows(IOException.class, () ->
                Exceptions.silent((Runnable2<String, String>) (a, b) -> {
                    throw ex;
                }, "a", "b"));
        assertSame(ex, thrown);
    }

    @Test
    void testSuppressRunnable0() {
        assertDoesNotThrow(() -> Exceptions.suppress(() -> {
        }));

        RuntimeException rex = new RuntimeException("runtime");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> Exceptions.suppress(() -> {
            throw rex;
        }));
        assertSame(rex, thrown);

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () -> Exceptions.suppress(() -> {
            throw ex;
        }));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressRunnable1() {
        AtomicBoolean flag = new AtomicBoolean(false);
        Exceptions.suppress(flag::set, true);
        assertTrue(flag.get());

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () ->
                Exceptions.suppress((Runnable1<Boolean>) (b) -> {
                    throw ex;
                }, false));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressRunnable2() {
        AtomicReference<String> out = new AtomicReference<>();
        Exceptions.suppress((Runnable2<String, String>) (a, b) -> out.set(a + b), "a", "b");
        assertEquals("ab", out.get());

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () ->
                Exceptions.suppress((Runnable2<String, String>) (a, b) -> {
                    throw ex;
                }, "1", "2"));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressFunction0() {
        assertEquals(123, Exceptions.suppress(() -> 123));

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () -> Exceptions.suppress(() -> {
            throw ex;
        }));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressFunction1() {
        assertEquals("ABC", Exceptions.suppress((String s) -> s.toUpperCase(), "abc"));

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () ->
                Exceptions.suppress((Runnable1<String>) (s) -> {
                    throw ex;
                }, "q"));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressFunction2() {
        assertEquals(2, Exceptions.suppress((String s, String o) -> s.indexOf(o), "abc", "c"));

        IOException ex = new IOException("fail");
        RuntimeException wrapped = assertThrows(RuntimeException.class, () ->
                Exceptions.suppress((Runnable2<String, String>) (a, b) -> {
                    throw ex;
                }, "a", "b"));
        assertEquals(ex, wrapped.getCause());
    }

    @Test
    void testSuppressWithHandlerFunction0() {
        assertEquals("ok", Exceptions.suppress(() -> "ok", ex -> "fallback"));
        assertEquals("fallback", Exceptions.suppress(() -> {
            throw new IOException();
        }, ex -> "fallback"));
    }

    @Test
    void testSuppressWithHandlerFunction1() {
        assertEquals("x", Exceptions.suppress((s) -> s, "x", ex -> "fallback"));
        assertEquals("fallback", Exceptions.suppress((s) -> {
            throw new IOException();
        }, "x", ex -> "fallback"));
    }

    @Test
    void testSuppressWithHandlerFunction2() {
        assertEquals("xy", Exceptions.suppress((a, b) -> a + b, "x", "y", ex -> "fallback"));
        assertEquals("fallback", Exceptions.suppress((a, b) -> {
            throw new IOException();
        }, "x", "y", ex -> "fallback"));
    }
}
