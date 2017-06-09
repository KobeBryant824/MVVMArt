package com.cxh.mvvmsample.ui.activity.component;

import com.cxh.mvvmsample.ui.activity.XXXActivity;

import dagger.Component;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
@Component
public interface XXXComponent {

    void inject(XXXActivity xxxActivity);
}
