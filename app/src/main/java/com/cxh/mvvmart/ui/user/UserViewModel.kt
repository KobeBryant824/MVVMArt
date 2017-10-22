package com.cxh.mvvmart.ui.user

import android.databinding.ObservableField
import com.cxh.mvpart.RestfulApi

import com.cxh.mvvmart.base.BaseViewModel
import com.cxh.mvvmart.rx.RxFragmentObserver
import com.cxh.mvvmart.rx.RxScheduler
import com.cxh.mvvmart.rx.function.HttpResultFunc

import javax.inject.Inject

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
class UserViewModel @Inject constructor(private val mUserActivity: UserActivity, private val mRestfulApi: RestfulApi) : BaseViewModel {

    val mStrarsStr = ObservableField<String>()

    override fun loadData() {
        mRestfulApi.getStargazers()
                .onErrorResumeNext(HttpResultFunc<String>())
                .compose(RxScheduler.switchSchedulers(mUserActivity))
                .subscribe(object : RxFragmentObserver<String, UserFragment>() {
                    override fun refreshUI(t: String) {
                        mUserActivity.showContentView()
                        mStrarsStr.set(t)
                    }
                })
    }

}
