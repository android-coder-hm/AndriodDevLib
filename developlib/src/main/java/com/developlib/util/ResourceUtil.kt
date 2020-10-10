package com.developlib.util

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.developlib.ConfigSingleton.getAppContext

/**
 * 加载资源的工具
 */

/**
 * 获取字符串资源
 * @param stringResId 字符资源ID
 */
fun getStringRes(@StringRes stringResId: Int): String = getAppContext().getString(stringResId)


/**
 * 获取颜色资源
 * @param colorResId 颜色资源ID
 */
fun getColorRes(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(getAppContext(), colorResId)
}

/**
 * 获取drawable资源
 * @param drawableRes drawable资源ID
 */
fun getDrawableRes(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(getAppContext(), drawableRes)
}
