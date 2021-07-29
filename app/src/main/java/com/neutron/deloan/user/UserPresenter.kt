package  com.neutron.deloan.user

import  com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import  com.neutron.deloan.utils.PreferencesHelper

import  com.neutron.deloan.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class UserPresenter : BasePresenter<UserContract.View>(),
    UserContract.Presenter {


    override fun getProducts() {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            try {
                mView?.returnRequestState(RetrofitUtil.service.getProducts(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }
    }

    override fun getUserStatus(isClick: Boolean) {
        job = GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            try {
                mView?.returnUserStatus(isClick,RetrofitUtil.service.getUserStatus(Utils.createBody(Utils.createCommonParams(map))))
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
//                mView?.returnRequestState(RetrofitUtil.service.getUserConfig(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }
    }


}