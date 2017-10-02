package com.cxh.mvvmart.ui.home;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseActivity;
import com.cxh.mvvmart.databinding.ActivityMainBinding;
import com.cxh.mvvmart.manager.ActivityManager;
import com.cxh.mvvmart.ui.user.UserActivity;
import com.cxh.mvvmart.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected boolean isInject() {
        return false;
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void dataBindingView() {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbarTitle.setText(R.string.app_name);
        mMainBinding.mvvm.setOnClickListener(view -> pushPage(UserActivity.class));

    }

    @Override
    protected void refreshState() {

    }

    private boolean mDoubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            ActivityManager.getInstance().appExit();
            return;
        }
        mDoubleBackToExitPressedOnce = true;
        ToastUtils.show("再次点击退出" + getString(R.string.app_name));

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> mDoubleBackToExitPressedOnce = false);
    }

}
