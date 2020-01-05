package com.semba.weatherlogger.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.semba.weatherlogger.api.ApiService
import com.semba.weatherlogger.api.Config
import com.semba.weatherlogger.api.MyRetrofitInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-02.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setDateFormat("yyyy-M-dd hh:mm:ss").setLenient().create()

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient
    {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(MyRetrofitInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Config.host)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}