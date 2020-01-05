package com.semba.weatherlogger.api

import com.semba.weatherlogger.utils.Constants
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException

/**
 * Created by SeMbA on 2020-01-02.
 */
class MyRetrofitInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        var request = chain!!.request()
        val accept = "application/json"
        val content = "application/json"

        val httpUrl = request.url().newBuilder().addQueryParameter("units","metric").addQueryParameter("APPID", Constants.API_KEY).build()

        request = request.newBuilder()
            .addHeader("Accept",accept)
            .addHeader("Content-Type",content)
            .url(httpUrl)
            .build()

        val bodyMirror = bodyToString(request.body())
        return chain.proceed(request)
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val copy = request
            val buffer = Buffer()
            if (copy != null)
                copy.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}