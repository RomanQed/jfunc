package com.github.romanqed.jfunc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public final class SuppressTest extends Assertions {
    @Test
    public void testRunnable0Suppress() {
        Runnable0 func = () -> {
            throw new IOException();
        };
        assertAll(
                () -> Exceptions.suppress(func, e -> assertEquals(IOException.class, e.getClass())),
                () -> {
                    RuntimeException caught = null;
                    try {
                        Exceptions.suppress(func);
                    } catch (RuntimeException e) {
                        caught = e;
                    }
                    assertNotNull(caught);
                    assertEquals(IOException.class, caught.getCause().getClass());
                }
        );
    }

    @Test
    public void testFunction0Suppress() {
        Function0<Integer> func = () -> {
            throw new IOException();
        };
        assertAll(
                () -> assertEquals(0, Exceptions.suppress(func, e -> {
                    assertEquals(IOException.class, e.getClass());
                    return 0;
                })),
                () -> {
                    RuntimeException caught = null;
                    try {
                        Exceptions.suppress(func);
                    } catch (RuntimeException e) {
                        caught = e;
                    }
                    assertNotNull(caught);
                    assertEquals(IOException.class, caught.getCause().getClass());
                }
        );
    }
}
