package com.neutron.deloan.bean

data class ConfirmInfoResult(
    val amount: Int,
    val bank_name: String,
    val card_no: String,
    val duration: Int,
    val fee: String,
    val mobile: String,
    val no_ktp: String,
    val pay: String,
    val rate: String,
    val real_name: String,
    val risk: String,
    val service: String
)