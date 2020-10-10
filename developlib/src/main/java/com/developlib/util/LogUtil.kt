package com.developlib.util

import android.util.Log
import com.developlib.ConfigSingleton

/**
 * 日志记录工具
 * 后期扩展到本地存储
 */

/**
 * 打印调试日志信息 受是否是debug模式控制 release模式下不会打印
 */
fun logInfo(tag: String, msg: String) {
    if (ConfigSingleton.isDebug()) {
        Log.d(tag, msg)
    }
}

/**
 * 打印错误信息 不受控制 完全打印
 */
fun logError(tag: String, msg: String) {
    Log.e(tag, "出错信息:$msg")
}
