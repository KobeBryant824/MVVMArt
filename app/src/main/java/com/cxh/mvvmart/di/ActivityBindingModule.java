package com.cxh.mvvmart.di;


import com.cxh.mvvmart.ui.user.UserActivity;
import com.cxh.mvvmart.ui.user.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module()
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = UserModule.class)
    abstract UserActivity contributeUserActivityInjector();




}
