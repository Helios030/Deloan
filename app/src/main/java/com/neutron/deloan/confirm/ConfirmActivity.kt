package com.neutron.deloan.confirm


import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.ConfirmInfoResult
import com.neutron.deloan.bean.RequestOrderResult
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.*
import com.neutron.deloan.view.CommDialog
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.toolbar_common.*

class ConfirmActivity : BaseActivity<ConfirmContract.View, ConfirmContract.Presenter>(),
    ConfirmContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_confirm
    }

    var productID: String? = null
    override fun initData() {
        productID = PreferencesHelper.getProductId()
        getInfoById(productID)
//仅线下
//        uploadCallAndSMS()
    }
//    private fun uploadCallAndSMS() {
//        mPresenter?.uploadCallAndSms(this)
//    }
    private fun getInfoById(id: Any?) {
        id?.let {
            showLoading()
            mPresenter?.getInfoById(id.toString())

        }
    }

    override fun initView() {


    initToolbar(getString(R.string.confirm_request),true)

        btn_confirm.setOnClickListener {
            val userId = PreferencesHelper.getUserID()
            val livenessId = PreferencesHelper.getLivenessID()
            showLoading()

            val dataMap = HashMap<String, Any>()

if(PreferencesHelper.isReRequest()){
    dataMap["user_id"] = userId
    dataMap["product_id"] = productID?:""
    mPresenter?.uploadRERequest(dataMap)

}else{
    dataMap["user_id"] = userId
    dataMap["product_id"] = productID?:""
    dataMap["file"] = livenessId?:""
    dataMap["method"] = "advance"
    mPresenter?.uploadRequest(dataMap)
}





        }

    }


    override fun returnInfo(result: BaseResponse<ConfirmInfoResult>) {


        Slog.d("确认信息   $result")

        val confirmInfoResult = result.result
        tv_daily_interest_rate.text = confirmInfoResult?.rate
        tv_fees_for_technical_services.text = confirmInfoResult?.risk
        tv_audit_consulting_responsibility.text = confirmInfoResult?.service
        tv_pay_the_fee.text = confirmInfoResult?.pay
        tv_money.text = confirmInfoResult?.amount.toString()
        tv_money_date.text = "${confirmInfoResult?.duration} วัน"

        hideLoading()

    }

    override fun returnConfirmInfo(result: BaseResponse<RequestOrderResult>) {
        hideLoading()
        if (result.code == "200") {


            val dialog = CommDialog(this)
                .setMessage(UIUtils.getString(R.string.confirm_tip))
            dialog.setOnClickBottomListener(object : CommDialog.OnClickBottomListener {
                override fun onOkClick() {
                    PreferencesHelper.setProductId("")
                    toast(R.string.data_submitted_successfully)
                    startTo(MainActivity::class.java, true)
                }
            })
            dialog.show()


        } else {
            toast(result.message)
        }
    }

    override fun setPresenter(): ConfirmContract.Presenter {
        return ConfirmPresenter()
    }


}