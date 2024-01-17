package com.github.romanqed.jfunc;

/**
 * Represents a function that takes three parameters and returns a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #invoke(Object, Object, Object)}.
 *
 * @param <T1> the type of the first function parameter
 * @param <T2> the type of the second function parameter
 * @param <T3> the type of the third function parameter
 * @param <R>  the type of the return value
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R> {

    /**
     * Main functional method of interface, takes one parameter, performs assumed action and produce result.
     *
     * @param t1 first function parameter
     * @param t2 second function parameter
     * @param t3 third function parameter
     * @return produced result
     * @throws Throwable if problems occur during execution
     */
    R invoke(T1 t1, T2 t2, T3 t3) throws Throwable;
}
