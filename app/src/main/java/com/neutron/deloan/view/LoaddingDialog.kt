package com.neutron.deloan.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.neutron.deloan.R


class LoaddingDialog : Dialog {


    constructor(context: Context?) : super(context!!, R.style.CustomDialog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_load)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)

    }

}