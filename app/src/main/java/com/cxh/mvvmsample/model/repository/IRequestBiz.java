package com.cxh.mvvmsample.model.repository;


import com.cxh.mvvmsample.listener.OnRequestListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public interface IRequestBiz<T>{

    void requestData(RxAppCompatActivity activity, OnRequestListener<T> listener);

}
