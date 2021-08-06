package com.neutron.deloan.login


import android.annotation.SuppressLint
import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.LoginType
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LoginPresenter : BasePresenter<LoginContract.View>(),
    LoginContract.Presenter {


    @SuppressLint("CheckResult")
    override fun getVerificationCode(phoneNumber: String) {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["phone"] = phoneNumber
            map["type"] = LoginType.type_login
            Slog.d("getVerificationCode $map")
            try {
                mView?.getCodeState(RetrofitUtil.service.sendSms(Utils.createBody(Utils.createCommonParams(map))))

            } catch (e: Exception) {
                e.printStackTrace()
                Slog.e("请求验证码出错  $e")
                mView?.showError(e)
            }

        }
    }

    override fun testCode(phone: String, code: String, type: String, id: String) {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["phone"] = phone
            map["vcode"] = code
//            map["socialType"] = type
//            map["socialId"] = id
            try {
                mView?.loginState(RetrofitUtil.service.smsLogin(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }
        }
    }


//    @SuppressLint("CheckResult")
//    override fun testCode(code: String,type: String,id: String) {
//        if (number == null) {
//            Slog.e("验证码提交 电话号码为空")
//            return
//        }
//        val map = HashMap<String, Any>();
//        map["phone"] = number!!
//        map["vcode"] = code
//        map["socialType"] = type
//        map["socialId"] = id
//
//
//
//        Slog.d("注册参数设置  $map")
//        val requestBody = Utils.createBody(Utils.createCommonParams(map))
//        RetrofitUtil.instance.getRetrofit(false, Constants.BaseUri)
//            .create(NetWorkService::class.java)
//            .smsLogin(requestBody)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//                mView?.loginState(it)
//
//
//
//
//            }, { Slog.d("验证码访问失败  $it") }, {
//                Slog.d("验证码完毕")
//            })
//
//
//    }
//
//    @SuppressLint("CheckResult")
//    override fun socialLogin(map: HashMap<String, Any>) {
//        val requestBody = Utils.createBody(Utils.createCommonParams(map))
//        RetrofitUtil.instance.getRetrofit(false, Constants.BaseUri)
//            .create(NetWorkService::class.java)
//            .socialLogin(requestBody)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                mView?.socialLoginState(it)
//
//
//
//
//            }, { Slog.d("第三方访问失败  $it") })
//
//    }


}