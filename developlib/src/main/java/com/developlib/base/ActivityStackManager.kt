package com.developlib.base

import androidx.appcompat.app.AppCompatActivity


/**
 * activity 堆栈管理
 */
object ActivityStackManager {

    private val activities = ArrayList<AppCompatActivity>()

    @JvmStatic
    fun addActivity(activity: AppCompatActivity) {
        activities.add(activity)
    }

    @JvmStatic
    fun removeActivity(activity: AppCompatActivity) {
        if (activities.contains(activity)) {
            activities.remove(activity)
            activity.finish()
        }
    }

    @JvmStatic
    fun getTopActivity(): AppCompatActivity? =
        if (activities.isEmpty()) null else activities[activities.size - 1]

    @JvmStatic
    fun finishAll() {
        for (activity in activities)
            if (!activity.isFinishing) activity.finish()
    }
}