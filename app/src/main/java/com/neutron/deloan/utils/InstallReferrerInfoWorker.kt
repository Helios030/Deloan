//package com.ronal.crazy.util
//
//import android.content.Context
//import android.text.TextUtils
//import androidx.work.WorkerParameters
//import com.ronal.crazy.base.BaseWorker
//import com.ronal.crazy.constant.Constant
//import com.ronal.crazy.ext.createBody
//import com.ronal.crazy.ext.createCommonParams
//import com.ronal.crazy.ext.createSign
//
///**
// * @author: Leo
// * @time: 2021/3/31
// * @desc:
// */
//class InstallReferrerInfoWorker(private val context: Context, params: WorkerParameters) :
//    BaseWorker(context, params) {
//
//    private var deviceInfoFactory: DeviceInfoFactory = DeviceInfoFactory.getInstance()
//
//
//    override suspend fun doWork(): Result {
//        return request("installReferer") {
//            val referrer = AppUtils.getReferrer()
//            var hashMap = HashMap<String, Any>()
//            val sign = AppUtils.getSign()
//            val time = System.currentTimeMillis().toString()
//            hashMap = hashMap.createCommonParams(time)
//            hashMap["sign"] = if (TextUtils.isEmpty(sign)) hashMap.createSign() else sign
//            hashMap["uuid"] = deviceInfoFactory.getUUID().toString()
//            hashMap["imei"] = deviceInfoFactory.getIMEI().toString()
//            hashMap["referrer"] = referrer
//            hashMap["download_time"] = deviceInfoFactory.getCurrentTime()
//            mApiRepository.uploadAppReferrer(hashMap.createBody())
//            AfPointUtils.userAppsFlyerReturnDataEvent(
//                Constant.AF_APP_INSTALL, Constant.EVENT_CODE_INSTALL, "", context
//            )
//        }
//    }
//}