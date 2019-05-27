package com.github.lnr.permission;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lnr
 * @date 2019/5/22
 * description :
 */
public class PermissionManager {

    private final PermissionsRequest mRxPermissions;
    private String[] permissions;

    public PermissionManager(PermissionsRequest permissions) {
        this.mRxPermissions = permissions;
    }


    public PermissionManager permission(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    public void request(@NonNull PermissionCallback callback) {
        mRxPermissions.requestEachCombined(permissions).subscribe(new CallBack(callback));
    }

    private class CallBack implements Observer<List<Permission>> {

        final PermissionCallback callback;

        private CallBack(PermissionCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onNext(List<Permission> permissions) {
            List<Permission> retry = new ArrayList<>();
            List<Permission> neverAsk = new ArrayList<>();
            for (Permission p : permissions) {
                if(p.granted) {
                    continue;
                }
                if (p.shouldShowRequestPermissionRationale) {
                    retry.add(p);
                } else {
                    neverAsk.add(p);
                }
            }

            if(!neverAsk.isEmpty()) {
                callback.onNeverAsk(retry, neverAsk);
            }else if(!retry.isEmpty()) {
                callback.onCanRetryAsk(retry);
            }else {
                callback.onAllGranted();
            }
        }

        @Override
        public void onError(Throwable ex) {

        }
    }
}
