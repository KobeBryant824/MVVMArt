package com.cxh.mvvmsample.view.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseAutoActivity;
import com.cxh.mvvmsample.databinding.MyBinding;
import com.cxh.mvvmsample.databinding.ViewStub1Binding;
import com.cxh.mvvmsample.listener.OkListener;
import com.cxh.mvvmsample.model.api.entity.Event;
import com.cxh.mvvmsample.model.api.entity.User;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.view.widget.DividerItemDecoration;
import com.cxh.mvvmsample.viewmodel.DataBindingViewModel;

import static com.cxh.mvvmsample.AppConstants.DATABINDINGVIEWMODEL_LISTENER;
import static com.cxh.mvvmsample.AppConstants.DATABINDINGVIEWMODEL_MREPLYCOMMAND;

/**
 * Desc, databinding一些简单使用
 * Created by Hai (haigod7@gmail.com) on 2017/4/28 13:58.
 */
public class DataBindingActivity extends BaseAutoActivity implements OkListener {

    private MyBinding mBinding;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
//        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected void RetryEvent() {

    }

    @Override
    protected void initViewsAndEvents() {
        User user = new User("Kobe", "Bryant", 37);
        mBinding.setUser(user);
        mBinding.setOkText("hello点我");
        mBinding.setListener(this);

        mBinding.viewStub.setOnInflateListener((stub, inflated) -> {
            ViewStub1Binding binding = DataBindingUtil.bind(inflated);
            binding.setUser(user);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.setViewModel(new DataBindingViewModel());
    }

    @Override
    public void onMainEvent(Event event) {
        super.onMainEvent(event);
        switch (event.getTag()){
            case DATABINDINGVIEWMODEL_MREPLYCOMMAND:
                ToastUtils.showToast(DataBindingActivity.this, event.getData().toString());
                break;

            case DATABINDINGVIEWMODEL_LISTENER:
                ToastUtils.showToast(DataBindingActivity.this, event.getData().toString());
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
