package com.github.lnr.permission;

import android.support.annotation.NonNull;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
interface ObservableSource<T> {

    void subscribe(@NonNull Observer<? super T> observer);

}
