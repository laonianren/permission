package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
class PublishSubject<T> extends Observable<T> implements Observer<T> {

    private Observer<? super T> observer;

    static <T> PublishSubject<T> create() {
        return new PublishSubject<>();
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        this.observer = observer;
    }

    @Override
    public void onNext(T t) {
        observer.onNext(t);
    }

    @Override
    public void onError(Throwable ex) {
        observer.onError(ex);
    }
}
