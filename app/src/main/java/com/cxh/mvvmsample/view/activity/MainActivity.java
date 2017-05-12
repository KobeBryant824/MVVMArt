package com.cxh.mvvmsample.view.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.databinding.ActivityMainBinding;
import com.cxh.mvvmsample.manager.ActivityManager;
import com.cxh.mvvmsample.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends BaseAutoActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected void setContentView() {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Override
    public void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {

        // 解决Handler可能造成的内存泄漏，其实这段代码用handler也不会造成泄漏，hiahia~~
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                // Replace or Expand lambda , alt + enter
                .subscribe(aLong -> mPageStateManager.showContent());

        RxView.clicks(mMainBinding.mvpBtn)
                .throttleFirst(2000, TimeUnit.MICROSECONDS)
                .subscribe(o -> {
                            startActivity(new Intent(MainActivity.this, XXXActivity.class));
                });

    }

    public void permission(View view) {
        requestPermission();
    }

    private void requestPermission() {
        // M 才需要申请权限 ， leakcanary 在M以上需要权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MainActivityPermissionsDispatcher.agreeWithCheck(this);
        }
    }

    public void dataBinding(View view){
        startActivity(new Intent(this, DataBindingActivity.class));
    }

    private boolean doubleBackToExitPressedOnce = false;

    // 双击返回键退出
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            ActivityManager.getInstance().appExit();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再次点击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aLong -> doubleBackToExitPressedOnce = false);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void agree() {
        ToastUtils.showToast(this, "写SD卡限权已申请");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("这是申请写SD卡权限的说明....")
                .setPositiveButton("确定", (dialog, which) -> {
                    // 再次执行请求
                    request.proceed();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void permissionDenied() {
        ToastUtils.showToast(this, "权限被拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void neverAskAgain() {
        Toast.makeText(this, "下次需要该权限请到系统设置中打开", Toast.LENGTH_SHORT).show();
    }
}
