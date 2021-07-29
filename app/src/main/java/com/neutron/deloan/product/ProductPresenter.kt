package  com.neutron.deloan.product

import  com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import  com.neutron.deloan.utils.PreferencesHelper
import  com.neutron.deloan.utils.Slog
import  com.neutron.deloan.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class ProductPresenter : BasePresenter<ProductContract.View>(),
    ProductContract.Presenter {


    override fun getProducts() {
        Slog.d("getProducts ---")
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




}