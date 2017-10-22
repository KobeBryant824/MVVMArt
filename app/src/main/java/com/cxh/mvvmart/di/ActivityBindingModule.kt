package com.cxh.mvvmart.di


import com.cxh.mvvmart.ui.user.UserActivity
import com.cxh.mvvmart.ui.user.UserModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(UserModule::class))
    abstract fun contributeUserActivityInjector(): UserActivity


}
