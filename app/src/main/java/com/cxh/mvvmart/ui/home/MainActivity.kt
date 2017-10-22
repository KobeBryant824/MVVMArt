package com.cxh.mvvmart.ui.home

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

import com.cxh.mvpart.manager.ActivityManager
import com.cxh.mvvmart.util.ToastUtils
import com.cxh.mvvmart.R
import com.cxh.mvvmart.base.BaseActivity
import com.cxh.mvvmart.ui.user.UserActivity

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
class MainActivity : BaseActivity() {

    private var mDoubleBackToExitPressedOnce = false

    override fun isInject(): Boolean {
        return false
    }

    override fun displayHomeAsUpEnabled(): Boolean {
        return false
    }

    override fun dataBindingView() {
       DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)
    }

    override fun initViewsAndEvents() {
        toolbarTitle.setText(R.string.app_name)
        mvvm.onClick {
            startActivity<UserActivity>()
        }
    }

    override fun refreshState() {

    }

    override fun onBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed()
            ActivityManager.appExit()
            return
        }
        mDoubleBackToExitPressedOnce = true
        ToastUtils.show("再次点击退出" + getString(R.string.app_name))

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe { mDoubleBackToExitPressedOnce = false }
    }

}
