package com.neutron.deloan.confirm

import android.annotation.SuppressLint
import android.content.Context
import com.neutron.deloan.NApplication
import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.Utils
import com.ronal.crazy.util.AfPointUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ConfirmPresenter : BasePresenter<ConfirmContract.View>(), ConfirmContract.Presenter {



    @SuppressLint("CheckResult")
    override fun getInfoById(id: String) {
        val userId = PreferencesHelper.getUserID()
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["user_id"] = userId
            map["product_id"] = id
            try {
                mView?.returnInfo(RetrofitUtil.service.confirmInfo(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }





    }

    override fun uploadCallAndSms(context: Context) {
        GlobalScope.launch {
            val phoneEPRE = PreferencesHelper.getPhoneEPRE();
            val phone = PreferencesHelper.getPhone();
            val userId = PreferencesHelper.getUserID()
            val mobile = "$phoneEPRE$phone";
            val record = Utils.getMessage(context);
            if (record.size > 0) {
                val hashMap = HashMap<String, Any>()
                hashMap["user_id"] = userId
                hashMap["self_mobile"] = mobile
                hashMap["account_id"] = mobile
                hashMap["record"] = record
                RetrofitUtil.service.uploadSMS(Utils.createBody(Utils.createCommonParams(hashMap)))
            }





        }
    }

    @SuppressLint("CheckResult")
    override fun uploadRequest(map: HashMap<String, Any>) {


        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                var result =
                    RetrofitUtil.service.uploadRequest(Utils.createBody(Utils.createCommonParams(map)))
                mView?.returnConfirmInfo(result)
                AfPointUtils.userAppsFlyerReturnSuccessDataEvent(
                    Constants.AF_APPPLY_SUCCESS,
                    result.result.loanId.toString(),
                    Constants.EVENT_CODE_APPLY_SUCCESS,
                    PreferencesHelper.getPhone(),
                    NApplication.sContext
                )
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }
        }


    }


}