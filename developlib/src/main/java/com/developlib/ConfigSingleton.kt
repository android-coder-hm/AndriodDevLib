/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package com.developlib

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher

/**
 * APP 在整个生命周期运行中的一些必须提前初始化的配置
 */
object ConfigSingleton {
    private lateinit var appContext: Context
    private var isDebug: Boolean = true
    private lateinit var imageLoader: ImageLoader

    fun init(appContext: Context, isDebug: Boolean) {
        ConfigSingleton.appContext = appContext
        ConfigSingleton.isDebug = isDebug
        initImageLoader()
    }

    fun getAppContext(): Context = appContext

    fun getImageLoader(): ImageLoader = imageLoader

    fun isDebug(): Boolean = isDebug

    /**
     * 初始化图片加载引擎
     */
    private fun initImageLoader() {
        imageLoader = ImageLoader.Builder(appContext)
            .componentRegistry {
                add(SvgDecoder(appContext))
                add(VideoFrameFileFetcher(appContext))
                add(VideoFrameUriFetcher(appContext))
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }.build()
    }
}
