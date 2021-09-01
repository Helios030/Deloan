package com.neutron.deloan.main

import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.PreferencesHelper

import com.neutron.deloan.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainPresenter : BasePresenter<MainContract.View>(),
    MainContract.Presenter {
    override fun getRequestState() {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["user_id"] = PreferencesHelper.getUserID()
            try {
                mView?.returnRequestState(RetrofitUtil.service.getRequestState(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }

    }

    override fun getUserConfig() {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            try {
                mView?.returnUserConfig(RetrofitUtil.service.getUserConfig(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }
    }
//pay_type     string     true     2     支付默认
    override fun getRepayment() {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>()
            map["pay_type"] = 2
            try {
            mView?.returnRepayment(RetrofitUtil.service.getRepayment(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }
    }


}