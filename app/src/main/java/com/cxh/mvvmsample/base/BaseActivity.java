package com.cxh.mvvmsample.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cxh.mvvmsample.App;
import com.cxh.mvvmsample.di.component.ActivityComponent;
import com.cxh.mvvmsample.di.component.DaggerActivityComponent;
import com.cxh.mvvmsample.di.moduel.ActivityModule;
import com.cxh.mvvmsample.manager.ActivityManager;
import com.hss01248.pagestate.PageManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    protected PageManager mPageStateManager;
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ActivityManager.getInstance().pushOneActivity(this);

        if (useEventBus()) EventBus.getDefault().register(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) getBundleExtras(extras);

        PageManager.initInApp(getApplicationContext());
        mPageStateManager = PageManager.init(this, true, this::RetryEvent);
        mPageStateManager.showLoading();

        mActivityComponent = DaggerActivityComponent.builder().appComponent(App.getAppComponent()).activityModule(new ActivityModule(this)).build();
        initDagger();

        initDataAndEvent();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        // 其他用得少请自行引用autolayoutwidget库
        if (name.equals(LAYOUT_FRAMELAYOUT)) view = new AutoFrameLayout(context, attrs);

        if (name.equals(LAYOUT_LINEARLAYOUT)) view = new AutoLinearLayout(context, attrs);

        if (name.equals(LAYOUT_RELATIVELAYOUT)) view = new AutoRelativeLayout(context, attrs);

        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    public void showContent() {
        mPageStateManager.showContent();
    }

    public void showError() {
        mPageStateManager.showError();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popOneActivity(this);
        if (useEventBus()) EventBus.getDefault().unregister(this);
    }

    protected void pushPage(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void pushPage(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void pushPageThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    protected void pushPageThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    protected void pushPageForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void pushPageForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void showSnackbar(View v, String msg) {
        if (!TextUtils.isEmpty(msg)) Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
    }

    protected abstract void setContentView();

    protected boolean useEventBus() { return false;}

    protected void getBundleExtras(Bundle extras) {}

    protected abstract void initDagger();

    protected abstract void RetryEvent();

    protected abstract void initDataAndEvent();

}
