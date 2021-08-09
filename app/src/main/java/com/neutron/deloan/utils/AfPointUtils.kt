package com.ronal.crazy.util

import android.content.Context
import android.text.TextUtils
import com.appsflyer.AppsFlyerLib
import java.util.*

/**
 * @author: Leo
 * @time: 2021/4/19
 * @desc:
 */
object AfPointUtils {
    /**
     * AF应用内事件
     * @param eventName
     */
    fun trackEvent(eventName: String?, context: Context) {
        if (!TextUtils.isEmpty(eventName)) {
            val eventValues: Map<String, Any> = HashMap()
            AppsFlyerLib.getInstance().trackEvent(context, eventName, eventValues)
        }
    }

    /**
     * appsFlyer回传数据
     */
    fun userAppsFlyerReturnDataEvent(
        eventName: String?,
        eventCode: String?,
        mobile: String?,
        context: Context
    ) {
        if (!TextUtils.isEmpty(eventCode)) {
            val eventValues: MutableMap<String, Any?> = HashMap()
            if (!TextUtils.isEmpty(mobile)) {
                eventValues["mobile"] = mobile
            }
            eventValues["event_code"] = eventCode
            AppsFlyerLib.getInstance().trackEvent(context, eventName, eventValues)
        }
    }

    /**
     * appsFlyer回传成功数据
     */
    fun userAppsFlyerReturnSuccessDataEvent(
        eventName: String?,
        loanId: String?,
        eventCode: String?,
        mobile: String?,
        context: Context
    ) {
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(loanId)) {
            val eventValues: MutableMap<String, Any?> = HashMap()
            eventValues["mobile"] = mobile
            eventValues["event_code"] = eventCode
            eventValues["loan_id"] = loanId
            AppsFlyerLib.getInstance().trackEvent(context, eventName, eventValues)
        }
    }
}