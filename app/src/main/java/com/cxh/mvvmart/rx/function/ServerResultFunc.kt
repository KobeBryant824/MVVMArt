package com.cxh.mvvmart.rx.function

import com.cxh.mvvmart.base.Result
import com.cxh.mvvmart.rx.exception.ServerException

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/8/30
 */
class ServerResultFunc<T> : Function<Result<T>, T> {

    @Throws(Exception::class)
    override fun apply(@NonNull httpResult: Result<T>): T? {
        if (httpResult.code != 1) {
            throw ServerException(httpResult.code, httpResult.msg!!)
        }
        return httpResult.data
    }
}