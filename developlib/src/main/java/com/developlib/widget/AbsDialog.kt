package com.developlib.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.developlib.R
import com.developlib.util.getScreenSize

/**
 * 通用dialog框架封装
 */
abstract class AbsDialog(context: Context, style: Int = R.style.CustomDialogStyle) : Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initDialogSize()
        setCancelable(cancelable())
        setCanceledOnTouchOutside(canceledOnTouchOutside())
    }

    private fun initDialogSize() {
        window?.apply {
            val layoutParams = attributes
            layoutParams.width = (getScreenSize().width * getWidthRatio()).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            this.attributes = layoutParams
            setGravity(getGravity())
        }
    }


    /**
     * 获取布局文件
     */
    abstract fun getLayoutId(): Int

    /**
     * 宽度比例
     */
    open fun getWidthRatio(): Float = 0.7F

    /**
     * 显示位置
     */
    open fun getGravity(): Int = Gravity.CENTER

    /**
     * 点击返回是否可关闭
     */
    open fun cancelable(): Boolean = true

    /**
     * 点击外部区域是否可关闭
     */
    open fun canceledOnTouchOutside(): Boolean = true

}