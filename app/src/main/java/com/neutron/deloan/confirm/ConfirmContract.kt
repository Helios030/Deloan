package com.neutron.deloan.confirm

import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.ConfirmInfoResult
import com.neutron.deloan.bean.RequestOrderResult
import com.neutron.deloan.net.BaseResponse
import java.util.*

class ConfirmContract {

    interface View : IView {

        fun returnInfo(confirmInfoResult: BaseResponse<ConfirmInfoResult>)

        fun returnConfirmInfo(result: BaseResponse<RequestOrderResult>)


    }

    interface Presenter : IPresenter<View> {

        fun getInfoById(id: String)


        fun uploadRequest(map: HashMap<String, Any>)

    }

}