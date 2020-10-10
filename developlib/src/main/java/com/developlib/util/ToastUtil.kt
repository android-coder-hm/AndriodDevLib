package com.developlib.util

import android.widget.Toast
import com.developlib.ConfigSingleton

/**
 * 显示Toast的工具
 */

/**
 * 显示短时间toast
 */
fun showToastShort(msg: String) {
    showToast(msg, Toast.LENGTH_SHORT)
}

/**
 * 显示长时间toast
 */
fun showToastLong(msg: String) {
    showToast(msg, Toast.LENGTH_LONG)
}

private fun showToast(msg: String, duration: Int) {
    Toast.makeText(ConfigSingleton.getAppContext(), msg, duration).show()
}
