package com.neutron.deloan.net

import com.neutron.deloan.utils.Constants
import com.neutron.deloan.net.BaseRetrofitClient
import com.neutron.deloan.net.NetWorkService


object RetrofitUtil : BaseRetrofitClient() {

val service by lazy {
    getService(NetWorkService::class.java, Constants.BaseUri)
}



}
