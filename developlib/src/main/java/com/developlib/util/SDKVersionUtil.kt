package com.developlib.util

import android.os.Build

/**
 * 检查Android SDK 版本工具类
 */

/**
 * android 版本是否是R 之后的版本 包含R
 */
fun isSdkAfterAndroidR(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R


/**
 * android 版本是否是Q 之后的版本 包含Q
 */
fun isSdkAfterAndroidQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

