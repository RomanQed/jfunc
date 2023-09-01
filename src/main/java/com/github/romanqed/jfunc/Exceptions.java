package com.github.romanqed.jfunc;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Exceptions {
    /**
     * @param func
     * @param except
     */
    public static void suppress(Function0 func, Consumer<Throwable> except) {
        try {
            func.run();
        } catch (Throwable e) {
            except.accept(e);
        }
    }

    /**
     *
     * @param func
     */
    public static void suppress(Function0 func) {
        try {
            func.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param func
     * @param except
     * @param <T>
     * @return
     */
    public static <T> T suppress(Invokable0<T> func, Function<Throwable, T> except) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            return except.apply(e);
        }
    }

    /**
     * @param func
     * @param def
     * @param <T>
     * @return
     */
    public static <T> T suppress(Invokable0<T> func, T def) {
        return Exceptions.suppress(func, (Function<Throwable, T>) e -> def);
    }

    /**
     * @param func
     * @param <T>
     * @return
     */
    public static <T> T suppress(Invokable0<T> func) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
