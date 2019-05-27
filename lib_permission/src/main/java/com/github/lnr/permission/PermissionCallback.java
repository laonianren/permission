package com.github.lnr.permission;

import java.util.List;

/**
 * @author : lnr
 * @date 2019/5/22
 * description : 权限回调
 */
public interface PermissionCallback {

    /**
     * 全部权限允许
     */
    void onAllGranted();

    /**
     * 可以重新申请权限
     * @param permissions 需要重新申请的权限
     */
    void onCanRetryAsk(List<Permission> permissions);

    /**
     * 同意了不在弹出授权框时回调(当同时申请多个权限时，只要有一个点了此选项，在会回调此方法)
     * @param canRetryList 可以重新申请的权限
     * @param neverAskList 不在提示的权限
     */
    void onNeverAsk(List<Permission> canRetryList, List<Permission> neverAskList);
}
