package com.neutron.deloan.confirm

import android.content.Context
import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.ConfirmInfoResult
import com.neutron.deloan.bean.RequestOrderResult
import java.util.*

class ConfirmContract {

    interface View : IView {

        fun returnInfo(confirmInfoResult: BaseResponse<ConfirmInfoResult>)

        fun returnConfirmInfo(result: BaseResponse<RequestOrderResult>)


    }

    interface Presenter : IPresenter<View> {

        fun getInfoById(id: String)

        fun uploadCallAndSms(context: Context)


        fun uploadRequest(map: HashMap<String, Any>)

    }

}