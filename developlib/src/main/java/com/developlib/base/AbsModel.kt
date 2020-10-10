package com.developlib.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 模型层抽象 封装协程操作
 */
open class AbsModel {
    suspend fun <T> launch(bloc: () -> T): T {
        return withContext(Dispatchers.IO) {
            bloc()
        }
    }
}