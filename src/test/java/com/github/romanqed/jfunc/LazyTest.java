package com.github.romanqed.jfunc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public final class LazyTest extends Assertions {

    @Test
    public void testFunction() {
        var func = new LazyFunction<Integer, Integer>(e -> e + 1);
        assertEquals(1, func.apply(0));
        assertEquals(1, func.apply(0));
    }

    @Test
    public void testFunction0() throws Throwable {
        var func = new LazyFunction0<>(() -> ThreadLocalRandom.current().nextInt());
        var value = func.invoke();
        assertEquals(value, func.invoke());
    }

    @Test
    public void testFunction1() throws Throwable {
        var func = new LazyFunction1<Integer, Integer>(e -> e + 1);
        assertEquals(1, func.invoke(0));
        assertEquals(1, func.invoke(0));
    }

    @Test
    public void testSupplier() {
        var func = new LazySupplier<>(() -> ThreadLocalRandom.current().nextInt());
        var value = func.get();
        assertEquals(value, func.get());
    }
}
