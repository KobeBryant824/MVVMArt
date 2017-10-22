package com.cxh.mvvmart.ui.user

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

import com.cxh.mvvmart.util.ActivityUtils
import com.cxh.mvvmart.R
import com.cxh.mvvmart.base.BaseActivity

import javax.inject.Inject

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
class UserActivity : BaseActivity() {

    @Inject lateinit var mUserFragment: UserFragment

    override fun dataBindingView() {
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_user)
    }

    override fun initViewsAndEvents() {
        ActivityUtils.addFragmentToActivity(supportFragmentManager, mUserFragment, R.id.content)
    }

    override fun refreshState() {

    }


}
