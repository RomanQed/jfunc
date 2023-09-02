package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * Represents a function that accepts a single parameter and does not return a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #run(Object)}.
 *
 * @param <T> the type of the function parameter
 */
@FunctionalInterface
public interface Function1<T> {

    /**
     * Creates a combined {@link Function1} containing the calls of
     * the passed interfaces inside in the specified order.
     *
     * @param first  the function that will be executed first, must be non-null
     * @param second the function that will be executed second, must be non-null
     * @return a composed {@link Function1}
     * @throws NullPointerException if first or second function is null
     */
    static <T> Function1<T> combine(Function1<T> first, Function1<T> second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        return (value) -> {
            first.run(value);
            second.run(value);
        };
    }

    /**
     * Main functional method of interface, takes one parameter and performs assumed action.
     *
     * @param t function parameter
     */
    void run(T t) throws Throwable;

    /**
     * Creates a combined {@link Function1} containing first a call to this function,
     * and then a call to the specified function.
     *
     * @param func the function that will be executed after this function
     * @return a composed {@link Function1}
     * @throws NullPointerException if passed function is null
     */
    default Function1<T> andThen(Function1<T> func) {
        return Function1.combine(this, func);
    }
}
