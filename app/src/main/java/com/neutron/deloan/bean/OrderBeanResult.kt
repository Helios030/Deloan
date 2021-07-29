package com.neutron.deloan.bean


data class OrderBeanResult(
    val amount2Account: String,
    val app_time: String,
    val application_id: String,
    val apply_time: Long,
    val approval_time: Any,
    val bank_code: String,
    val bid_status: Int,
    val borrow_time: String,
    val borrow_time_kim: String,
    val card_holder: String,
    val card_no: String,
    val deposit_time: String,
    val duration: String,
    val fale_fee: String,
    val fee: String,
    val has_loan_app: Boolean,
    val interest: String,
    val loan_status: Int,
    val no_ktp: String,
    val pay_time: String,
    val paymentAmount: String,
    val penalty: String,
    val penalty_days: Int,
    val principal: String,
    val reach_amount: String,
    val reach_time: Long,
    val remainAmount: String,
    val secAppTime: Any
)