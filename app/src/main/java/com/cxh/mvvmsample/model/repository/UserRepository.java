package com.cxh.mvvmsample.model.repository;

import com.cxh.mvvmsample.App;
import com.cxh.mvvmsample.base.IModel;
import com.cxh.mvvmsample.callback.OnRequestListener;
import com.cxh.mvvmsample.manager.RxScheduler;
import com.cxh.mvvmsample.model.api.UserApi;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;


/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserRepository implements IModel<UserApi.WelcomeEntity> {

    public void requestData(RxAppCompatActivity activity, final OnRequestListener<UserApi.WelcomeEntity> listener) {

        UserApi userApi = App.getRetrofit().create(UserApi.class);

        userApi.welcomeObservable()
                .compose(activity.bindToLifecycle())
                .compose(RxScheduler.schedulersObservableTransformer(activity))
                .subscribe(listener::onSuccess, throwable -> listener.onFailed());

        Flowable.interval(1, TimeUnit.SECONDS)
                .doOnCancel(() -> KLog.e("Unsubscribing subscription from onCreate()"))
                .compose(activity.bindToLifecycle())
                .subscribe(aLong -> KLog.e("Started in onCreate(), running until onDestroy(): " + aLong));

    }

}
