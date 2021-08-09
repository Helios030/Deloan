package com.neutron.deloan.net

import com.neutron.deloan.utils.Constants


object RetrofitUtil : BaseRetrofitClient() {

val service by lazy {
    getService(NetWorkService::class.java, Constants.BaseUri)
}



}
