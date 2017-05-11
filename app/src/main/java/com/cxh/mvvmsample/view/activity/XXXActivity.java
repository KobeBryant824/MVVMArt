package com.cxh.mvvmsample.view.activity;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.databinding.ActivityXxxBinding;
import com.cxh.mvvmsample.model.api.entity.Event;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.viewmodel.XXXViewModel;
import com.socks.library.KLog;

import static com.cxh.mvvmsample.AppConstants.XXXVIEWMODEL_MREPLYCOMMAND;


public class XXXActivity extends BaseAutoActivity {

    private ActivityXxxBinding mBinding;

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
        mBinding.setViewModel(new XXXViewModel()); // 模拟从后台可视时数据更新，View更新
    }

    @Override
    public void onMainEvent(Event event) {
        super.onMainEvent(event);
        KLog.e();
        if (event.getTag().equals(XXXVIEWMODEL_MREPLYCOMMAND))
            ToastUtils.showToast(this, "click a replyCommand: " + event.getData());

    }
}
