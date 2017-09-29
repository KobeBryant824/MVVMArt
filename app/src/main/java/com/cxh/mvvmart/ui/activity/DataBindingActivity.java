package com.cxh.mvvmart.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseActivity;
import com.cxh.mvvmart.databinding.MyBinding;
import com.cxh.mvvmart.databinding.ViewStub1Binding;
import com.cxh.mvvmart.callback.OkListener;
import com.cxh.mvvmart.model.User;
import com.cxh.mvvmart.util.ToastUtils;
import com.cxh.mvvmart.ui.widget.DividerItemDecoration;
import com.cxh.mvvmart.viewmodel.DataBindingViewModel;

import org.greenrobot.eventbus.Subscribe;

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
    protected void dataBindingView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
//        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void injectDagger() {
        mActivityComponent.inject(this);
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
    protected void refreshState() {

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
