package com.cxh.mvvmart.di.component;

import android.app.Activity;
import android.content.Context;


import com.cxh.mvvmart.di.moduel.ActivityModule;
import com.cxh.mvvmart.di.qualifier.ContextLife;
import com.cxh.mvvmart.di.scope.ActivityScoped;
import com.cxh.mvvmart.ui.activity.DataBindingActivity;
import com.cxh.mvvmart.ui.activity.MainActivity;
import com.cxh.mvvmart.ui.activity.UserActivity;

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
