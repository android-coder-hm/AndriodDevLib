package com.androidlib

import com.developlib.base.AbsActivity
import com.developlib.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbsActivity<MainViewModel>() {

    private companion object {
        private const val TAG = "MainActivity"
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        initDisplayUtilData()
        initViewData()
    }

    private fun initDisplayUtilData() {
        val screenSize = getScreenSize()
        logInfo(TAG, "屏幕宽:${screenSize.width}  高:${screenSize.height}")
        logInfo(TAG, "24dp转换为px:${dp2px(24F)}")
        logInfo(TAG, "48sp转换为px:${sp2px(48F)}")
    }

    private fun initViewData() {
        imgCircle.loadDrawableRes(R.drawable.ic_launcher_background)
        imageNetwork.loadNetImageCircle("https://profile.csdnimg.cn/E/4/F/3_u013210543")
    }
}