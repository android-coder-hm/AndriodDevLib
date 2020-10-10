package com.developlib.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * 界面跳转路由 现在只支持Activity
 * 后期考虑支持  fragment切换
 */

/**
 * 直接跳转至某个界面
 * @param context 上下文
 * @param clazz 需要跳转到的界面
 */
fun launchActivity(context: Context, clazz: Class<out AppCompatActivity>) {
    val intent = Intent(context, clazz)
    context.startActivity(intent)
}


/**
 * 跳转时某个界面 需要界面返回结果
 * @param activity 源界面
 * @param clazz 目标界面
 * @param requestCode 请求码
 */
fun launchActivityForResult(activity: AppCompatActivity, clazz: Class<out AppCompatActivity>, requestCode: Int) {
    val intent = Intent(activity, clazz)
    activity.startActivityForResult(intent, requestCode)
}

/**
 * 携带数据跳转至某个界面
 * @param context 上下文
 * @param bundle 携带的数据
 * @param clazz 目标界面
 */
fun launchActivityWithData(context: Context, bundle: Bundle, clazz: Class<out AppCompatActivity>) {
    val intent = Intent(context, clazz)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

/**
 * 携带数据跳转至某个界面 需要界面返回结果
 * @param activity 源界面
 * @param bundle 携带的数据
 * @param clazz 目标界面
 * @param requestCode 请求码
 */
fun launchActivityWithDataForResult(activity: AppCompatActivity, bundle: Bundle, requestCode: Int, clazz: Class<out AppCompatActivity>) {
    val intent = Intent(activity, clazz)
    intent.putExtras(bundle)
    activity.startActivityForResult(intent, requestCode)
}

