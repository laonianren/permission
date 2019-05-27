package com.github.lnr.permission.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.lnr.permission.Permission;
import com.github.lnr.permission.PermissionCallback;
import com.github.lnr.permission.PermissionManager;
import com.github.lnr.permission.PermissionsRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionManager = new PermissionManager(new PermissionsRequest(this));
    }

    public void request(View view) {
        String[] permission = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        mPermissionManager.permission(permission)
                .request(new PermissionCallback() {
                    @Override
                    public void onAllGranted() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("申请权限")
                                .setMessage("方法调用：onAllGranted()\n所有权限申请已允许")
                                .show();
                    }

                    @Override
                    public void onCanRetryAsk(List<Permission> permissions) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("申请权限")
                                .setMessage("方法调用：onCanRetryAsk()\n部分权限未允许，但可以重新申请\n" + list2String("canRetryList", permissions))
                                .show();
                    }

                    @Override
                    public void onNeverAsk(List<Permission> canRetryList, List<Permission> neverAskList) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("申请权限")
                                .setMessage("方法调用：onNeverAsk()\n部分权限未允许，点击了不在弹出\n" + list2String("canRetryList", canRetryList) + list2String("neverAskList", neverAskList))
                                .show();
                    }
                });
    }

    private String list2String(String name, List<Permission> permissions) {
       if(permissions.isEmpty()) {
           return name + " list is empty\n";
       }

       StringBuilder builder = new StringBuilder(name);
       builder.append("\n").append("[\n");
       for (Permission permission : permissions) {
           builder.append(permission.name).append("\n");
       }
        builder.append("]\n");
       return builder.toString();
    }
}
