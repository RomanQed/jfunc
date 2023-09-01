package com.github.romanqed.jfunc;

/**
 *
 */
@FunctionalInterface
public interface Function0 {

    /**
     * @param first
     * @param second
     * @return
     */
    static Function0 combine(Function0 first, Function0 second) {
        return () -> {
            first.run();
            second.run();
        };
    }

    /**
     * @throws Throwable
     */
    void run() throws Throwable;

    /**
     * @param func
     * @return
     */
    default Function0 andThen(Function0 func) {
        return Function0.combine(this, func);
    }
}
