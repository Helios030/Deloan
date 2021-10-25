package com.neutron.deloan.bean

import java.io.Serializable

data class LoanStatusResult(
    val amount2Account: String,
    val app_time: String,
    val application_id: String,
    val bank_code: String,
    val borrow_time: String,
    val card_holder: String,
    val card_no: String,
    val data_status: Int,
    val deposit_time: String,
    val duration: String,
    val fale_fee: String,
    val fee: String,
    val interest: String,
    val loan_status: String,
    val no_ktp: String,
    val paymentAmount: String,
    val penalty: String,
    val penalty_days: Int,
    val principal: String,
    val reach_amount: String,
    val remainAmount: String,

    val reApplyFace: String,
    val has_loan_app: String,

    val risk: String,
    val service: String,
    val pay: String,
    val va: String,
    val valid_time: String
):Serializable