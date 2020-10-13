package com.developlib.util

import com.developlib.ConfigSingleton
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 网络请求工具
 * 只做最基本的网络请求 不参与任何业务
 */
private const val TIME_OUT = 60 * 1000L


fun getDefaultOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor { message ->
        logInfo("http", message)
    }
    // 包含header、body数据
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

/**
 * 发送GET 请求
 * @param url 网络请求地址
 * @return 请求返回的数据 或者请求错误抛出异常
 */
fun sendGetRequest(url: String): String = executeRequest(Request.Builder().url(url).build())

/**
 * 发送POST 请求 并且请求数据为json
 * @param url 网络请求地址
 * @param jsonData 请求数据
 */
fun sendPostJsonRequest(url: String, jsonData: String): String {
    val requestBody = jsonData.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder().url(url).post(requestBody).build()
    return executeRequest(request)
}

/**
 * 发送POST表单数据提交
 * @param url 请求地址
 * @param formData 表单数据
 */
fun sendPostFormRequest(url: String, formData: Map<String, String>): String {
    val formBodyBuilder = FormBody.Builder()
    formData.entries.forEach {
        formBodyBuilder.add(it.key, it.value)
    }
    val request = Request.Builder().url(url).post(formBodyBuilder.build()).build()
    return executeRequest(request)
}

/**
 * 文件上传
 * @param url 文件上传地址
 * @param file 需要上传的文件
 */
fun uploadFile(url: String, file: File): String {
    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val multipartBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("file", file.name, requestBody).build()
    val request = Request.Builder().url(url).post(multipartBody).build()
    return executeRequest(request)
}

/**
 * 文件下载
 * @param url 文件下载路径
 * @param saveFile 本地保存的文件
 * @param downloadListener 下载进度监听 不需要时可以不传
 */
fun downloadFile(url: String, saveFile: File, downloadListener: ((downloadedSize: Int, totalSize: Int) -> Unit)? = null) {
    val request = Request.Builder().url(url).build()
    val response = ConfigSingleton.getOkHttpClient().newCall(request).execute()
    val contentLength = response.body?.contentLength() ?: 100L
    val inputStream = response.body?.byteStream()
    inputStream?.let {
        val fileOutputStream = saveFile.outputStream()
        val buffer = ByteArray(1024)
        var len = it.read(buffer)
        while (-1 != len) {
            fileOutputStream.write(buffer, 0, len)
            len = it.read(buffer)
            downloadListener?.let {
                it(len, contentLength.toInt())
            }
        }
        it.close()
        fileOutputStream.close()
    }
}


private fun executeRequest(request: Request): String {
    val response = ConfigSingleton.getOkHttpClient().newCall(request).execute()
    if (response.isSuccessful) {
        return response.body?.string() ?: throw HttpDataException()
    } else {
        throw HttpException(response.code.toString(), response.message)
    }
}

/**
 * 网络异常
 */
class HttpException(val code: String, httpExceptionMsg: String) : Exception(httpExceptionMsg)

/**
 * 原始网络返回数据解析异常
 */
class HttpDataException : Exception()