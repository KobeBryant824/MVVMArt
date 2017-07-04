package com.cxh.mvvmsample.model.repository;

import com.cxh.mvvmsample.App;
import com.cxh.mvvmsample.base.IModel;
import com.cxh.mvvmsample.callback.OnRequestListener;
import com.cxh.mvvmsample.manager.RxScheduler;
import com.cxh.mvvmsample.model.entity.WelcomeEntity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserRepository implements IModel<WelcomeEntity> {

    public void requestData(RxAppCompatActivity activity, final OnRequestListener<WelcomeEntity> listener) {

        App.getRepository().getUser(false)
                .compose(RxScheduler.schedulersObservableTransformer(activity))
                .subscribe(listener::onSuccess, throwable -> listener.onFailed());
    }

}
