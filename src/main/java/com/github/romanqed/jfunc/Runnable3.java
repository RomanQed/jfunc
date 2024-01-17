package com.github.romanqed.jfunc;

/**
 * Represents a function that accepts three parameters and does not return a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #run(Object, Object, Object)}.
 *
 * @param <T1> the type of the first function parameter
 * @param <T2> the type of the second function parameter
 * @param <T3> the type of the third function parameter
 */
@FunctionalInterface
public interface Runnable3<T1, T2, T3> {

    /**
     * Main functional method of interface, takes three parameters and performs assumed action.
     *
     * @param t1 first function parameter
     * @param t2 second function parameter
     * @param t3 third function parameter
     * @throws Throwable if problems occur during execution
     */
    void run(T1 t1, T2 t2, T3 t3) throws Throwable;
}
