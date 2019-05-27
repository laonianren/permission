package com.github.lnr.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
class ObservableBuffer<T> extends AbstractObservableWithUpstream<T, List<T>> {

    private int count;

    ObservableBuffer(ObservableSource<T> source, int count) {
        super(source);
        this.count = count;
    }

    @Override
    public void subscribeActual(Observer<? super List<T>> observer) {
        source.subscribe(new BufferObserver<>(observer, count));
    }

    private static class BufferObserver<T> implements Observer<T> {

        private final Observer<? super List<T>> downstream;
        private final int count;
        private List<T> buffer;
        private int size;

        private BufferObserver(Observer<? super List<T>> downstream, int count) {
            this.downstream = downstream;
            this.count = count;
            this.buffer = new ArrayList<>();
        }

        @Override
        public void onNext(T t) {
            List<T> b = buffer;
            if (b != null) {
                b.add(t);

                if (++size >= count) {
                    downstream.onNext(b);
                    size = 0;
                    buffer = new ArrayList<>();
                }
            }
        }

        @Override
        public void onError(Throwable ex) {
            downstream.onError(ex);
        }
    }
}
