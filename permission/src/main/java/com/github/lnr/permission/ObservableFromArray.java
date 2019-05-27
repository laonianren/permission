package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
class ObservableFromArray<T> extends Observable<T> {

    private final T[] array;

    ObservableFromArray(T[] array) {
        this.array = array;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        for (T t : array) {
            observer.onNext(t);
        }
    }
}
