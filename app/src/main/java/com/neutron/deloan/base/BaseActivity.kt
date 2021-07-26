package com.neutron.deloan.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.neutron.deloan.MainActivity
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.utils.AppManager
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
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


    open fun startTo(targetClass: Class<out Activity>) {
        Slog.d("普通 跳转 目标 ${targetClass.name}")
        val intent = Intent(this, targetClass)
        startActivity(intent)
    }


    open fun startToNewTask(targetClass: Class<out Activity>) {
        val intent = Intent(this, targetClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    open fun reStartAPP() {
        AppManager.instance.clearStack()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    abstract fun getLayout(): View
    abstract fun initData()
    abstract fun initView()


    var mAgentWeb: AgentWeb? = null


    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        if (!Utils.checkNet(this)) {
            showToast(getString(R.string.not_net))
        }
        mAgentWeb?.webLifeCycle?.onResume()

        super.onResume()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()

    }


}