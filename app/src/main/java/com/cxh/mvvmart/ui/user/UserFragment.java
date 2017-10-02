package com.cxh.mvvmart.ui.user;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxh.mvvmart.R;
import com.cxh.mvvmart.base.BaseFragment;
import com.cxh.mvvmart.databinding.FragmentUserBinding;
import com.cxh.mvvmart.di.ActivityScoped;
import com.cxh.mvvmart.util.GlideUtils;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/6/12
 */
@ActivityScoped
public class UserFragment extends BaseFragment {

    private FragmentUserBinding mFragmentUserBinding;

    @Inject
    UserViewModel mUserViewModel;

    @Inject
    public UserFragment() {
    }

    @Override
    protected View dataBindingView(LayoutInflater inflater, ViewGroup container) {
        mFragmentUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        return mFragmentUserBinding.getRoot();
    }

    @Override
    protected void initViewsAndEvents() {
        String path = "https://avatars0.githubusercontent.com/u/13111493?v=4&s=460";
        GlideUtils.loadImage(path, mFragmentUserBinding.showImage);
    }

    @Override
    protected void refreshState() {
        mUserViewModel.loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentUserBinding.setViewModel(mUserViewModel);
        mUserViewModel.loadData();
    }
}
