package com.cxh.mvvmart.ui.user;

import android.databinding.ObservableField;

import com.cxh.mvvmart.RestfulApi;
import com.cxh.mvvmart.base.BaseViewModel;
import com.cxh.mvvmart.rx.RxFragmentObserver;
import com.cxh.mvvmart.rx.RxScheduler;
import com.cxh.mvvmart.rx.function.HttpResultFunc;

import javax.inject.Inject;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class UserViewModel implements BaseViewModel {

    public final ObservableField<String> mStrarsStr = new ObservableField<>();

    private UserActivity mUserActivity;
    private RestfulApi mRestfulApi;

    @Inject
    UserViewModel(UserActivity activity, RestfulApi restfulApi) {
        mUserActivity = activity;
        mRestfulApi = restfulApi;
    }

    @Override
    public void loadData() {
        mRestfulApi.getStargazers()
                .onErrorResumeNext(new HttpResultFunc<>())
                .compose(RxScheduler.switchSchedulers(mUserActivity))
                .subscribe(new RxFragmentObserver<String, UserFragment>() {
                    @Override
                    protected void refreshUI(String data) {
                        mUserActivity.showContentView();
                        mStrarsStr.set(data);
                    }
                });
    }

}
