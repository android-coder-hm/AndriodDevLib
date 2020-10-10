package com.developlib.util

import android.content.Context
import com.developlib.ConfigSingleton.getAppContext

/**
 * share键值对存储工具
 * 后面会废弃采用 最新  dataSource 存储
 */

fun putShareValue(shareFileName: String, keyName: String, value: Any, model: Int = Context.MODE_PRIVATE) {
    with(getAppContext().getSharedPreferences(shareFileName, model).edit()) {
        when (value) {
            is Long -> putLong(keyName, value)
            is Int -> putInt(keyName, value)
            is String -> putString(keyName, value)
            is Float -> putFloat(keyName, value)
            is Boolean -> putBoolean(keyName, value)
            else -> throw IllegalArgumentException("sharedPreferences 类型错误")
        }.apply()
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> getShareValue(shareFileName: String, keyName: String, defaultValue: T, model: Int = Context.MODE_PRIVATE): T {
    with(getAppContext().getSharedPreferences(shareFileName, model)) {
        return when (T::class) {
            Int::class -> getInt(keyName, if (defaultValue is Int) defaultValue else 0)
            String::class -> getString(keyName, if (defaultValue is String) defaultValue else "")
            Long::class -> getLong(keyName, if (defaultValue is Long) defaultValue else 0L)
            Float::class -> getFloat(keyName, if (defaultValue is Float) defaultValue else 0.0f)
            Boolean::class -> getBoolean(
                keyName,
                if (defaultValue is Boolean) defaultValue else false
            )
            else -> throw IllegalArgumentException("sharedPreferences 类型错误")
        } as T
    }
}