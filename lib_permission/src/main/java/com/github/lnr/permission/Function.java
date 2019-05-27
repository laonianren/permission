package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
interface Function<T, R> {

    R apply(T t);
}
