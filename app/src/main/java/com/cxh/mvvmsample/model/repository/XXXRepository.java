package com.cxh.mvvmsample.model.repository;

import com.cxh.mvvmsample.App;
import com.cxh.mvvmsample.listener.OnRequestListener;
import com.cxh.mvvmsample.manager.RxScheduler;
import com.cxh.mvvmsample.model.api.XXXApi;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;


/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class XXXRepository implements IRequestBiz<XXXApi.WelcomeEntity> {

    public void requestData(RxAppCompatActivity activity, final OnRequestListener<XXXApi.WelcomeEntity> listener) {

        XXXApi xxxApi = App.getRetrofit().create(XXXApi.class);

        xxxApi.getWelcomeEntity()
                .compose(activity.bindToLifecycle())
                .compose(RxScheduler.schedulersObservableTransformer(activity))
                .subscribe(listener::onSuccess, throwable -> listener.onFailed());

        Flowable.interval(1, TimeUnit.SECONDS)
                .doOnCancel(() -> KLog.e("Unsubscribing subscription from onCreate()"))
                .compose(RxScheduler.schedulersFlowableTransformer(activity))
                .subscribe(aLong -> KLog.e("Started in onCreate(), running until onDestroy(): " + aLong));


    }

}
