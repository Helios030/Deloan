package com.neutron.deloan.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.neutron.deloan.R


class CommDialog : Dialog {

    /**
     * 确认
     */
    private var btn_ok: TextView? = null
    private var btn_cancel: TextView? = null
    private var tvMessage: TextView? = null
    private var tvTitle: TextView? = null

    constructor(context: Context?) : super(context!!, R.style.CustomDialog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_dialog_layout)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        //初始化界面控件
        initView()

    }

    var message: String? = null

    fun setMessage(message: String?): CommDialog {
        this.message = message
        return this
    }

    var title: String? = null

    fun setTitle(message: String?): CommDialog {
        this.title = title
        return this
    }





    override fun show() {
        super.show()
        refreshView()
    }

    private fun refreshView() {


        if (!TextUtils.isEmpty(message)) {
            tvMessage!!.text = message
        }



    }

    override fun dismiss() {
        super.dismiss()

    }

    /**
     * 初始化界面控件
     */
    private fun initView() {
        btn_ok = findViewById<View>(R.id.btn_ok) as TextView
        tvMessage = findViewById<View>(R.id.tv_message) as TextView



        btn_ok?.setOnClickListener {
            onClickBottomListener?.onOkClick()
        }

        refreshView()


    }


    /**
     * 设置确定取消按钮的回调
     */
    var onClickBottomListener: OnClickBottomListener? = null
    fun setOnClickBottomListener(onClickBottomListener: OnClickBottomListener?): CommDialog {
        this.onClickBottomListener = onClickBottomListener
        return this
    }


    interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        fun onOkClick()

        /**
         * 点击确定按钮事件
         */
//        fun onCancelClick()
    }


}