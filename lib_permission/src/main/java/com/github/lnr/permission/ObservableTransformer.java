package com.github.lnr.permission;

import android.support.annotation.NonNull;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
interface ObservableTransformer<Upstream, Downstream> {

    ObservableSource<Downstream> apply(@NonNull Observable<Upstream> upstream);
}
