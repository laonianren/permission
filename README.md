# android运行时权限申请框架
基于RxPermissions实现，剔除Rx依赖，功能不变。[参见https://github.com/tbruyelle/RxPermissions](https://github.com/tbruyelle/RxPermissions)
## 使用方法
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
                        //全部同意
                    }

                    @Override
                    public void onCanRetryAsk(List<Permission> permissions) {
                        //部分权限未允许，但可以重新申请
                    }

                    @Override
                    public void onNeverAsk(List<Permission> canRetryList, List<Permission> neverAskList) {
                        //部分权限未允许，点击了不再弹出
                    }
                });
    }
