package com.cxh.mvvmsample.view.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.databinding.MyBinding;
import com.cxh.mvvmsample.databinding.ViewStub1Binding;
import com.cxh.mvvmsample.listener.OkListener;
import com.cxh.mvvmsample.model.api.entity.User;
import com.cxh.mvvmsample.model.api.entity.event.DataBindingViewModelEvent;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.view.widget.DividerItemDecoration;
import com.cxh.mvvmsample.viewmodel.DataBindingViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Desc, DataBinding 一些简单使用
 * Created by Hai (haigod7@gmail.com) on 2017/4/28 13:58.
 */
public class DataBindingActivity extends BaseAutoActivity implements OkListener {

    private MyBinding mBinding;
    private User mUser;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
//        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Override
    protected void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {

        mBinding.setOkText("hello点我");
        mBinding.setListener(this);

        mBinding.viewStub.setOnInflateListener((stub, inflated) -> {
            ViewStub1Binding binding = DataBindingUtil.bind(inflated);
            binding.setUser(mUser);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.setViewModel(new DataBindingViewModel());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(User user){
        mUser = user;
        mBinding.setUser(user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(DataBindingViewModelEvent event) {
        switch (event.getTag()){
            case DataBindingViewModelEvent.REPLY_COMMAND:
                ToastUtils.showToast(DataBindingActivity.this, event.getData());
                break;

            case DataBindingViewModelEvent.ONITEMCLICKLISTENER:
                ToastUtils.showToast(DataBindingActivity.this, event.getData());
                break;
        }
    }

    @Override
    public void onClickOk(View view) {
        ToastUtils.showToast(this, "OkListener");
    }

    public void inflateViewStub(View view) {
        if (!mBinding.viewStub.isInflated()) {
            mBinding.viewStub.getViewStub().inflate();
        }
    }

}
