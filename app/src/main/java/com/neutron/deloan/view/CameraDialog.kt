package com.neutron.deloan.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.neutron.deloan.R


class CameraDialog : Dialog {
    private var btn_ok: TextView? = null
    constructor(context: Context?) : super(context!!, R.style.CustomDialog)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_dialog_layout)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        //初始化界面控件
        initView()
    }
    /**
     * 初始化界面控件
     */
    private fun initView() {
        btn_ok = findViewById<View>(R.id.btn_ok) as TextView
        btn_ok?.setOnClickListener {
            onClickBottomListener?.onOkClick()
        }
    }
    /**
     * 设置确定取消按钮的回调
     */
    var onClickBottomListener: OnClickBottomListener? = null
    fun setOnClickBottomListener(onClickBottomListener: OnClickBottomListener?): CameraDialog {
        this.onClickBottomListener = onClickBottomListener
        return this
    }
    interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        fun onOkClick()
    }
}