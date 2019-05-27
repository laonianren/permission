package com.github.lnr.permission;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
class ObservableFlatMap<T, U> extends AbstractObservableWithUpstream<T, U> {

    private final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

    ObservableFlatMap(ObservableSource<T> source,
                             Function<? super T, ? extends ObservableSource<? extends U>> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public void subscribeActual(Observer<? super U> observer) {
        source.subscribe(new FlatMapObservable<>(observer, mapper));
    }

    private static class FlatMapObservable<T, U> implements Observer<T> {

        private final Observer<? super U> downstream;
        private final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

        FlatMapObservable(Observer<? super U> downstream, Function<? super T, ? extends ObservableSource<? extends U>> mapper) {
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            mapper.apply(t).subscribe(downstream);
        }

        @Override
        public void onError(Throwable ex) {
            downstream.onError(ex);
        }
    }
}
