package com.neutron.deloan.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.neutron.deloan.R
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.AppManager
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils
import com.neutron.deloan.utils.toast
import com.neutron.deloan.view.LoaddingDialog
import kotlinx.android.synthetic.main.toolbar_common.*

abstract class  IBaseActivity : AppCompatActivity(), IView  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initView()
        AppManager.instance.addActivity(this)
        initData()
    }

    open fun setStatusBarColor(activity: Activity, statusColor: Int) {
        val decorView = this.window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        decorView.systemUiVisibility = option
        activity.window.statusBarColor = activity.getColor(statusColor)

    }


    open fun reStartAPP() {
        AppManager.instance.clearStack()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()




    override fun onPause() {

        super.onPause()
    }

    override fun onResume() {
        if (!Utils.checkNet(this)) {
            toast(R.string.not_net)
        }


        super.onResume()
    }



    var loadding: LoaddingDialog? = null

    override fun showLoading() {
        loadding = LoaddingDialog(this)
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


    open fun initToolbar(title: String, isShowBack: Boolean=false) {
        tv_title.text = title
        if (isShowBack){
            iv_exit.visibility = View.VISIBLE
        } else{
            iv_exit.visibility = View.GONE
        }
        iv_exit.setOnClickListener {
            finish()
        }
    }



    override fun showError(e: Exception) {
        e.printStackTrace()

Slog.d("网络访问错误 ${e}")
    }



}