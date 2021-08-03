package com.neutron.deloan.confirm

import android.annotation.SuppressLint
import com.neutron.deloan.NApplication
import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.*
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