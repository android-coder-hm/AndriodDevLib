package com.developlib.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developlib.util.isNetworkConnect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * viewModel层封装
 */
abstract class AbsViewModel : ViewModel() {

    val showLoadingLiveData = MutableLiveData<Boolean>()
    val netWorkErrorLiveData = MutableLiveData<Boolean>()

    fun wrapExecute(showLoading: Boolean = true, netWorkErrorFun: (() -> Boolean)? = null, errorFun: ((e: Exception) -> Unit)? = null, bloc: suspend CoroutineScope.() -> Unit) {
        netWorkErrorFun?.let {
            if (isNetworkConnect()) {
                launch(showLoading, errorFun, bloc)
            } else {
                netWorkErrorLiveData.value = true
            }
            return
        }
        launch(showLoading, errorFun, bloc)
    }

    private fun launch(showLoading: Boolean = true, errorFun: ((e: Exception) -> Unit)? = null, bloc: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                changeLoadingStatus(showLoading, true)
                bloc()
                changeLoadingStatus(showLoading, false)
            } catch (e: Exception) {
                changeLoadingStatus(showLoading, false)
                errorFun?.let {
                    it(e)
                }
            }
        }
    }

    private fun changeLoadingStatus(needShowLoading: Boolean, isShow: Boolean) {
        if (needShowLoading) {
            showLoadingLiveData.value = isShow
        }
    }
}