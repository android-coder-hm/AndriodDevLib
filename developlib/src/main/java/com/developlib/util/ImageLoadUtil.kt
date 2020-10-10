package com.developlib.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.api.load
import coil.transform.CircleCropTransformation
import com.developlib.ConfigSingleton
import java.io.File

/**
 * 图片加载工具
 */


/**
 * 加载网络图片
 * @param url 图片地址
 * @param defaultDrawableRes 默认显示图片  在加载失败时会显示
 * @param loadingDrawableRed 在加载过程中显示的图片
 */
fun ImageView.loadNetImage(url: String?, defaultDrawableRes: Int? = null, loadingDrawableRed: Int? = null) {
    this.load(url, ConfigSingleton.getImageLoader()) {
        defaultDrawableRes?.let {
            error(it)
        }
        loadingDrawableRed?.let {
            placeholder(it)
        }
    }
}

/**
 * 加载网络图片 以圆形显示
 * @param url 图片地址
 * @param defaultDrawableRes 默认显示图片  在加载失败时会显示
 * @param loadingDrawableRed 在加载过程中显示的图片
 */
fun ImageView.loadNetImageCircle(url: String?, @DrawableRes defaultDrawableRes: Int? = null, @DrawableRes loadingDrawableRed: Int? = null) {
    this.load(url, ConfigSingleton.getImageLoader()) {
        defaultDrawableRes?.let {
            error(it)
        }
        loadingDrawableRed?.let {
            placeholder(it)
        }
        transformations(CircleCropTransformation())
    }
}

/**
 * 加载资源文件中的图片
 * @param drawableResId 图片资源ID
 */
fun ImageView.loadDrawableRes(@DrawableRes drawableResId: Int) {
    this.load(drawableResId)
}


/**
 * 加载本地图片文件
 *@param filePath 文件路径
 * @param defaultDrawableRes 默认资源文件
 * @param loadingDrawableRed 在过程中的资源文件
 */
fun ImageView.loadLocalImage(filePath: String?, @DrawableRes defaultDrawableRes: Int? = null, @DrawableRes loadingDrawableRed: Int? = null) {
    if (filePath.isNullOrEmpty()) {
        defaultDrawableRes?.let {
            loadDrawableRes(it)
        }
    } else {
        this.load(File(filePath), ConfigSingleton.getImageLoader()) {
            defaultDrawableRes?.let {
                error(it)
            }
            loadingDrawableRed?.let {
                placeholder(it)
            }
            transformations(CircleCropTransformation())
        }
    }
}

/**
 * 加载本地图片文件
 *@param filePath 文件路径
 * @param defaultDrawableRes 默认资源文件
 * @param loadingDrawableRed 在过程中的资源文件
 */
fun ImageView.loadLocalImageCircle(filePath: String?, @DrawableRes defaultDrawableRes: Int? = null, @DrawableRes loadingDrawableRed: Int? = null) {
    if (filePath.isNullOrEmpty()) {
        defaultDrawableRes?.let {
            loadDrawableRes(it)
        }
    } else {
        this.load(File(filePath), ConfigSingleton.getImageLoader()) {
            defaultDrawableRes?.let {
                error(it)
            }
            loadingDrawableRed?.let {
                placeholder(it)
            }
        }
    }
}