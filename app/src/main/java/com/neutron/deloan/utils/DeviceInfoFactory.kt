package com.ronal.crazy.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.neutron.deloan.NApplication
import com.neutron.deloan.utils.PreferencesHelper
import java.io.File
import java.io.FileFilter
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: weiyun
 * @Time: 2019/10/19
 * @Description:设备信息
 */
@SuppressLint("HardwareIds")
class DeviceInfoFactory {
    private val mContext by lazy { NApplication.sContext }
    private var mTelephonyManager: TelephonyManager? = null
    private var mUUID: UUID? = null
    private var mIMEI: String? = ""

    companion object {

        private var INSTANCE: DeviceInfoFactory? = null

        @JvmStatic
        fun getInstance(): DeviceInfoFactory {
            return INSTANCE
                ?: DeviceInfoFactory().apply {
                    INSTANCE = this
                }
        }
    }

    init {
        mTelephonyManager =
            mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
    }

    /**
     *获取UUID
     */

    fun getUUID(): UUID? {
        val id: String? = PreferencesHelper.getDeviceId()
        if (!id.isNullOrBlank()) {
            mUUID = UUID.fromString(id)
        } else {
            val androidId =
                Settings.Secure.getString(mContext.contentResolver, Settings.Secure.ANDROID_ID)
            var tmDevice: String? = ""
            var tmSerial: String? = ""
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (checkPermission(mContext, permission.READ_PHONE_STATE)) {
                        tmDevice = mTelephonyManager?.deviceId
                        tmSerial = mTelephonyManager?.simSerialNumber
                    }
                }

                if (TextUtils.isEmpty(tmDevice) || tmDevice == "unknown"
                    || TextUtils.isEmpty(tmSerial) || tmDevice == "000000000000000"
                ) {
                    if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
                        mUUID = UUID.nameUUIDFromBytes(androidId.toByteArray())
                    } else {
                        mUUID = UUID.randomUUID()
                    }
                } else {
                    mUUID = UUID(
                        androidId.hashCode().toLong(),
                        tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong()
                    )
                }
                mUUID?.let {
                    PreferencesHelper.setDeviceId(it.toString())
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        return mUUID
    }

    /**
     *获取IMEI
     */
    fun getIMEI(): String? {
        mIMEI = PreferencesHelper.getIMEI()
        if (mIMEI?.isNotEmpty() == true) {
            return mIMEI
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            try {
                if (checkPermission(mContext, permission.READ_PHONE_STATE)) {
                    mIMEI = mTelephonyManager?.deviceId
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mIMEI = getUUID().toString()
            }
        } else {
            mIMEI = getUUID().toString()
        }
        mIMEI?.let { PreferencesHelper.setIMEI(it) }

        return mIMEI
    }

    /**
     * 获取设备ICCID(sim)
     */
    fun getSim(): String? {
        var iccId: String? = PreferencesHelper.getDeviceIccid()
        if (iccId?.isNotEmpty() == true) {
            return iccId
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (checkPermission(mContext, permission.READ_PHONE_STATE)) {
                iccId = mTelephonyManager?.simSerialNumber
            }
        } else {
            iccId = getUUID().toString()
        }

        iccId?.let { PreferencesHelper.setDeviceIccid(it) }
        return iccId
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    fun getBrands(): String {
        return android.os.Build.BRAND
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    fun getMobil(): String {
        return android.os.Build.MODEL
    }

    /**
     * CPU型号
     *
     * @return
     */
    fun getCpuModel(): String {
        return android.os.Build.CPU_ABI
    }


    /**
     * 获取当前手机系统版本号
     */
    fun getSystemVersion(): String {
        return Build.VERSION.RELEASE
    }

    /**
     * 屏幕分辨率
     * @return
     */
    fun getResolution(): String {
        val metric = DisplayMetrics()
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getRealMetrics(metric)
        val width = metric.widthPixels
        val height = metric.heightPixels;
        val densityDpi = metric.densityDpi;
        return "$width×$height -$densityDpi"
    }

    /**
     * 获取wifi名称
     */
    @SuppressLint("WifiManagerPotentialLeak", "MissingPermission")
//    @Deprecated("WifiName")
    fun getWifiName(): String {
        val wifiMgr: WifiManager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiMgr.connectionInfo

        val networkId: Int = info.networkId
        val configuredNetworks: MutableList<WifiConfiguration> = wifiMgr.configuredNetworks
        var ssid = ""
        run breaking@{
            configuredNetworks.forEach {
                if (it.networkId == networkId) {
                    ssid = it.SSID
                    return@breaking
                }
            }
        }
        if (ssid.contains("\"")) {
            ssid = ssid.replace("\"", "")
        }
        return ssid
    }

    @Suppress("DEPRECATION")
    fun getNewWifiName(): String {
        val ssid = "unknown id"

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val wifiMgr: WifiManager =
                mContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifiMgr.connectionInfo
            return info.ssid.replace("\"", "")
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            val connManager: ConnectivityManager = mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connManager.activeNetworkInfo
            if (networkInfo?.isConnected == true) {
                if (networkInfo.extraInfo != null) {
                    return networkInfo.extraInfo.replace("\"", "")
                }
            }
        }
        return ssid
    }

    /**
     * 获取wifi mac 地址
     */
    fun getWifiMac(): String {
        val networkInterfaces: Enumeration<NetworkInterface> =
            NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val element: NetworkInterface? = networkInterfaces.nextElement()
            val address: ByteArray = element?.hardwareAddress ?: continue
            if (element.name == "wlan0") {
                val builder = StringBuilder()
                address.forEach {
                    builder.append(String.format("%02X:", it))
                }
                if (builder.isNotEmpty()) {
                    builder.deleteCharAt(builder.length - 1)
                }
                return builder.toString()
            }
        }
        return ""
    }

    /**
     * 获取wifi信号强度 state
     */
    @SuppressLint("WifiManagerPotentialLeak")
    fun getWifiState(): String {
        val wifiMgr: WifiManager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiMgr.connectionInfo
        if (info.ssid != null) {
            val strength: Int = WifiManager.calculateSignalLevel(info.rssi, 5)
            return strength.toString()
        }
        return ""
    }


    /*
     * 打开App电量
     * @return [Int] 电量
     */
    var openAppBatteryLevel: Int? = 0

    /**
     * 获取电量
     * @return [Int] 电量
     */
    fun getBatteryLevel(): Int? {
        val intent: Intent? =
            ContextWrapper(mContext).registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
        return intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)!! * 100 /
                intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val batteryManager: BatteryManager? =
//                mContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//            block(batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))
//        } else {
//            val intent: Intent? =
//                ContextWrapper(mContext).registerReceiver(
//                    null,
//                    IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//                )
//            block(intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)!! * 100 /
//                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1))
//        }
    }

    /**
     * 获取开启App时间
     */
    var openAppTime: String = ""

    /**
     * 判断手机是否截屏
     */
    var whetherScreenshot: Int = 0

    /**
     * 获取当前时间
     */
    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return SimpleDateFormat(format).format(GregorianCalendar().time)
    }



    /**
     * 获取RAM
     */
    fun getRAMInfo(): String {
        val manager: ActivityManager =
            mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(memoryInfo)
        val totalSize: Long = memoryInfo.totalMem
        val availableSize: Long = memoryInfo.availMem
        return (Formatter.formatFileSize(mContext, availableSize) + "/"
                + Formatter.formatFileSize(mContext, totalSize))
    }


    /**
     * 获取Rom
     *
     * @return
     */
    @Suppress("DEPRECATION")
    fun getRomInfo(): String {
        val file: File = Environment.getExternalStorageDirectory()
        val statFs = StatFs(file.path)
        val blockSizeLong = statFs.blockSizeLong
        val blockCountLong = statFs.blockCountLong
        val availableBlocksLong = statFs.availableBlocksLong
        return Formatter.formatFileSize(mContext, availableBlocksLong * blockSizeLong) + "/" +
                Formatter.formatFileSize(mContext, blockCountLong * blockSizeLong)
    }

    /**
     * CPU核数
     *
     * @return
     */
    fun getCpuCores(): String {
        val count = getNumberOfCPUCores()
        return count.toString() + ""
    }

    private fun getNumberOfCPUCores(): Int {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            return 1
        }
        var cores: Int
        try {
            cores = File("/sys/devices/system/cpu/").listFiles(CPU_FILTER)!!.size
        } catch (e: SecurityException) {
            cores = 0
        }

        return cores
    }

    private val CPU_FILTER = FileFilter { pathname ->
        val path = pathname.name
        if (path.startsWith("cpu")) {
            for (i in 3 until path.length) {
                if (path[i] < '0' || path[i] > '9') {
                    return@FileFilter false
                }
            }
            return@FileFilter true
        }
        false
    }


    /**
     * 是否root或是否实体
     * 1是root机和虚拟机
     * 0是非root机和实体机
     */
    fun isSuEnableRoot(): Int {
        var file: File? = null
        val paths = arrayOf(
            "/system/bin/",
            "/system/xbin/",
            "/system/sbin/",
            "/sbin/",
            "/vendor/bin/",
            "/su/bin/"
        )
        try {
            for (path in paths) {
                file = File(path + "su")
                if (file.exists() && file.canExecute()) {
                    Log.i("TAG", "find su in : $path")
                    return 1
                }
            }
        } catch (x: Exception) {
            x.printStackTrace()
        }
        return 0
    }

    var virtualMachine: Int = 1
    fun checkDeviceInfo(): Boolean {
        var result: Boolean = (Build.FINGERPRINT.startsWith("generic") // 唯一识别码
                || Build.MODEL.contains("google_sdk") // 版本 用户最终可以见的名称
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion") // 硬件制造商
                || Build.HARDWARE == "goldfish" || Build.HARDWARE == "vbox86" || Build.PRODUCT == "sdk" || Build.PRODUCT == "google_sdk" || Build.PRODUCT == "sdk_x86" || Build.PRODUCT == "vbox86p" || Build.BOARD.toLowerCase()
            .contains("nox") // 主板
                || Build.BOOTLOADER.toLowerCase().contains("nox") // 系统启动程序版本号
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.SERIAL.toLowerCase().contains("nox")) // 硬件序列号
        if (result) return true
        result =
            result or (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
                "generic"
            ))
        if (result) return true
        result = result or ("google_sdk" == Build.PRODUCT)
        return result
    }

    /**
     * 检查权限
     */
    private fun checkPermission(context: Context, permName: permission): Boolean {
        val perm: Int = context.checkCallingOrSelfPermission("android.permission.$permName")
        return perm == PackageManager.PERMISSION_GRANTED
    }

    private enum class permission {
        READ_PHONE_STATE,
        WRITE_EXTERNAL_STORAGE
    }
}