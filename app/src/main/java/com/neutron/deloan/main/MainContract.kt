package com.neutron.deloan.main

import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.bean.UserConfigResult
import com.neutron.deloan.bean.BaseResponse

class MainContract {


    interface View : IView {
        fun returnRequestState(loanStatus: BaseResponse<LoanStatusResult>)
        fun returnUserConfig(userConfig: BaseResponse<UserConfigResult>)
    }

    interface Presenter : IPresenter<View> {
        fun getRequestState()
        fun getUserConfig()
    }

}