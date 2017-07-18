package com.cxh.mvvmart.model.repository;

import com.cxh.mvvmart.App;
import com.cxh.mvvmart.base.IModel;
import com.cxh.mvvmart.callback.OnRequestListener;
import com.cxh.mvvmart.manager.RxScheduler;
import com.cxh.mvvmart.model.entity.WelcomeEntity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserRepository implements IModel<WelcomeEntity> {

    public void requestData(RxAppCompatActivity activity, final OnRequestListener<WelcomeEntity> listener) {

        App.getRxCacheClient().getUser(false)
                .compose(RxScheduler.applyObservableSchedulers(activity))
                .subscribe(listener::onSuccess, throwable -> listener.onFailed());
    }

}
