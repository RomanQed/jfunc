package com.github.romanqed.jfunc;

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
     * Main functional method of interface, takes two parameters and performs assumed action.
     *
     * @param t1 first function parameter
     * @param t2 second function parameter
     * @throws Throwable if problems occur during execution
     */
    void run(T1 t1, T2 t2) throws Throwable;
}
