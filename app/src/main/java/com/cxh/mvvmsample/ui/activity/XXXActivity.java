package com.cxh.mvvmsample.ui.activity;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseActivity;
import com.cxh.mvvmsample.databinding.ActivityXxxBinding;
import com.cxh.mvvmsample.model.api.entity.event.XXXVMEvent;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.viewmodel.XXXViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class XXXActivity extends BaseActivity {

    private ActivityXxxBinding mBinding;
    @Inject XXXViewModel mXXXViewModel;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_xxx);
    }

    @Override
    protected void initDagger() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.setViewModel(new XXXViewModel(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(XXXVMEvent event) {
        ToastUtils.show("click a replyCommand: " + event.getTag());
    }
}
