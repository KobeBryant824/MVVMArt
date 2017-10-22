package com.cxh.mvvmart.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.cxh.mvpart.manager.ActivityManager
import com.cxh.mvvmart.R
import com.cxh.mvvmart.ui.widget.autolayout.*
import com.fingdo.statelayout.StateLayout
import com.socks.library.KLog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
abstract class BaseActivity : RxAppCompatActivity(), AnkoLogger, StateLayout.OnViewRefreshListener, HasFragmentInjector, HasSupportFragmentInjector {

    private var stateLayout: StateLayout? = null
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isInject()) AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        dataBindingView()

        ActivityManager.pushOneActivity(this)

        if (isUseDefaultToolbar()) setupToolbar()

        if (isUseEventBus()) EventBus.getDefault().register(this)

        setupStateLayout()

        initViewsAndEvents()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        var view: View? = null
        if (name == LAYOUT_FRAMELAYOUT) view = AutoFrameLayout(context, attrs)

        if (name == LAYOUT_LINEARLAYOUT) view = AutoLinearLayout(context, attrs)

        if (name == LAYOUT_RELATIVELAYOUT) view = AutoRelativeLayout(context, attrs)

        if (name == LAYOUT_CARDVIEW) view = AutoCardView(context, attrs)

        if (name == LAYOUT_TOOLBAR) view = AutoToolbar(context, attrs)

        if (name == LAYOUT_RADIOGROUP) view = AutoRadioGroup(context, attrs)

        if (name == LAYOUT_SCROLLVIEW) view = AutoScrollView(context, attrs)

        if (name == LAYOUT_TABLAYOUT) view = AutoTabLayout(context, attrs)

        if (name == LAYOUT_CONSTRAINTLAYOUT) view = AutoConstraintLayout(context, attrs)

        return if (view != null) view else super.onCreateView(name, context, attrs)
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.let {
            supportActionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled())
            supportActionBar.setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupStateLayout() {
        stateLayout = findViewById(R.id.stateLayout)
        stateLayout?.let {
            stateLayout?.isUseAnimation = false
            stateLayout?.setTipText(5, getString(R.string.statelayout_loading))
            stateLayout?.setRefreshListener(this)
            stateLayout?.showLoadingView()
        }
    }

    override fun onDestroy() {
        if (isUseEventBus()) EventBus.getDefault().unregister(this)
        super.onDestroy()
        ActivityManager.popOneActivity(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun refreshClick() {
        stateLayout?.showLoadingView()
        refreshState()
    }

    override fun loginClick() {}

    @Subscribe
    fun onEvent(event: String) = KLog.e(event)

    fun showLoadingView() = stateLayout?.showLoadingView()

    fun showContentView() = stateLayout?.showContentView()

    fun showErrorView() = stateLayout?.showErrorView()

    fun showTimeoutView() = stateLayout?.showTimeoutView()

    protected open fun isInject() = true

    protected open fun isUseDefaultToolbar() = true

    protected open fun displayHomeAsUpEnabled() = true

    protected open fun isUseEventBus() = false

    protected abstract fun dataBindingView()

    protected abstract fun initViewsAndEvents()

    protected abstract fun refreshState()

    companion object {

        private val LAYOUT_LINEARLAYOUT = "LinearLayout"
        private val LAYOUT_FRAMELAYOUT = "FrameLayout"
        private val LAYOUT_RELATIVELAYOUT = "RelativeLayout"
        private val LAYOUT_SCROLLVIEW = "ScrollView"
        private val LAYOUT_RADIOGROUP = "RadioGroup"
        private val LAYOUT_CARDVIEW = "android.support.v7.widget.CardView"
        private val LAYOUT_TOOLBAR = "android.support.v7.widget.Toolbar"
        private val LAYOUT_TABLAYOUT = "android.support.design.widget.TabLayout"
        private val LAYOUT_CONSTRAINTLAYOUT = "android.support.constraint.ConstraintLayout"
    }

}
