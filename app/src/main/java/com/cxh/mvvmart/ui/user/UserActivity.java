package com.cxh.mvvmart.ui.user;

import android.databinding.DataBindingUtil;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseActivity;
import com.cxh.mvvmart.util.ActivityUtils;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserActivity extends BaseActivity {

    @Inject
    UserFragment mUserFragment;

    @Override
    protected void dataBindingView() {
        DataBindingUtil.setContentView(this, R.layout.activity_user);
    }

    @Override
    protected void initViewsAndEvents() {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mUserFragment, R.id.content);
    }

    @Override
    protected void refreshState() {

    }


}
