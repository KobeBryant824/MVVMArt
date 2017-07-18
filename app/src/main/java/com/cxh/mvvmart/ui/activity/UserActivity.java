package com.cxh.mvvmart.ui.activity;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseActivity;
import com.cxh.mvvmart.databinding.ActivityUserBinding;
import com.cxh.mvvmart.viewmodel.UserViewModel;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserActivity extends BaseActivity {

    private ActivityUserBinding mBinding;
    @Inject
    UserViewModel mUserViewModel;

    @Override
    protected void setContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
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
        mBinding.setViewModel(mUserViewModel);
    }


}
