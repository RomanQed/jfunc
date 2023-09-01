package com.github.romanqed.jfunc;

/**
 * @param <T>
 */
@FunctionalInterface
public interface Function1<T> {

    /**
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    static <T> Function1<T> combine(Function1<T> first, Function1<T> second) {
        return (value) -> {
            first.run(value);
            second.run(value);
        };
    }

    /**
     * @param t
     */
    void run(T t) throws Throwable;

    /**
     * @param func
     * @return
     */
    default Function1<T> andThen(Function1<T> func) {
        return Function1.combine(this, func);
    }
}
