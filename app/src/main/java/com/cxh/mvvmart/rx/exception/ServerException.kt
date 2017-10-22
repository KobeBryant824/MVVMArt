package com.cxh.mvvmart.rx.exception

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/5/31
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()
