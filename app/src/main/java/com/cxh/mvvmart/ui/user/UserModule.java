package com.cxh.mvvmart.ui.user;


import com.cxh.mvvmart.base.BaseViewModel;
import com.cxh.mvvmart.di.ActivityScoped;
import com.cxh.mvvmart.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/10/1
 */
@Module
public abstract class UserModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserFragment userFragment();

    @ActivityScoped
    @Binds
    abstract BaseViewModel userViewModel(UserViewModel userViewModel);


}
