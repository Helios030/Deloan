package  com.neutron.deloan.product

import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.ProductsResult
import com.neutron.deloan.bean.UserStatusResult

class ProductContract {


    interface View : IView {

        fun returnRequestState(products: BaseResponse<List<ProductsResult>>)
        fun returnUserStatus(click: Boolean, userStatus: BaseResponse<UserStatusResult>)

    }

    interface Presenter : IPresenter<View> {

        fun getProducts()

        fun getUserStatus(isClick:Boolean)


    }

}