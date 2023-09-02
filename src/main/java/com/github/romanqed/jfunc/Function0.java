package com.github.romanqed.jfunc;

import java.util.Objects;

/**
 * Represents a simple function that does not take parameters and does not return a value.
 *
 * <p>This is a
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">functional interface</a>
 * whose functional method is {@link #run()}.
 */
@FunctionalInterface
public interface Function0 {

    /**
     * Creates a combined {@link Function0} containing the calls of
     * the passed interfaces inside in the specified order.
     *
     * @param first  the function that will be executed first, must be non-null
     * @param second the function that will be executed second, must be non-null
     * @return a composed {@link Function0}
     * @throws NullPointerException if first or second function is null
     */
    static Function0 combine(Function0 first, Function0 second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        return () -> {
            first.run();
            second.run();
        };
    }

    /**
     * Main functional method of interface, performs assumed action.
     *
     * @throws Throwable if problems occur during execution
     */
    void run() throws Throwable;

    /**
     * Creates a combined {@link Function0} containing first a call to this function,
     * and then a call to the specified function.
     *
     * @param func the function that will be executed after this function
     * @return a composed {@link Function0}
     * @throws NullPointerException if passed function is null
     */
    default Function0 andThen(Function0 func) {
        return Function0.combine(this, func);
    }
}
