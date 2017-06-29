package com.cxh.mvvmsample.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseActivity;
import com.cxh.mvvmsample.databinding.ActivityMainBinding;
import com.cxh.mvvmsample.manager.ActivityManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected void setContentView() {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initDagger() {

    }

    @Override
    public void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> mPageStateManager.showContent());

        RxView.clicks(mMainBinding.mvpBtn)
                .throttleFirst(2000, TimeUnit.MICROSECONDS)
                .subscribe(o -> pushPage(XXXActivity.class));

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(() -> KLog.e("Unsubscribing subscription from onCreate()"))
                .compose(bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(num -> KLog.e("Started in onCreate(), running until onPause(): " + num));

    }

    public void dataBinding(View view) {
        pushPage(DataBindingActivity.class);
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
        Toast.makeText(this, "再次点击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aLong -> mDoubleBackToExitPressedOnce = false);
    }

}
