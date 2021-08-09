package com.neutron.deloan.login

import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.LoginResuleResult
import com.neutron.deloan.bean.SmsLoginResult

class LoginContract {


    interface View : IView {

        fun getCodeState(loginResule: BaseResponse<LoginResuleResult>?)

        fun loginState(smsLoginResults: BaseResponse<SmsLoginResult>)
//
//        fun socialLoginState(it: SocialLoginBean)


    }

    interface Presenter : IPresenter<View> {

        fun getVerificationCode(phoneNumber: String)

        fun testCode(phone: String,code: String,type: String="",id: String="")

//        fun socialLogin(map: HashMap<String, Any>)

    }

}