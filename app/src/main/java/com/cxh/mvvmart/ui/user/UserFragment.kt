package com.cxh.mvvmart.ui.user

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cxh.mvvmart.R
import com.cxh.mvvmart.base.BaseFragment
import com.cxh.mvvmart.databinding.FragmentUserBinding
import com.cxh.mvvmart.di.ActivityScoped
import com.cxh.mvvmart.util.GlideUtils
import kotlinx.android.synthetic.main.fragment_user.*
import javax.inject.Inject

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/6/12
 */
@ActivityScoped
class UserFragment @Inject constructor() : BaseFragment() {

    private lateinit var mFragmentUserBinding: FragmentUserBinding

    @Inject lateinit var mUserViewModel: UserViewModel

    override fun dataBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        mFragmentUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val viewDataBinding = mFragmentUserBinding as ViewDataBinding
        return viewDataBinding.root
    }

    override fun initViewsAndEvents() {
        val path = "https://avatars0.githubusercontent.com/u/13111493?v=4&s=460"
        GlideUtils.loadImage(path, showImage)
    }

    override fun refreshState() {
        mUserViewModel.loadData()
    }

    override fun onResume() {
        super.onResume()
        mFragmentUserBinding.viewModel = mUserViewModel
        mUserViewModel.loadData()
    }
}
