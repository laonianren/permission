package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
interface Observer<T> {

    void onNext(T t);

    void onError(Throwable ex);
}
