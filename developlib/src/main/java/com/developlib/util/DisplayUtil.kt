/*
 *屏幕显示相关工具类
 */
package com.developlib.util

import android.content.Context
import android.graphics.Point
import android.util.Size
import android.view.WindowManager
import com.developlib.ConfigSingleton


/**
 * dp转换为px
 */
fun dp2px(dpValue: Float): Int {
    val scale = ConfigSingleton.getAppContext().resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


/**
 * 将sp转化为px
 */
fun sp2px(spValue: Float): Int {
    val scale = ConfigSingleton.getAppContext().resources.displayMetrics.density
    return (spValue / scale + 0.5f).toInt()
}

/**
 * 获取屏幕大小
 */
@Suppress("DEPRECATION")
fun getScreenSize(): Size {
    val windowManager =
        ConfigSingleton.getAppContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (isSdkAfterAndroidR()) {
        val bounds = windowManager.currentWindowMetrics.bounds
        Size(bounds.width(), bounds.height())
    } else {
        val display = windowManager.defaultDisplay
        val sizePoint = Point()
        display.getSize(sizePoint)
        Size(sizePoint.x, sizePoint.y)
    }
}