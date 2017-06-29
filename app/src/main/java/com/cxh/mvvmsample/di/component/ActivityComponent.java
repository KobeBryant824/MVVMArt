package com.cxh.mvvmsample.di.component;

import android.app.Activity;
import android.content.Context;


import com.cxh.mvvmsample.di.moduel.ActivityModule;
import com.cxh.mvvmsample.di.qualifier.ContextLife;
import com.cxh.mvvmsample.di.scope.ActivityScoped;
import com.cxh.mvvmsample.ui.activity.DataBindingActivity;
import com.cxh.mvvmsample.ui.activity.MainActivity;
import com.cxh.mvvmsample.ui.activity.UserActivity;

import dagger.Component;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/6/12
 */
@ActivityScoped
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    void inject(MainActivity activity);

    void inject(UserActivity activity);

    void inject(DataBindingActivity activity);

}
