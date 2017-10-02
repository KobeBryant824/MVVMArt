package com.cxh.mvvmart.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxh.mvvmart.R;
import com.fingdo.statelayout.StateLayout;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/7/21
 */
public abstract class BaseFragment extends RxFragment implements StateLayout.OnViewRefreshListener,HasSupportFragmentInjector {

    private StateLayout stateLayout;

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        if (isInject()) AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
//        return viewDataBinding.getRoot();
        return dataBindingView(inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isUseEventBus()) EventBus.getDefault().register(this);

        setupStateLayout(view);

        initViewsAndEvents();
    }

    private void setupStateLayout(View view) {
        stateLayout = view.findViewById(R.id.stateLayout);
        if (stateLayout == null) return;
        stateLayout.setUseAnimation(false);
        stateLayout.setTipText(5, getString(R.string.statelayout_loading));
        stateLayout.setRefreshListener(this);
        stateLayout.showLoadingView();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void showLoginView() {
        stateLayout.showLoginView();
    }

    public void showContentView() {
        stateLayout.showContentView();
    }

    public void showErrorView() {
        stateLayout.showErrorView();
    }

    public void showTimeoutView() {
        stateLayout.showTimeoutView();
    }

    @Override
    public void refreshClick() {
        stateLayout.showLoadingView();
        refreshState();
    }

    @Override
    public void loginClick() {
    }

    @Subscribe
    public void onEvent(String event) {
        KLog.e();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (isUseEventBus()) EventBus.getDefault().unregister(this);
    }

    protected boolean isInject() {
        return true;
    }

    public boolean isUnbind() {
        // Android中ViewPager + Fragment使用 ButterKnife注解时出现空指针NullPoint的情况
        return true;
    }

    protected boolean isUseEventBus() {
        return false;
    }

    protected abstract View dataBindingView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initViewsAndEvents();

    protected abstract void refreshState();

}
