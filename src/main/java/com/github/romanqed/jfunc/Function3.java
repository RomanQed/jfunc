package com.github.romanqed.jfunc;

public interface Function3<T1, T2, T3, R> {
    R invoke(T1 t1, T2 t2, T3 t3) throws Throwable;
}
