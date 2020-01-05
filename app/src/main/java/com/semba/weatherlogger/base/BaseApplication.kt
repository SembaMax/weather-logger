package com.semba.weatherlogger.base

import com.semba.weatherlogger.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by SeMbA on 2020-01-02.
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Building dagger injection.
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}