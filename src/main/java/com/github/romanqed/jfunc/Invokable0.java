package com.github.romanqed.jfunc;

/**
 * Represents a function that does not accept parameters and returns a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #invoke()}.
 *
 * @param <T> the type of the return value
 */
@FunctionalInterface
public interface Invokable0<T> {

    /**
     * Main functional method of interface, performs assumed action and produce result.
     *
     * @return produced result
     * @throws Throwable if problems occur during execution
     */
    T invoke() throws Throwable;
}
