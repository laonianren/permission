package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
class ObservableEmpty<T> extends Observable<T> {

    @Override
    public void subscribeActual(Observer<? super T> observer) {

    }
}
