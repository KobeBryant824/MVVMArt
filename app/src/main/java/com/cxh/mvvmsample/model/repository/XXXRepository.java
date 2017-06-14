package com.cxh.mvvmsample.model.repository;

import android.util.Log;

import com.cxh.mvvmsample.App;
import com.cxh.mvvmsample.listener.OnRequestListener;
import com.cxh.mvvmsample.manager.RxDisposable;
import com.cxh.mvvmsample.manager.RxScheduler;
import com.cxh.mvvmsample.model.api.XXXApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;


/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class XXXRepository implements IRequestBiz<XXXApi.WelcomeEntity> {

    public void requestData(final OnRequestListener<XXXApi.WelcomeEntity> listener) {

        XXXApi xxxApi =  App.getRetrofit().create(XXXApi.class);

        Disposable subscribe = xxxApi.getWelcomeEntity()
                .compose(RxScheduler.schedulersFlowableTransformer())
                .subscribe(listener::onSuccess, throwable -> listener.onFailed(), () -> {});

        Disposable subscribe1 = Flowable.interval(1, TimeUnit.SECONDS)
                .doOnCancel(() -> Log.d("hh", "Unsubscribing subscription from onCreate()"))
                .subscribe(aLong -> Log.d("hh", "Started in onCreate(), running until onDestroy(): " + aLong));

        RxDisposable.add(subscribe);

        RxDisposable.add(subscribe1);

    }

}
