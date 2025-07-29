package com.github.romanqed.jfunc;

import java.util.function.Function;

/**
 * Utility class containing methods that allow wrapping function calls with checked exceptions.
 */
public final class Exceptions {
    private Exceptions() {
    }

    /**
     * Rethrows the given {@link Throwable} as an unchecked exception.
     *
     * @param throwable the throwable to rethrow
     * @param <T> a generic type representing the throwable
     * @throws T always throws the provided throwable
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAny(Throwable throwable) throws T {
        throw (T) throwable;
    }

    /**
     * Executes the given {@link Runnable0}, rethrowing any thrown exception as-is.
     *
     * @param func the operation to execute
     */
    public static void silent(Runnable0 func) {
        try {
            func.run();
        } catch (Throwable e) {
            throwAny(e);
        }
    }

    /**
     * Executes the given {@link Runnable1}, rethrowing any thrown exception as-is.
     *
     * @param func the operation to execute
     * @param t the input parameter
     * @param <T> the input type
     */
    public static <T> void silent(Runnable1<T> func, T t) {
        try {
            func.run(t);
        } catch (Throwable e) {
            throwAny(e);
        }
    }

    /**
     * Executes the given {@link Runnable2}, rethrowing any thrown exception as-is.
     *
     * @param func the operation to execute
     * @param t1 the first input
     * @param t2 the second input
     * @param <T1> type of the first input
     * @param <T2> type of the second input
     */
    public static <T1, T2> void silent(Runnable2<T1, T2> func, T1 t1, T2 t2) {
        try {
            func.run(t1, t2);
        } catch (Throwable e) {
            throwAny(e);
        }
    }

    /**
     * Executes the given {@link Function0}, rethrowing any thrown exception as-is.
     *
     * @param func the function to execute
     * @param <R> return type of the function
     * @return the result of the function
     */
    public static <R> R silent(Function0<R> func) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            throwAny(e);
            // Stub-return to suppress javac error, cannot be reached
            return null;
        }
    }

    /**
     * Executes the given {@link Function1}, rethrowing any thrown exception as-is.
     *
     * @param func the function to execute
     * @param t the input parameter
     * @param <T> type of input
     * @param <R> type of result
     * @return the result of the function
     */
    public static <T, R> R silent(Function1<T, R> func, T t) {
        try {
            return func.invoke(t);
        } catch (Throwable e) {
            throwAny(e);
            // Stub-return to suppress javac error, cannot be reached
            return null;
        }
    }

    /**
     * Executes the given {@link Function2}, rethrowing any thrown exception as-is.
     *
     * @param func the function to execute
     * @param t1 first input
     * @param t2 second input
     * @param <T1> type of first input
     * @param <T2> type of second input
     * @param <R> result type
     * @return the result of the function
     */
    public static <T1, T2, R> R silent(Function2<T1, T2, R> func, T1 t1, T2 t2) {
        try {
            return func.invoke(t1, t2);
        } catch (Throwable e) {
            throwAny(e);
            // Stub-return to suppress javac error, cannot be reached
            return null;
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func the target function to be called
     * @throws RuntimeException if function throws {@link Throwable} exception
     */
    public static void suppress(Runnable0 func) {
        try {
            func.run();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given {@link Runnable1}, wrapping any thrown checked exception in a {@link RuntimeException}.
     *
     * @param func the operation to execute
     * @param t the input parameter
     * @param <T> the input type
     * @throws RuntimeException if an exception is thrown during execution
     */
    public static <T> void suppress(Runnable1<T> func, T t) {
        try {
            func.run(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given {@link Runnable2}, wrapping any thrown checked exception in a {@link RuntimeException}.
     *
     * @param func the operation to execute
     * @param t1 the first input
     * @param t2 the second input
     * @param <T1> the type of the first input
     * @param <T2> the type of the second input
     * @throws RuntimeException if an exception is thrown during execution
     */
    public static <T1, T2> void suppress(Runnable2<T1, T2> func, T1 t1, T2 t2) {
        try {
            func.run(t1, t2);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func   the target function to be called
     * @param except handler for caught exception
     * @param <T>    type of {@link Function0} return value
     * @return the resulting value produced by the target function
     */
    public static <T> T suppress(Function0<T> func, Function<Throwable, T> except) {
        try {
            return func.invoke();
        } catch (Throwable e) {
            return except.apply(e);
        }
    }

    /**
     * Executes the given {@link Function1} and handles any thrown exception using the provided fallback function.
     *
     * @param func the function to execute
     * @param t the input parameter
     * @param except the fallback function to handle thrown exceptions
     * @param <T> the input type
     * @param <R> the result type
     * @return the result of the function, or the result of {@code except} if an exception occurs
     */
    public static <T, R> R suppress(Function1<T, R> func, T t, Function<Throwable, R> except) {
        try {
            return func.invoke(t);
        } catch (Throwable e) {
            return except.apply(e);
        }
    }

    /**
     * Executes the given {@link Function2} and handles any thrown exception using the provided fallback function.
     *
     * @param func the function to execute
     * @param t1 the first input
     * @param t2 the second input
     * @param except the fallback function to handle thrown exceptions
     * @param <T1> the type of the first input
     * @param <T2> the type of the second input
     * @param <R> the result type
     * @return the result of the function, or the result of {@code except} if an exception occurs
     */
    public static <T1, T2, R> R suppress(Function2<T1, T2, R> func, T1 t1, T2 t2, Function<Throwable, R> except) {
        try {
            return func.invoke(t1, t2);
        } catch (Throwable e) {
            return except.apply(e);
        }
    }

    /**
     * Calls the passed function, catching exceptions if they are thrown.
     *
     * @param func the target function to be called
     * @param <T>  type of {@link Function0} return value
     * @return the resulting value produced by the target function
     * @throws RuntimeException if function throws {@link Throwable} exception
     */
    public static <T> T suppress(Function0<T> func) {
        try {
            return func.invoke();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given {@link Function1}, wrapping any thrown checked exception in a {@link RuntimeException}.
     *
     * @param func the function to execute
     * @param t the input parameter
     * @param <T> the input type
     * @param <R> the result type
     * @return the result of the function
     * @throws RuntimeException if an exception is thrown during execution
     */
    public static <T, R> R suppress(Function1<T, R> func, T t) {
        try {
            return func.invoke(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given {@link Function2}, wrapping any thrown checked exception in a {@link RuntimeException}.
     *
     * @param func the function to execute
     * @param t1 the first input
     * @param t2 the second input
     * @param <T1> the type of the first input
     * @param <T2> the type of the second input
     * @param <R> the result type
     * @return the result of the function
     * @throws RuntimeException if an exception is thrown during execution
     */
    public static <T1, T2, R> R suppress(Function2<T1, T2, R> func, T1 t1, T2 t2) {
        try {
            return func.invoke(t1, t2);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
