package com.neutron.deloan.net

data class BaseResponse<T>(
    val code: String,
    val message: String,
    val result: T,
    val sign: String
    )

data class BaseResponseNoData(
    val code: String,
    val message: String,
    val sign: String
)