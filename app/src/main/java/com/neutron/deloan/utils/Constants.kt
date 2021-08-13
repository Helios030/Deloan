package com.neutron.deloan.utils

import android.Manifest
import com.neutron.deloan.NApplication

class Constants {
    companion object {
        const val Intent_URI: String = "Intent_URI"
        const val IS_MAIN: String = "IS_MAIN" //是否从首页进入
        const val LOAN_STATUS_RESULT: String = "LOAN_STATUS_RESULT" //是否从首页进入
        const val ACTIVITY_ACTION_TITLE: String = "ACTIVITY_ACTION_TITLE"
        const val SIGNKEY = "signkey1"
        const val SIGNRANDOMCODE = "signkey2"
        const val Unit = "วัน"
//        const val AF_APP_KEY = "yGBMuxgzaU8tuLWvbjqrA8"
        const val AF_APP_KEY = "BuPCWdVodX32VUQXTtJhbV"
        //  val BaseUri = "http://api.th.golden-union.top/"
        val BaseUri = "https://api.dedeloan.com"
//        val H5BaseUri = "http://192.168.1.79:9930"
        val H5BaseUri = "https://h5.dedeloan.com"
        const val APPROVE = "/#/approve"//      个人认证
        const val BASEINFO = "/#/baseInfo"//       基本信息
        const val WORKINFO = "/#/workInfo"//     工作信息
        const val CONNECTINFO = "/#/connectInfo"//         联系人信息
        const val BANKCARDINFO = "/#/bankCardInfo"//           银行卡信息
        const val MYPROFILE = "/#/myProfile"//        我的资料
        const val RECORD = "/#/record"//        借还款记录
        const val PERIOD = "/#/period"//     展期还款
        const val REPAY = "/#/repay"//       立即还款

        var privacypolicy = PreferencesHelper.getPPrivate()
        val REALM_VERSION = 1L
        val REALM_KEY = NApplication.sContext.packageName
        var PERMISSIONS_LIST: Array<String> = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
        )

        var PERMISSIONS_PHONE_LIST: Array<String> = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )


        //AF中的eventName状态值
        const val AF_APP_INSTALL = "安装"
        const val AF_APP_ACTIVATION = "激活"
        const val AF_APP_LOGIN = "登录成功"
        const val AF_APP_REGISTER = "注册成功"
        const val AF_SUBMIT_BANK_SUCCESS = "提交银行卡成功"
        const val AF_APPPLY_SUCCESS = "提交借款成功"
        const val AF_VA_SUCCESS = "获取还款账号成功"
        const val AF_CLICK_VA = "点击获取还款账号"

        //AF中的eventCode状态值
        const val EVENT_CODE_INSTALL = "install"
        const val EVENT_CODE_LOGIN = "login"
        const val EVENT_CODE_CLICK_VA = "click_va"
        const val EVENT_CODE_VA_SUCCESS = "va_success"
        const val EVENT_CODE_APPLY_SUCCESS = "apply_success"
        const val EVENT_NEW_REGISTER = "register"


    }


}


class MoneyState {
    companion object {

        //可借款 ---显示借款界面
        val STATE_BORROWABLE = 1

        //        申请中
        val STATE_APPLYING = 2

        //        审批拒绝---显示借款界面
        val STATE_APPROVAL_REJECTED = 3

        //        4．待还款-未逾期
        val STATE_PENDING_REPAYMENT = 4

        //        5．放款中
        val STATE_LOANING = 5

        //        6．待还款-逾期
        val STATE_OVERDUE = 6

        //        7．确认申请中
        val STATE_CONFIRM_APPLYING = 7


    }

}

class LoginType {
    companion object {
        val type_register = 1
        val type_login = 2
    }

}

class PreferencesKey {
    companion object {


        const val DEVICE_IMEI = "DEVICE_IMEI"
        const val IS_FIRST = "IS_FIRST"
        const val ABOUT_US = "ABOUT_US"
        const val PPRIVATE = "PPRIVATE"
        const val HOT_TEL = "HOT_TEL"
        const val NAME = "NAME"
        const val LIVENESSID = "LIVENESSID"
        const val REFERRER = "REFERRER"
        const val ISSHOWFEILED = "ISSHOWFEILED"
        const val PRODUCTID = "PRODUCTID"
        const val DEVICEICCID = "DEVICEICCID"
        const val DEVICEID = "DEVICEID"
        const val USERLINE = "USERLINE"
        const val USERPHONE = "USERPHONE"
        const val USERID = "USERID"
        const val PHONEPRE = "PHONEPRE"
        const val USERINFO = "USERINFO"


    }
}

