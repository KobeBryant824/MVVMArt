package com.cxh.mvvmart.rx

import com.cxh.mvvmart.rx.exception.ApiException
import com.cxh.mvvmart.rx.exception.ERROR
import com.cxh.mvvmart.util.ToastUtils
import com.cxh.mvvmart.Constant
import com.cxh.mvvmart.base.BaseActivity

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/8/31
 */
abstract class RxActivityObserver<T, K : BaseActivity> : Observer<T> {
    private var k: K? = null

    constructor()

    protected constructor(k: K) {
        this.k = k
    }

    override fun onSubscribe(@NonNull d: Disposable) {

    }

    override fun onNext(@NonNull t: T) {
        k?.showContentView()
        refreshUI(t)
    }

    override fun onError(@NonNull e: Throwable) {
        k?.showErrorView()

        if (e is ApiException)
            onError(e)
        else {
            val apiException = ApiException(e, ERROR.ONNEXT_ERROR)
            apiException.displayMessage = "onNext()出错"
            onError(apiException)
        }
    }

    override fun onComplete() {

    }

    private fun onError(ex: ApiException) {
        if (Constant.BUILD) {
            if (ex.code == ERROR.ONNEXT_ERROR)
                throw RuntimeException(ex.cause)
            else
                ToastUtils.show(ex.displayMessage)
        } else {
            ToastUtils.show(ex.displayMessage)
        }
    }

    protected abstract fun refreshUI(t: T)

}


