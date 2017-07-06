package com.cxh.mvvmsample.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.cxh.mvvmsample.R;
import com.cxh.mvvmsample.base.BaseActivity;
import com.cxh.mvvmsample.databinding.MyBinding;
import com.cxh.mvvmsample.databinding.ViewStub1Binding;
import com.cxh.mvvmsample.callback.OkListener;
import com.cxh.mvvmsample.model.entity.User;
import com.cxh.mvvmsample.util.ToastUtils;
import com.cxh.mvvmsample.ui.widget.DividerItemDecoration;
import com.cxh.mvvmsample.viewmodel.DataBindingViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class DataBindingActivity extends BaseActivity implements OkListener {

    private MyBinding mBinding;
    private User mUser;
    @Inject
    DataBindingViewModel mDataBindingViewModel;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
//        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected boolean useEventBus() {
        return true;
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
        mBinding.setViewModel(mDataBindingViewModel);
    }

    @Subscribe()
    public void onMainEvent(User user){
        mUser = user;
        mBinding.setUser(user);
    }

    @Override
    public void onClickOk(View view) {
        ToastUtils.show("OkListener");
    }

    public void inflateViewStub(View view) {
        if (!mBinding.viewStub.isInflated()) {
            mBinding.viewStub.getViewStub().inflate();
        }
    }

}
