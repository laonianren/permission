package com.github.lnr.permission;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
abstract class Observable<T> implements ObservableSource<T> {

    @Override
    public void subscribe(@NonNull Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    static <T> Observable<T> empty() {
        return new ObservableEmpty<>();
    }

    static <T> Observable<T> error() {
        return new ObservableError<>();
    }

    static <T> Observable<T> just(T t) {
        return new ObservableJust<>(t);
    }

    static final class ObservableJust<T> extends Observable<T> {

        private final T value;
        ObservableJust(final T value) {
            this.value = value;
        }

        @Override
        public void subscribeActual(Observer<? super T> observer) {
            observer.onNext(value);
        }
    }

    static final class ObservableFromSource<T> extends Observable<T> {
        final ObservableSource<T> source;

        ObservableFromSource(ObservableSource<T> source) {
            this.source = source;
        }

        @Override
        public void subscribeActual(Observer<? super T> observer) {
            source.subscribe(observer);
        }
    }

    <R> Observable<R> compose(ObservableTransformer<T, R> transformer) {
        return new ObservableFromSource<>(transformer.apply(this));
    }

    @SafeVarargs
    static <T> Observable<T> fromArray(T... items) {
        return new ObservableFromArray<>(items);
    }

    final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return new ObservableFlatMap<>(this, mapper);
    }

    static <T> Observable<T> flatMap(ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return new ObservableFlatMap<>(sources, new Function<ObservableSource<? extends T>, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(ObservableSource<? extends T> observableSource) {
                return observableSource;
            }
        });
    }

    static <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
        return fromArray(source1, source2).flatMap(new Function<ObservableSource<? extends T>, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(ObservableSource<? extends T> observableSource) {
                return observableSource;
            }
        });
    }

    final Observable<List<T>> buffer(int count) {
        return new ObservableBuffer<>(this, count);
    }


}
