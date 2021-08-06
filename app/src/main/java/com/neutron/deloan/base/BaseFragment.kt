package com.neutron.deloan.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils
import com.neutron.deloan.view.LoaddingDialog
import com.neutron.deloan.web.WebViewActivity
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



    open fun startTo(targetClass: Class<out Activity>, isNewTask: Boolean = false) {
        val intent = Intent(activity, targetClass)
        if (isNewTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
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
     var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
     fun showPopWindow(context: Context,title: String, message: String, method: () -> Unit,view: View) {

        val popupView = LayoutInflater.from(context).inflate(R.layout.layout_popwindow, null)


        val tvMessage = popupView.findViewById<TextView>(R.id.tv_pop_message)
        val tvTitle = popupView.findViewById<TextView>(R.id.tv_pop_title)
        tvTitle.text = title
        tvMessage.text = message

        tvMessage.setOnClickListener {
            method.invoke()
        }

        val btnCancel = popupView.findViewById<TextView>(R.id.tv_pop_cancel)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );// 创建参数对象，设置宽和高
        val navHeight = Utils.getNavigationBarHeight(context)
        layoutParams.setMargins(0, 10, 0, navHeight + 10);


        btnCancel.layoutParams = layoutParams
        val lister = View.OnClickListener {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }

        }
        btnCancel.setOnClickListener(lister)
        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0F, Animation.RELATIVE_TO_PARENT, 0F,
            Animation.RELATIVE_TO_PARENT, 1F, Animation.RELATIVE_TO_PARENT, 0F
        )
        animation.interpolator = AccelerateInterpolator()
        animation.duration = 200
        popupWindow.contentView = popupView
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popupWindow.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.isClippingEnabled = false
        if (!popupWindow.isShowing) {
            popupWindow.showAtLocation(
                view,
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                20
            )
            popupView.startAnimation(animation)
            changeBackground(0.5F)
        }
        popupWindow.setOnDismissListener {
            changeBackground(1F)
        }

    }

    private fun changeBackground(alpha: Float) {
        val lp: WindowManager.LayoutParams? = activity?.window?.attributes
        lp?.alpha = alpha
        activity?.window?.attributes = lp
    }


     fun openUri(uri: String,isMain:Boolean,loanStatusResult: LoanStatusResult? =null) {

        startActivity(Intent(activity, WebViewActivity::class.java).apply {
            putExtra(Constants.Intent_URI, "${Constants.H5BaseUri}${uri}")
            putExtra(Constants.IS_MAIN, isMain)
            putExtra(Constants.LOAN_STATUS_RESULT, loanStatusResult)
        })


    }


}

