package com.neutron.deloan.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.utils
 * @ClassName:      PreferencesUtil
 * @Description:    封装SP 可以存取任意类型数据
 * @Author:         Ben
 * @CreateDate:     2019/10/15 15:3 1
 */
object PreferencesUtil {
    private val fileName = "OSport_config"


    //传统sp
//    private val prefs: SharedPreferences by lazy { UIUtils.getContext().getSharedPreferences(name, Context.MODE_PRIVATE) }
    //加密sp
    private val prefs: SharedPreferences by lazy {

        if (Build.VERSION.SDK_INT >= 23) {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            EncryptedSharedPreferences.create(
                fileName,
                masterKeyAlias,
                UIUtils.getContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
//            加密方式仅支持sdk23以上 SP 里面没有保存任何重要的个人信息
            UIUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }


    }

    fun createSP() {

    }

    /**
     * 获取存放数据
     * @return 值
     */
    @Suppress("UNCHECKED_CAST")
    fun getValue(key: String, default: Any): Any = with(prefs) {
        return when (default) {
            is Int -> getInt(key, default)
            is String -> this.getString(key, default)!!
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }
    }

    fun getString(key: String, default: String = ""): String {
        return getValue(key, default) as String
    }

    fun getInt(key: String, default: Int = 0): Int {
        return getValue(key, default) as Int
    }

    fun getLong(key: String, default: Long = 0): Long {
        return getValue(key, default) as Long
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getValue(key, default) as Boolean
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return getValue(key, default) as Float
    }

    /**
     * 存放SharedPreferences
     * @param key 键
     * @param value 值
     */
    fun saveValue(key: String, value: Any) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }.apply()
    }

    /**
     * 清除
     */
    fun clear() {
        prefs.edit().clear().apply()
    }

    /**
     * 删除某Key的值
     */
    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
}