package com.neutron.deloan.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.R
import com.neutron.deloan.utils.AppManager
import com.neutron.deloan.utils.Utils
import com.neutron.deloan.utils.toast
import com.neutron.deloan.view.LoaddingDialog


abstract class BaseActivity<in V : IView, P : IPresenter<V>>() : IBaseActivity() {

    var mPresenter: P? = null

    protected abstract fun setPresenter(): P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setPresenter()
        mPresenter?.attachView(this as V)
    }


    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null

        super.onDestroy()
    }

}