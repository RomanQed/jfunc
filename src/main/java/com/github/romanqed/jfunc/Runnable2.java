package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * Represents a function that accepts two parameters and does not return a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #run(Object, Object)}.
 *
 * @param <T1> the type of the first function parameter
 * @param <T2> the type of the second function parameter
 */
@FunctionalInterface
public interface Runnable2<T1, T2> {

    /**
     * Returns a composed {@link Runnable2} that performs this operation followed by the {@code second}.
     *
     * @param first the first operation to apply
     * @param second the second operation to apply after the first
     * @param <T1> the type of the first input
     * @param <T2> the type of the second input
     * @return the composed {@link Runnable2}
     * @throws NullPointerException if either argument is null
     */
    static <T1, T2> Runnable2<T1, T2> combine(Runnable2<T1, T2> first, Runnable2<T1, T2> second) {
        return first.andThen(second);
    }

    /**
     * Main functional method of interface, takes two parameters and performs assumed action.
     *
     * @param t1 first function parameter
     * @param t2 second function parameter
     * @throws Throwable if problems occur during execution
     */
    void run(T1 t1, T2 t2) throws Throwable;

    /**
     * Returns a composed {@link Runnable2} that performs this operation followed by the given one.
     *
     * @param func the operation to perform after this one
     * @return a composed {@link Runnable2}
     * @throws NullPointerException if {@code func} is null
     */
    default Runnable2<T1, T2> andThen(Runnable2<T1, T2> func) {
        Objects.requireNonNull(func);
        return (t1, t2) -> {
            run(t1, t2);
            func.run(t1, t2);
        };
    }
}
