package com.neutron.deloan.utils

import com.neutron.deloan.bean.UserInfo


object PreferencesHelper {
    fun getAboutUs(): String = PreferencesUtil.getString(PreferencesKey.ABOUT_US, "https://privacy.dedeloan.com")
    fun setAboutUs(str: String) = PreferencesUtil.saveValue(PreferencesKey.ABOUT_US, str)


    //    todo 发布版替换
    fun getPPrivate(): String =
        PreferencesUtil.getString(PreferencesKey.PPRIVATE, "https://privacy.dedeloan.com")

    fun setPPrivate(str: String) = PreferencesUtil.saveValue(PreferencesKey.PPRIVATE, str)


    fun getUserInfo(): UserInfo {
        val beanStr = PreferencesUtil.getString(PreferencesKey.USERINFO, "")
        return Utils.toBean(beanStr) as UserInfo
    }

    fun setUserInfo(user: UserInfo) =
        PreferencesUtil.saveValue(PreferencesKey.USERINFO, Utils.fromBean(user))


    fun getPhoneEPRE(): String = PreferencesUtil.getString(PreferencesKey.PHONEPRE, "")
    fun setPhoneEPRE(phone: String) = PreferencesUtil.saveValue(PreferencesKey.PHONEPRE, phone)


    fun getPhone(): String = PreferencesUtil.getString(PreferencesKey.USERPHONE, "")
    fun setPhone(phone: String) = PreferencesUtil.saveValue(PreferencesKey.USERPHONE, phone)

    fun getUserID(): String = PreferencesUtil.getString(PreferencesKey.USERID, "")
    fun setUserID(id: String) = PreferencesUtil.saveValue(PreferencesKey.USERID, id)


    fun getIMEI(): String = PreferencesUtil.getString(PreferencesKey.DEVICE_IMEI, "")
    fun setIMEI(imei: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICE_IMEI, imei)


    fun isFirstStart(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_FIRST, true)
    fun setFirstStart(isFirst: Boolean) =
        PreferencesUtil.saveValue(PreferencesKey.IS_FIRST, isFirst)



    fun getLine(): String = PreferencesUtil.getString(PreferencesKey.USERLINE, "")
    fun setLine(str: String) = PreferencesUtil.saveValue(PreferencesKey.USERLINE, str)


    fun getHotTel(): String = PreferencesUtil.getString(PreferencesKey.HOT_TEL, "")
    fun setHotTel(str: String) = PreferencesUtil.saveValue(PreferencesKey.HOT_TEL, str)

    fun getRealname(): String = PreferencesUtil.getString(PreferencesKey.NAME, "")
    fun setRealname(str: String) = PreferencesUtil.saveValue(PreferencesKey.NAME, str)


    fun getLivenessID(): String = PreferencesUtil.getString(PreferencesKey.LIVENESSID, "")
    fun setLivenessID(str: String) = PreferencesUtil.saveValue(PreferencesKey.LIVENESSID, str)

    fun getReferrer(): String = PreferencesUtil.getString(PreferencesKey.REFERRER, "")
    fun setReferrer(str: String) = PreferencesUtil.saveValue(PreferencesKey.REFERRER, str)

    fun getProductId(): String = PreferencesUtil.getString(PreferencesKey.PRODUCTID, "")
    fun setProductId(str: String) = PreferencesUtil.saveValue(PreferencesKey.PRODUCTID, str)


    fun isShowFeiled(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.ISSHOWFEILED, true)
    fun setShowFeiled(isShow: Boolean) =
        PreferencesUtil.saveValue(PreferencesKey.ISSHOWFEILED, isShow)


    fun getDeviceIccid(): String = PreferencesUtil.getString(PreferencesKey.DEVICEICCID, "")
    fun setDeviceIccid(str: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICEICCID, str)


    fun getDeviceId(): String = PreferencesUtil.getString(PreferencesKey.DEVICEID, "")
    fun setDeviceId(str: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICEID, str)


    fun getUploadTime(): Long = PreferencesUtil.getLong(PreferencesKey.UPLOA_DTIME, 0L)
    fun setUploadTime(time: Long) = PreferencesUtil.saveValue(PreferencesKey.UPLOA_DTIME, time)

//    fun IsNeedFace(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.NEEDFACE, true)
//    fun setIsNeedFace(isCheck: Boolean) = PreferencesUtil.saveValue(PreferencesKey.NEEDFACE,isCheck)


}