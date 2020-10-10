package com.developlib.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType


/**
 * 应用此框架的所有activity的基类
 */
abstract class AbsActivity<T : AbsViewModel> : AppCompatActivity() {

    val viewModel: T by lazy {
        val genericSuperclass = javaClass.genericSuperclass
        val parameterizedType = genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments

        @Suppress("UNCHECKED_CAST")
        val viewModelClass: Class<T> = actualTypeArguments[0] as Class<T>
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStackManager.addActivity(this)
        setStatusBar()
        setContentView(getLayoutId())
        initView()
    }

    open fun setStatusBar() {}

    abstract fun getLayoutId(): Int

    open fun initView() {}


    /**
     * 隐藏软键盘
     */
    fun hideKeyboard(editView: EditText) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editView.windowToken, 0)
    }

    /**
     * 显示键盘
     */
    private fun showKeyboard(editView: EditText) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editView, InputMethodManager.SHOW_FORCED)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStackManager.removeActivity(this)
    }
}