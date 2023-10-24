package com.github.romanqed.jfunc;

public interface Function2<T1, T2, R> {
    R invoke(T1 t1, T2 t2) throws Throwable;
}
