package com.neutron.deloan.bean

import java.io.Serializable


/**
 * 上传设备信息
 */
data class UploadDeviceInfo(
    val user_id: String?,
    val imei: String?,
    val brands: String?,
    val mobile_model: String?,
    val mobile: String?,
    val cpu_model: String?,
    val cpu_cores: String?,
    val ram: String?,
    val rom: String?,
    val resolution: String?,
    val open_power: String?,
    val open_time: String?,
    val version: String?,
    val complete_apply_power: String?,
    val complete_apply_time: String?,
    val back_num: String?,
    val screen: String?,
    val root: String?,
    val wifi_name: String?,
    val wifi_mac: String?,
    val wifi_state: String?,
    val real_machine: String?,
    val hit_num: String?,
    val applist: MutableList<AppList>?
) : Serializable


data class AppList(
    val firstTime: String?,
    val lastTime: String?,
    val name: String?,
    val packageName: String?,
    val versionCode: String?,
    val systemApp: String?
) : Serializable