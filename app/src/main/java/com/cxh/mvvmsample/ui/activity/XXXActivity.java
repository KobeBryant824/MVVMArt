package com.cxh.mvvmsample.ui.activity;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseActivity;
import com.cxh.mvvmsample.databinding.ActivityXxxBinding;
import com.cxh.mvvmsample.model.api.entity.event.XXXViewModelEvent;
import com.cxh.mvvmsample.ui.activity.component.DaggerXXXComponent;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.viewmodel.XXXViewModel;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;


public class XXXActivity extends BaseActivity {

    private ActivityXxxBinding mBinding;
    @Inject
    XXXViewModel mXXXViewModel;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_xxx);
    }

    @Override
    protected void RetryEvent() {
        KLog.e(); // onResume() 替代了这个action
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaggerXXXComponent.builder().build().inject(this); // rebuild
        mBinding.setViewModel(mXXXViewModel); // 模拟从后台可视时数据更新，View更新
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(XXXViewModelEvent event) {
        ToastUtils.showToast(this, "click a replyCommand: " + event.getData());
    }
}
