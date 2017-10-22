package com.cxh.mvvmart.ui.user


import com.cxh.mvvmart.base.BaseViewModel
import com.cxh.mvvmart.di.ActivityScoped
import com.cxh.mvvmart.di.FragmentScoped

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/10/1
 */
@Module
abstract class UserModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun userFragment(): UserFragment

    @ActivityScoped
    @Binds
    internal abstract fun userViewModel(userViewModel: UserViewModel): BaseViewModel


}
