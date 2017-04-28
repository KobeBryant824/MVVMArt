package com.cxh.mvvmsample.view.activity;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.databinding.ActivityXxxBinding;
import com.cxh.mvvmsample.viewmodel.XXXViewModel;

public class XXXActivity extends BaseAutoActivity {

    private ActivityXxxBinding mBinding;

    @Override
    protected void setContentView() {
       mBinding = DataBindingUtil.setContentView(this, R.layout.activity_xxx);
    }

    @Override
    protected void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {
    }

    public void showContent(){
        mPageStateManager.showContent();
    }

    public void showError(){
        mPageStateManager.showError();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.setViewModel(new XXXViewModel(this)); // 模拟从后台可视时数据更新，View更新
    }
}
