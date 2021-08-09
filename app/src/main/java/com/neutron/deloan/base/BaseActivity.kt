package com.neutron.deloan.base

import android.os.Bundle


abstract class BaseActivity<in V : IView, P : IPresenter<V>>() : IBaseActivity() {

    var mPresenter: P? = null

    protected abstract fun setPresenter(): P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setPresenter()
        mPresenter?.attachView(this as V)
        initData()
    }

    abstract fun initData()
    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null

        super.onDestroy()
    }

}