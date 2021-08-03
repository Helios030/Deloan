package com.neutron.deloan.bean

import java.io.Serializable

data class UserInfo(
    val user_id: String? = null,
    val userName: String? = null,
    val signKeyToken: String? = null,
    val vcode: String? = null,
    val phone: String? = null,
    val phonepre: String? = null,
    val register: String? = null
) : Serializable