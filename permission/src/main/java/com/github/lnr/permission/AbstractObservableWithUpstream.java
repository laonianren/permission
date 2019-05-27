package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> {
    final ObservableSource<T> source;

    AbstractObservableWithUpstream(ObservableSource<T> source) {
        this.source = source;
    }

}
