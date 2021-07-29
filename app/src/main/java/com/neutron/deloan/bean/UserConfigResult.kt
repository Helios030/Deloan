package com.neutron.deloan.bean


data class UserConfigResult(
    val about_us: String,
    val default_amount: String,
    val duration: Any,
    val facebook_login_use: String,
    val hot_tel: String,
    val k_private: String,
    val line: Any,
    val loan_agree: String,
    val riskReportBackImg: String,
    val riskReportUrl: String,
    val sensitive_words_version: String,
    val switch: Boolean,
    val term_of_use: String
)