package com.github.romanqed.jfunc;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Utility class containing methods that allow wrapping function calls with checked exceptions.
 */
public final class Exceptions {
    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func   the target function to be called
     * @param except handler for caught exception
     */
    public static void suppress(Function0 func, Consumer<Throwable> except) {
        try {
            func.run();
        } catch (Throwable e) {
            except.accept(e);
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func the target function to be called
     * @throws RuntimeException if function throws {@link Throwable} exception
     */
    public static void suppress(Function0 func) {
        try {
            func.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func   the target function to be called
     * @param except handler for caught exception
     * @param <T>    type of {@link Invokable0} return value
     * @return the resulting value produced by the target function
     */
    public static <T> T suppress(Invokable0<T> func, Function<Throwable, T> except) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            return except.apply(e);
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func the target function to be called
     * @param def  default value that will be returned if the target function throws an exception
     * @param <T>  type of {@link Invokable0} return value
     * @return the resulting value produced by the target function
     */
    public static <T> T suppress(Invokable0<T> func, T def) {
        return Exceptions.suppress(func, (Function<Throwable, T>) e -> def);
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func the target function to be called
     * @param <T>  type of {@link Invokable0} return value
     * @return the resulting value produced by the target function
     * @throws RuntimeException if function throws {@link Throwable} exception
     */
    public static <T> T suppress(Invokable0<T> func) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
