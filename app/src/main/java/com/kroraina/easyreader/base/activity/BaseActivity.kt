package com.kroraina.easyreader.base.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.annotations.ActivityUI
import com.kroraina.easyreader.base.annotations.NavigationBar
import com.kroraina.easyreader.utils.StatusBarCompat

import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    protected var mDisposable: CompositeDisposable? = null
    //ButterKnife
    private var mToolbar: Toolbar? = null

    private var unbinder: Unbinder? = null
    /****************************abstract area */

    protected open fun getContentId(): Int {
        return 0
    }

    /************************init area */
    protected fun addDisposable(d: Disposable) {
        if (mDisposable == null) {
            mDisposable = CompositeDisposable()
        }
        mDisposable!!.add(d)
    }

    /**
     * 配置Toolbar
     * @param toolbar
     */
    protected open fun setUpToolbar(toolbar: Toolbar) {

    }

    protected open fun initData(savedInstanceState: Bundle?) {}
    /**
     * 初始化零件
     */
    protected open fun initWidget() {

    }

    /**
     * 初始化点击事件
     */
    protected open fun initClick() {}

    /**
     * 逻辑使用区
     */
    protected open fun processLogic() {}

    /*************************lifecycle area */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityUI = this.javaClass.getAnnotation(ActivityUI::class.java)
        if (activityUI != null && activityUI.layoutId != 0) {
            setContentView(activityUI.layoutId)
        } else {
            setContentView(getContentId())
        }


        initData(savedInstanceState)
        unbinder = ButterKnife.bind(this)
        initStatusBar()
        initToolbar()
        initWidget()
        initClick()
        processLogic()

    }

    private fun initStatusBar() {
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.main_color))
    }

    private fun initToolbar() {
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar)
        if (mToolbar != null) {
            processNavigationBarAnnotations()
            supportActionBar(mToolbar!!)
            setUpToolbar(mToolbar!!)
        }
    }

    private fun processNavigationBarAnnotations() {
        val navigationBar = javaClass.getAnnotation(NavigationBar::class.java)
        if (navigationBar != null) {
            mToolbar!!.visibility = View.VISIBLE
            if (navigationBar.titleResId != 0) {
                mToolbar!!.setTitle(navigationBar.titleResId)
            } else {
                mToolbar!!.title = navigationBar.title
            }
        } else {
            //mToolbar.setVisibility(View.GONE);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
        if (mDisposable != null) {
            mDisposable!!.dispose()
        }
    }

    /**************************used method area */

    protected fun startActivity(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    protected fun supportActionBar(toolbar: Toolbar): ActionBar? {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        mToolbar!!.setNavigationOnClickListener { v -> finish() }
        return actionBar
    }

    protected fun setStatusBarColor(statusColor: Int) {
        StatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor))
    }
}
