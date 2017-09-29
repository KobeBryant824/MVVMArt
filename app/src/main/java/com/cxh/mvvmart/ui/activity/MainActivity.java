package com.cxh.mvvmart.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseActivity;
import com.cxh.mvvmart.databinding.ActivityMainBinding;
import com.cxh.mvvmart.manager.ActivityManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void dataBindingView() {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void injectDagger() {

    }

    @Override
    protected void initViewsAndEvents() {
        toolbarTitle.setText(R.string.app_name);
        mMainBinding.mvpBtn.setOnClickListener(view -> pushPage(UserActivity.class));

    }

    @Override
    protected void refreshState() {

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
                .subscribe(aLong -> mDoubleBackToExitPressedOnce = false);
    }

}
