package  com.neutron.deloan.user

import  com.neutron.deloan.base.IPresenter
import  com.neutron.deloan.base.IView
import  com.neutron.deloan.bean.ProductsResult
import  com.neutron.deloan.bean.UserStatusResult
import  com.neutron.deloan.net.BaseResponse

class UserContract {


    interface View : IView {
        fun returnRequestState(products: BaseResponse<List<ProductsResult>>)

        fun returnUserStatus(click: Boolean, userStatus: BaseResponse<UserStatusResult>)


    }

    interface Presenter : IPresenter<View> {

        fun getProducts()

        fun getUserStatus(isClick:Boolean)

        fun getUserConfig()
    }

}