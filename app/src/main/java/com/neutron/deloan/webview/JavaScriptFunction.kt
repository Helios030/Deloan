package com.neutron.deloan.webview

import android.app.Activity
import android.webkit.JavascriptInterface
import com.google.gson.Gson
import com.just.agentweb.AgentWeb
import com.neutron.deloan.NApplication
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.Utils
import com.ronal.crazy.util.AfPointUtils
import java.util.*


//getToolbarHeight 导航高度
//isToBorrow 从首页进来
//closeSelf 原生返回
//getUser 用户信息
//getHeaders 公共请求头部信息
//selectContact 选择联系人
//setInterceptOpenType 拍照0 选照片1
//refreshStatus 更新状态
//jumpLive 跳活体
//getProduct 获取还款信息
//submitBankCardSuccess 提交银行卡信息完毕
//repaySuccess 还款成功


class JavaScriptFunction(isToBorrow: Boolean, listener: Listener?) {
    private var lStatusResult: LoanStatusResult? = null

    constructor(isToBorrow: Boolean, loanStatusResult: LoanStatusResult, listener: Listener?) : this(isToBorrow, listener) {
        lStatusResult = loanStatusResult
    }



    private var isToBorrow = false



    private var openType = 0

    @JavascriptInterface
    fun setOpenType(type: Int) {
        openType = type
    }
    fun getOpenType(): Int {
        return openType
    }


    private val listener: Listener?

    @JavascriptInterface
    fun isToBorrow(): Int {
        return if (isToBorrow) 1 else 0
    }

    @JavascriptInterface
    fun getToolbarHeight(): Int{
        return    48
    }


    @JavascriptInterface
    fun closeSelf() {
        listener?.setResult(Activity.RESULT_OK, true)
    }

    @JavascriptInterface
    fun getUser(): String {
        return Gson().toJson(PreferencesHelper.getUserInfo());
    }

    @JavascriptInterface
    fun getProduct(): String? {
        return Gson().toJson(lStatusResult)
    }


    @JavascriptInterface
    fun startContact(index: Int) {
        Slog.d("startContact  $index")
        listener?.onStartContact(index)
    }

    @JavascriptInterface
    fun jumpLive() {
        listener?.onStartLive()
    }

    @JavascriptInterface
    fun getCommonHeaders(): String {
//     val body=   Utils.createBody(Utils.createCommonParams(HashMap<String, Any>()))
//        Slog.d("body $body")
     val str=   Gson().toJson(Utils.createCommonParams(HashMap<String, Any>()))
        Slog.d("str $str")
        return str
    }


    fun sendContact(webView: AgentWeb?, contact: HashMap<String, String>, index: Int) {
      val value=  "javascript:getAddressBook" + index+ "('"+ Gson().toJson(contact)+ "')"
        Slog.d("发送联系人  $value")
        webView?.webCreator?.webView?.loadUrl(
            value
        )

    }

    interface Listener {
        fun onStartContact(requestCode: Int)
        fun onStartLive()
        fun setResult(resultCode: Int, isFinish: Boolean)
    }

    companion object {
        const val NOTHING = -99
        const val OPEN_CAMERA = 0
        const val OPEN_GALLERY = 1
        const val START_CONTACT = 0x1111
    }

    init {
        this.isToBorrow = isToBorrow
        this.listener = listener
    }

    //    请求成功
    @JavascriptInterface
    fun submitVASuccess() {
        AfPointUtils.userAppsFlyerReturnDataEvent(
            Constants.AF_VA_SUCCESS,
            Constants.EVENT_CODE_VA_SUCCESS,
            PreferencesHelper.getPhone(),  NApplication.sContext
        )

    }
//    发起请求
    @JavascriptInterface
    fun clickVASuccess(phone:String) {
        AfPointUtils.userAppsFlyerReturnDataEvent(
            Constants.AF_CLICK_VA,
            Constants.EVENT_CODE_CLICK_VA,
            PreferencesHelper.getPhone(), NApplication.sContext
        )
    }





    @JavascriptInterface
    fun submitBankCardSuccess() {
        AfPointUtils.trackEvent(Constants.AF_SUBMIT_BANK_SUCCESS, NApplication.sContext)
    }


    @JavascriptInterface
    fun repaySuccess() {
        listener?.setResult(Activity.RESULT_OK, false)
    }


    @JavascriptInterface
    fun refreshStatus() {
//       需要刷新
        listener?.setResult(Activity.RESULT_OK, false)
    }

}