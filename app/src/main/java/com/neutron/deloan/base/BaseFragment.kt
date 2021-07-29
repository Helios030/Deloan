package com.neutron.deloan.base

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils
import com.neutron.deloan.view.LoaddingDialog
import kotlinx.android.synthetic.main.toolbar_common.*

abstract  class BaseFragment<in V : IView, P : IPresenter<V>> : IBaseFragment(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P


    override fun onResume() {
        super.onResume()

    }

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    var loadding: LoaddingDialog? = null

    override fun showLoading() {
        loadding = LoaddingDialog(activity)
        if (loadding != null && !loadding!!.isShowing) {
            loadding!!.show()
        }

    }

    override fun hideLoading() {
        if (loadding != null && loadding!!.isShowing) {
            loadding!!.dismiss()
            loadding = null
        }

    }


    override fun showError(e: Exception) {
        hideLoading()
        e.printStackTrace()
        Slog.d("出错 ",e)
    }

    open fun startToNewTask(targetClass: Class<out Activity>) {
        val intent = Intent(activity, targetClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    open fun startTo(targetClass: Class<out Activity>) {
        Slog.d("普通 跳转 目标 ${targetClass.name}")
        val intent = Intent(activity, targetClass)
        startActivity(intent)
    }


    fun showToast(msg: String?) {

        if (!msg.isNullOrEmpty()) {
            Toast.makeText(NApplication.sContext, msg, Toast.LENGTH_LONG).show()
        }
    }


    fun showToast(msg: Int?) {
        msg?.let {
            Toast.makeText(NApplication.sContext, it, Toast.LENGTH_LONG).show()
        }

    }
    open fun initToolbar(title: String, isShowBack: Boolean=false) {
        tv_title.text = title
        if (isShowBack){
            iv_exit.visibility = View.VISIBLE
        } else{
            iv_exit.visibility = View.GONE
        }
        iv_exit.setOnClickListener {
//            finish()
        }
    }
}

