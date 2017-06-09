package com.cxh.mvvmsample.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cxh.mvvmsample.manager.ActivityManager;
import com.cxh.mvvmsample.manager.RxDisposable;
import com.cxh.mvvmsample.model.api.entity.event.PageStateEvent;
import com.hss01248.pagestate.PageManager;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.cxh.mvvmsample.model.api.entity.event.PageStateEvent.ON_FAILED;
import static com.cxh.mvvmsample.model.api.entity.event.PageStateEvent.ON_SUCCESS;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public abstract class BaseActivity extends RxAppCompatActivity{
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    protected PageManager mPageStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ActivityManager.getInstance().pushOneActivity(this);

        if (useEventBus())
            EventBus.getDefault().register(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        PageManager.initInApp(getApplicationContext());
        mPageStateManager = PageManager.init(this, true, this::RetryEvent);
        mPageStateManager.showLoading();

        initViewsAndEvents();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        // 其他用得少请自行引用autolayoutwidget库
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        ActivityManager.getInstance().popOneActivity(this);
    }

    protected void pushPageThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    protected void pushPageThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void pushPageForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void pushPageForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showSnackbar(View v, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(PageStateEvent event) {
        switch (event.getTag()) {
            case ON_SUCCESS:
                mPageStateManager.showContent();
                break;
            case ON_FAILED:
                mPageStateManager.showError();
                break;
        }
    }

    public boolean useEventBus() {
        return true;
    }

    protected void getBundleExtras(Bundle extras) {}

    protected abstract void setContentView();

    protected abstract void RetryEvent();

    protected abstract void initViewsAndEvents();

}
